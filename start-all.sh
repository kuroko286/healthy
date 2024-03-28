#!/bin/sh
echo "Starting redis..."
docker container start localredis
echo "redis started"
echo "----------------------------------"

echo "starting spring app..."
cd api
mvn spring-boot:run &
sleep 20 # wait for 20 secs for sure the application to start
echo "----------------------------------"
echo "starting frontend..."
cd ../frontend
yarn run dev
