#!/usr/bin/bash

sudo rm -rf target
docker container stop amazingco-java_service_1 amazingco-java_mysqlserver_1
docker container rm amazingco-java_service_1 amazingco-java_mysqlserver_1
docker images | sort | grep none | awk '{ print $3 }' | xargs docker rmi
docker rmi amazingco-java_service:latest mysql:8.0.23
docker volume rm amazingco-java_mysql_config amazingco-java_mysql_data
