### STAGE 1: The Builder (Basado en Java 17) ###
FROM maven:3-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests


### STAGE 2: The Final Image (Usando la etiqueta 'latest' para máxima compatibilidad) ###
# Usamos 'latest' para evitar problemas de autorización o formato de etiquetas.
FROM wildfly:latest

# Usamos la ruta y nombre de archivo CORRECTOS que descubriste
COPY --from=builder /app/ear/target/Laboratorio.ear /opt/jboss/wildfly/standalone/deployments/Laboratorio.ear

# Le decimos a WildFly que arranque en el puerto que Render le asigne
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Djboss.http.port=$PORT"]