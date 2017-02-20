#!/bin/bash
count=0
total=0
for file in `find ./build/output -type f`
do
	http -f POST :8080/api/upload file@./$file &
    ((count++))
    ((total++))
    echo "$total"
    if [ "$count" == "20" ]; then
        count=0
        wait
    fi

done
