# Ethereum Node Monitor

Monitor your ethereum node. This monitor simply provides you with a health check which you can use verify the integrity of your Ethereum Node.

## Running the application (Docker)

```
docker run -i --rm -e ETHEREUM_ENDPOINT=https://kovan.arkane.network -p 8080:8080 cindercloud/ethereum-node-monitor:latest
```

## Environment Properties

|Property|Meaning|
|---|---|
|ETHEREUM_ENDPOINT|HTTP(S) endpoint we're monitoring|

## Health Checks

- [x] Sync Status
- [ ] Mining Status
- [ ] Diskspace Status