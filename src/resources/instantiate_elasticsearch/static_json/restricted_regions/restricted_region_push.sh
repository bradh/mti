#!/bin/bash
ELASTICSEARCH=$1
INDEX=limdis
TYPE=restricted_region

for filename in *.json; do
	RESTRICTED_REGION=`basename $filename .json`
    echo "Uploading $RESTRICTED_REGION"
    curl -XPUT "$ELASTICSEARCH/$INDEX/$TYPE/$RESTRICTED_REGION" -d @$filename
done
