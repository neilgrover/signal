/******************** Add Table: daily_serving ************************/

DROP TABLE daily_serving IF EXISTS;
/* Build Table Structure */
CREATE TABLE daily_serving
(
	id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	date_created TIMESTAMP DEFAULT now() NOT NULL,
	date_updated TIMESTAMP DEFAULT now() NOT NULL,
	food_group_id CHAR(2) NOT NULL,
	gender CHAR(1) NOT NULL,
	min_age INTEGER UNSIGNED NOT NULL,
	max_age INTEGER UNSIGNED NULL,
	min_serving INTEGER UNSIGNED NOT NULL,
	max_serving INTEGER UNSIGNED NULL
);


/******************** Add Table: family ************************/

DROP TABLE family IF EXISTS;
/* Build Table Structure */
CREATE TABLE family
(
	id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	date_created TIMESTAMP DEFAULT now() NOT NULL,
	date_updated TIMESTAMP DEFAULT now() NOT NULL
);


/******************** Add Table: food ************************/

DROP TABLE food IF EXISTS;
/* Build Table Structure */
CREATE TABLE food
(
	id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	food_group_category_id INTEGER UNSIGNED NOT NULL,
	name TEXT NOT NULL,
	serving_size TEXT NOT NULL,
	date_created TIMESTAMP DEFAULT now() NOT NULL,
	date_updated TIMESTAMP DEFAULT now() NOT NULL
);


/******************** Add Table: food_group ************************/

DROP TABLE food_group IF EXISTS;
/* Build Table Structure */
CREATE TABLE food_group
(
	id CHAR(2) NOT NULL,
	name TEXT NOT NULL,
	date_created TIMESTAMP DEFAULT now() NOT NULL,
	date_updated TIMESTAMP DEFAULT now() NOT NULL
);

/* Add Primary Key */
ALTER TABLE food_group ADD CONSTRAINT pkfood_group
	PRIMARY KEY (id);


/******************** Add Table: food_group_category ************************/

DROP TABLE food_group_category IF EXISTS;
/* Build Table Structure */
CREATE TABLE food_group_category
(
	id INTEGER UNSIGNED NOT NULL,
	date_created TIMESTAMP DEFAULT now() NOT NULL,
	date_updated TIMESTAMP DEFAULT now() NOT NULL,
	name TEXT NOT NULL,
	food_group_id CHAR(2) NOT NULL
);

/* Add Primary Key */
ALTER TABLE food_group_category ADD CONSTRAINT pkfood_group_category
	PRIMARY KEY (id);


/******************** Add Table: food_group_statement ************************/

/* Build Table Structure */
DROP TABLE food_group_statement IF EXISTS;
CREATE TABLE food_group_statement
(
	id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	date_created TIMESTAMP DEFAULT now() NOT NULL,
	date_updated TIMESTAMP DEFAULT now() NOT NULL,
	food_group_id CHAR(2) NOT NULL,
	statement_text TEXT NOT NULL
);


/******************** Add Table: person ************************/

DROP TABLE person IF EXISTS;
/* Build Table Structure */
CREATE TABLE person
(
	username VARCHAR(512) NOT NULL,
	family_id INTEGER UNSIGNED NULL,
	date_created TIMESTAMP DEFAULT now() NOT NULL,
	date_updated TIMESTAMP DEFAULT now() NOT NULL,
	gender CHAR(1) NOT NULL,
	age INTEGER UNSIGNED NOT NULL
);

/* Add Primary Key */
ALTER TABLE person ADD CONSTRAINT pkperson
	PRIMARY KEY (username);

/************ Add Foreign Keys ***************/

/* Add Foreign Key: fk_daily_serving_food_group */
ALTER TABLE daily_serving ADD CONSTRAINT fk_daily_serving_food_group
	FOREIGN KEY (food_group_id) REFERENCES food_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_food_food_group_category */
ALTER TABLE food ADD CONSTRAINT fk_food_food_group_category
	FOREIGN KEY (food_group_category_id) REFERENCES food_group_category (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_food_group_category_food_group */
ALTER TABLE food_group_category ADD CONSTRAINT fk_food_group_category_food_group
	FOREIGN KEY (food_group_id) REFERENCES food_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_food_group_statement_food_group */
ALTER TABLE food_group_statement ADD CONSTRAINT fk_food_group_statement_food_group
	FOREIGN KEY (food_group_id) REFERENCES food_group (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;

/* Add Foreign Key: fk_person_family */
ALTER TABLE person ADD CONSTRAINT fk_person_family
	FOREIGN KEY (family_id) REFERENCES family (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;