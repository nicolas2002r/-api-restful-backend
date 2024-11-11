# Usa la imagen de OpenJDK 17 como base
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado en tu proyecto a la carpeta de trabajo en el contenedor
COPY build/libs/api-restful-backend-0.0.1-SNAPSHOT.jar app.jar


# Expone el puerto que utiliza la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
