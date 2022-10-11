-----------------
-- TABLE users --
-----------------
drop table if exists users;
CREATE TABLE users
(
    id       int NOT NULL PRIMARY KEY auto_increment,
    email    varchar(50) NOT NULL UNIQUE,
    username varchar(20) NOT NULL UNIQUE
);
--
-- -----------------
-- -- TABLE roles --
-- -----------------
drop table if exists documents;
CREATE TABLE documents
(
    id     int NOT NULL PRIMARY KEY auto_increment,
    name   varchar(20) NOT NULL,
    idUser int,
    activo bit,
    url    varchar(200)
);

-- ----------------------
-- -- TABLE user_roles --
-- ----------------------
-- CREATE TABLE user_roles (
--     user_id bigint(20) NOT NULL PRIMARY KEY,
--     role_id int(11)    NOT NULL PRIMARY KEY
-- );

-- INSERT INTO roles(name) VALUES('ROLE_USER');
-- INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
-- INSERT INTO roles(name) VALUES('ROLE_ADMIN');


INSERT INTO users(email, username)
VALUES ('ejemplo@ejemplo.com', 'chuy');