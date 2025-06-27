-- Create City table
CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    state VARCHAR(45) NOT NULL
);

-- Create Specialty table
CREATE TABLE specialty (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL
);

-- Create Mentor table
CREATE TABLE mentor (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    description VARCHAR(200) NOT NULL,
    email VARCHAR(80) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    city_id INTEGER NOT NULL,
    specialty_id INTEGER NOT NULL,
    CONSTRAINT fk_mentor_city FOREIGN KEY (city_id) REFERENCES city(id),
    CONSTRAINT fk_mentor_specialty FOREIGN KEY (specialty_id) REFERENCES specialty(id)
);

-- Create indexes for better performance
CREATE INDEX idx_mentor_city_id ON mentor(city_id);
CREATE INDEX idx_mentor_specialty_id ON mentor(specialty_id);
CREATE INDEX idx_mentor_email ON mentor(email);
