# Blog Platform Application (Spring Boot Backend)

The project involves building a **Blog Platform Application** with a **Spring Boot backend**.  
The application supports core blogging features such as **user authentication, content management, and organization**.

---

## Key Functionalities

- **User Management:** Secure authentication using Spring Security and JWT tokens.  
- **Content Management:** CRUD operations for blog posts, categories, and tags.  
- **Post States:** Support for draft and published states.  
- **Organization:** Posts are categorized and tagged for better content discovery.  
- **Filtering:** Posts can be filtered by categories and tags.  

---

## Technologies Used

### **Backend**
- **Spring Boot:** Framework for building the backend application.  
- **Spring Security:** Used for authentication and authorization.  
- **JSON Web Tokens (JWT):** Stateless authentication mechanism.  
- **Spring Data JPA:** ORM for database interaction.  
- **Hibernate:** Used for database schema management and entity mapping.  
- **PostgreSQL:** Database for persistent storage.  
- **H2 Database:** In-memory database for testing.  
- **Docker:** Containerization for running PostgreSQL and Adminer.  

---

## Development Tools
- **IntelliJ IDEA:** IDE for Java development.  
- **Maven:** Build and dependency management tool.  
- **MapStruct:** Code generator for mapping between entities and DTOs.  

---

## Concepts Used

- **RESTful APIs:** Designed endpoints for CRUD operations and filtering.  
- **Entity Relationships:** Implemented one-to-many, many-to-one, and many-to-many relationships (e.g., posts and tags).  
- **Validation:** Input validation using annotations.  
- **Error Handling:** Centralized error handling with custom responses.  
- **Lifecycle Callbacks:** Used JPA annotations like `@PrePersist` and `@PreUpdate` for automatic timestamp management.  
- **Security:** Stateless authentication with JWT and role-based access control.  
- **Database Management:** Used Docker Compose for consistent database setup across environments.  
- **Testing:** Configured H2 database for isolated testing.  
- **Performance Optimization:** Lazy loading for entity relationships and efficient query methods.

---

## Credits & Resources

- This project was inspired by Devtiro’s structured task briefs.  
- Devtiro's resources helped refine key features.  
- Special thanks to Aaron for providing practical insights into Spring Boot concepts.  

---

**Attribution:**  
Build a Blog Platform App by Devtiro is licensed under CC BY-NC-SA 4.0.  
Original content available at [Devtiro YouTube](https://www.youtube.com/@devtiro) and [Devtiro Website](https://www.devtiro.com).
