# E-Core - Senior Software Engineer Assessment

This project was created to assess my skills using Java and Springboot.

The infrastructure was created using Docker and Docker-compose, and you can bring it up live by running the following commmand:
- `docker-compose up -d`

The project will be running on port `8080`, and it'll be using `/api` endpoint prefix.

### Features
- Array, Object, Collection, String manipulation (Hello RestController)
- CRUD operations with entity-relationship, Cronjob and caching implementation (Customer RestController)
- Transformer implementation (CustomerSummaryTransformer)
- Queueing (Pub/Sub), Filtering, Paging, Sorting operations (Occurrence RestController)
- Data Validation Example through DTO (Hello RestController)
- Multi threading Examples (Threading RestController)
- Custom Exceptions implementation (ConflictExeption and NotFoundException)
- Unit and Integration tests (Transformers, Helpers, RestControllers)

### Code Infrastructure
- Database System: Postgres 14
- Database and Migration features - Flyway
- Message Queuing System - RabbitMQ Alpine
- Caching System - Springboot Container

### Important

There's an script inside the `scripts` folder that are used only if you decided to change something and needed to clean up the project.

If you want to manually play with the endpoints, a postman collection (v2.1) can be found inside the `postman` folder.

PS: All new scripts must be stored inside that `scripts` folder.

### Requirements

There are some basic requirements to run this project:

- Internet connection
- Unix-like environment (Ubuntu, Debian, MacOSX)
- Java 11 (OpenJDK)
