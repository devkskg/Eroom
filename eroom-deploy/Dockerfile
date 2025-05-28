FROM openjdk:17

# Jenkins 스크립트에서 미리 app.jar로 이름 바꿔서 복사하므로, 이것만 복사
COPY app.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]