INSERT into customer (email, name) values ('user1@gmail.com', 'user1');
INSERT into customer (email, name) values ('user2@gmail.com', 'user2');
INSERT into customer (email, name) values ('user3@gmail.com', 'user3');
INSERT into customer (email, name) values ('user4@gmail.com', 'user4');

ALTER SEQUENCE customer_id_seq RESTART WITH 100;
