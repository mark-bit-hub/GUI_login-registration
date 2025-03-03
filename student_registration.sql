CREATE DATABASE student_registration;
USE student_registration;

CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    is_minor BOOLEAN NOT NULL,
    guardian_name VARCHAR(255),
    guardian_contact VARCHAR(255)
);