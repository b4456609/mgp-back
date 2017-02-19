#!/bin/bash
count=0
total=0
for file in `find ./build/output -type f`
do
	curl -X POST \
	-H "Content-Type: multipart/form-data" \
	-F "file=@./$file" http://140.121.102.164/api/upload &
#	-F "file=@./$file" http://localhost:8080/api/upload
    ((count++))
    ((total++))
    echo "$total"
    if [ "$count" == "10" ]; then
        count=0
        wait
    fi

done
