Write-Output "Ejecuta ./gradlew.bat build para construir el artefacto .jar"
./gradlew.bat assemble
Write-Output "Construye el contenedor docker"
./gradlew.bat docker
Write-Output "Levanta el cluster local"
./gradlew.bat localenv-win-up
Write-Output "Lee versión de build.gradle"
$version_line = (Select-String -Path "build.gradle" -Pattern 'version =').ToString()
$version = $version_line.split(' ')[-1].replace("'", "")
Write-Output $version
Write-Output "Lee variable de entorno GITHUB_OAUTH"
$github_oauth = [System.Environment]::GetEnvironmentVariable('GITHUB_OAUTH')
Write-Output "Lee variable de entorno GITHUB_LOGIN"
$github_login = [System.Environment]::GetEnvironmentVariable('GITHUB_LOGIN')
Write-Output "Ejecuta kind load docker-image para desplegar la aplicación en el cluster kubernetes"
kind load docker-image audit-server:$version --name audit-server
#kind load docker-image audit-server --name audit-server
Write-Output "Ejecuta helm upgrade para configurar el cluster"
helm upgrade --install audit-server ./deploy/charts/audit-server -f ./deploy/local.yaml --set image.tag="$version" --set config.githubOauth="$github_oauth" --set config.githubLogin="$github_login" --atomic --timeout 3m0s
Write-Output "Ejecuta kubectl port para conectar el puerto 80 del cluster al 8080 de la máquina anfitriona"
kubectl port-forward svc/audit-server 8080:80
exit $lastexitco