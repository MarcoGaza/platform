#!/bin/bash
set -e

DATE=$(date +%Y%m%d%H%M)
# Basic path
BASE_PATH=/work/projects/blossom-server
# After compilation jar Address。When deploying，Jenkins Will upload jar Package into this directory
SOURCE_PATH=$BASE_PATH/build
# Service Name。Agree to deploy services at the same time jar The package name is also this。
SERVER_NAME=blossom-server
# Environment
PROFILES_ACTIVE=development
# Health Check URL
HEALTH_CHECK_URL=http://127.0.0.1:58080/actuator/health/

# heapError Storage path
HEAP_ERROR_PATH=$BASE_PATH/heapError
# JVM Parameters
JAVA_OPS="-Xms512m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$HEAP_ERROR_PATH"

# SkyWalking Agent Configuration
#export SW_AGENT_NAME=$SERVER_NAME
#export SW_AGENT_COLLECTOR_BACKEND_SERVICES=192.168.0.84:11800
#export SW_GRPC_LOG_SERVER_HOST=192.168.0.84
#export SW_AGENT_TRACE_IGNORE_PATH="Redisson/PING,/actuator/**,/admin/**"
#export JAVA_AGENT=-javaagent:/work/skywalking/apache-skywalking-apm-bin/agent/skywalking-agent.jar

# Backup
function backup() {
    # If it does not exist，No need to back up
    if [ ! -f "$BASE_PATH/$SERVER_NAME.jar" ]; then
        echo "[backup] $BASE_PATH/$SERVER_NAME.jar Does not exist，Skip backup"
    # If exists，Back up to backup In the directory，Use time as suffix
    else
        echo "[backup] Start backup $SERVER_NAME ..."
        cp $BASE_PATH/$SERVER_NAME.jar $BASE_PATH/backup/$SERVER_NAME-$DATE.jar
        echo "[backup] Backup $SERVER_NAME Completed"
    fi
}

# Latest build code Move to project environment
function transfer() {
    echo "[transfer] Start transfer $SERVER_NAME.jar"

    # Delete original jar Package
    if [ ! -f "$BASE_PATH/$SERVER_NAME.jar" ]; then
        echo "[transfer] $BASE_PATH/$SERVER_NAME.jar Does not exist，Skip deletion"
    else
        echo "[transfer] Remove $BASE_PATH/$SERVER_NAME.jar Completed"
        rm $BASE_PATH/$SERVER_NAME.jar
    fi

    # Copy New jar Package
    echo "[transfer] From $SOURCE_PATH Get $SERVER_NAME.jar and migrate to $BASE_PATH ...."
    cp $SOURCE_PATH/$SERVER_NAME.jar $BASE_PATH

    echo "[transfer] Transfer $SERVER_NAME.jar Completed"
}

# Stop：Elegantly shut down the previously started service
function stop() {
    echo "[stop] Start Stop $BASE_PATH/$SERVER_NAME"
    PID=$(ps -ef | grep $BASE_PATH/$SERVER_NAME | grep -v "grep" | awk '{print $2}')
    # If Java Service starting，Close
    if [ -n "$PID" ]; then
        # Normal shutdown
        echo "[stop] $BASE_PATH/$SERVER_NAME Running，Start kill [$PID]"
        kill -15 $PID
        # Wait for maximum 120 seconds，Until shutdown is complete。
        for ((i = 0; i < 120; i++))
            do
                sleep 1
                PID=$(ps -ef | grep $BASE_PATH/$SERVER_NAME | grep -v "grep" | awk '{print $2}')
                if [ -n "$PID" ]; then
                    echo -e ".\c"
                else
                    echo "[stop] Stop $BASE_PATH/$SERVER_NAME Success"
                    break
                fi
		    done

        # If normal shutdown fails，Then enforce it kill -9 Close
        if [ -n "$PID" ]; then
            echo "[stop] $BASE_PATH/$SERVER_NAME Failed，Mandatory kill -9 $PID"
            kill -9 $PID
        fi
    # If Java Service not started，No need to close
    else
        echo "[stop] $BASE_PATH/$SERVER_NAME Not started，No need to stop"
    fi
}

# Start：Start the backend project
function start() {
    # Before starting，Print startup parameters
    echo "[start] Start startup $BASE_PATH/$SERVER_NAME"
    echo "[start] JAVA_OPS: $JAVA_OPS"
    echo "[start] JAVA_AGENT: $JAVA_AGENT"
    echo "[start] PROFILES: $PROFILES_ACTIVE"

    # Start startup
    BUILD_ID=dontKillMe nohup java -server $JAVA_OPS $JAVA_AGENT -jar $BASE_PATH/$SERVER_NAME.jar --spring.profiles.active=$PROFILES_ACTIVE &
    echo "[start] Start $BASE_PATH/$SERVER_NAME Completed"
}

# Health Check：Automatically determine whether the backend project is started normally
function healthCheck() {
    # If you configure health check，Perform a health check
    if [ -n "$HEALTH_CHECK_URL" ]; then
        # Health Check Maximum 120 seconds，Until the health check passes
        echo "[healthCheck] Start passing $HEALTH_CHECK_URL Address，Conduct a health check";
        for ((i = 0; i < 120; i++))
            do
                # Request health check address，Only get the status code。
                result=`curl -I -m 10 -o /dev/null -s -w %{http_code} $HEALTH_CHECK_URL || echo "000"`
                # If the status code is 200，This means the health check has passed
                if [ "$result" == "200" ]; then
                    echo "[healthCheck] Health check passed";
                    break
                # If the status code is not 200，It means it has not passed。sleep 1 Seconds later，Continue to retry
                else
                    echo -e ".\c"
                    sleep 1
                fi
            done

        # Health check failed，Exit abnormally shell Script，Do not continue deployment。
        if [ ! "$result" == "200" ]; then
            echo "[healthCheck] Health check failed，Possible deployment failure。View log，Judge whether the startup is successful by yourself";
            tail -n 10 nohup.out
            exit 1;
        # Health check passed，Print last 10 Line log，People who may have deployed the application may want to see the logs。
        else
            tail -n 10 nohup.out
        fi
    # If health check is not configured，Then sleep 120 seconds，Manually check the log to see if the deployment is successful。
    else
        echo "[healthCheck] HEALTH_CHECK_URL Not configured，Start sleep 120 seconds";
        sleep 120
        echo "[healthCheck] sleep 120 Completed in seconds，View log，Judge whether the startup is successful by yourself";
        tail -n 50 nohup.out
    fi
}

# Deployment
function deploy() {
    cd $BASE_PATH
    # Backup original jar
    backup
    # Stop Java Service
    stop
    # Deploy new jar
    transfer
    # Start Java Service
    start
    # Health Check
    healthCheck
}

deploy
