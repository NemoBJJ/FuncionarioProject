FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
EXPOSE 8082
RUN ls -la target/ # <--- LINHA ADICIONADA PARA VER O NOME DO JAR
CMD ["java", "-jar", "target/funcionario-project-0.0.1-SNAPSHOT.jar"]