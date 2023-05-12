# Registration PoC

This project is a PoC implementation of a user registration. It consists of four dockerized microservices namely:
gateway-ms (a spring cloud gateway implementation) , notification-ms (used to send emails leveraging a 
fake smtp server), user-ms (that exposes a RESTful CRUD API for user resources) and the registration-ms 
acting as an RPC style orchestrator service that enables a user to register to the system.
## Installation

For successful installation JDK 11, Docker & Docker compose are required

In the project root directory  **poc-project** execute buidAndDeploy.sh
This script builds the application, runs integration tests, creates docker images and finally
spins up the dockerized microservices.

```bash
./buildAndDeploy.sh
```

After successfully deployment of the project you can access API documentation via the following url:
http://localhost:9090/api/v1/swagger-ui.html
http://localhost:9080/webjars/swagger-ui/index.html
## Configuration

## Usage

## Design and Architecture

![pocArchitecture](https://github.com/dideliba/poc-project/assets/60351395/4a5d8abe-6549-443b-9b60-857a5429571e)

## Improvements
* Introduce new API operations that fetch paginated user data (that will be used by frontend apps to display users in a grid). 
This will require the use of PagingAndSortingRepository reference: https://docs.spring.io/spring-data/mongodb/docs/2.0.0.RC3/reference/html/#mongo.reactive.repositories.usage
* Implement PATCH API operations to support partial updates in users
* Add Global exception handler on registration service that will bubble up exceptions thrown by user service 
with the same http status code and message
* More unit tests (currently there is just an integration test on user service)
