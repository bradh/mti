package mti.commons.elasticsearch.dao.track;

import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;

import mti.commons.elasticsearch.dao.ElasticsearchPartitionedDAO;
import mti.commons.model.track.TrackSet;
import mti.commons.partitions.PartitionManager;

public class ElasticsearchTrackSetDAO extends ElasticsearchPartitionedDAO<TrackSet> {

	private static final String SORT_BY = "starttime";

	public ElasticsearchTrackSetDAO(PartitionManager partitionManager) {
		super(partitionManager, "track_set", TrackSet.class);
	}

	public List<TrackSet> findAllByAlgorithmOrderByTimeAsc(String algorithm) {
		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(indexTemplate)
				.setTypes(documentType).setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(),
						FilterBuilders.termFilter("algorithm", algorithm)))
				.addSort(SORT_BY, SortOrder.ASC);
		return template.queryForList(searchQuery, modelClass);
	}

}
