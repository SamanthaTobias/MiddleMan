# MiddleMan

MiddleMan is a Spring Boot application that acts as a middleman between a BasicApp service and clients. It allows clients to set the name of the BasicApp service, apply different effects to the response received from the BasicApp service, and reverse a given string.

## Dependencies

MiddleMan depends on the BasicApp service. You must have the BasicApp service running before starting MiddleMan.

## Installation

1. Install Docker Desktop on your machine: [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)
2. Download, build and run [BasicApp](https://github.com/SamanthaGobbo/BasicApp)
3. Build and run the application using the provided shell script: `./build-and-run-middleman.sh`

## Usage

Once the application is running, you can interact with it using the following endpoints:

- `POST /setName/{name}`: Set the name of the BasicApp service to `name`.
- `POST /setEffect/{effect}`: Set the effect applied to the response received from the BasicApp service. `effect` must be one of `NONE`, `LOWERCASE`, `UPPERCASE`, `RANDOMCASE`, or `ALPHABETIZE`.
- `GET /hello`: Retrieve the response received from the BasicApp service with the current effect applied.
- `GET /reverse?input={input}`: Reverse the `input` string and return the result.
- `GET /lastReversed`: Retrieve the last string that was reversed.
