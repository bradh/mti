#!/bin/bash

IN_FILE="$1"
OUT_FILE="$1".csv

echo "################################"
echo "##   Processing Ingest Log, Please wait . . ."

if [ -f $IN_FILE ]
then

 while read line;
  do
    #ingest file
      echo $line | grep -e '.4607' | grep -v '1343' | grep -e "Ingesting file"| awk '{gsub(",",".",$1);gsub("\047","",$8);print $1,","$6,$7,",",$8,",",$1,",,"}' >> $OUT_FILE

    #ingesting gmti file
       echo $line | grep -e '.4607' | grep -v '1343' | grep -e "Ingesting GMTI file"| awk '{gsub(",",".",$1);gsub("\047","",$9);print $1,","$6,$7,$8",",$9,",,,"}' >> $OUT_FILE

    # now 100
    echo $line | grep -e "now 100" | awk '{gsub(",",".",$1); gsub(/[ \t]+$/, "", $1); gsub(/[ \t]+$/, "", $11); print $1,","$6,$7,$8,$9,$10,$11,",,,,"}' >> $OUT_FILE

    #push to Elastic Search "PostgreSQL push to Elasticsearch has been completed.
     echo $line | grep -e "PostgreSQL push" |  awk '{gsub(",",".",$1);print $1,","$6,$7,$8,$9,$10,$11,$12",,,,",$18}' >> $OUT_FILE

    #time spent ingest
      echo $line | grep -e "Time spent ingesting" | awk '{gsub(",",".",$1);gsub("\047","",$11);print $1,","$6,$7,$8,$9,$10",",$11,",,"$1,",",$13}' >> $OUT_FILE

    done < $IN_FILE
fi

echo "##"
echo "##"   $OUT_FILE created.
echo "##"
echo "###############################"