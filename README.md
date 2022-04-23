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