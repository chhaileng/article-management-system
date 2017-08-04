CREATE TABLE IF NOT EXISTS tb_category (
	category_id serial PRIMARY KEY NOT NULL,
	category_name varchar(255)
);

CREATE TABLE IF NOT EXISTS tb_article (
	id serial PRIMARY KEY NOT NULL,
	title varchar(255),
	description text,
	thumbnail varchar(255),
	date varchar(15),
	author varchar(255),
	category_id int4 REFERENCES tb_category (category_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_user (
	id serial PRIMARY KEY NOT NULL,
	fullname varchar(255),
	username varchar(255),
	password varchar(255),
	status varchar,
	photo varchar
);

CREATE TABLE IF NOT EXISTS tb_role (
	id serial PRIMARY KEY NOT NULL,
	role varchar
);

CREATE TABLE IF NOT EXISTS tb_user_role (
	user_id int4 NOT NULL,
	role_id int4 NOT NULL,
	date timestamp(6) NULL DEFAULT now(),
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES tb_user(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (role_id) REFERENCES tb_role(id) ON UPDATE CASCADE ON DELETE CASCADE
)