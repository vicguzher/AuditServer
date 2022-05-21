$cluster_already_exists = (kind create cluster --name audit-server 2>&1).ToString()
$exists_error_message = "ERROR: failed to create cluster: node(s) already exist for a cluster with the name `"audit-server`""

if ($cluster_already_exists -eq $exists_error_message) {
  Write-Host "Cluster already exists"
  exit 0
} else {
  Write-Host "Creating cluster"
  kind create cluster --name audit-server
}
exit $lastexitcode
