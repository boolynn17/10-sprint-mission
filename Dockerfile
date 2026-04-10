# 1단계: 빌드
FROM amazoncorretto:17 AS builder

WORKDIR /app

# 의존성 캐시를 위해 gradle 파일 먼저 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon

# 소스코드 복사 후 빌드
COPY src src
RUN ./gradlew clean build -x test --parallel --no-daemon

# 2단계: 런타임
FROM amazoncorretto:17-al2023-jre

WORKDIR /app

ENV PROJECT_NAME=discodeit
ENV PROJECT_VERSION=1.2-M8
ENV JVM_OPTS=""

# 빌드 단계에서 jar만 복사
COPY --from=builder /app/build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar app.jar

EXPOSE 80

ENTRYPOINT ["sh", "-c", "java ${JVM_OPTS} -jar app.jar"]