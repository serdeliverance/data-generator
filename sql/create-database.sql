-- User Transaction
DROP DATABASE IF EXISTS utdb;
CREATE DATABASE utdb;
\c utdb;

DROP TABLE IF EXISTS "user";

CREATE TABLE IF NOT EXISTS "user" (
  id BIGSERIAL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS "transaction";

CREATE TABLE IF NOT EXISTS "transaction" (
  id BIGSERIAL,
  amount NUMERIC(22,0) NOT NULL,
  card_last_digits VARCHAR(255) NOT NULL,
  date_time VARCHAR(255) NOT NULL,
  installments SMALLINT NOT NULL,
  card_type VARCHAR(255) NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  status  VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

-- crypto DB

DROP DATABASE IF EXISTS cryptodb;
CREATE DATABASE cryptodb;
\c cryptodb;

DROP TABLE IF EXISTS "user";

CREATE TABLE IF NOT EXISTS "user" (
  id BIGSERIAL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS "cryptocurrency";

CREATE TABLE IF NOT EXISTS "cryptocurrency" (
  id BIGSERIAL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS "portfolio";

CREATE TABLE IF NOT EXISTS "portfolio" (
  id BIGSERIAL,
  user_id NUMERIC(22,0) NOT NULL,
  crypto_currency_id VARCHAR(255) NOT NULL,
  amount VARCHAR(255) NOT NULL,
  PRIMARY KEY(id),
  CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES "user"(id),
  CONSTRAINT fk_cryptocurrency FOREIGN KEY(crypto_currency_id) REFERENCES cryptocurrency(id)
);