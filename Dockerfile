FROM amazoncorretto:17-alpine

# Set the working directory
WORKDIR /app

# Create directories
RUN mkdir -p applications/app-service/build/libs
RUN mkdir -p domain/model/build/libs
RUN mkdir -p domain/usecase/build/libs
RUN mkdir -p infrastructure/driven-adapters/mongo-repository/build/libs
RUN mkdir -p infrastructure/entry-points/reactive-web/build/libs

# Copy the built JAR files to the working directory
COPY applications/app-service/build/libs/*.jar applications/app-service/build/libs/
COPY domain/model/build/libs/*.jar domain/model/build/libs/
COPY domain/usecase/build/libs/*.jar domain/usecase/build/libs/
COPY infrastructure/driven-adapters/mongo-repository/build/libs/*.jar infrastructure/driven-adapters/mongo-repository/build/libs/
COPY infrastructure/entry-points/reactive-web/build/libs/*.jar infrastructure/entry-points/reactive-web/build/libs/

# Expose the port that the application listens on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "applications/app-service/build/libs/EveryFit.jar"]