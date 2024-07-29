# Docker Build & Up

Target: Quick deployment experience system，Help understand the dependencies between systems。
Dependency：docker compose v2，Delete`name: blossom-system`，Lower`version`Version is`3.3`Below，Support`docker-compose`。

## Function file list

```text
.
├── Docker-HOWTO.md                 
├── docker-compose.yml              
├── docker.env                      <-- Providedocker-composeEnvironment variable configuration
├── blossom-server
│   └── Dockerfile
└── blossom-ui-admin
    ├── .dockerignore
    ├── Dockerfile
    └── nginx.conf                  <-- Provide basic configuration，gzipCompression、apiForward
```

## Build jar Package

```shell
# CreatemavenCachevolume
docker volume create --name blossom-maven-repo

docker run -it --rm --name blossom-maven \
    -v blossom-maven-repo:/root/.m2 \
    -v $PWD:/usr/src/mymaven \
    -w /usr/src/mymaven \
    maven mvn clean install package '-Dmaven.test.skip=true'
```

## Build startup service

```shell
docker compose --env-file docker.env up -d
```

The container will be automatically built when running for the first time。Can be passed`docker compose build [service]`To manually build all or somedockerMirror

`--env-file docker.env`is an optional parameter，Just showing the pass`.env`File configuration container startup environment variables，`docker-compose.yml`It already provides enough default parameters to run the system normally。

## Server host port mapping

- admin ui: http://localhost:8080
- api server: http://localhost:58080
- mysql: root/123456, port: 3306
- redis: port: 6379
