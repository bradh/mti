package mti.commons.elasticsearch.dao.mti;

import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;

import mti.commons.elasticsearch.dao.ElasticsearchBasicDAO;
import mti.commons.model.Sensor;

public class ElasticsearchSensorDAO extends ElasticsearchBasicDAO<Sensor> {

	public static String index = "gmti_static";
	public static String type = "gmti_sensor";
	
	public ElasticsearchSensorDAO() {
		super(
			index,
			type,
			Sensor.class);
	}
	
	public List<Sensor> findAll()
	{
		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(index).setTypes(type)
				.setQuery(QueryBuilders.matchAllQuery());
		
		List<Sensor> sensors = template.queryForList(searchQuery, Sensor.class);
		
		return sensors;
	} 
	
	public Sensor findOneByPlatformIdsContains(String platformID)
	{
		SearchRequestBuilder searchQuery = template.NativeSearchQueryBuilder().setIndices(index).setTypes(type)
				.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilders.termFilter("platformIds", platformID)));
		
		Sensor sensor = template.queryForOne(searchQuery, Sensor.class);
		
		return sensor;
	}
}
