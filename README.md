# Task-Management-System
A web-based task management application built with Spring Boot, Thymeleaf, and Oracle Database. Users can register, login, create, view, edit,and delete tasks efficiently. The application features a modern, responsive UI and ensures data integrity with proper database constraints.
## Features
- User registration and login
- Task creation with date and time
- View all tasks for logged-in user
- Delete tasks
-Edit tasks
- Responsive UI with Bootstrap
- Input validation and secure session management

## Tech Stack
- Java, Spring Boot, Spring MVC, Spring Data JPA
- Thymeleaf, HTML5, CSS3, Bootstrap 5
- Oracle Database
- Maven
**##Structure**

  
src/main/java/in/vishal/
├── controller/
│   ├── UserController.java
│   └── TaskController.java
├── model/
│   ├── User.java
│   └── Task.java
├── repository/
│   ├── UserRepository.java
│   └── TaskRepository.java
├── service/
│   ├── UserService.java
│   └── TaskService.java
└── TodoApplication.java

src/main/resources/
├── templates/
│   ├── register.html
│   ├── login.html
│   ├── tasks.html
│   └── create-task.html
      └── edit-task.html
├── application.yml
