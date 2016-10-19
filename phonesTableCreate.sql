use nc1_phoneBook;
create table if not exists `phones` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(30) NOT NULL,
	`phoneNumber` VARCHAR(15) NOT NULL,
	PRIMARY KEY(`id`),
	UNIQUE(`name`,`phoneNumber`)
);
