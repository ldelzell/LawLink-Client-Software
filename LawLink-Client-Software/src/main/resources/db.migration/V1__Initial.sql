CREATE TABLE client (
    id int AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    case_id int,
    attorney_id int NOT NULL
);
CREATE TABLE attorney (
    id int AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE "user"
(
    id               INT          NOT NULL AUTO_INCREMENT,
    username         VARCHAR(20)  NOT NULL,
    password         VARCHAR(100) NOT NULL,
    client_id        INT          NULL,
    attorney_id      INT          NULL,
    refresh_token_id INT NULL,
    PRIMARY KEY (id),
    UNIQUE (username),
    FOREIGN KEY (client_id) REFERENCES client (id)
);


CREATE TABLE user_role
(
    id        int         NOT NULL AUTO_INCREMENT,
    user_id   int         NOT NULL,
    role_name varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);
CREATE TABLE law_case (
    id int AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    startingDate DATE NOT NULL,
    information LONGTEXT NOT NULL,
    status BOOLEAN NOT NULL,
    isCaseWon BOOLEAN NOT NULL

);
CREATE TABLE attorney_case (
        id int AUTO_INCREMENT PRIMARY KEY,
        attorney_id int  NOT NULL,
        case_id int  NOT NULL
);
CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          from_user_id int,
                          to_user_id int,
                          text TEXT,
                          timestamp TIMESTAMP
);
CREATE TABLE refresh_token (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id int not null,
                               token VARCHAR(255) NOT NULL UNIQUE,
                               expiry_date DATETIME NOT NULL
);