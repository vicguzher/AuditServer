$version_line = (Select-String -Path "build.gradle" -Pattern 'version =').ToString()
$version = $version_line.split(' ')[-1].replace("'", "")
$github_token = [System.Environment]::GetEnvironmentVariable('GITHUB_TOKEN')

kind load docker-image audit-server:$version --name audit-server
helm upgrade --install audit-server ./deploy/charts/audit-server -f ./deploy/local.yaml --set image.tag="$version" --set config.githubToken="$github_token" --atomic --timeout 3m0s

exit $lastexitcode
