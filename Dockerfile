### STAGE 1: The Builder (Basado en Java 17) ###
FROM maven:3-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

# Este comando generará el archivo en 'ear/target/Laboratorio.ear'
RUN mvn clean package -DskipTests


### STAGE 2: The Final Image (Basado en WildFly 37) ###
FROM jboss/wildfly:37.0.0.Final

# Usamos la ruta y nombre de archivo CORRECTOS que descubriste
COPY --from=builder /app/ear/target/Laboratorio.ear /opt/jboss/wildfly/standalone/deployments/Laboratorio.ear

# Le decimos a WildFly que arranque en el puerto que Render le asigne
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Djboss.http.port=$PORT"]