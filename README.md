
# Blog Application – Spring Boot Backend

---

A production-ready blog backend built with **Spring Boot**, implementing JWT-based authentication, ownership-based authorization, and performance-optimized data access, with a strong emphasis on clean architecture and maintainable API design.

---

## Key Features

* JWT-based authentication (stateless)
* Secure login & registration
* Ownership-based authorization (users manage only their own posts)
* Draft & published post support
* Category and tag management
* Optimized database access (batch fetching, JOIN FETCH)
* Global exception handling with standardized API responses
* Interactive API documentation using Swagger

---

## Tech Stack

* **Spring Boot**
* **Spring Security + JWT**
* **Hibernate / JPA**
* **PostgreSQL**
* **MapStruct**
* **Swagger (OpenAPI 3)**

---

## API Documentation (Swagger)

Interactive API documentation is available via Swagger.

```
http://localhost:8080/swagger-ui/index.html
```

Secured endpoints can be tested by providing a JWT token using the **Authorize** button.

---

## Notes

* Designed with real-world backend best practices
* Stateless API, suitable for frontend integration
* Easily extendable for roles, pagination, and caching

---

## Important Note

* The UI used to interact with this backend is for demonstration purposes only and is not the primary focus of this project.
* This repository emphasizes backend architecture, security, and API design.

---

## License

MIT License

---

