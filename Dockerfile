# ================================
# Etapa 1: Compilar con Maven
# ================================
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar el código fuente
COPY . .

# Compilar todo, incluyendo el módulo web que genera el WAR
RUN mvn clean install -pl !ear -DskipTests

# ================================
# Etapa 2: Ejecutar en WildFly
# ================================
FROM jboss/wildfly:latest

# Copiar el WAR al directorio de despliegue de WildFly
COPY --from=builder /app/web/target/*.war /opt/jboss/wildfly/standalone/deployments/

# Exponer el puerto HTTP de WildFly
EXPOSE 8080

# Iniciar WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
