CREATE TABLE profile_entity (
	cpf VARCHAR2(11) NOT NULL,
	email VARCHAR2(50) NOT NULL,
	full_name VARCHAR2(120) NOT NULL,
	phone_number VARCHAR2(13) NOT NULL,
	first_name VARCHAR2(50) NOT NULL,
	last_name VARCHAR2(70) NOT NULL
);

ALTER TABLE profile_entity ADD CONSTRAINT profile_entity_pk PRIMARY KEY (cpf);
CREATE INDEX profile_entity_email_idx ON profile_entity(email);