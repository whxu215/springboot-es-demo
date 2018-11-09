#!/bin/bash

#Runtime Setup

MODE=dev

#Default Environment Values:

if [ "$1" = 'local' ]; then
  JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
  APP_PATH="./target"
else
  if [ "$1" = 'docker' ]; then
    echo 'using docker configuration'
    JAVA_HOME="/usr/lib/jvm/default-jvm"
    APP_PATH="/work"
  else
    echo "using dev/sit/uat configuration"
    JAVA_HOME="/usr/java/jdk1.8.0_102/"
    APP_PATH="/data1/javaapp/skyeye"
  fi
fi

PID_FILE=/var/run/javaapp_skyeye.pid
JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF-8 -Dspring.profiles.active=$MODE"
if [ "$1" = 'docker' ]; then
  ${JAVA_HOME}/bin/java ${JAVA_OPTS} -jar ${APP_PATH}/skyeye.jar
else
  nohup ${JAVA_HOME}/bin/java ${JAVA_OPTS} -jar ${APP_PATH}/skyeye.jar > /data1/logs/skyeye/skyeye.log 2>&1 &

  for t_s in {1..10} ;do
    sleep 1
    APP_PID=$(ps -ef | grep "/data1/javaapp/skyeye" | grep -Ev "grep" | awk -F " " '{print $2}')
    if [ "null$APP_PID" != "null" ];then
      echo ${APP_PID} > ${PID_FILE}
      break
    fi
  done
fi
