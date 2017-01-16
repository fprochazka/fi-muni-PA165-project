#!/usr/bin/env bash

echo "USER_UID=$(id -u)" >> .env
echo "USER_GID=$(id -g)" >> .env
echo "USER_NAME=$(id -un)" >> .env
