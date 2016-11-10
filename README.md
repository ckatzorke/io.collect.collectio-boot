# collectio-boot

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
	

## Available features

### Giantbomb API

Search /rest/giantbomb/search?query=Dark+Souls

Game resource /rest/giantbomb/game/32697

### Howlongtobeat API

/rest/howlongtobeat?game=Mafia