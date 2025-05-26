# Java 17 런타임 이미지 사용
FROM openjdk:17

# JAR 파일을 이미지 내부로 복사
COPY app/build/libs/eroom-0.0.1-SNAPSHOT.jar app.jar

# 컨테이너 실행 시 JAR 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
