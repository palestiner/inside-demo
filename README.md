# Inside demo app

## Project Structure

`/src/*` structure follows default Java structure.

`/src/main/docker` - Docker configurations for the application and services that the application depends on

## Development

To start a postgresql database in a docker container, run:

```
docker-compose -f .\src\main\docker\docker-compose.yml up -d inside-db
```

To stop it and remove the container, run:

```
docker-compose -f .\src\main\docker\docker-compose.yml down inside-db
```

To start an application, run:

```
mvn spring-boot:run
```

To run app in docker:

```
docker-compose -f .\src\main\docker\docker-compose.yml up -d
```

For more information refer to [Using Docker and Docker-Compose](https://docs.docker.com/compose/gettingstarted/).

## Docker image
https://hub.docker.com/r/palestiner/inside-demo
