### STAGE 1: The Builder ###
# Usamos una imagen que tiene Maven y Java (JDK 11 en este caso, ajústalo si es necesario)
FROM maven:3-eclipse-temurin-17 AS builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos todo el código fuente de tu proyecto al contenedor
COPY . .

# Ejecutamos el comando de Maven para compilar todo y generar el .ear
# Esto funcionará porque Maven tiene el contexto completo del proyecto multi-módulo
RUN mvn clean package -DskipTests


### STAGE 2: The Final Image ###
# Usamos la imagen oficial de WildFly como base para nuestra aplicación final
FROM jboss/wildfly

# Copiamos el archivo .ear que se generó en el STAGE 1
# Lo copiamos desde la etapa 'builder' a la carpeta de despliegue de WildFly
COPY --from=builder /app/practico1-ear/target/practico1.ear /opt/jboss/wildfly/standalone/deployments/

# Opcional: Renombramos el .ear a ROOT.ear para que se despliegue en la raíz del servidor
RUN mv /opt/jboss/wildfly/standalone/deployments/practico1.ear /opt/jboss/wildfly/standalone/deployments/ROOT.ear

# El comando por defecto de la imagen jboss/wildfly ya es iniciar el servidor,
# así que no necesitamos un CMD o ENTRYPOINT.