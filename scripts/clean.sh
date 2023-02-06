#!/usr/bin/env bash
cd ..
docker-compose stop
sudo rm -r target
./mvnw clean package -DskipTests
docker-compose up -d