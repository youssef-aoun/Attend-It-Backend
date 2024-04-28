use `isd-entities`;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id   int,
    name VARCHAR(20)
);


INSERT INTO roles
VALUES (0, 'ROLE_USER'),
       (1, 'ROLE_ADMIN'),
       (2, 'ROLE_EMPLOYEE');