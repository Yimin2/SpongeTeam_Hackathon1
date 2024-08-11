# 첫 번째 스테이지: 빌드 스테이지
FROM gradle:jdk17-alpine as builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 래퍼와 소스 코드, 설정 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Gradle 래퍼에 실행 권한 부여
RUN chmod +x ./gradlew

# 종속성 캐시를 활용하기 위해 빈 소스 디렉토리 설정 후 종속성 설치
RUN ./gradlew build --no-daemon

# 소스 코드 복사
COPY src src

# 애플리케이션 빌드
RUN ./gradlew build --no-daemon

# 두 번째 스테이지: 실행 스테이지
FROM eclipse-temurin:17-jre-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 첫 번째 스테이지에서 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 실행할 JAR 파일 지정
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
