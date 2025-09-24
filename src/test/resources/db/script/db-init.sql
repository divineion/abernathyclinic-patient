CREATE SCHEMA IF NOT EXISTS test_abernathyclinic_patient;

SET SCHEMA test_abernathyclinic_patient;

-- -----------------------------------------------------
-- Table city
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS city (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  zip VARCHAR(5) NOT NULL,
  CONSTRAINT uq_city_name_zip UNIQUE (name, zip)
);

-- -----------------------------------------------------
-- Table street
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS street (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  city_id INT NOT NULL,
  CONSTRAINT fk_street_city FOREIGN KEY (city_id)
  REFERENCES city(id)
);

ALTER TABLE street ADD CONSTRAINT uq_street_name_city UNIQUE (name, city_id);

-- -----------------------------------------------------
-- Table address
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS address (
  id INT PRIMARY KEY AUTO_INCREMENT,
  street_number VARCHAR(5),
  street_id INT NOT NULL,
  CONSTRAINT uq_address_street_number UNIQUE (street_number, street_id)
);

-- -----------------------------------------------------
-- Table patient
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS patient (
  id INT PRIMARY KEY AUTO_INCREMENT,
  uuid UUID NOT NULL UNIQUE,
  last_name VARCHAR(80) NOT NULL,
  first_name VARCHAR(80) NOT NULL,
  birth_date DATE NOT NULL,
  gender VARCHAR(10) NOT NULL,
  address_id INT NULL,
  phone VARCHAR(20) NULL,
  CONSTRAINT fk_patient_address FOREIGN KEY (address_id) 
  	REFERENCES address(id)
  	ON DELETE SET NULL
);

-- -----------------------------------------------------
-- Data for table city
-- -----------------------------------------------------
INSERT INTO city(name, zip)
VALUES('Cambridge', '10139');

INSERT INTO city(name, zip)
VALUES('North Platte', '29101');

INSERT INTO city(name, zip)
VALUES('San Diego', '30139');

INSERT INTO city(name, zip)
VALUES('Bethia', '42103');

-- -----------------------------------------------------
-- Data for table street
-- -----------------------------------------------------
INSERT INTO street(name, city_id)
VALUES( 'Brookside St', 1);

INSERT INTO street(name, city_id)
VALUES('Kyle Street', 2);

INSERT INTO street(name, city_id)
VALUES('Randall Drive', 3);

INSERT INTO street(name, city_id)
VALUES('Flemming Wayr', 4);

-- -----------------------------------------------------
-- Data for table address
-- -----------------------------------------------------
INSERT INTO address(street_number, street_id)
VALUES ('101', 1);

INSERT INTO address(street_number, street_id)
VALUES ('288', 2);

INSERT INTO address(street_number, street_id)
VALUES ('3685', 3);

INSERT INTO address(street_number, street_id)
VALUES ('4335', 4);

-- -----------------------------------------------------
-- Data for table patient
-- -----------------------------------------------------

INSERT INTO patient(uuid, last_name, first_name, birth_date, gender, address_id, phone) 
VALUES('ad1ca72c-a9a4-4e26-b634-55659f2b8423', 'Almeida', 'Mary', '1966-12-31', 'F', 1, '100-222-3333');

INSERT INTO patient(uuid, last_name, first_name, birth_date, gender, address_id, phone) 
VALUES('e64932dd-4d17-4070-aafb-8b2dd309c628', 'Bacho', 'Dewey', '1966-12-31', 'M', 2, '200-222-3333');

INSERT INTO patient(uuid, last_name, first_name, birth_date, gender, address_id, phone) 
VALUES('101b8bbc-b56e-4549-bc31-d1525340353c', 'Cimms', 'Samuel', '1966-12-31', 'M', 3, '300-222-3333');

INSERT INTO patient(uuid, last_name, first_name, birth_date, gender, address_id, phone) 
VALUES('aa1358cb-024b-4e59-a03c-5c463aee170b', 'Destarac', 'Leonor', '1966-12-31', 'F', 4, '400-555-666');


  	