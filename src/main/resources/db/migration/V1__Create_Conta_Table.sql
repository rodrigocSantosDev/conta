CREATE TABLE conta (
   id SERIAL PRIMARY KEY,
   data_vencimento DATE NOT NULL,
   data_pagamento DATE,
   valor NUMERIC(19, 2) NOT NULL,
   descricao VARCHAR(255) NOT NULL,
   situacao VARCHAR(50) NOT NULL
);
