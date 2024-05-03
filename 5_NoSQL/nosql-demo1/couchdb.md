## All over http
### Thunder client vscode

#### Get dbs
GET http://localhost:5984/_all_dbs

#### Create db
PUT http://localhost:5984/testhttpdb

#### Delete db
DEL http://localhost:5984/testhttpdb

#### Get specific db
GET http://localhost:5984/testhttpdb

#### POST 
GET http://localhost:5984/testhttpdb/_bulk_docs

GET http://localhost:5984/testhttpdb/_bulk_docs?limit=10

#### Add document to db
PUT http://localhost:5984/testhttpdb
data json

#### Get all documents from db
GET http://localhost:5984/testhttpdb/_all_docs?include_docs=true
params: include_docs, value: true

#### update document
PUT http://localhost:5984/testhttpdb/"docid"?rev="revid"
params: rev, value: "revid"
body json (deletes values not in new json)

#### Get with mango query
POST http://localhost:5984/testhttpdb/_find
body json mango query

#### Get view
GET http://localhost:5984/testhttpdb/_design/byschool/_view/school?keys=%5B%22UHasselt%22%5D&group=true
params: keys, value: %5B%22UHasselt%22%5D
params: group, value: true
(params: reduce, value: true)

```javascript
function (doc) {
  emit(doc.school, doc.favouriteCourses);
  //emit([doc.age,doc.school], doc.favouriteCourses);
}

//REDUCE _count

//OR
function (doc) {
  if(doc.age > 20){
    emit(doc.school, doc.favouriteCourses);
  }
}

//REDUCE _count
```

```javascript
{
   "selector": {
      "age": {
         "$gt": 20
      }
   },
   "fields": [
      "_id",
      "_rev",
      "age"
   ],
  //  "sort": [
  //     {
  //       "age": "asc"
  //     }
  //  ]
}
```

```javascript
//index
{
   "index": {
      "fields": [
         "age"
      ]
   },
   "name": "age-json-index",
   "type": "json"
}
```