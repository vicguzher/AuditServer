#!/bin/bash

set -e

export CLUSTER_ALREADY_EXISTS=$(kind get clusters | grep "audit-server")
if [ ! -z "${CLUSTER_ALREADY_EXISTS}" ]; then
  echo "Deleting cluster"
  kind delete cluster --name audit-server
else
  echo "Cluster does not exist"
fi

