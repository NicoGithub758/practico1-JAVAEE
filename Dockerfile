# ===================================================================
# Dockerfile Final - Con Script de Arranque Inteligente y sin JPA
# ===================================================================

FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

RUN apt-get update && apt-get install -y unzip curl && \
    curl -L -o /tmp/wildfly.zip https://github.com/wildfly/wildfly/releases/download/30.0.1.Final/wildfly-30.0.1.Final.zip && \
    unzip /tmp/wildfly.zip -d /opt/ && \
    mv /opt/wildfly-30.0.1.Final /opt/wildfly && \
    rm /tmp/wildfly.zip

RUN mv ear/target/*.ear /opt/wildfly/standalone/deployments/
COPY start.sh /opt/
RUN chmod +x /opt/start.sh

EXPOSE 8080

# Ahora que la app es más ligera, podemos ser un poco más generosos con la memoria.
ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:MetaspaceSize=64M -XX:MaxMetaspaceSize=128m"

CMD ["/opt/start.sh"]