# --- Etapa 1: Construcción (Build) ---
# Usa una imagen oficial de Maven que incluye JDK 17
FROM maven:3.9-eclipse-temurin-17 AS build

# Establece el directorio de trabajo dentro de la imagen
WORKDIR /app

# Copia primero el pom.xml para aprovechar la caché de Docker
COPY pom.xml .

# Descarga las dependencias
RUN mvn dependency:go-offline

# Copia el resto del código fuente
COPY src ./src

# Compila el proyecto y crea el .jar
RUN mvn package -DskipTests


# --- Etapa 2: Ejecución (Final) ---
# Usa una imagen súper ligera que solo tiene lo necesario para ejecutar Java 17
FROM eclipse-temurin:17-jre-jammy

# Establece el directorio de trabajo
WORKDIR /app

# Copia ÚNICAMENTE el .jar creado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto en el que correrá la aplicación
EXPOSE 8081

# Comando para arrancar la aplicación cuando se inicie el contenedor
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]