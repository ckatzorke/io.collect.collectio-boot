# collectio-boot

![Latest Build Status](https://api.travis-ci.org/ckatzorke/io.collect.collectio-boot.svg?branch=master)

Game collector application



## Run

	mvn spring-boot:run -D"giantbomb.apikey=apikey"

or

	mvn clean package
	java -jar target/collectio-boot.jar -D"giantbomb.apikey=apikey"
	
## Using docker

	mvn clean package docker:build
	docker run -e "GIANTBOMB_API_KEY=<apikey>" -p 8080:8080 -t collectio/collectio-boot
	
You can the access the app via localhost:8080.
Note for windows (if not running native docker, but docker-machine): to get the ip of your docker host use

	docker-machine ip default
	
You can use the ip address (like 192.168.99.100) to access the app

## Deploy on Digitalocean (for my information ;-) )

Create a droplet

	docker-machine create --driver digitalocean --digitalocean-access-token $DOTOKEN do-collectio
	
Connect to remote docker host

	eval $(docker-machine env do-collectio)

Create the docker image on remote host

	mvn clean package docker:build
		
Spin up remote docker image

	docker run -e "GIANTBOMB_API_KEY=<apikey>" -p 8080:8080 -t collectio/collectio-boot
	

## About Project Lombok

This project uses [Lombok](https://projectlombok.org/index.html) to generate Java typical bloated getter/setter/toString/hashCode stuff for Java Beans.
Follow the instructions for IDE integration.

## Available features

### Jobs api (currenty only Platformimport)

Overview GET /api/games/jobs

Detail /api/games/jobs/{id}

### Platform api

All platform GET /api/games/platform

Search by name GET /api/games/platform/search?query=xxx

### Games api

Search by name GET /api/games/search?query=xxx

### Giantbomb API

Search /rest/giantbomb/search?query=Dark+Souls

Game resource /rest/giantbomb/game/32697

### Howlongtobeat API

/rest/howlongtobeat?game=Mafia
