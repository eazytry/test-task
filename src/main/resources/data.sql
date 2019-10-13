DROP TABLE IF EXISTS account;

CREATE TABLE account (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  balance DECIMAL(19, 2) NOT NULL, CHECK (balance >= 0)
);

INSERT INTO account values ( 0, 1000.00 ), ( 1, 5000.00 );