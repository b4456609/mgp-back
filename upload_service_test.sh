#!/bin/bash

curl -X POST -H "Content-Type: multipart/form-data" \
	-F "files=@./src/test/resources/serviceTest/easylearn_note.json" \
	-F "files=@./src/test/resources/serviceTest/easylearn_note.md" \
	-F "files=@./src/test/resources/serviceTest/easylearn_pack.json" \
	-F "files=@./src/test/resources/serviceTest/easylearn_pack.md" \
	"http://localhost:8080/api/test/serviceTest"
