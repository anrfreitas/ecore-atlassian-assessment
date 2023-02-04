CREATE TABLE customer (
	id int8 NOT NULL,
	name varchar(255) NOT NULL,
	email varchar(255) UNIQUE NOT NULL,
    telephone varchar(255) NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (id)
);