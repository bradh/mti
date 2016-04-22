#!/bin/bash
ELASTICSEARCH=$1
INDEX=gmti_static
TYPE=gmti_platform

for filename in *.json; do
        PLATFORM=`basename $filename .json`
        echo "Uploading $PLATFORM"
        curl -XPUT "$ELASTICSEARCH/$INDEX/$TYPE/$PLATFORM" -d @$filename
done
