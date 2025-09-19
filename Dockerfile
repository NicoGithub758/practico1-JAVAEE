# ================================
# Etapa 1: Compilar con Maven (Java 17)
# ================================
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar el código fuente
COPY . .

# Compilar todo, incluyendo el módulo web que genera el WAR
RUN mvn clean install -pl !ear -DskipTests

# ================================
# Etapa 2: Ejecutar en WildFly (Java 17)
# ================================
# Usamos una versión de WildFly compatible con Java 17
FROM quay.io/wildfly/wildfly:30.0.1.Final-jdk17

# Copiar el WAR al directorio de despliegue de WildFly
COPY --from=builder /app/web/target/*.war /opt/jboss/wildfly/standalone/deployments/

# Exponer el puerto HTTP de WildFly
EXPOSE 8080

# Iniciar WildFly (el CMD que tenías es correcto)
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]