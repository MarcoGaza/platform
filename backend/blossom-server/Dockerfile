## AdoptOpenJDK Stop publishing OpenJDK Binary，And Eclipse Temurin It is an extension of it，Provide better stability
## Thanks to Dr. Fudan Nuclear for his advice！Brother Huizi，cowhide！
FROM eclipse-temurin:8-jre

## Create directory，and use it as the working directory
RUN mkdir -p /blossom-server
WORKDIR /blossom-server
## The backend project Jar File，Copy to mirror
COPY ./target/blossom-server.jar app.jar

## Settings TZ Time zone
ENV TZ=Asia/Shanghai
## Settings JAVA_OPTS Environment variables，Passable docker run -e "JAVA_OPTS=" Overwrite
ENV JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

## Application parameters
ENV ARGS=""

## Exposing backend projects 58080 Port
EXPOSE 58080

## Start the backend project
CMD java ${JAVA_OPTS} -jar app.jar $ARGS
