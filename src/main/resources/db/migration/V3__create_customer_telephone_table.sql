CREATE TABLE customer_telephone (
	id int8 NOT NULL,
    customer_id int8 NOT NULL,
	phone varchar(255) NOT NULL,

    CONSTRAINT telephone_pkey PRIMARY KEY (id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);