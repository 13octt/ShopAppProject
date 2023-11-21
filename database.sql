DROP DATABASE `shop_app`
CREATE DATABASE `shop_app`
USE `shop_app`

CREATE TABLE `roles` (
	`role_id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(20) NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `users` (
    `user_id` bigint NOT NULL AUTO_INCREMENT,
    `full_name` varchar(100) NOT NULL,
    `phone_number` varchar(20) NOT NULL,
    `address` varchar(100) DEFAULT NULL,
    `password` varchar(100) NOT NULL,
    `created_time` datetime(6) NOT NULL,
    `updated_time` datetime(6) NOT NULL,
    `is_active` tinyint DEFAULT 1,
    `date_of_birth` date NOT NULL,
    `facebook_account_id` int DEFAULT 0,
    `google_acount_id` int DEFAULT 0,
    `role_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (`role_id`) REFERENCES roles(`role_id`)
);

CREATE TABLE `tokens` (
	`token_id` bigint NOT NULL AUTO_INCREMENT,
    `token` varchar(255) UNIQUE NOT NULL,
    `token_type` varchar(50) NOT NULL,
    `expiration_date` datetime(6) DEFAULT NULL,
    `revoked` tinyint NOT NULL, 
    `expired` tinyint NOT NULL, 
    `user_id` bigint NOT NULL, 
    PRIMARY KEY (`token_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
);

CREATE TABLE `social_accounts` (
	`social_account_id` bigint NOT NULL AUTO_INCREMENT,
    `provider` varchar(20) NOT NULL,
    `provider_id` varchar(50) NOT NULL,
    `email` varchar(150) NOT NULL,
    `name` varchar(100) NOT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`social_account_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
);

CREATE TABLE `categories` (
	`category_id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    PRIMARY KEY (`category_id`)
);


CREATE TABLE `products` (
	`product_id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(350) NOT NULL,
    `price` float NOT NULL CHECK (price >= 0),
    `thumbnail` VARCHAR(300) DEFAULT NULL,
    `description` LONGTEXT DEFAULT NULL,
    `created_time` datetime NOT NULL,
    `updated_time` datetime NOT NULL,
    `category_id` bigint NOT NULL,
    PRIMARY KEY (`product_id`),
    FOREIGN KEY (`category_id`) REFERENCES categories(`category_id`)
);

CREATE TABLE `orders` (
	`order_id` bigint NOT NULL AUTO_INCREMENT,
    `user_id` bigint NOT NULL,
    `full_name` varchar(100) DEFAULT NULL,
    `email` varchar(100) DEFAULT NULL,
    `phone_number` varchar(20) NOT NULL,
    `note` varchar(200) DEFAULT NULL,
    `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
    `status` ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled'),
    `total_money` float CHECK (total_money >= 0),
	`shipping_method` varchar(100) ,
	`shipping_address` varchar(200) ,
	`shipping_date` date,
	`tracking_number` varchar(100),
	`payment_method` varchar(100),
	`active` tinyint,
	PRIMARY KEY (`order_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
);

CREATE TABLE `order_details` (
	`order_detail_id` bigint NOT NULL AUTO_INCREMENT	,
    `order_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `price` float CHECK(price >=0),
    `number_of_products` int CHECK (number_of_products > 0),
    `total_money` float CHECK(total_money >= 0),
    `color` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`order_detail_id`),
    FOREIGN KEY (`order_id`) REFERENCES orders(`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES products(`product_id`)
);