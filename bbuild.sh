rm -rf target 
mvn clean  -Dmaven.test.skip=true package 
docker build  -t 127.0.0.1:5000/shopdbs .
docker -H ssh://banjoe@mail.nepextel.hu:2727 push 127.0.0.1:5000/shopdbs
