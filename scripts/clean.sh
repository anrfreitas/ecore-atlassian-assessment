#!/usr/bin/env bash
cd ..
sudo rm -r target
./mvnw clean package -DskipTests