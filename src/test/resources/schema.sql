CREATE TABLE IF NOT EXISTS car (
    carId BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    notation VARCHAR(4000),
    manufacturer VARCHAR(255),
    manufacturedYear INT,
    last_modified_timestamp TIMESTAMP,
    creation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `user` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255),
    surname VARCHAR(255),
    address VARCHAR(255),
    telno VARCHAR(10),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    creation_timestamp TIMESTAMP,
    last_modified_timestamp TIMESTAMP
);
