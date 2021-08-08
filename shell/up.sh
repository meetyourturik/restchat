#!/bin/bash

path=$(dirname "$0")
cd $path/../docker

docker-compose up -d