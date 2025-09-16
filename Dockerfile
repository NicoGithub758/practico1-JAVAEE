### STAGE 1: The Builder ###
FROM maven:3-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

### STAGE 2: The Final Image ###
FROM jboss/wildfly
COPY --from=builder /app/practico1-ear/target/practico1.ear /opt/jboss/wildfly/standalone/deployments/