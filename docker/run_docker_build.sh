sudo docker-compose up -d
mvn clean package exec:java "-Dexec:args=-s MONGODB -f JSON -p 27017 -host 127.0.0.1 -db demo -c test"