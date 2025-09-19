FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
# Asegurarse de que el .ear se construya al final
RUN mvn clean install -DskipTests

# ================================
# Etapa 2: Ejecutar en WildFly (Java 17)
# ================================
FROM quay.io/wildfly/wildfly:30.0.1.Final-jdk17

# Copia el .ear
COPY --from-builder /app/ear/target/*.ear /opt/jboss/wildfly/standalone/deployments/

# Define los JAVA_OPTS de forma agresiva para ahorrar memoria
ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:MetaspaceSize=64M -XX:MaxMetaspaceSize=128m"

# --- SOLUCIÓN DE ARRANQUE Y HEALTHCHECK INTEGRADA ---
# 1. Inicia WildFly en segundo plano (&) y captura su PID.
# 2. Espera 45 segundos (nuestro "Grace Period" manual para que la app arranque).
# 3. Después de la espera, trae el proceso de WildFly al primer plano (wait),
#    lo que mantiene el contenedor vivo mientras WildFly se ejecute.
CMD ["sh", "-c", "/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 & PID=$! ; echo 'WildFly starting in background...'; sleep 45; echo 'Grace period over. Waiting for WildFly process to exit.'; wait $PID"]