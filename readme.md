# Getting Started

### Setup
Ensure you have the following installed/available:
* [Docker](https://www.docker.com/products/docker-desktop/)
* [Java 17](https://openjdk.org/projects/jdk/17/)
* [Gradle](https://gradle.org/install/)
  * If using intellij, ensure Gradle JVM matches java version

### Running
* Start docker
* Run `docker-compose up -d` in the root directory of project
* Run the RentalApplication main method to start the spring boot application.
* Navigate to http://localhost:8080/swagger-ui/index.html#/ in browser to use
* `docker-compose down -v` when done to remove postgresql container



### Additional Information
Technologies used:
* Spring Boot
* Spring Data JPA
  * For ORM persistence and retrieval of default data
* Flyway
  * For Database migrations and initial seed data
* PostgreSQL
  * Database storage
* Docker 
  * For local postgresql instance
  * For local TestContainer testing
* Lombok
  * For easy POJO creation
* TestContainer
  * For standalone local testing