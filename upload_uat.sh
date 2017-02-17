#!/bin/bash

curl -X POST \
	-H "Content-Type: multipart/form-data" \
	-F "file=@./src/test/resources/uat.json" \
	"http://localhost:8080/api/test/uat"
