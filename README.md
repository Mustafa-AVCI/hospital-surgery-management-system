# hospital-surgery-management-system
Hospital Surgery and Inventory Management System developed in Java with DAO architecture and relational database design



--Hospital Surgery Management System

This project is a comprehensive Hospital Management System developed using Java and a relational database architecture.
It focuses on managing patients, surgeries, surgeons, staff, medical inventory, storage locations, and user authorization within a hospital environment.

The system was developed as part of a Database Systems course, emphasizing real-world database design, DAO architecture, and secure user management.


-- Project Objectives

Design a real-world hospital database system

Apply DAO (Data Access Object) design pattern

Manage complex relationships between hospital entities

Implement role-based user authentication and authorization

Track medical inventory and surgery-related materials


 --System Architecture

The project follows a layered architecture:

UI / Management Layer

Java Swing screens for system interaction

DAO Layer

Database operations (CRUD)

Model (Entity) Layer

Represents database tables

Utility Layer

Security, validation, session handling

Database Layer

Relational database with constraints and relationships

 --Main System Modules
 --User & Role Management

Secure login system

Role-based authorization

Password hashing and validation

Session management

Change password functionality




--Patient Management

Patient registration and update

Surgery history tracking

Relationship with surgeries and operation rooms


--Surgery Management

Surgery scheduling

Operation room assignment

Surgery–staff mapping

Surgery–material tracking


 Surgeon & Staff Management

Surgeon and staff records

Specialty management

Surgeon–specialty mapping

Role-based access control



--Inventory & Material Management

Medical material tracking

Material categories

Storage locations

Inventory transactions (IN / OUT / TRANSFER)

Surgery-material consumption tracking



--Database Design Highlights

Strong use of foreign keys

One-to-many and many-to-many relationships

Transaction tracking for inventory operations

Separate tables for:

Patients

Surgeries

Surgeons

Staff

Materials

Inventory

Storage locations

User accounts & roles


 --Security Features

Password hashing utility

Input validation mechanisms

Session-based authentication

Role-based access control


📂 Project Structure
hospital-surgery-management-system/
│
├── dao/
│   ├── PatientDAO.java
│   ├── SurgeryDAO.java
│   ├── StaffDAO.java
│   ├── InventoryDAO.java
│   ├── UserAccountDAO.java
│   └── ...
│
├── model/
│   ├── Patient.java
│   ├── Surgery.java
│   ├── Surgeon.java
│   ├── Staff.java
│   ├── Material.java
│   └── ...
│
├── ui/
│   ├── LoginScreen.java
│   ├── MainMenu.java
│   ├── SurgeryManagement.java
│   ├── InventoryManagement.java
│   └── ...
│
├── util/
│   ├── DBConnection.java
│   ├── PasswordUtil.java
│   ├── SessionManager.java
│   └── ValidationUtil.java
│
└── README.md




Technologies Used

Java

Java Swing

JDBC

Relational Database (SQL)

DAO Design Pattern

MVC-inspired layered architecture


 
 Learning Outcomes

Designed a real-world hospital database system

Implemented DAO-based data access architecture

Managed complex relational database structures

Applied secure authentication and authorization

Developed a large-scale Java desktop application



Author

Mustafa Avcı
Computer Engineering Student
Java | Database Systems | Software Engineering
