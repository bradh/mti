package mti.commons.elasticsearch.dao.mti;

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
import mti.commons.model.TargetReport;
import mti.commons.partitions.PartitionManager;
import mti.commons.repositories.filters.GeoFilter;
import mti.commons.repositories.filters.LimdisFilter;
import mti.commons.repositories.filters.TimeFilter;

public class ElasticsearchDotDAO extends
	ElasticsearchPartitionedDAO<TargetReport>
{

	public static String sortBy = "dwellDateTime";

	public ElasticsearchDotDAO(
		PartitionManager partitionManager ) {
		super(
			partitionManager,
			"gmti_targetReport",
			TargetReport.class);
	}

	public TargetReport findOneByDotUID(
		Long dotUID ) {
		FilterBuilder preFilter = FilterBuilders.termFilter(
			"dotUID",
			dotUID);

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

		TargetReport t = template.queryForOne(
			searchQuery,
			TargetReport.class);

		return t;
	}

	public List<TargetReport> findByMissionUIDAndDotMask(
		Long missionUID,
		Short mask,
		int pageNumber,
		int pageSize ) {

		FilterBuilder preFilter = FilterBuilders.termFilter(
			"missionUID",
			missionUID);

		SearchRequestBuilder searchQuery = template
			.NativeSearchQueryBuilder()
			.setIndices(
				partitionManager.getAlias())
			.setTypes(
				documentType)
			.addSort(
				sortBy,
				SortOrder.ASC)
			.setQuery(
				QueryBuilders.filteredQuery(
					QueryBuilders.matchAllQuery(),
					preFilter))
			.setPostFilter(
				LimdisFilter.getDotMaskFilter(
					mask));

		List<TargetReport> dots = template.queryForPage(
			searchQuery,
			pageNumber,
			pageSize,
			TargetReport.class);

		return dots;
	}

	public List<TargetReport> getDotsByPage(
		Date start,
		Date end,
		List<Geometry> geometries,
		Short mask,
		FilterBuilder dotFilter,
		int pageNumber,
		int pageSize ) {
		String[] partitions = partitionManager.getPartitions(
			start,
			end);
		if (partitions.length == 0) return Collections.emptyList();

		AndFilterBuilder preFilter = FilterBuilders.andFilter(
			TimeFilter.getTimeRangeFilter(
				"dwellDateTime",
				start,
				end));

		if (dotFilter != null) preFilter.add(
			dotFilter);

		AndFilterBuilder postFilter = FilterBuilders.andFilter(
			LimdisFilter.getDotMaskFilter(
				mask));

		if (geometries != null && !geometries.isEmpty()) postFilter.add(
			GeoFilter.getGeometryFilter(
				"targetLocation",
				geometries));

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
					preFilter))
			.setPostFilter(
				postFilter);

		List<TargetReport> dots = template.queryForPage(
			searchQuery,
			pageNumber,
			pageSize,
			TargetReport.class);

		return dots;

	}

	public List<TargetReport> getDotsByList(
		Long missionUID,
		Long dwellUID,
		Date start,
		Date end,
		List<Geometry> geometries,
		Short mask,
		FilterBuilder dotFilter ) {
		String[] partitions = partitionManager.getPartitions(
			start,
			end);
		if (partitions.length == 0) return Collections.emptyList();

		AndFilterBuilder preFilter = FilterBuilders.andFilter(
			FilterBuilders.termFilter(
				"missionUID",
				missionUID),
			FilterBuilders.termFilter(
				"dwellUID",
				dwellUID),
			TimeFilter.getTimeRangeFilter(
				"dwellDateTime",
				start,
				end));

		if (dotFilter != null) preFilter.add(
			dotFilter);

		AndFilterBuilder postFilter = FilterBuilders.andFilter(
			LimdisFilter.getDotMaskFilter(
				mask));

		if (geometries != null && !geometries.isEmpty()) postFilter.add(
			GeoFilter.getGeometryFilter(
				"targetLocation",
				geometries));

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
					preFilter))
			.setPostFilter(
				postFilter);

		List<TargetReport> dots = template.queryForList(
			searchQuery,
			TargetReport.class);

		return dots;
	}

	public long countDots(
		Long missionUID,
		Date start,
		Date end,
		List<Geometry> geometries,
		FilterBuilder dotFilter ) {
		String[] partitions = partitionManager.getPartitions(
			start,
			end);
		if (partitions.length == 0) return 0L;

		AndFilterBuilder preFilter = FilterBuilders.andFilter(
			TimeFilter.getTimeRangeFilter(
				"dwellDateTime",
				start,
				end));

		if (missionUID != null) preFilter.add(
			FilterBuilders.termFilter(
				"missionUID",
				missionUID));

		if (dotFilter != null) preFilter.add(
			dotFilter);

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
				"targetLocation",
				geometries));

		long dotCount = template.count(
			searchQuery);

		return dotCount;
	}
}
