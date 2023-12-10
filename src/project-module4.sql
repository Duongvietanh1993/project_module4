CREATE DATABASE project_module4;
USE project_module4;

CREATE TABLE user
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100)        NOT NULL,
    email     VARCHAR(255) UNIQUE NOT NULL,
    password  VARCHAR(255)        NOT NULL,
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

CREATE TABLE product
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price       FLOAT        NOT NULL,
    stock       INT          NOT NULL,
    status      BIT(1) DEFAULT 1,
    category_id INT          NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE likes
(
    id_like    INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    product_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE images
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    image      VARCHAR(255),
    product_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id)
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
    SET user_name = new_user_name,
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

DELIMITER //
CREATE PROCEDURE PROC_FIND_USER_BY_EMAIL(IN new_email VARCHAR(255))
BEGIN
    SELECT * FROM user WHERE email = new_email;
END;
//

/*product*/

DELIMITER //
CREATE PROCEDURE PROC_SHOW_PRODUCT()
BEGIN
    SELECT * FROM product;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_CREATE_PRODUCT(
    IN new_name VARCHAR(100),
    IN new_description VARCHAR(255),
    IN new_price FLOAT,
    IN new_stock INT,
    IN new_status BIT(1),
    IN new_category_id INT
)
BEGIN
    INSERT INTO product (name, description, price, stock, status, category_id)
    VALUES (new_name, new_description, new_price, new_stock, new_status, new_category_id);
END;
//

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_PRODUCT(
    IN id_update INT,
    IN new_name VARCHAR(100),
    IN new_description VARCHAR(255),
    IN new_price FLOAT,
    IN new_stock INT,
    IN new_status BIT(1),
    IN new_category_id INT
)
BEGIN
    UPDATE product
    SET name        = new_name,
        description = new_description,
        price       = new_price,
        stock       = new_stock,
        status      = new_status,
        category_id= new_category_id
    WHERE id = id_update;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_FIND_PRODUCT_BY_ID(IN new_id INT)
BEGIN
    SELECT *FROM product WHERE id = new_id;
END;
//

DELIMITER //
CREATE PROCEDURE PROC_UPDATE_STATUS_PRODUCT(IN new_id INT, IN new_status BIT(1))
BEGIN
    UPDATE product SET status = new_status WHERE id = new_id;
END;
//