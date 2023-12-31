CREATE TABLE credit (
  crd_cd_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   crd_uid_credit_code UUID NOT NULL,
   crd_big_credit_value DECIMAL NOT NULL,
   crd_dt_day_first_installment date NOT NULL,
   crd_int_number_of_installments INTEGER NOT NULL,
   crd_enm_status SMALLINT NOT NULL,
   crd_fk_customer BIGINT NOT NULL,
   CONSTRAINT pk_credit PRIMARY KEY (crd_cd_id)
);

ALTER TABLE credit ADD CONSTRAINT uc_credit_crd_uid_credit_code UNIQUE (crd_uid_credit_code);

ALTER TABLE credit ADD CONSTRAINT FK_CREDIT_ON_CRD_FK_CUSTOMER FOREIGN KEY (crd_fk_customer) REFERENCES customer (ctm_cd_id);