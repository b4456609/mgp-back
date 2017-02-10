#!/bin/bash

curl -X POST -H "Content-Type: application/json" -d '{
	"pactHostUrl": "http://140.121.102.161:8880/",
	"bddGitUrl": "https://github.com/b4456609/easylearn-uat.git"
}' "http://localhost:8080/api/setting"
