DROP ALL OBJECTS DELETE FILES;
CREATE USER shopping_admin password 'jiYdar67';
CREATE SCHEMA monolith AUTHORIZATION shopping_admin;
CREATE TABLE monolith.item (
	id		varchar(40)	PRIMARY KEY,
	name		varchar(50)	UNIQUE NOT NULL,
	description	varchar(75)	UNIQUE NOT NULL,
	brand		varchar(50)	NOT NULL	
);

CREATE TABLE monolith.inventory (
	id		varchar(40)	PRIMARY KEY,
	itemId		varchar(40)	NOT NULL,
	category	enum('Home', 'Office', 'Outdoors', 'Eyewear', 'Tools', 'Furniture', 'Accessories', 'Personal')	NOT NULL,
	subCategory	enum('Handtools', 'Boat', 'Glasses', 'MobilePhone', 'Furniture', 'Haircare', 'Other')	NOT NULL,
	quantity	smallint	NOT NULL,
	price		decimal(5, 2)	NOT NULL,
	foreign key (itemId) references monolith.item(id)
);
CREATE TABLE monolith.account (
	id		varchar(40)	PRIMARY KEY,
	startDate	DATE		NOT NULL,
	lastPurchase	DATE		NOT NULL,
	points		INT		NOT NULL
);
CREATE TABLE monolith.customer (
	id	  	varchar(40)	PRIMARY KEY,
	fname		varchar(25)	NOT NULL,
	lname		varchar(30)	NOT NULL,
	address		varchar(40)	NOT NULL,
	city		varchar(30)	NOT NULL,
	state		varchar(2)	NOT NULL,
	zip		varchar(12)	NOT NULL,
	phone		varchar(11)	NOT NULL,
	accountId	varchar(40)	NOT NULL,
	foreign key (accountId) references monolith.account(id)
);
CREATE TABLE monolith.purchase_order (
	id		varchar(40)	PRIMARY KEY,
	customerId	varchar(40)	NOT NULL,
	orderDate	DATE		NOT NULL,
	foreign key (customerId) references monolith.customer(id)
);
CREATE TABLE monolith.item_ordered (
	id		varchar(40)	PRIMARY KEY,
	itemId		varchar(40)	NOT NULL,
	orderId		varchar(40)	NOT NULL,
	quantity	INT		NOT NULL, 
	foreign key (itemId) references monolith.item(id),
	foreign key (orderId) references monolith.purchase_order(id)
);

