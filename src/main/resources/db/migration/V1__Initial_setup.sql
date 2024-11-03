-- Create the patients table
CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the doctors table
CREATE TABLE doctors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR(255),
    phone_number VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the consults table
CREATE TABLE consults (
    id SERIAL PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    date TIMESTAMP NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);

-- Create the pathologies table
CREATE TABLE pathologies (
    id SERIAL PRIMARY KEY,
    patient_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients (id)
);

-- Create the symptoms table
CREATE TABLE symptoms (
    id SERIAL PRIMARY KEY,
    pathology_id INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    FOREIGN KEY (pathology_id) REFERENCES pathologies (id)
);
