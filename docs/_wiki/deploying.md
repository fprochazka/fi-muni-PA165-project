---
layout: page
title: Running the application in Docker
---

* [Install Docker](https://docs.docker.com/engine/installation/)
* [Install latest docker-compose](https://docs.docker.com/compose/install/), that supports `docker-compose.yml` version `2.1`
* Run `./build-env.sh` and make sure the produced `USER_GID` and `USER_UID` env variables are correct
* Run docker compose

```bash
docker-compose up --build
```
