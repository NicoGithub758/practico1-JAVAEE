### STAGE 1: The Builder (Basado en Java 17) ###
FROM maven:3-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

### STAGE 2: The Final Image (Basado en WildFly 37) ###
FROM jboss/wildfly:37.0.0.Final
COPY --from=builder /app/ear/target/Laboratorio-ear-1.0-SNAPSHOT.ear /opt/jboss/wildfly/standalone/deployments/Laboratorio.ear

# --- LÍNEA NUEVA Y CRUCIAL ---
# Esta línea le dice a WildFly que inicie en el puerto que Render le asigne a través de la variable $PORT.
# -b 0.0.0.0 es para que acepte conexiones desde cualquier IP (necesario en un contenedor).
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Djboss.http.port=$PORT"]