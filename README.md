# AuditServer

Servidor de métricas, apoyado en la librería Audit4Improve
## Objetivo

El objetivo de este código es practicar con:
- La gestión de dependencias
- La integración continua
- El despliegue continuo

## Dependencias

Este servidor utiliza la api [Audit4Improve](https://github.com/MIT-FS/Audit4Improve-API), en la que los alumnos han trabajado a lo largo de las prácticas de la asignatura.

Para el entorno local de desarrollo vamos a usar [Kubernetes] en la
máquina local. Para ello, vamos a necesitar installar previamente
[Kind] y [kubectl]. Una vez instalado comprueba que podemos ejecutar el binario:

```shell
kind --version
```

La aplicación se va a desplegar en Kubernetes usando una herramienta de gestión
de la configuración muy liviana llamada [helm], que además es capaz de
automatizar el proceso de release y rollback.

Para ello [descarga e instala helm]. Una vez instalado, comprueba que podemos
ejecutar el binario:

```shell
helm version
```

Para finalizar, necesitaremos un [Personal Access Token de Github] para poder
probar el funcionamiento de la aplicación.

[Kubernetes]: https://kubernetes.io/
[Kind]: https://kind.sigs.k8s.io/docs/user/quick-start#installation
[kubectl]: https://kubernetes.io/docs/tasks/tools/#kubectl
[helm]: https://helm.sh/
[descarga e instala helm]: https://helm.sh/docs/intro/install/
[Personal Access Token de Github]: https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token

## Desarrollo local

Antes de empezar, necesitamos cargar nuestro Personal Access Token en la shell
donde probemos nuestro código.

```shell
export GITHUB_TOKEN=<token>
```

Para ejecutar el servidor web de en la máquina local, ejecuta el siguiente comando:

```shell
./gradlew bootRun
```

Prueba que el servicio expone un endpoint de metricas en /metricsInfo:

```shell
curl http://localhost:8080/metricsInfo/issues
```

El endpoint debe devolver la siguiente respuesta:

```shell

StatusCode        : 200
StatusDescription :
Content           : {"name":"issues","unit":"issues","description":"Tareas sin finalizar en el repositorio","type":"java.lang.Integer"}
RawContent        : HTTP/1.1 200
                    Transfer-Encoding: chunked
...
```

### Ejecutar los tests

Para ejecutar los tests unitarios, ejecuta el siguiente comando:

```shell
./gradlew test
```

### Desplegar el entorno local de desarrollo

Para ejecutar el entorno local de desarrollo, ejecuta el siguiente comando:

```shell
./gradlew localenv-up
```

La tarea `localenv` levanta un cluster de Kubernetes y configura de forma
automÃ¡tica el contexto de Kubernetes para que podamos acceder a la API
de Kubernetes de manera local usando `kubectl`. Levantar el entorno local
debe tardar alrededor de 3 minutos.

Finalmente comprobamos que tenemos acceso al cluster de Kubernetes:

```shell
kubectl get po --all-namespaces
```

Deberemos obtener uns salida similar a la siguiente:

```shell
NAMESPACE            NAME                                                 READY   STATUS    RESTARTS   AGE
kube-system          coredns-558bd4d5db-l8q2g                             1/1     Running   0          83s
kube-system          coredns-558bd4d5db-rqcbm                             1/1     Running   0          84s
kube-system          etcd-audit-server-control-plane                      1/1     Running   0          94s
kube-system          kindnet-glbk7                                        1/1     Running   0          85s
kube-system          kube-apiserver-audit-server-control-plane            1/1     Running   1          94s
kube-system          kube-controller-manager-audit-server-control-plane   1/1     Running   0          94s
kube-system          kube-proxy-db56r                                     1/1     Running   0          85s
kube-system          kube-scheduler-audit-server-control-plane            1/1     Running   0          94s
local-path-storage   local-path-provisioner-547f784dff-vz7c2              1/1     Running   0          83s
```

Cuando hayamos terminado, simplemente borramos el cluster:

```shell
./gradlew localenv-down
```

### Despliegue de la aplicación en el entorno local

Para desplegar la aplicación, ejecuta el siguiente comando:

```shell
./gradlew localenv-deploy
```

Al finalizar, aplicación se encuentra en el `default` namespace, y debe
de haber 1 pod en estado running:

```shell
âžœ  ~ kubectl get po
NAME                            READY   STATUS    RESTARTS   AGE
audit-server-7b7f9cbb96-x6kfw   1/1     Running   0          98s
```

Podemos interactuar con la aplicación y simular que recibe peticiones
HTTP haciendo port-forwarding del servicio a nuestra máquina local. De esta
forma, no necesitamos un Load Balancer real en nuestra infraestructura, ni
configuración DNS extra:

```shell
kubectl port-forward svc/audit-server 8000:80
```

Esto abre un tunel al cluster de Kubernetes y expone el puerto 80 del servicio,
que mapea al puerto 8080 del container que se ejecuta en la pod, al puerto 8000
de nuestra máquina local. Y ahora podemos abrir otro terminal y lanzarle
peticiones a nuestro servicio:

```shell
âžœ  ~ curl http://localhost:8000/healthz
{"healthy":true}
âžœ  ~ curl http://localhost:8000/metricsInfo/forks
{"name":"forks","unit":"forks","description":"Número de forks, no son los forks de la web","type":"java.lang.Integer"}
```

## Comenzar con Spring Boot para el desarrollo de servicios REST

Enlaces generados automáticamente al crear el esqueleto del servicio en [start.spring.io](https://start.spring.io/)

### Documentación de referencia

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.7/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.7/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#boot-features-developing-web-applications)

### Guías

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### Enlaces adicionales

These additional references should also help you:

* [Gradle Build Scans â€“ insights for your project's build](https://scans.gradle.com#gradle)

