**README.md**

## Kotlin Credit Application System Backend

This is a Kotlin backend application for a credit application system. It uses the following technologies:

* Spring Boot
* Spring Data JPA
* Spring Validation
* Spring Web
* Springdoc Open API
* Jackson Kotlin module
* Flyway
* Kotlin Reflect
* Spring Cloud OpenFeign
* Lombok
* PostgreSQL

## How to Install and Run

To install and run the application, follow these steps:

1. Build the Docker image:

```
docker-compose build
```

2. Start the application:

```
docker-compose up -d
```

The application will be available at `http://localhost:8080/api/credit-application-system`.

## How to Use

To use the application, you can use a tool like Postman to send requests to the API.

### Example

To create a new credit application, send a POST request to `http://localhost:8080/api/credit-application-system/credit` with the following JSON body:

```json
{
  "creditValue": 12300,
  "dayFirstInstallment": "2023-11-14",
  "numberOfInstallments": 48,
  "customerId": 1
}
```

To get all credit applications, send a GET request to `http://localhost:8080/api/credit-application-system/credit`.

## Documentation

The application's API documentation is available at `http://localhost:8080/api/credit-application-system/swagger-ui/index.html`.

## License

This application is licensed under the MIT.

## Credits

This application was created by Nicolas Jandre.

## Additional Notes

* The application uses Docker Compose to manage the application and its dependencies.
* The application's configuration is stored in the `application.properties` file.
* The application's API is documented using Swagger UI.