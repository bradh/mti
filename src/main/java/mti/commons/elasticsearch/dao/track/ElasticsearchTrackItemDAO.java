package mti.commons.elasticsearch.dao.track;

import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;

import mti.commons.elasticsearch.ESPartitionedModel;
import mti.commons.elasticsearch.dao.ElasticsearchPartitionedDAO;
import mti.commons.partitions.PartitionManager;

public class ElasticsearchTrackItemDAO<T extends ESPartitionedModel> extends ElasticsearchPartitionedDAO<T> {

	private static final String SORT_BY = "time";

	public ElasticsearchTrackItemDAO(PartitionManager partitionManager, String documentType, Class<T> clazz) {
		super(partitionManager, documentType, clazz);
	}

	public List<T> findAllByTrackUuid(String trackUuid) {
		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(indexTemplate)
				.setTypes(documentType).setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(),
						FilterBuilders.termFilter("trackUuid", trackUuid)))
				.addSort(SORT_BY, SortOrder.ASC);
		return template.queryForList(searchQuery, modelClass);
	}
}
