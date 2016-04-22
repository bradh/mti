#!/bin/bash
ELASTICSEARCH=$1
INDEX=gmti_static
TYPE=gmti_cocom

for filename in *.json; do
	COCOM=`basename $filename .json`
    echo "Uploading $COCOM"
    curl -XPUT "$ELASTICSEARCH/$INDEX/$TYPE/$COCOM" -d @$filename
done
