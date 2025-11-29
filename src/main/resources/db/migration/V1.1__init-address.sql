-- -----------------------------------------------------
-- Table city
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS city (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  zip VARCHAR(5) NOT NULL,
  UNIQUE (name, zip)
);

-- -----------------------------------------------------
-- Table street
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS street (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  city_id INT NOT NULL,
  UNIQUE (name, city_id),
  CONSTRAINT fk_street_city_id 
  	FOREIGN KEY (city_id)
  	REFERENCES city(id)
);

-- -----------------------------------------------------
-- Table address
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS address (
  id BIGSERIAL PRIMARY KEY,
  street_number VARCHAR(5) NOT NULL,
  street_id INT NOT NULL,
  UNIQUE (street_number, street_id),
  CONSTRAINT fk_address_street_id 
    FOREIGN KEY (street_id) 
    REFERENCES street(id)
);
