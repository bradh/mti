#!/bin/bash
ELASTICSEARCH=$1

#Instantiate ID Gen
curl -XPUT "$ELASTICSEARCH/idgen" -d @mappings/idgen_mapping.json
curl -XPUT "$ELASTICSEARCH/idgen/id/1" -d @static_json/idgen/id.json

#Instantiate the GMTI models
curl -XDELETE "$ELASTICSEARCH/gmti*"
curl -XPUT "$ELASTICSEARCH/_template/gmti_template" -d @mappings/elasticsearch_gmti_template.json
curl -XPUT "$ELASTICSEARCH/gmti_mapping" -d @mappings/elasticsearch_gmti_template.json
curl -XPUT "$ELASTICSEARCH/gmti_mapping/_alias/gmti"

#Instantiate the Track models
curl -XDELETE "$ELASTICSEARCH/track*"
curl -XPUT "$ELASTICSEARCH/_template/track_template" -d @mappings/elasticsearch_track_template.json
curl -XPUT "$ELASTICSEARCH/track_mapping" -d @mappings/elasticsearch_track_template.json
curl -XPUT "$ELASTICSEARCH/track_mapping/_alias/track"

#Instantiate gmti_static index
curl -XPUT "$ELASTICSEARCH/gmti_static" -d @mappings/gmti_static_mapping.json

#Instantiate partitions index
curl -XDELETE "$ELASTICSEARCH/partitions"
curl -XPUT "$ELASTICSEARCH/partitions" -d @mappings/partition_mapping.json

#Instantiate partitions index
curl -XDELETE "$ELASTICSEARCH/limdis"
curl -XPUT "$ELASTICSEARCH/limdis" -d @mappings/restricted_region_mapping.json

#Load gmti_static from static_json
(cd static_json/cocoms && ./cocom_push.sh $ELASTICSEARCH)
(cd static_json/platforms && ./platform_push.sh $ELASTICSEARCH)
(cd static_json/sensors && ./sensor_push.sh $ELASTICSEARCH)
(cd static_json/restricted_regions && ./restricted_region_push.sh $ELASTICSEARCH)