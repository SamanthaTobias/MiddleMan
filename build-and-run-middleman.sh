#!/bin/bash

# Build the Maven project
mvn package #-Dspring.config.location=classpath:application.properties

# Build the Docker image
docker build -t middle-man .

# Run the Docker container
docker run --rm --name middle-man --network my-network -p 9010:9010 middle-man
