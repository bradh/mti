package mti.commons.elasticsearch.dao.mti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.vividsolutions.jts.geom.Geometry;

import mti.commons.elasticsearch.dao.ElasticsearchPartitionedDAO;
import mti.commons.model.Mission;
import mti.commons.partitions.PartitionManager;
import mti.commons.repositories.filters.GeoFilter;
import mti.commons.repositories.filters.TimeFilter;

public class ElasticsearchMissionDAO extends
	ElasticsearchPartitionedDAO<Mission>
{
	public static String sortBy = "startTime";

	public ElasticsearchMissionDAO(
		PartitionManager partitionManager ) {
		super(
			partitionManager,
			"gmti_mission",
			Mission.class);
	}

	public Mission findOneByMissionUID(
		Long missionUID ) {
		FilterBuilder preFilter = FilterBuilders.termFilter(
			"missionUID",
			missionUID);

		SearchRequestBuilder searchQuery = template
			.NativeSearchQueryBuilder()
			.setIndices(
				partitionManager.getAlias())
			.setTypes(
				documentType)
			.setQuery(
				QueryBuilders.filteredQuery(
					QueryBuilders.matchAllQuery(),
					preFilter));

		Mission m = template.queryForOne(
			searchQuery,
			Mission.class);

		return m;
	}

	public List<Mission> getMissionsByPage(
		List<String> platformId,
		Date start,
		Date end,
		List<Geometry> geometries,
		int pageNumber,
		int pageSize ) {
		String[] partitions = partitionManager.getPartitions(
			start,
			end);
		if (partitions.length == 0) return Collections.emptyList();

		AndFilterBuilder preFilter = FilterBuilders.andFilter(
			TimeFilter.getTimeRangeFilter(
				"startTime",
				"stopTime",
				start,
				end));

		if (platformId != null && !platformId.isEmpty()) preFilter.add(
			FilterBuilders.inFilter(
				"platformId",
				platformId));

		SearchRequestBuilder searchQuery = template
			.NativeSearchQueryBuilder()
			.setIndices(
				partitions)
			.setTypes(
				documentType)
			.addSort(
				sortBy,
				SortOrder.ASC)
			.setQuery(
				QueryBuilders.filteredQuery(
					QueryBuilders.matchAllQuery(),
					preFilter));

		if (geometries != null && !geometries.isEmpty()) searchQuery.setPostFilter(
			GeoFilter.getGeometryFilter(
				"boundingArea",
				geometries));

		List<Mission> missions = template.queryForPage(
			searchQuery,
			pageNumber,
			pageSize,
			Mission.class);

		return missions;
	}

	public List<Mission> getMissionsByList(
		List<String> platformId,
		Date start,
		Date end,
		List<Geometry> geometries ) {
		String[] partitions = partitionManager.getPartitions(
			start,
			end);
		if (partitions.length == 0) return Collections.emptyList();

		AndFilterBuilder preFilter = FilterBuilders.andFilter(
			TimeFilter.getTimeRangeFilter(
				"startTime",
				"stopTime",
				start,
				end));

		if (platformId != null && !platformId.isEmpty()) preFilter.add(
			FilterBuilders.inFilter(
				"platformId",
				platformId));

		SearchRequestBuilder searchQuery = template
			.NativeSearchQueryBuilder()
			.setIndices(
				partitions)
			.setTypes(
				documentType)
			.addSort(
				sortBy,
				SortOrder.ASC)
			.setQuery(
				QueryBuilders.filteredQuery(
					QueryBuilders.matchAllQuery(),
					preFilter));

		if (geometries != null && !geometries.isEmpty()) searchQuery.setPostFilter(
			GeoFilter.getGeometryFilter(
				"boundingArea",
				geometries));

		List<Mission> missions = template.queryForList(
			searchQuery,
			Mission.class);

		return missions;
	}

	public long countMissions(
		List<String> platformId,
		Date start,
		Date end,
		List<Geometry> geometries ) {

		String[] partitions = partitionManager.getPartitions(
			start,
			end);
		if (partitions.length == 0) return 0L;

		long count = 0;

		AndFilterBuilder preFilter = FilterBuilders.andFilter(
			TimeFilter.getTimeRangeFilter(
				"startTime",
				"stopTime",
				start,
				end));

		if (platformId != null && !platformId.isEmpty()) preFilter.add(
			FilterBuilders.inFilter(
				"platformId",
				platformId));

		SearchRequestBuilder searchQuery = template
			.NativeSearchQueryBuilder()
			.setIndices(
				partitions)
			.setTypes(
				documentType)
			.setQuery(
				QueryBuilders.filteredQuery(
					QueryBuilders.matchAllQuery(),
					preFilter));

		if (geometries != null && !geometries.isEmpty()) searchQuery.setPostFilter(
			GeoFilter.getGeometryFilter(
				"boundingArea",
				geometries));

		count = template.count(
			searchQuery);

		return count;
	}

	public List<Long> getMissionUIDs(
		List<String> platformId,
		Date start,
		Date end,
		List<Geometry> geometries ) {

		String[] partitions = partitionManager.getPartitions(
			start,
			end);
		if (partitions.length == 0) return Collections.emptyList();

		AndFilterBuilder preFilter = FilterBuilders.andFilter(
			TimeFilter.getTimeRangeFilter(
				"startTime",
				"stopTime",
				start,
				end));

		if (platformId != null && !platformId.isEmpty()) preFilter.add(
			FilterBuilders.inFilter(
				"platformId",
				platformId));

		SearchRequestBuilder searchQuery = template
			.NativeSearchQueryBuilder()
			.setIndices(
				partitions)
			.setTypes(
				documentType)
			.addSort(
				sortBy,
				SortOrder.ASC)
			.setQuery(
				QueryBuilders.filteredQuery(
					QueryBuilders.matchAllQuery(),
					preFilter));

		if (geometries != null && !geometries.isEmpty()) searchQuery.setPostFilter(
			GeoFilter.getGeometryFilter(
				"boundingArea",
				geometries));

		List<Mission> missions = template.queryForList(
			searchQuery,
			Mission.class);

		List<Long> missionUIDs = new ArrayList<Long>(
			missions.size());

		for (Mission mission : missions)
			missionUIDs.add(
				mission.getMissionUID());

		return missionUIDs;
	}
}
