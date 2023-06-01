# User Registration PoC

This project is a PoC implementation of a user registration platform. It consists of four dockerized microservices 
namely: gateway-ms (a spring cloud gateway implementation), notification-ms (used to send emails leveraging a 
fake smtp server), user-ms (that exposes a RESTful CRUD API for user resources) and the registration-ms 
acting as an RPC style orchestrator service that enables a user to register to the platform.
## Installation

For successful installation JDK 11, Docker & Docker compose are required

In the project root directory  **poc-project** execute buidAndDeploy.sh
This script builds the application, runs integration tests, creates docker images and finally
spins up the dockerized microservices.

```bash
./buildAndDeploy.sh
```

## Configuration
The majority of the configuration resides inside the docker-compose.yml (located on project root folder) and some 
application specif parameters can be configured on application.yml resource of each service.

To enable reuse of mongoDB testcontainer, you must set 'testcontainers.reuse.enable=true' in a file located 
at ~/.testcontainers.properties


## Usage
After successful deployment of all microservices you can check the OpenApi documentation 
and try the API operations.

User API can be accessed here http://localhost:9080/webjars/swagger-ui/index.html
Registration API can be accessed here: http://localhost:9081/webjars/swagger-ui/index.html

Access to services via the gateway is provided using bellow paths:
http://localhost:9090/api/user-service
http://localhost:9090/api/registration-service

All services run locally on the host network so port collision might occur if a port is already 
used on the host machine. Please check docker-compose.yml for more details on ports used.

No stress testing nor a frontend has been implemented for this project.

## Design and Architecture

![pocArchitecture](https://github.com/dideliba/poc-project/assets/60351395/4a5d8abe-6549-443b-9b60-857a5429571e)

The system is composed by an spring cloud gateway which routes the requests to the user or registration services
depending on the context path (/api/v1/user-service and /api/v1/registration-service respectively).
User service is backed by a mongoDB as persistent storage and all interactions with the DB have been implemented
in a non-blocking leveraging Flux and Mono publishers.
The registration service communicates via REST http calls with user service and after successfully 
creating a user it also produces a kafka event which in turn consumed by notification service.
Notification service on receiving this event generates an email and sends it to a Fake smpt server which is 
deployed locally.

Regarding testing, Integration tests have been implemented for the user service API by using
Junit5, a mongoDB testcontainer (same version as the one used by the app) and a reactive WebTestClient.


## Kubernetes Instalation
The following instructions enable us to run all microservices and supporting stack on a local kubernetes deployment using
microk8s https://microk8s.io 
The following can run after successful project build where all application ms docker have been generated. Docker images
are stored on a local Docker registry provided by microk8s (registry addon)
```bash
#Install microk8s
sudo snap install microk8s --classic
sudo usermod -a -G microk8s $USER
sudo chown -R $USER ~/.kube
mkdir ~/.kube && microk8s config > ~/.kube/config
#install required microk8s addons
microk8s enable dns dashboard storage registry ingress helm
#create poc namespace that every PoC related container will run there
microk8s kubectl create namespace poc

#Install Kafka
cd supporting-stack/kafka/helm
microk8s helm install my-confluent .  --namespace poc

#Install MongoDB
cd supporting-stack/mongodb/helm
microk8s helm install community-operator . --namespace poc
microk8s kubectl apply -f poc_mongo_cr.yaml --namespace poc

#Install user ms
docker tag user-ms:0.0.2-SNAPSHOT localhost:32000/user-ms:0.0.2-SNAPSHOT
docker push localhost:32000/user-ms:0.0.2-SNAPSHOT
cd user-ms/helm
microk8s helm install user-ms .  --namespace poc

#Install registration ms
docker tag registration-ms:0.0.2-SNAPSHOT localhost:32000/registration-ms:0.0.2-SNAPSHOT
docker push localhost:32000/registration-ms:0.0.2-SNAPSHOT
cd registration-ms/helm
microk8s helm install registration-ms . --namespace poc

#Install notification ms
docker tag notification-ms:0.0.2-SNAPSHOT localhost:32000/notification-ms:0.0.2-SNAPSHOT
docker push localhost:32000/notification-ms:0.0.2-SNAPSHOT
cd notification-ms/helm
microk8s helm install notification-ms .  --namespace poc

#Install gateway ms
docker tag gateway-ms:0.0.2-SNAPSHOT localhost:32000/gateway-ms:0.0.2-SNAPSHOT
docker push localhost:32000/gateway-ms:0.0.2-SNAPSHOT
cd gateway-ms/helm
microk8s helm install gateway-ms .  --namespace poc

#Install fake smtp server
cd supporting-stack/fakesmtp/helm 
microk8s helm install fakesmtp .  --namespace poc

#Install k6 for stress testing the cluster
sudo snap install k6
```


## Running stress tests
```bash
cd stressTests
k6 createTest.js
k6 readTest.js #change user id in url to read an existing user 
```