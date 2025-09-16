### STAGE 1: The Builder ###
FROM maven:3-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

### STAGE 2: The Final Image ###
# Volvemos a la oficial de Docker Hub, que es el estándar.
# El error anterior pudo ser puntual.
FROM jboss/wildfly:27.0.1.Final
COPY --from=builder /app/practico1-ear/target/practico1.ear /opt/jboss/wildfly/standalone/deployments/