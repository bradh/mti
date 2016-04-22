package mti.commons.elasticsearch.dao.track;

import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.vividsolutions.jts.geom.Geometry;

import mti.commons.elasticsearch.dao.ElasticsearchPartitionedDAO;
import mti.commons.model.track.Track;
import mti.commons.partitions.PartitionManager;
import mti.commons.repositories.filters.GeoFilter;
import mti.commons.repositories.filters.TimeFilter;

public class ElasticsearchTrackDAO extends ElasticsearchPartitionedDAO<Track> {

	private static final String SORT_BY = "startTime";

	public ElasticsearchTrackDAO(PartitionManager partitionManager) {
		super(partitionManager, "track", Track.class);
	}

	public List<Track> findAllByTrackSetUuid(String trackSetUuid, int pageNumber, int pageSize) {

		FilterBuilder preFilter = FilterBuilders.termFilter("trackSetUuid", trackSetUuid);

		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(indexTemplate)
				.setTypes(documentType).setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), preFilter))
				.addSort(SORT_BY, SortOrder.ASC);

		return template.queryForPage(searchQuery, pageNumber, pageSize, modelClass);
	}

	public List<Track> findAllMatching(Date start, Date end, List<Geometry> geometries, int pageNumber, int pageSize) {

		String[] partitions = partitionManager.getPartitions(start, end);
		FilterBuilder preFilter = TimeFilter.getTimeRangeFilter("startTime", "stopTime", start, end);

		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(partitions)
				.setTypes(documentType).setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), preFilter))
				.addSort(SORT_BY, SortOrder.ASC);

		if (geometries != null && !geometries.isEmpty())
			searchQuery.setPostFilter(GeoFilter.getGeometryFilter("path", geometries));

		return template.queryForPage(searchQuery, pageNumber, pageSize, modelClass);
	}

	public long countAllMatching(Date start, Date end, List<Geometry> geometries) {

		String[] partitions = partitionManager.getPartitions(start, end);
		FilterBuilder preFilter = TimeFilter.getTimeRangeFilter("startTime", "stopTime", start, end);

		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(partitions)
				.setTypes(documentType)
				.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), preFilter));

		if (geometries != null && !geometries.isEmpty())
			searchQuery.setPostFilter(GeoFilter.getGeometryFilter("path", geometries));

		return template.count(searchQuery);
	}
}
