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


## Improvements
* Introduce new API operations that fetch paginated user data (that will be used by frontend apps to display users in a grid). 
This will require the use of PagingAndSortingRepository reference: https://docs.spring.io/spring-data/mongodb/docs/2.0.0.RC3/reference/html/#mongo.reactive.repositories.usage
* Implement PATCH API operations to support partial updates in users
* Add Global exception handler on registration service that will bubble up exceptions thrown by user service 
with the same http status code and message
* More unit tests (currently there is just an integration test on user service)
* Implement a UI
* Stress testing