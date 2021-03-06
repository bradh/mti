package mti.commons.elasticsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NativeElasticsearchTemplate
{

	private final Logger log = LoggerFactory.getLogger(
		this.getClass());
	private Client client;
	private ObjectMapper mapper;

	public NativeElasticsearchTemplate(
		Client client,
		ObjectMapper mapper ) {
		this.client = client;
		this.mapper = mapper;
	}

	public boolean createIndex(
		String indexName ) {
		CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(
			indexName);
		CreateIndexResponse response = createIndexRequestBuilder.execute().actionGet();

		return response.isAcknowledged();
	}

	public boolean createAlias(
		String indexName,
		String aliasName ) {

		IndicesAliasesResponse createAliasResponse = client.admin().indices().prepareAliases().addAlias(
			indexName,
			aliasName).execute().actionGet();

		return createAliasResponse.isAcknowledged();
	}

	public boolean indexExists(
		String indexName ) {
		return client.admin().indices().prepareExists(
			indexName).execute().actionGet().isExists();
	}

	public <T extends ESModel> boolean index(
		String indexName,
		String type,
		T o ) {
		boolean created = false;

		try {
			String source = mapper.writeValueAsString(
				o);
			IndexResponse response = client
				.prepareIndex(
					indexName,
					type,
					o.getId())
				.setSource(
					source)
				.get();
			created = response.isCreated();

			if (created) {
				o.setId(
					response.getId());
			}
		}
		catch (Exception e) {
			log.error(
				e.getMessage(),
				e);
		}

		return created;
	}

	public <T extends ESModel> void bulkIndex(
		String indexName,
		String type,
		Collection<T> objects ) {
		try {
			BulkRequestBuilder bulkRequest = client.prepareBulk();

			for (T object : objects) {
				String source = mapper.writeValueAsString(
					object);
				bulkRequest.add(
					client.prepareIndex(
						indexName,
						type,
						object.getId()).setSource(
							source));
			}

			BulkResponse response = bulkRequest.execute().get();

			// not so friendly way of allowing collections
			// unfortunately collection does not implement get(index)
			if (objects instanceof List) {
				List<T> l = (List<T>) objects;

				for (BulkItemResponse item : response.getItems()) {
					if (!item.isFailed()) {
						l.get(
							item.getItemId()).setId(
								item.getId());
					}
				}
			}

		}
		catch (Exception e) {
			log.error(
				e.getMessage(),
				e);
		}
	}

	public <T extends ESModel> List<T> queryForList(
		SearchRequestBuilder searchQuery,
		Class<T> clazz ) {

		SearchResponse response = searchQuery.setSize(
			Integer.MAX_VALUE).execute().actionGet();

		List<T> results = new ArrayList<T>(
			response.getHits().getHits().length);

		for (int i = 0; i < response.getHits().getHits().length; ++i) {
			try {
				SearchHit hit = response.getHits().getHits()[i];
				T result = mapper.readValue(
					hit.getSourceAsString(),
					clazz);
				result.setId(
					hit.getId());
				results.add(
					result);
			}
			catch (Exception e) {
				log.error(
					e.getMessage(),
					e);
			}
		}

		return results;
	}

	public <T extends ESModel> List<T> queryForPage(
		SearchRequestBuilder searchQuery,
		int pageNumber,
		int pageSize,
		Class<T> clazz ) {

		SearchResponse response = searchQuery
			.setFrom(
				pageNumber * pageSize)
			.setSize(
				pageSize)
			.execute()
			.actionGet();

		List<T> results = new ArrayList<T>(
			response.getHits().getHits().length);

		for (int i = 0; i < response.getHits().getHits().length; ++i) {
			try {
				SearchHit hit = response.getHits().getHits()[i];
				T result = mapper.readValue(
					hit.getSourceAsString(),
					clazz);
				result.setId(
					hit.getId());
				results.add(
					result);
			}
			catch (Exception e) {
				log.error(
					e.getMessage(),
					e);
			}
		}

		results.removeAll(
			Arrays.asList(
				"",
				null));

		return results;
	}

	public <T extends ESModel> T findOne(
		String index,
		String type,
		String id,
		Class<T> clazz ) {

		T result = null;
		try {
			GetResponse response = client.prepareGet(
				index,
				type,
				id).execute().get();
			if (response.isExists()) {
				result = mapper.readValue(
					response.getSourceAsBytes(),
					clazz);
				result.setId(
					response.getId());
			}
		}
		catch (Exception e) {
			log.error(
				e.getMessage(),
				e);
		}

		return result;
	}

	public <T extends ESModel> T queryForOne(
		SearchRequestBuilder searchQuery,
		Class<T> clazz ) {
		SearchResponse response = searchQuery.execute().actionGet();

		T result = null;

		try {
			if (response.getHits().getTotalHits() > 0L) {
				result = mapper.readValue(
					response.getHits().getHits()[0].getSourceAsString(),
					clazz);
				result.setId(response.getHits().getHits()[0].getId());
			}
		}
		catch (Exception e) {
			log.error(
				e.getMessage(),
				e);
		}

		return result;
	}

	public long count(
		SearchRequestBuilder searchQuery ) {

		SearchResponse response = searchQuery.setSearchType(
			SearchType.COUNT).execute().actionGet();

		return response.getHits().getTotalHits();
	}

	public SearchRequestBuilder NativeSearchQueryBuilder() {
		return new SearchRequestBuilder(
			this.client);
	}

	public <T extends ESModel> boolean delete(
		String index,
		String type,
		T instance ) {
		boolean result = false;

		try {
			DeleteResponse response = client.prepareDelete(
				index,
				type,
				instance.getId()).execute().get();
			result = response.isFound();
		}
		catch (Exception e) {
			log.error(
				e.getMessage(),
				e);
		}

		return result;
	}

	public <T extends ESModel> int delete(
		String index,
		String type,
		Collection<T> objects ) {
		int result = 0;

		try {
			// DeleteResponse response = client.prepareDelete(index, type,
			// instance.getId()).execute().get();

			BulkRequestBuilder bulkRequest = client.prepareBulk();

			for (T object : objects) {
				bulkRequest.add(
					client.prepareDelete(
						index,
						type,
						object.getId()));
			}

			BulkResponse response = bulkRequest.execute().get();

			if (response.hasFailures()) {
				for (BulkItemResponse item : response.getItems()) {
					if (!item.isFailed()) ++result;
				}
			}
			else {
				result = objects.size();
			}

		}
		catch (Exception e) {
			log.error(
				e.getMessage(),
				e);
		}

		return result;
	}

	public boolean refresh(
		String index ) {
		boolean success = false;

		try {
			RefreshResponse result = client.admin().indices().prepareRefresh(
				index).execute().get();
			success = (result.getShardFailures().length == 0);
		}
		catch (Exception e) {
			log.error(
				e.getMessage(),
				e);
		}

		return success;
	}
}
