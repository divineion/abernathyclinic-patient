-- -----------------------------------------------------
-- Table patient
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS patient (
  id BIGSERIAL PRIMARY KEY,
  uuid UUID NOT NULL UNIQUE,
  last_name VARCHAR(80) NOT NULL,
  first_name VARCHAR(80) NOT NULL,
  birth_date DATE NOT NULL,
  gender VARCHAR(10) NOT NULL,
  address_id int NULL,
  phone VARCHAR(20) NULL
);
