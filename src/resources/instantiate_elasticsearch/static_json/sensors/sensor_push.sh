#!/bin/bash
ELASTICSEARCH=$1
INDEX=gmti_static
TYPE=gmti_sensor

for filename in *.json; do
        SENSOR=`basename $filename .json`
        echo "Uploading $SENSOR"
        curl -XPUT "$ELASTICSEARCH/$INDEX/$TYPE/$SENSOR" -d @$filename
done
