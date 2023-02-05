CREATE TABLE customer (
	id SERIAL,
	name varchar(255) NOT NULL,
	email varchar(255) UNIQUE NOT NULL,

	CONSTRAINT customer_pkey PRIMARY KEY (id)
);