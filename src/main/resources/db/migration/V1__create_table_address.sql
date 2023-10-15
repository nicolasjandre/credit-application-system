CREATE TABLE address (
  adr_cd_zip_code VARCHAR(9) NOT NULL,
   ctm_tx_address VARCHAR(255),
   ctm_tx_complement VARCHAR(255),
   ctm_tx_neighborhood VARCHAR(255),
   ctm_tx_locality VARCHAR(255),
   ctm_tx_state VARCHAR(2),
   ctm_tx_ibge VARCHAR(255),
   ctm_tx_gia VARCHAR(255),
   ctm_tx_ddd VARCHAR(255),
   ctm_tx_siafi VARCHAR(255),
   CONSTRAINT pk_address PRIMARY KEY (adr_cd_zip_code)
);