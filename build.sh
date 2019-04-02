#!/usr/bin/env bash

mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t cindercloud/ethereum-node-monitor .