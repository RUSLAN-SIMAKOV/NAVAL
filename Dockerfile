FROM mcr.microsoft.com/playwright/java:v1.53.0-jammy

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/naval-0.0.1-SNAPSHOT.jar"]