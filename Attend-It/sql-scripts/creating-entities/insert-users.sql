USE `isd-entities`;

INSERT INTO `user` (first_name, last_name, email, password, verified)
VALUES
    ('John', 'Doe', 'john@example.com', 'password1', false),
    ('Alice', 'Smith', 'alice@example.com', 'password2', false),
    ('Bob', 'Johnson', 'bob@example.com', 'password3', false),
    ('Emily', 'Brown', 'emily@example.com', 'password4', false),
    ('Michael', 'Davis', 'michael@example.com', 'password5', false);
