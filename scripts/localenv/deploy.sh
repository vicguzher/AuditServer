#!/bin/bash

set -e

export VERSION=$(grep "version =" build.gradle | awk '{print $3}' | sed "s/'//g")
export IMAGE_LOADED=$(kubectl get node audit-server-control-plane -o yaml | grep -i docker.io/library/audit-server:${VERSION})
if [ -z "${IMAGE_LOADED}" ]; then
  echo "Loading image"
  kind load docker-image audit-server:${VERSION} --name audit-server
else
  echo "Image already loaded"
fi
helm upgrade --install audit-server ./deploy/charts/audit-server \
  -f ./deploy/local.yaml \
  --set image.tag="${VERSION}" \
  --set config.githubOauth="${GITHUB_OAUTH}",config.githubLogin="${GITHUB_LOGIN}" \
  --atomic \
  --timeout 3m0s
