#!/bin/bash
count=0
total=0
for file in `find ./build/serviceTest -type f`
do
    filename=$(basename $file)
    echo $filename
    PROT=${filename#*-}
	PRO=${PROT%.*}
	CON="${filename%%-*}"
	echo $PRO
    echo $CON

    curl -v -XPUT \-H "Content-Type: application/json" \
        -d@$file \
        http://localhost:8880/pacts/provider/$PRO/consumer/$CON/version/1.0.0
done
