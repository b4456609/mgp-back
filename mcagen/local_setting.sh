#!/bin/bash

curl -X POST \
  http://localhost:8080/api/setting \
  -H 'content-type: application/json' \
  -d '{
  "pactHostUrl": "http://localhost:8880/",
  "bddGitUrl": "https://github.com/b4456609/movie-uat.git"
}'

curl -X POST \
  http://localhost:8080/api/update