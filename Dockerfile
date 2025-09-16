### STAGE 1: The Builder ###
FROM maven:3-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

### STAGE 2: The Final Image ###
# Usamos la imagen oficial y moderna de WildFly (Jakarta EE)
FROM jboss/wildfly

# Copiamos el artefacto a la carpeta de despliegue
COPY --from=builder /app/practico1-ear/target/practico1.ear /opt/jboss/wildfly/standalone/deployments/

# --- AÑADIDO: Paso de corrección de permisos ---
# Cambiamos temporalmente al usuario root para poder cambiar el propietario del archivo
USER root
# Hacemos que el usuario 'jboss' (UID 1000) sea el dueño del .ear
RUN chown 1000:1000 /opt/jboss/wildfly/standalone/deployments/practico1.ear
# Regresamos al usuario por defecto para la ejecución
USER jboss