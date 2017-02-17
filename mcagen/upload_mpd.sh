#!/bin/bash
for file in `find ./build/output -type f`
do
	curl -i -X POST \
	-H "Content-Type: multipart/form-data" \
	-F "file=@./$file" http://140.121.102.164/api/upload
#	-F "file=@./$file" http://localhost:8080/api/upload

done
