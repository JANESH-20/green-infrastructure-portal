🌿 Green Infrastructure Monitoring Portal

A role-based environmental monitoring system built using Spring Boot, MySQL, and Thymeleaf to track and analyze green infrastructure metrics such as water usage, electricity consumption, trees planted and 
waste collected.This project demonstrates proper backend architecture, database normalization, and real-time dashboard analytics, suitable for academic evaluation and professional portfolios.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

📌 Project Overview

* Urban sustainability requires continuous monitoring of environmental resources.

* This application provides a centralized platform where administrators can:

* Record environmental data

* View real-time dashboard metrics

* Manage secure login with role-based access

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

🚀 Features

✅ Authentication & Security

* Role-based login using Spring Security

* BCrypt password encryption

* Secure session handling

✅ Dashboard (Admin)

* Live metrics fetched from database:

💧 Water usage (L)

⚡ Electricity consumption (kWh)

🌳 Trees planted

♻ Waste collected (kg)

* Clean UI using Bootstrap

✅ Data Management

* Add infrastructure reports via form

* Store data in normalized relational tables

* View all submitted reports

✅ Clean Architecture

Controller → Service → Repository → Database

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

🛠 Tech Stack

Frontend
HTML5
CSS3
Bootstrap 5
Thymeleaf (server-side templates)

Backend
Java
Spring Boot
Spring MVC
Spring Data JPA (Hibernate)

Database
MySQL

Security
Spring Security (Role-Based Access: ADMIN, USER)

Tools
Maven
IntelliJ / Eclipse
MySQL Workbench
Git/GitHub

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

📊 Dashboard Data Flow
Dashboard UI
   ↓
HomeController
   ↓
DashboardService
   ↓
ReportValueRepository
   ↓
MySQL Database


All dashboard values are computed dynamically from the database using optimized JPQL queries.

🔐 Security Details

Passwords stored using BCrypt hashing
Custom UserDetailsService for authentication
Only authenticated users can access dashboard and reports

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

⚙️ Setup Instructions

1️⃣ Clone Repository
git clone https://github.com/your-username/green-infrastructure-portal.git
cd green-infrastructure-portal

2️⃣ Configure Database

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/green_portal
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

3️⃣ Run Application
mvn clean install
mvn spring-boot:run

4️⃣ Access Application
http://localhost:8080


