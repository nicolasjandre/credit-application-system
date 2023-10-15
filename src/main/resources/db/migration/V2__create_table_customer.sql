CREATE TABLE customer (
  ctm_cd_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   ctm_tx_first_name VARCHAR(255) NOT NULL,
   ctm_tx_last_name VARCHAR(255) NOT NULL,
   ctm_tx_cpf VARCHAR(255) NOT NULL,
   ctm_tx_email VARCHAR(255) NOT NULL,
   ctm_big_income DECIMAL NOT NULL,
   ctm_tx_password VARCHAR(255) NOT NULL,
   ctm_fk_address VARCHAR(8) NOT NULL,
   CONSTRAINT pk_customer PRIMARY KEY (ctm_cd_id)
);

ALTER TABLE customer ADD CONSTRAINT uc_customer_ctm_tx_cpf UNIQUE (ctm_tx_cpf);

ALTER TABLE customer ADD CONSTRAINT uc_customer_ctm_tx_email UNIQUE (ctm_tx_email);

ALTER TABLE customer ADD CONSTRAINT FK_CUSTOMER_ON_CTM_FK_ADDRESS FOREIGN KEY (ctm_fk_address) REFERENCES address (adr_cd_zip_code);