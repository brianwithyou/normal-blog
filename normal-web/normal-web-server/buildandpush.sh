mvn -f ../../pom.xml clean install
mvn -f pom.xml clean package


docker build . -t xx.xx.xx.xx:5000/normal-web-server:0.0.1-SNAPSHOT

docker push xx.xx.xx.xx:5000/normal-web-server:0.0.1-SNAPSHOT

#docker stop normal-web-server
#docker rm normal-web-server
#docker rmi $( docker images | grep web | awk '{print $3}'  )

#   docker run --name normal-web-server -p 5050:5050  -d xx.xx.xx.xx:5000/normal-web-server:0.0.1-SNAPSHOT

