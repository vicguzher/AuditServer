#!/bin/bash

set -e

./gradlew build
export VERSION=$(grep "version =" build.gradle | awk '{print $3}' | sed "s/'//g")
docker build --build-arg VERSION=${VERSION} -t audit-server:${VERSION} .
