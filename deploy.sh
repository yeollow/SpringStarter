#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=springStarter

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"
git pull

echo "> 프로젝트 build"
./gradlew build

cd $REPOSITORY

echo "> Build file 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

CURRENT_ID=$(pgrep -f %{PROJECT_NAME}*.jar)
if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

JAR_NAME=$(ls -tr $REPOSITORY/ | grep *.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties, /home/ec2-user/app/application-oauth.properties, /home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=real \
    $REPOSITORY/$JAR_NAME 2>&1 &
~
~
