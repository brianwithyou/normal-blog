
mvn -f ../../pom.xml clean install
mvn -f pom.xml clean package

docker build . -t xx.xx.xx.xx:5000/normal-user-server:0.0.1-SNAPSHOT

docker push xx.xx.xx.xx:5000/normal-user-server:0.0.1-SNAPSHOT

#docker stop normal-user-server
#docker rm normal-user-server
#docker rmi $( docker images | grep normal-user | awk '{print $3}'  )
#   docker run --name normal-user-server -p 5051:5051  -d xx.xx.xx.xx:5000/normal-user-server:0.0.1-SNAPSHOT

