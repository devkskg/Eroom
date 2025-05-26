FROM openjdk:17

# 이 위치 기준으로 위로 올라가서 jar 복사
COPY ../eroom/build/libs/eroom-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
