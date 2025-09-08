# Hotel Reservation
A Java Spring Boot + HSQLDB application for managing hotel reservations with RESTful API endpoints. 
This system allows for creation, retrieval, updating, and deletion of reservations in a hotel management context.
You need to install Java 21 and Gradle to run this application and your favorite IDE (IntelliJ IDEA-recommended, VSCode, Eclipse, etc.) 

## Technologies
- Java 21
- Gradle
- Spring Boot 3.5.4
- Spring Data JPA
- HSQLDB (embedded database)
- Jakarta Bean Validation
- Lombok
- MapStruct
- Swagger/OpenAPI documentation


## API Endpoints
### Reservations

| Method | Endpoint                    | Description                    |
|--------|-----------------------------|--------------------------------|
| GET    | `/api/v1/reservations`      | Get all reservations           |
| GET    | `/api/v1/reservations/{id}` | Get reservation by ID          |
| POST   | `/api/v1/reservations`      | Create a new reservation       |
| PUT    | `/api/v1/reservations/{id}` | Update an existing reservation |
| DELETE | `/api/v1/reservations/{id}` | Delete a reservation           |


## Running Application
1. Clone the repository: 'git clone https://github.com/jormakroon/hotelreservation.git'
2. Run './gradlew clean build': This will build the application and run unit tests
3. Run './gradlew hsqldbStart': This will start the embedded HSQLDB database
4. Run './gradlew hsqldbStop': This will stop the embedded HSQLDB database
5. Run './gradlew clean test': This will run unit tests

Access the Swagger UI: http://localhost:8080/swagger-ui/index.html


## Future Improvements
- Authentication and authorization
- Reservation status management
- Payment integration
- Email notifications
- Room availability calendar
- Booking reports and analytics
- Multi-language support


