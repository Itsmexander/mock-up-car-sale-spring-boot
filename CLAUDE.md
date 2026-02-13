# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean install

# Run application (starts on port 8082, defaults to JDBC profile)
./mvnw spring-boot:run

# Run with JPA profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=jpa

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=TestApplicationTests

# Package as JAR
./mvnw clean package
```

Prerequisites: Java 21, MySQL running on localhost:3306 with a database named `test1` and user `root` with empty password.

## Architecture

This is a Spring Boot 3.5.10 REST API (Java 21) for a car sales application. The backend is designed to pair with a Vue.js frontend at `http://localhost:5173`. Virtual threads are enabled (`spring.threads.virtual.enabled=true`).

**Layer structure:** `Controller → Service → DAO → MySQL`

### JDBC/JPA Hot-Swap via Spring Profiles

The DAO layer supports two interchangeable persistence implementations, selected by Spring profile:

- **`jdbc` profile (default):** `CarDaoJdbcImpl` / `UserDaoJdbcImpl` — use `JdbcTemplate` for all queries including search/filter/sort/pagination.
- **`jpa` profile:** `CarDaoJpaImpl` / `UserDaoJpaImpl` — wrap Spring Data JPA repositories (`CarRepository` / `UserRepository`).

Both implement the same `CarDao` / `UserDao` interfaces. The service layer is persistence-agnostic.

**Profile configuration files:**
- `application.properties` — shared config (datasource, port, default profile = `jdbc`)
- `application-jdbc.properties` — excludes Hibernate/JPA auto-configuration
- `application-jpa.properties` — enables `ddl-auto=update`, SQL logging

**Domain model** (JPA-annotated with `@Entity`, `@Table`, `@Id`, etc. — annotations are ignored in JDBC mode):
- `Car`: carId, name, price, notation, manufacturer, manufacturedYear, timestamps
- `User`: id, firstname, surname, address, telno, email, password, timestamps

**Validation** is done via custom `Validator<T>` implementations (`CarValidator`, `UserValidator`) that throw `ValidatorException`. Services call validators before delegating to DAOs.

**Security:** Spring Security is configured with all endpoints permitted (`/**`), CSRF disabled, and BCrypt (strength 12) for password hashing. `UserServiceImpl` implements `UserDetailsService` using email as the username.

**CORS** allows all methods from `http://localhost:5173`. The `X-Total-Count` header is exposed for pagination metadata.

## Key Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/registerCar` | Create a car |
| GET | `/store` | Search/filter/sort cars (paginated, default 10/page) |
| GET | `/car/{id}` | Get car by ID |
| PUT | `/update-car/{id}` | Update car |
| DELETE | `/deleteCar/{id}` | Delete car |
| POST | `/auth/register` | Register user |
| POST | `/auth/login` | Login |
| POST | `/auth/passwordChange` | Change password |
| GET | `/auth/test/getalluser` | List all users (test only) |

`GET /store` accepts query params: `query`, `minPrice`, `maxPrice`, `minYear`, `maxYear`, `page`, `size`, `sortBy`, `sortOrder`.

## Testing

Tests use H2 in-memory database with the `test` and `jdbc` profiles active (`@ActiveProfiles({"test", "jdbc"})`). Schema is initialized from `src/test/resources/schema.sql`. Tests use `@MockitoBean` (Spring Boot 3.5+).
