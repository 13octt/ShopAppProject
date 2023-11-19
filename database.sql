CREATE TABLE `users` (
    `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `full_name` varchar(100) NOT NULL,
    `phone_number` varchar(20) NOT NULL,
    `address` varchar(100) DEFAULT NULL,
    `password` varchar(100) NOT NULL,
    `created_time` datetime(6) NOT NULL,
    `updated_time` datetime(6) NOT NULL,
    `is_active` tinyint(1) DEFAULT 1,
    `date_of_birth` date NOT NULL,
    `facebook_account_id` int(11) DEFAULT 0,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73189 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tokens` (
	`token_id` int(11) NOT NULL AUTO_INCREMENT,
    `token` varchar(255) UNIQUE NOT NULL,
    `token_type` varchar(50) NOT NULL,
    `expiration_date` datetime(6) DEFAULT NULL,
    `revoked` tinyint(1) NOT NULL, 
    `expired` tinyint(1) NOT NULL, 
    `user_id` bigint(11) NOT NULL, 
    PRIMARY KEY (`token_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT= 1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `social_accounts` (
	`social_account_id` int NOT NULL AUTO_INCREMENT,
    `provider` varchar(20) NOT NULL COMMENT 'Name of Social Network',
    `provider_id` varchar(50) NOT NULL,
    `email` varchar(150) NOT NULL,
    `name` varchar(100) NOT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`social_account_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
);

CREATE TABLE `categories` (
	`category_id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    PRIMARY KEY (`category_id`)
);

CREATE TABLE `products` (
	`product_id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(350) NOT NULL,
    `price` float NOT NULL,
    `thumbnail` VARCHAR(300) DEFAULT NULL,
    `description` LONGTEXT DEFAULT NULL,
    `created_time` datetime NOT NULL,
    `updated_time` datetime NOT NULL,
    `category_id` int NOT NULL,
    PRIMARY KEY (`product_id`),
    FOREIGN KEY (`category_id`) REFERENCES categories(`category_id`)
);

ALTER TABLE `shopapp`.`products` 
CHANGE `price` `price` float NOT NULL CHECK (price >= 0)

CREATE TABLE `roles` (
	`role_id` int,
    `name` varchar(20) NOT NULL,
    PRIMARY KEY (`role_id`)
);

ALTER TABLE `shopapp`.`users` 
ADD COLUMN `role_id` int AFTER `facebook_account_id`

ALTER TABLE `users`
ADD FOREIGN KEY (`role_id`) REFERENCES roles(`role_id`)

CREATE TABLE `orders` (
	`order_id` int NOT NULL AUTO_INCREMENT,
    `user_id` bigint NOT NULL,
    `full_name` varchar(100) DEFAULT NULL,
    `email` varchar(100) DEFAULT NULL,
    `phone_number` varchar(20) NOT NULL,
    `note` varchar(200) DEFAULT NULL,
    `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
    `status` varchar(20),
    `total_money` float CHECK (total_money >= 0),
	PRIMARY KEY (`order_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
);

ALTER TABLE `orders` ADD COLUMN `shipping_method` varchar(100) AFTER `total_money`
ALTER TABLE `orders` ADD COLUMN `shipping_address` varchar(200) AFTER `shipping_method`
ALTER TABLE `orders` ADD COLUMN `shipping_date` date AFTER `shipping_address`
ALTER TABLE `orders` ADD COLUMN `tracking_number` varchar(100) AFTER `shiorder_detailspping_date`
ALTER TABLE `orders` ADD COLUMN `payment_method` varchar(100) AFTER `tracking_number`
ALTER TABLE `orders` ADD COLUMN `active` tinyint(1) AFTER `payment_method`
ALTER TABLE `orders` 
MODIFY COLUMN `status` ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled')

CREATE TABLE `order_details` (
	`order_detail_id` int not null auto_increment,
    `order_id` int NOT NULL,
    `product_id` int NOT NULL,
    `price` float CHECK(price >=0),orders
    `number_of_products` int CHECK (number_of_products > 0),
    `total_money` float CHECK(total_money >= 0),
    `color` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`order_detail_id`),
    FOREIGN KEY (`order_id`) REFERENCES orders(`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES products(`product_id`)
);








