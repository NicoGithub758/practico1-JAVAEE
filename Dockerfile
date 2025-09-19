# ================================
# Etapa 1: Compilar con Maven
# ================================
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar todos los archivos del proyecto
COPY . .

# Compilar y empaquetar, generando el EAR
RUN mvn clean install -DskipTests

# ================================
# Etapa 2: Ejecutar en WildFly
# ================================
FROM quay.io/wildfly/wildfly:27.0.1.Final

# Copiar el EAR al directorio de despliegue de WildFly
COPY --from=builder /app/ear/target/*.ear /opt/jboss/wildfly/standalone/deployments/

# Exponer puerto HTTP
EXPOSE 8080

# Arrancar WildFly en modo standalone
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]