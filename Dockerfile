### STAGE 1: The Builder ###
FROM maven:3-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

### STAGE 2: The Final Image ###
# Usamos una imagen de WildFly compatible con Java EE 8 (javax)
FROM quay.io/wildfly/wildfly-centos7:latest

# La ruta de despliegue en esta imagen es ligeramente diferente
COPY --from=builder /app/practico1-ear/target/practico1.ear /opt/wildfly/standalone/deployments/
RUN mv /opt/wildfly/standalone/deployments/practico1.ear /opt/wildfly/standalone/deployments/ROOT.ear