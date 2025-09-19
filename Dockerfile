# ===================================================================
# Dockerfile de UNA SOLA ETAPA para máxima compatibilidad
# ===================================================================

# 1. Empezamos con una imagen que ya tiene Maven y Java 17
FROM maven:3.9.9-eclipse-temurin-17

# 2. Creamos el directorio de trabajo y copiamos TODO el código fuente
WORKDIR /app
COPY . .

# 3. Compilamos la aplicación. Esto generará el .ear en /app/ear/target/
RUN mvn clean install -DskipTests

# 4. Descargamos e instalamos WildFly DENTRO de la misma imagen
RUN apt-get update && apt-get install -y unzip && \
    curl -L -o /tmp/wildfly.zip https://github.com/wildfly/wildfly/releases/download/30.0.1.Final/wildfly-30.0.1.Final.zip && \
    unzip /tmp/wildfly.zip -d /opt/ && \
    mv /opt/wildfly-30.0.1.Final /opt/wildfly && \
    rm /tmp/wildfly.zip

# 5. Copiamos el .ear (que ya compilamos) al directorio de despliegue de WildFly
COPY ear/target/*.ear /opt/wildfly/standalone/deployments/

# 6. Exponemos el puerto de la aplicación
EXPOSE 8080

# 7. Definimos los JAVA_OPTS de forma agresiva para ahorrar memoria
ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:MetaspaceSize=64M -XX:MaxMetaspaceSize=128m"

# 8. Usamos el comando de arranque con la espera integrada para el Healthcheck
CMD ["sh", "-c", "/opt/wildfly/bin/standalone.sh -b 0.0.0.0 & PID=$! ; echo 'WildFly starting...'; sleep 45; echo 'Grace period over.'; wait $PID"]