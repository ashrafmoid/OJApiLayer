# OJApiLayer
First layer of interaction from clients

#### APIs
* Swagger link :- http://localhost:8082/documentation/swagger-ui/index.html

#### Sandboxing technique for code execution
* Used docker container for executing code submitted by user.Communication between
  container and host happens through mounted volume.
* Spotify java client is used for launching docker container from application

#### Spotify java client
* repo link :- https://github.com/spotify/docker-client

#### Common know Problems
* If error related to building images is observed with `connection refused to localhost:80`,
  then try cleaning all non-essential docker images and restart laptop once.

#### Single testfile per question is allowed, and different test case should have 1 line gap

#### TODO
* Add memory limitation on each container, recording execution time for each test case
* Scaling kafka consumer with multithreaded consumption
* Authentication for resources related to a user visible to him
* Prometheus and grafana support
* Minimum UI support to run from browser rather than postman
* dockerise the complete project with DB support included (see if kafka can be included as well)


#### Filter APIs
* Query should be of the following string combination :-

| Basic Operator | Description         |
|----------------|---------------------|
| ==             | Equal To            |
| !=             | Not Equal To        |
| =gt=           | Greater Than        |
| =ge=           | Greater Or Equal To |
| =lt=           | Less Than           |
| =le=           | Less Or Equal To    |
| =in=           | In                  |
|=out=           | Not in              |


| Composite Operator | Description         |
|--------------------|---------------------|
| ;                  | Logical AND         |
| ,                  | Logical OR          |

* Examples :-
* age=gt=10;age=lt=20: find all people older than 10 and younger than 20
* age=lt=5,age=gt=30: find all people younger than 5 or older than 30
* age=gt=10;age=lt=20;(str=Fero,str=Hero): find all people older than 10 and younger than 20 and living either at Fero or Hero.
* refer https://github.com/tennaito/rsql-jpa for more details. 