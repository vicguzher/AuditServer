./gradlew.bat build
$version_line = (Select-String -Path "build.gradle" -Pattern 'version =').ToString()
$version = $version_line.split(' ')[-1].replace("'", "")
docker build --build-arg VERSION=$version -t audit-server:$version .
exit $lastexitcode
