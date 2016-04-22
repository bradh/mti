package mti.commons.elasticsearch.dao.mti;

import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;

import com.vividsolutions.jts.geom.Geometry;

import mti.commons.elasticsearch.dao.ElasticsearchBasicDAO;
import mti.commons.model.Cocom;
import mti.commons.repositories.filters.GeoFilter;

public class ElasticsearchCocomDAO extends
	ElasticsearchBasicDAO<Cocom>
{
	public static String index = "gmti_static";
	public static String type = "gmti_cocom";

	public ElasticsearchCocomDAO() {
		super(
			index,
			type,
			Cocom.class);
	}

	public List<Cocom> getCocoms(
		List<Geometry> geometries ) {

		SearchRequestBuilder searchQuery = template
			.NativeSearchQueryBuilder()
			.setIndices(
				index)
			.setTypes(
				type)
			.setQuery(
				QueryBuilders.matchAllQuery())
			.setPostFilter(
				FilterBuilders.nestedFilter(
					"regions",
					GeoFilter.getGeometryFilter(
						"regions.area",
						geometries)));

		List<Cocom> cocoms = template.queryForList(
			searchQuery,
			Cocom.class);

		return cocoms;
	}
}
