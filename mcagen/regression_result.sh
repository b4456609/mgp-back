#!/bin/bash
rm -r build/result
mkdir build/result
mkdir build/result/uat
mkdir build/result/serviceTest
for file in `find ./build/output -type f`
do
    echo $file
    filenamet=$(basename $file)
    filename=${filenamet%.*}
    echo $filename
    curl -X GET \
      http://localhost:8080/api/regression/serviceTest/$filename?num=100 > build/result/serviceTest/$filename
    curl -X GET \
      http://localhost:8080/api/regression/uat/$filename?num=100 > build/result/uat/$filename
done