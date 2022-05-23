
#$cluster_already_exists = (kind create cluster --name audit-server 2>&1).ToString()
#$exists_error_message = "ERROR: failed to create cluster: node(s) already exist for a cluster with the name `"audit-server`""
Write-Output "Verifico si el cluster existe, cuidado sólo funciona si no hay ningún otro cluster en la máquina. Para una funcionamiento más general habría que manipular el string de salida buscando la cadena apropiada"
$cluster = (kind get clusters 2>&1).ToString()
Write-Output $cluster
if ($cluster -eq "audit-server") {
  Write-Output "El cluster ya existe"
# exit 0
} else {
  Write-Output "Ejecuta kind create cluster, para construir el cluster kubernetes"
  kind create cluster --name audit-server
}
exit $lastexitcode
