# ================================
# Etapa 1: Compilar con Maven (Java 17)
# ================================
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar el código fuente
COPY . .

# Compilar el proyecto completo para generar el .ear final
RUN mvn clean install -DskipTests

# ================================
# Etapa 2: Ejecutar en WildFly (Java 17)
# ================================
FROM quay.io/wildfly/wildfly:30.0.1.Final-jdk17

# Copiar el ARCHIVO .EAR (no el .war) al directorio de despliegue
COPY --from=builder /app/ear/target/*.ear /opt/jboss/wildfly/standalone/deployments/

# Exponer el puerto HTTP de WildFly
EXPOSE 8080

# Iniciar WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]