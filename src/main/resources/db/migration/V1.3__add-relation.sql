ALTER TABLE patient 
ADD CONSTRAINT fk_patient_address_id FOREIGN KEY (address_id) 
  	REFERENCES address(id)
  	ON DELETE SET NULL;