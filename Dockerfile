# ================================
# Etapa 1: Compilar con Maven (Java 17)
# ================================
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# ================================
# Etapa 2: Ejecutar en WildFly (Java 17)
# ================================
FROM quay.io/wildfly/wildfly:30.0.1.Final-jdk17

# Copia el .ear
COPY --from=builder /app/ear/target/*.ear /opt/jboss/wildfly/standalone/deployments/

# === CAMBIOS PARA EL SCRIPT DE ARRANQUE ===
# 1. Copia el script al contenedor
COPY start.sh /opt/jboss/

# 2. Dale permisos de ejecución
RUN chmod +x /opt/jboss/start.sh

# 3. Define los JAVA_OPTS de forma agresiva para ahorrar memoria
ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:MetaspaceSize=64M -XX:MaxMetaspaceSize=128m"

# 4. Usa el script como punto de entrada (ENTRYPOINT)
ENTRYPOINT ["/opt/jboss/start.sh"]