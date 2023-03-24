CREATE TABLE IF NOT EXISTS medication (
    id SERIAL,
    startDate DATE,
    endDate DATE,
    application_id int,
    CONSTRAINT med FOREIGN KEY(application_id) REFERENCES applications(id),
    PRIMARY KEY (id)
    
);

CREATE TABLE IF NOT EXISTS family (
   id SERIAL PRIMARY KEY,
   first_name VARCHAR(20) NOT NULL,
   last_name VARCHAR(20) NOT NULL,
   gender VARCHAR(20) NOT NULL,
   birth_date DATE NOT NULL,
   birth_country VARCHAR(20) NOT NULL,
   citizen_country VARCHAR(20) NOT NULL,
   email VARCHAR(50),
   application_id int,
   CONSTRAINT fam FOREIGN KEY(application_id) REFERENCES applications(id)
    
);


INSERT INTO family (
          first_name,
          last_name,
          gender,
          birth_date,
          birth_country,
          citizen_country,
          email,
          application_id
         
     )
VALUES (
          'Ayaan',
          'Pupala',
          'Male',
          '1999-06-07',
          'India',
          'India',
          'ayaansp@gmail.com',
          '1'
     );
INSERT INTO family (
          first_name,
          last_name,
          gender,
          birth_date,
          birth_country,
          citizen_country,
          email,
          application_id
         
     )
VALUES (
          'Samar',
          'Pupala',
          'Male',
          '1999-06-07',
          'India',
          'India',
          'ayaansp@gmail.com',
          '1'
     );
     
INSERT INTO medication (

          startDate,
          endDate,
          application_id
         
     )
VALUES (
          
          '1999-06-07',
          '1999-06-30',
          '1'
        
     );
INSERT INTO medication (

          startDate,
          endDate,
          application_id
         
     )
VALUES (
          
          '1999-07-07',
          '2000-06-30',
          '1'
        
     );