# --- ETAPA 1: Compilar el proyecto con Maven ---
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos los archivos de configuración y código fuente
COPY pom.xml .
COPY src ./src

# Compilamos el proyecto y generamos el archivo WAR ignorando los tests para agilizar
RUN mvn clean package -DskipTests

# --- ETAPA 2: Ejecutar en Tomcat ---
FROM tomcat:10.1-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copiamos dinámicamente el WAR generado en la etapa anterior y lo renombramos a ROOT.war
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
