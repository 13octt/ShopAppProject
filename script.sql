USE `shop_app`

SELECT * FROM categories

SELECT * FROM product_images

SELECT * FROM products WHERE product_id = 2

ALTER TABLE product_images
DROP FOREIGN KEY FKqnq71xsohugpqwf3c9gxmsuy;

SELECT * FROM products

SELECT * FROM users

INSERT INTO users(full_name, phone_number, address, date_of_birth, password, created_time, updated_time, role_id)
VALUES ('Lam Bao Duy', '123', 'Ho Chi Minh', '2000-12-12', '1234', '2020-12-12', '2020-12-12', 1)

SHOW ERRORS

INSERT INTO users (
    full_name,
    phone_number,
    address,
    password,
    created_time,
    updated_time,
    is_active,
    date_of_birth,
    facebook_account_id,
    google_account_id,
    role_id
) VALUES (
    'John Doe',
    '123456789',
    '123 Main St',
    'hashed_password', -- replace with the actual hashed password
    NOW(), -- replace with the current timestamp
    NOW(), -- replace with the current timestamp
    1, -- or 0 based on your needs
    '1990-01-01', -- replace with the actual date of birth
    123456, -- replace with the actual Facebook account ID
    789012, -- replace with the actual Google account ID
    1 -- replace with the actual role_id from the roles table
);

INSERT INTO roles (role_name) VALUES ('RoleName');

SELECT * FROM roles

INSERT INTO roles(name) VALUES ('admin')
SELECT * FROM users

INSERT INTO users(is_active) VALUES (true) WHERE user_id = 3


