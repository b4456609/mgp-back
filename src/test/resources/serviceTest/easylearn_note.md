# easylearn_pack

| Description    | Value |
| -------------- | ----- |
| Date Generated | Wed Feb 08 17:18:19 CST 2017 |
| Pact Version   | 3.3.6 |

## Verifying a pact between _easylearn_web_ and _easylearn_pack_

From URL: http://140.121.102.161:8880/pacts/provider/easylearn_pack/consumer/easylearn_web/version/0.8.0
Given **get fb auth token**  
&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: yellow'>WARNING: State Change ignored as there is no stateChange URL</span>  
a request for easylearn token  
&nbsp;&nbsp;returns a response which  
&nbsp;&nbsp;&nbsp;&nbsp;has status code **200** (<span style='color:red'>FAILED</span>)

```
assert expectedStatus == actualStatus
       |              |  |
       200            |  404
                      false
```

&nbsp;&nbsp;&nbsp;&nbsp;includes headers  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"**Content-Type**" with value "**application/json**" (<span style='color:green'>OK</span>)  
&nbsp;&nbsp;&nbsp;&nbsp;has a matching body (<span style='color:red'>FAILED</span>)  

| Path | Failure |
| ---- | ------- |
|$.body|[mismatch:Type mismatch: Expected List List(Map(name -> test, viewCount -> 0, createTime -> 1477403036635, description -> test, public -> true, isPublic -> true, version -> List(Map(viewCount -> 0, createTime -> 1477894888307, public -> false, creatorUserName -> ??�振???, creatorUserId -> 1009840175700426, id -> version1477894888307, content -> <p>test</p><p>?��從此�?</p>), Map(viewCount -> 0, createTime -> 1477403036635, public -> false, creatorUserName -> ??�振???, creatorUserId -> 1009840175700426, id -> version1477403036635, content -> <p>test</p>)), creatorUserName -> ??�振???, creatorUserId -> 1009840175700426, id -> pack1477403034413, coverFilename -> uNS5dGs.png), Map(name -> private, viewCount -> 0, createTime -> 1478666090008, description -> private, public -> false, isPublic -> false, version -> List(Map(viewCount -> 0, createTime -> 1478666090008, public -> false, creatorUserName -> ??�振???, creatorUserId -> 1009840175700426, id -> version1478666090008, content -> <p>asdfasdf</p>), Map(viewCount -> 0, createTime -> 1479830920178, public -> false, creatorUserName -> ??�振???, creatorUserId -> 1009840175700426, id -> version1479830920177, content -> <p>asdfasdf tttt</p>)), creatorUserName -> ??�振???, creatorUserId -> 1009840175700426, id -> pack1478666090008, coverFilename -> ), Map(name -> SSD, viewCount -> 0, createTime -> 1479221373194, description -> ssd, public -> true, isPublic -> true, version -> List(Map(viewCount -> 0, createTime -> 1479221373194, public -> false, creatorUserName -> ??�振???, creatorUserId -> 1009840175700426, id -> version1479221373194, content -> <p>asdfasdfasf</p>)), creatorUserName -> ??�振???, creatorUserId -> 1009840175700426, id -> pack1479221373194, coverFilename -> )) but received Map Map(path -> /pack, timestamp -> 1486545505986, error -> Not Found, status -> 404, message -> No message available), diff:]
-[
-    {
-        "name": "test",
-        "viewCount": 0,
-        "createTime": 1477403036635,
-        "description": "test",
-        "public": true,
-        "isPublic": true,
-        "version": [
-            {
-                "viewCount": 0,
-                "createTime": 1477894888307,
-                "public": false,
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "creatorUserId": "1009840175700426",
-                "id": "version1477894888307",
-                "content": "<p>test</p><p>\ufffd\uf2ae\u657a\ued65\u8fe8\u7508\ufffd</p>"
-            },
-            {
-                "viewCount": 0,
-                "createTime": 1477403036635,
-                "public": false,
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "creatorUserId": "1009840175700426",
-                "id": "version1477403036635",
-                "content": "<p>test</p>"
-            }
-        ],
-        "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-        "creatorUserId": "1009840175700426",
-        "id": "pack1477403034413",
-        "coverFilename": "uNS5dGs.png"
-    },
-    {
-        "name": "private",
-        "viewCount": 0,
-        "createTime": 1478666090008,
-        "description": "private",
-        "public": false,
-        "isPublic": false,
-        "version": [
-            {
-                "viewCount": 0,
-                "createTime": 1478666090008,
-                "public": false,
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "creatorUserId": "1009840175700426",
-                "id": "version1478666090008",
-                "content": "<p>asdfasdf</p>"
-            },
-            {
-                "viewCount": 0,
-                "createTime": 1479830920178,
-                "public": false,
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "creatorUserId": "1009840175700426",
-                "id": "version1479830920177",
-                "content": "<p>asdfasdf tttt</p>"
-            }
-        ],
-        "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-        "creatorUserId": "1009840175700426",
-        "id": "pack1478666090008",
-        "coverFilename": ""
-    },
-    {
-        "name": "SSD",
-        "viewCount": 0,
-        "createTime": 1479221373194,
-        "description": "ssd",
-        "public": true,
-        "isPublic": true,
-        "version": [
-            {
-                "viewCount": 0,
-                "createTime": 1479221373194,
-                "public": false,
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "creatorUserId": "1009840175700426",
-                "id": "version1479221373194",
-                "content": "<p>asdfasdfasf</p>"
-            }
-        ],
-        "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-        "creatorUserId": "1009840175700426",
-        "id": "pack1479221373194",
-        "coverFilename": ""
-    }
-]
+{
+    "path": "/pack",
+    "timestamp": 1486545505986,
+    "error": "Not Found",
+    "status": 404,
+    "message": "No message available"
+}]|

Diff:

```diff
]
-[
-    {
-        "coverFilename": "uNS5dGs.png",
-        "createTime": 1477403036635,
-        "creatorUserId": "1009840175700426",
-        "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-        "description": "test",
-        "id": "pack1477403034413",
-        "isPublic": true,
-        "name": "test",
-        "public": true,
-        "version": [
-            {
-                "content": "<p>test</p><p>\ufffd\uf2ae\u657a\ued65\u8fe8\u7508\ufffd</p>",
-                "createTime": 1477894888307,
-                "creatorUserId": "1009840175700426",
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "id": "version1477894888307",
-                "public": false,
-                "viewCount": 0
-            },
-            {
-                "content": "<p>test</p>",
-                "createTime": 1477403036635,
-                "creatorUserId": "1009840175700426",
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "id": "version1477403036635",
-                "public": false,
-                "viewCount": 0
-            }
-        ],
-        "viewCount": 0
-    },
-    {
-        "coverFilename": "",
-        "createTime": 1478666090008,
-        "creatorUserId": "1009840175700426",
-        "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-        "description": "private",
-        "id": "pack1478666090008",
-        "isPublic": false,
-        "name": "private",
-        "public": false,
-        "version": [
-            {
-                "content": "<p>asdfasdf</p>",
-                "createTime": 1478666090008,
-                "creatorUserId": "1009840175700426",
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "id": "version1478666090008",
-                "public": false,
-                "viewCount": 0
-            },
-            {
-                "content": "<p>asdfasdf tttt</p>",
-                "createTime": 1479830920178,
-                "creatorUserId": "1009840175700426",
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "id": "version1479830920177",
-                "public": false,
-                "viewCount": 0
-            }
-        ],
-        "viewCount": 0
-    },
-    {
-        "coverFilename": "",
-        "createTime": 1479221373194,
-        "creatorUserId": "1009840175700426",
-        "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-        "description": "ssd",
-        "id": "pack1479221373194",
-        "isPublic": true,
-        "name": "SSD",
-        "public": true,
-        "version": [
-            {
-                "content": "<p>asdfasdfasf</p>",
-                "createTime": 1479221373194,
-                "creatorUserId": "1009840175700426",
-                "creatorUserName": "\ufffd\ufffd\uf076\uf5c4\ufffd\ufffd\ufffd",
-                "id": "version1479221373194",
-                "public": false,
-                "viewCount": 0
-            }
-        ],
-        "viewCount": 0
-    }
-]
+{
+    "error": "Not Found",
+    "message": "No message available",
+    "path": "/pack",
+    "status": 404,
+    "timestamp": 1486545505986
+}
```

## Verifying a pact between _easylearn_webback_ and _easylearn_pack_

From URL: http://140.121.102.161:8880/pacts/provider/easylearn_pack/consumer/easylearn_webback/version/0.8.0
Modify version content after adding note  
&nbsp;&nbsp;returns a response which  
&nbsp;&nbsp;&nbsp;&nbsp;has status code **200** (<span style='color:green'>OK</span>)  
&nbsp;&nbsp;&nbsp;&nbsp;has a matching body (<span style='color:green'>OK</span>)  
