# OJApiLayer
First layer of interaction from clients

#### APIs
* ToDO :- integrate swagger link for better documentation.

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