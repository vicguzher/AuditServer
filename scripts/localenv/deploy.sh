#!/bin/bash

set -e

export VERSION=$(grep "version =" build.gradle | awk '{print $3}' | sed "s/'//g")
docker build -t audit-server:${VERSION} .
