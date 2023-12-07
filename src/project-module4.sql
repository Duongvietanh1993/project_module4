CREATE DATABASE project_module4;
USE project_module4;
CREATE TABLE user
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255),
    user_name VARCHAR(100) UNIQUE,
    email     VARCHAR(255) UNIQUE,
    password  VARCHAR(255) NOT NULL,
    image     VARCHAR(255),
    phone     VARCHAR(255),
    address   VARCHAR(255),
    status    BIT(1) DEFAULT 1,
    role      BIT(1) DEFAULT 1
);

CREATE TABLE category
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(60)  NOT NULL,
    description VARCHAR(255) NOT NULL,
    status      BIT(1) DEFAULT 1
);

/*category*/
DELIMITER //
CREATE PROCEDURE PROC_SHOW_CATEGORY()
BEGIN
    SELECT * FROM category;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_CREATE_CATEGORY(
    IN new_name VARCHAR(60),
    IN new_description VARCHAR(255))
BEGIN
    INSERT INTO category (name, description) VALUES (new_name, new_description);
END;
//

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_CATEGORY(
    IN id_update INT,
    IN new_name VARCHAR(100),
    IN new_description VARCHAR(255),
    IN new_status BIT(1)
)
BEGIN
    UPDATE category
    SET name        = new_name,
        status      = new_status,
        description = new_description
    WHERE id = id_update;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_FIND_CATEGORY_BY_ID(IN new_id INT)
BEGIN
    SELECT *FROM category WHERE id = new_id;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_STATUS_CATEGORY(IN new_id INT, IN new_status BIT(1))
BEGIN
    UPDATE category SET status = new_status WHERE id = new_id;
END;
//

/*user*/
DELIMITER //
CREATE PROCEDURE PROC_SHOW_USER()
BEGIN
    SELECT * FROM user;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_CREATE_USER(
    IN new_user_name VARCHAR(100),
    IN new_email VARCHAR(255),
    IN new_password VARCHAR(255))
BEGIN
    INSERT INTO user (user_name, email, password) VALUES (new_user_name, new_email, new_password);
END;
//

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_USER(
    IN id_update INT,
    IN new_full_name INT,
    IN new_user_name VARCHAR(100),
    IN new_email VARCHAR(255),
    IN new_password VARCHAR(255),
    IN new_image VARCHAR(255),
    IN new_phone VARCHAR(100),
    IN new_address VARCHAR(255),
    IN new_status BIT(1),
    IN new_role BIT(1)
)
BEGIN
    UPDATE user
    SET full_name = new_full_name,
        user_name = new_user_name,
        email     = new_email,
        password  = new_password,
        image     = new_image,
        phone     = new_phone,
        address   = new_address,
        status    = new_status,
        role      = new_role
    WHERE id = id_update;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_FIND_USER_BY_ID(IN new_id INT)
BEGIN
    SELECT * FROM user WHERE id = new_id;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_STATUS_USER(IN new_id INT, IN new_status BIT(1))
BEGIN
    UPDATE user SET status = new_status WHERE id = new_id;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_ROLE_USER(IN new_id INT, IN new_role BIT(1))
BEGIN
    UPDATE user SET role = new_role WHERE id = new_id;
END;
//
