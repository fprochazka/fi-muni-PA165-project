#!/bin/bash
set -e

ORIGINAL_PWD=$(pwd)

if [ -z ${USER_NAME+x} ]; then
    echo "Variable USER_NAME is not set";
    exit 1
fi

chown -R $USER_NAME /srv
export HOME=/home/$USER_NAME

if [ "$1" = 'tomcat7:run' ]; then
    cd /srv
    /usr/local/bin/gosu $USER_NAME /usr/bin/mvn clean install -DskipTests=true -B -V
    cd $ORIGINAL_PWD
    exec /usr/local/bin/gosu $USER_NAME /usr/bin/mvn tomcat7:run
else
    exec /usr/local/bin/gosu $USER_NAME "$@"
fi
