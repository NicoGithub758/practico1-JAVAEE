# ===================================================================
# Dockerfile Final - Con Script de Arranque Inteligente
# ===================================================================

# 1. Empezamos con una imagen que ya tiene Maven y Java 17
FROM maven:3.9.9-eclipse-temurin-17

# 2. Creamos el directorio de trabajo y copiamos el código fuente
WORKDIR /app
COPY . .

# 3. Compilamos la aplicación
RUN mvn clean install -DskipTests

# 4. Descargamos e instalamos WildFly
RUN apt-get update && apt-get install -y unzip curl && \
    curl -L -o /tmp/wildfly.zip https://github.com/wildfly/wildfly/releases/download/30.0.1.Final/wildfly-30.0.1.Final.zip && \
    unzip /tmp/wildfly.zip -d /opt/ && \
    mv /opt/wildfly-30.0.1.Final /opt/wildfly && \
    rm /tmp/wildfly.zip

# 5. Movemos el .ear al directorio de despliegue
RUN mv ear/target/*.ear /opt/wildfly/standalone/deployments/

# === CAMBIOS PARA EL SCRIPT DE ARRANQUE ===
# 6. Copia el script de arranque al contenedor
COPY start.sh /opt/

# 7. Dale permisos de ejecución
RUN chmod +x /opt/start.sh

# 8. Exponemos el puerto
EXPOSE 8080

# 9. Definimos los JAVA_OPTS para ahorrar memoria
ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:MetaspaceSize=64M -XX:MaxMetaspaceSize=128m"

# 10. Usa el script como comando de inicio
CMD ["/opt/start.sh"]