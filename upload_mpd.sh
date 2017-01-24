#!/bin/bash
for file in `find ./src/test/resources/mdp -type f`
do
	curl -i -X POST \
	-H "Content-Type: multipart/form-data" \
	-F "file=@./$file" http://localhost:8080/api/upload
done
