# Fetching latest version of Java
FROM openjdk:17

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/travel2flight-0.0.1-SNAPSHOT.jar /app

# Exposing port 8081
EXPOSE 8081 5433

# Starting the application
CMD ["java", "-jar", "travel2flight-0.0.1-SNAPSHOT.jar"]