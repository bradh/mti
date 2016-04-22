#!/bin/bash

if [ $# -lt 2 ];
then
	echo "FATAL: not enough arguments"
	echo "USAGE: $0 infile1 [infile2 ... infileN] outfile"
	exit 1
fi

IN_FILES="${@:1:$# - 1}"
OUT_FILE="${@:$#:1}"

echo "Calculating ingest volume per day..."

echo "YYYY-MM-DD HH:MM - HH:MM,TOTAL_FILES_INGESTED,TOTAL_MILLISECONDS_OF_WORK,TOTAL_BYTES_PROCESSED" > $OUT_FILE

awk -F, '{ if ($4 != "MissionDone") { next }; date=substr($5,0,index($5,"T") - 1) " 00:00 - 23:59";\
		num_files[ date ]++; ttl_bytes[ date ] += $11; ttl_ms[ date ] += $7; }\
	END { for ( key in num_files ) { print key "," num_files[ key ] "," ttl_ms[ key ] "," ttl_bytes[ key ] ; } ; }' $IN_FILES | sort >> $OUT_FILE
