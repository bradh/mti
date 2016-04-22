package mti.commons.elasticsearch.dao.mti;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermFilterBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.vividsolutions.jts.geom.Geometry;

import mti.commons.elasticsearch.dao.ElasticsearchPartitionedDAO;
import mti.commons.model.Job;
import mti.commons.partitions.PartitionManager;
import mti.commons.repositories.filters.GeoFilter;
import mti.commons.repositories.filters.TimeFilter;

public class ElasticsearchJobDAO extends ElasticsearchPartitionedDAO<Job> {

	public static String DEFAULT_SORT_BY = "dwellStartDateTime";

	public ElasticsearchJobDAO(PartitionManager partitionManager) {
		super(
			partitionManager,
			"gmti_job",
			Job.class);
	}
	
	public Job findOneByJobDefinitionUID(Long jobDefinitionUID)
	{
		FilterBuilder preFilter = FilterBuilders.termFilter("jobDefinitionUID", jobDefinitionUID);

		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(partitionManager.getAlias()).setTypes(documentType)
				.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), preFilter));

		Job j = template.queryForOne(searchQuery, Job.class);

		return j;
	}
	
	public List<Job> pageByMissionUID(Long missionUID, int pageNumber, int pageSize, String sortBy)
	{
		TermFilterBuilder preFilter = FilterBuilders.termFilter("missionUID", missionUID);

		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(partitionManager.getAlias()).setTypes(documentType)
				.addSort(sortBy, SortOrder.ASC)
				.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), preFilter));

		List<Job> jobs = template.queryForPage(searchQuery, pageNumber, pageSize, Job.class);

		return jobs;
	}
	
	public List<Job> getJobsByPage(Long missionUID, Date start, Date end, List<Geometry> geometries, int pageNumber, int pageSize)
	{
		String[] partitions = partitionManager.getPartitions(start, end);
		if (partitions.length == 0)
			return Collections.emptyList();
		
		AndFilterBuilder preFilter = FilterBuilders
				.andFilter(TimeFilter.getTimeRangeFilter("dwellStartDateTime", "dwellStopDateTime", start, end));

		if (missionUID != null)
			preFilter.add(FilterBuilders.termFilter("missionUID", missionUID));

		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(partitions).setTypes(documentType)
				.addSort(DEFAULT_SORT_BY, SortOrder.ASC)
				.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), preFilter));

		if (geometries != null && !geometries.isEmpty())
			searchQuery.setPostFilter(GeoFilter.getGeometryFilter("boundingArea", geometries));

		List<Job> jobs = template.queryForPage(searchQuery, pageNumber, pageSize, Job.class);

		return jobs;
	}
	
	public List<Job> getJobsByList(Long missionUID, Date start, Date end, List<Geometry> geometries)
	{
		String[] partitions = partitionManager.getPartitions(start, end);
		if (partitions.length == 0)
			return Collections.emptyList();
		
		AndFilterBuilder preFilter = FilterBuilders
				.andFilter(TimeFilter.getTimeRangeFilter("dwellStartDateTime", "dwellStopDateTime", start, end));

		if (missionUID != null)
			preFilter.add(FilterBuilders.termFilter("missionUID", missionUID));

		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(partitions).setTypes(documentType)
				.addSort(DEFAULT_SORT_BY, SortOrder.ASC)
				.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), preFilter));

		if (geometries != null && !geometries.isEmpty())
			searchQuery.setPostFilter(GeoFilter.getGeometryFilter("boundingArea", geometries));

		List<Job> jobs = template.queryForList(searchQuery, Job.class);

		return jobs;
	}
}