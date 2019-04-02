#!/usr/bin/env bash

docker run -i --rm -e ETHEREUM_ENDPOINT=https://kovan.arkane.network -p 8080:8080 cindercloud/ethereum-node-monitor:latest