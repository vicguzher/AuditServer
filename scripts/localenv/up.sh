#!/bin/bash

set -e

export CLUSTER_ALREADY_EXISTS=$(kind get clusters | grep "audit-server")
if [ -z "${CLUSTER_ALREADY_EXISTS}" ]; then
  echo "Creating cluster"
  kind create cluster --name audit-server
else
  echo "Cluster already exists"
fi
