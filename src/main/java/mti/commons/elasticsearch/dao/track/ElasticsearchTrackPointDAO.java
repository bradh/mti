package mti.commons.elasticsearch.dao.track;

import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;

import mti.commons.elasticsearch.dao.ElasticsearchPartitionedDAO;
import mti.commons.model.track.TrackPoint;
import mti.commons.partitions.PartitionManager;

public class ElasticsearchTrackPointDAO extends ElasticsearchPartitionedDAO<TrackPoint> {

	private static final String SORT_BY = "time";

	public ElasticsearchTrackPointDAO(PartitionManager partitionManager) {
		super(partitionManager, "track_point", TrackPoint.class);
	}

	public List<TrackPoint> findAllByTrackUuidOrderByTimeAsc(String trackUuid) {
		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(indexTemplate)
				.setTypes(documentType).setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(),
						FilterBuilders.termFilter("trackUuid", trackUuid)))
				.addSort(SORT_BY, SortOrder.ASC);
		return template.queryForList(searchQuery, modelClass);
	}

}
