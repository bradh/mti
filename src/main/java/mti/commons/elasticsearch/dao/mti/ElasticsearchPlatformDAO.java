package mti.commons.elasticsearch.dao.mti;

import mti.commons.elasticsearch.dao.ElasticsearchBasicDAO;
import mti.commons.model.Platform;

public class ElasticsearchPlatformDAO extends
	ElasticsearchBasicDAO<Platform>
{

	public ElasticsearchPlatformDAO() {
		super(
			"gmti_static",
			"gmti_platform",
			Platform.class);
	}

}
