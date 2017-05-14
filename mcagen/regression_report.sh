#!/bin/bash

echo "Service Test"
for file in `find ./build/result/serviceTest -type f | sort`
do
    RESULT=`grep -o pactFile $file | wc -l`
    BASE=`basename $file`
    echo -e "$BASE\t$RESULT"
done


echo "UAT"
for file in `find ./build/result/uat -type f | sort`
do
    RESULT_TEMP=`grep -o \" $file | wc -l`
    RESULT=$(($RESULT_TEMP / 2))
    BASE=`basename $file`
    echo -e "$BASE\t$RESULT"
done