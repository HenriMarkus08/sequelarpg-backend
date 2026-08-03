# 1. Etapa de Compilação (Build)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Usa o Maven instalado diretamente na imagem, sem precisar do wrapper
RUN mvn clean package -DskipTests

# 2. Etapa de Execução (Runtime)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
