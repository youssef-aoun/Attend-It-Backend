USE `isd-entities`;

INSERT INTO `user` (username, first_name, last_name, email, password, verified)
VALUES
    ('johndoe','John', 'Doe', 'john@example.com', '{bcrypt}$2a$10$1cySlXFdnK50lKX4qLpqIOSunQIGMFfcJsxTo3phO3Ezjz9KGkcvK', false),
    ('alicesmith','Alice', 'Smith', 'alice@example.com', '{bcrypt}$2a$10$1cySlXFdnK50lKX4qLpqIOSunQIGMFfcJsxTo3phO3Ezjz9KGkcvK', false),
    ('bobjohnson','Bob', 'Johnson', 'bob@example.com', '{bcrypt}$2a$10$1cySlXFdnK50lKX4qLpqIOSunQIGMFfcJsxTo3phO3Ezjz9KGkcvK', false),
    ('emilybrown','Emily', 'Brown', 'emily@example.com', '{bcrypt}$2a$10$1cySlXFdnK50lKX4qLpqIOSunQIGMFfcJsxTo3phO3Ezjz9KGkcvK', false),
    ('michaeldavis','Michael', 'Davis', 'michael@example.com', '{bcrypt}$2a$10$1cySlXFdnK50lKX4qLpqIOSunQIGMFfcJsxTo3phO3Ezjz9KGkcvK', false);