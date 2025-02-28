# Simple Reactive APP

## Description
One API reactive endpoint **`GET /users//by-external-id`** to get exist user from Postgres DB or by call external service that's providing a new User emulating external system call.

## Requirements
- **Java 21**
- **Gradle**
- **Docker** (for containerization)

## How to Run

1. **Start the container with PostgreSQL DB**:
   ```sh
   docker-compose up -d
   ```
2. **Build with gradle**
   ```sh
   ./gradlew build
   ```
3. **Run application**
   ```sh
   ./gradlew bootRun
   ```
   
## Now can be called API endpoint to get users
**`http://localhost:8080/users/by-external-id`**
Randomly NotFound and ServiceUnavailable responses would be appeared.