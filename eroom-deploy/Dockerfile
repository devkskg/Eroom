# Java 17 런타임 이미지 사용
FROM openjdk:17

# JAR 파일 이름을 외부에서 전달받을 수 있도록 설정
ARG JAR_FILE=eroom-0.0.1-SNAPSHOT.jar

# JAR 파일을 이미지 내부로 복사
COPY app/${JAR_FILE} app.jar

# 컨테이너 실행 시 JAR 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
