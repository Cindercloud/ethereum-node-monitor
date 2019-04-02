#!/usr/bin/env bash

mvn clean package -Pnative # -Dnative-image.docker-build=true
docker build -f src/main/docker/Dockerfile.native -t cindercloud/ethereum-node-monitor .