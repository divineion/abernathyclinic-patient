Microservice Patient – Quick Setup
1. Install PostgreSQL

Install PostgreSQL on your machine (Linux/macOS/Windows).
Make sure the psql command is available.

2. Connect to PostgreSQL and Create the Database
Open a terminal and run:
`psql -U postgres`

Inside the shell:
`CREATE DATABASE abernathyclinic_patient`;

3. Environment Variables
Create a .env file and set your own local values:

`DATABASE_URL=jdbc:postgresql://localhost:5432/abernathyclinic_patient`
`DATABASE_USERNAME=postgres`
`DATABASE_PASSWORD=postgres`
`DATABASE_DRIVER=org.postgresql.Driver`


Replace the default username and password, host, and port if your setup is different.

4. Requirements

Java (17+ recommended)
Maven
PostgreSQL installed and running

5. Run the Microservice
`mvn spring-boot:run`


The microservice will start on port 8081 using your local database.