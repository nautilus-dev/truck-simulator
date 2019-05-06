# Truck Driver Simulator

## Notice
This is an extension of the forked zip from https://github.com/gschmutz/stream-processing-workshop/raw/master/06-iot-data-ingestion-over-mqtt/java-simulator/truck-client.zip
This extension features also producing MongoDB messages

## Running 

mvn clean package exec:java "-Dexec.args=-s MONGODB -f JSON -p <port> -host <ip> -db <demo> -c <collection>"

## Docker Container building

1) Execute mongodb using the docker-compose.yml in the docker subdirectory using:
cd docker
sudo docker-compose up -d

2) execute the simulator:
docker build \
--build-arg ENDPOINT: MONGODB \
--build-arg TYPE: JSON \
--build-arg PORT: 27017 \
--build-arg HOST: localhost \
--build-arg DB: demo \
--build-arg COLLECTION: test \
.

