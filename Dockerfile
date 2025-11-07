# Usando OpenJDK 21
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copiamos el jar de Spring Boot
COPY target/ejercicio18-0.0.1-SNAPSHOT.jar app.jar

# Exponemos puerto
EXPOSE 8080

# Ejecutamos la app
ENTRYPOINT ["java","-jar","app.jar"]