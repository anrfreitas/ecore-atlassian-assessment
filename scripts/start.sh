#!/usr/bin/env bash
./mvnw clean package -DskipTests
docker-compose up -d