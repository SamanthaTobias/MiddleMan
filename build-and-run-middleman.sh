#!/bin/bash

# Build the Maven project
mvn clean
mvn package #-Dspring.config.location=classpath:application.properties

# Build the Docker image
docker build -t middle-man .

# Check if my-network exists, create it if it doesn't
if ! docker network inspect my-network >/dev/null 2>&1; then
    docker network create my-network
fi

# Run the Docker container
docker run --rm --name middle-man --network my-network -p 9010:9010 middle-man
