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

## Curl commands

- login

```
curl --location --request POST 'http://localhost:8081/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "user",
    "password": "password"
}'
```

- create message

```
curl --location --request POST 'http://localhost:8081/messages' \
--header 'Authorization: Bearer_{token_from_login}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "user",
    "message": "сообщение10"
}'
```

- get last n messages

```
curl --location --request GET 'http://localhost:8081/messages' \
--header 'Authorization: Bearer_{token_from_login}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "user",
    "message": "history 10"
}'
```
