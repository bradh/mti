#!/bin/bash

if [ $# -lt 2 ];
then
	echo "FATAL: not enough arguments"
	echo "USAGE: $0 infile1 [infile2 ... infileN] outfile"
	exit 1
fi

IN_FILES="${@:1:$# - 1}"
OUT_FILE="${@:$#:1}"

echo "Calculating requests per hour..."

echo "YYYY-MM-DD HH:MM - HH:MM,TOTAL_REQUESTS" > $OUT_FILE

awk -F, '{ idx=index($3,"T"); date=substr($3,0,idx - 1); time=substr($3,idx + 1, 3); counter[ date " " time "00 - " time "59" ]++ ; }\
 END { for ( key in counter ) { print key "," counter[ key ] ; } ; }' $IN_FILES | sort >> $OUT_FILE
