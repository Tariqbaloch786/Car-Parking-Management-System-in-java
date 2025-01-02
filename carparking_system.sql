CREATE DATABASE carparking_system;
USE carparking_system;
CREATE TABLE cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    car_owner_name VARCHAR(100) NOT NULL,
    car_number VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);