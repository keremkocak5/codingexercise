
# Package API

A simple in-memory app for storing packages, product details, and an embedded currency converter.



## Running the application

The app can be started either with Maven or Docker.

### a. Docker   
Make sure you have Java 17, Maven, and Docker installed on your device. Then run the following command at the root folder (where Dockerfile resides) of your project.

mvn clean package

docker build -t codingexercice .

docker run -p 8080:8080 codingexercice

### b. Maven

Make sure you have Java 17 and Maven installed on your device. Then run the following command at the root folder (where pom.xml resides) of your project.

mvn spring-boot:run -Dspring-boot.run.profiles=dev

## Accessing the APIs

Swagger is enabled.

http://localhost:8080/codingexercise/swagger-ui/index.html


## Dependencies
Due to its in-memory database, this app is not horizontally scalable.
If Frankfurter API or Products API become unresponsive, the coding exercise will fail. No fallback / resilience mechanisms implemented. 

