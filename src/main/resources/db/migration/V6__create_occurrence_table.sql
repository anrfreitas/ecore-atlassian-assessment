CREATE TABLE occurrence (
	id SERIAL,
	created_at TIMESTAMP NULL,

    CONSTRAINT occurrence_pkey PRIMARY KEY (id)
);

ALTER TABLE occurrence ALTER COLUMN created_at SET DEFAULT now();
