CREATE DATABASE `happytails`; 
USE `happytails`;

CREATE TABLE `pet` (
  `PetID` int(11) NOT NULL AUTO_INCREMENT,
  `PetName` varchar(100) NOT NULL,
  `Species` varchar(50) NOT NULL,
  `Breed` varchar(100) DEFAULT NULL,
  `Age` int(11) NOT NULL,
  `Gender` char(1) NOT NULL,
  `DateOfBirth` date NOT NULL,
  PRIMARY KEY (`PetID`)
);

delete from pet where petId<999;

ALTER table pet
ADD COLUMN picURL varchar(256),
ADD COLUMN Owner varchar(36) NOT NULL,

ADD CONSTRAINT FK_Owner FOREIGN KEY (Owner) REFERENCES user(user_id) ON DELETE CASCADE ON UPDATE CASCADE;

select * from pet;
delete from pet where petID>0;

INSERT INTO pet (PetID, PetName, Species, Breed, Age, Gender, DateOfBirth, Owner, picURL)
VALUES 
(1, 'Buddy', 'Dog', 'Golden Retriever', 3, 'M', '2021-06-15', 'eb0c9283-a79f-11ef-8d99-00155dc4b958', 'default/1.dog.png'),
(2, 'Mittens', 'Cat', 'Siamese', 2, 'F', '2022-03-10', 'eb0c9283-a79f-11ef-8d99-00155dc4b958', 'default/2.cat.png'),
(3, 'Charlie', 'Dog', 'Beagle', 5, 'M', '2019-09-25', 'eb0c9283-a79f-11ef-8d99-00155dc4b958', 'default/1.dog.png');

CREATE TABLE `user` (
  `user_id` varchar(36) NOT NULL,
  `name` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `user` VALUES ('3a80219a-a7ae-11ef-8d99-00155dc4b958','Buddhika Bandara','bandarabuddhika2@gmail.com','12121212'),('96e3a9fc-a7f0-11ef-99e4-00155dc4b958','Dehemi ','deheminimshara@gmail.com','dehemi123'),('eb0c9283-a79f-11ef-8d99-00155dc4b958','John Doe','John@happytails.org','12121212');

CREATE TABLE `measurements` (
  `MeasurementID` INT NOT NULL AUTO_INCREMENT,
  `PetID` INT NOT NULL,
  `MeasurementType` ENUM('weight', 'height') NOT NULL,
  `Value` DECIMAL(10,2) NOT NULL,
  `RecordDate` DATE NOT NULL,
  PRIMARY KEY (`MeasurementID`),
  FOREIGN KEY (`PetID`) REFERENCES `pet`(`PetID`) ON DELETE CASCADE
);

select * from measurements;
select * from pet;
delete from pet where petid>3;
-- Clear existing data-- Clear existing data
DELETE FROM measurements where MeasurementID>70 ;
INSERT INTO measurements (MeasurementID, PetID, RecordDate, Value, MeasurementType)
VALUES
(1, 1, '2024-02-01', 3.0, 'weight'),
(2, 1, '2024-03-01', 4.2, 'weight'),
(3, 1, '2024-04-01', 5.3, 'weight'),
(4, 1, '2024-05-01', 6.0, 'weight'),
(5, 1, '2024-06-01', 6.8, 'weight'),
(6, 1, '2024-07-01', 7.5, 'weight'),
(7, 1, '2024-08-01', 8.1, 'weight'),
(8, 1, '2024-09-01', 8.5, 'weight'),
(9, 1, '2024-10-01', 8.9, 'weight'),
(10, 1, '2024-11-01', 9.0, 'weight'),
(11, 1, '2024-02-01', 20.0, 'height'),
(12, 1, '2024-03-01', 22.0, 'height'),
(13, 1, '2024-04-01', 24.0, 'height'),
(14, 1, '2024-05-01', 26.0, 'height'),
(15, 1, '2024-06-01', 28.0, 'height'),
(16, 1, '2024-07-01', 30.0, 'height'),
(17, 1, '2024-08-01', 32.0, 'height'),
(18, 1, '2024-09-01', 33.5, 'height'),
(19, 1, '2024-10-01', 35.0, 'height'),
(20, 1, '2024-11-01', 36.0, 'height');

-- Insert for PetID 2 (Cat)
INSERT INTO measurements (MeasurementID, PetID, RecordDate, Value, MeasurementType)
VALUES
(21, 2, '2024-02-01', 1.5, 'weight'),
(22, 2, '2024-03-01', 2.1, 'weight'),
(23, 2, '2024-04-01', 2.8, 'weight'),
(24, 2, '2024-05-01', 3.3, 'weight'),
(25, 2, '2024-06-01', 3.8, 'weight'),
(26, 2, '2024-07-01', 4.2, 'weight'),
(27, 2, '2024-08-01', 4.4, 'weight'),
(28, 2, '2024-09-01', 4.6, 'weight'),
(29, 2, '2024-10-01', 4.7, 'weight'),
(30, 2, '2024-11-01', 5.0, 'weight'),
(31, 2, '2024-02-01', 22.0, 'height'),
(32, 2, '2024-03-01', 24.0, 'height'),
(33, 2, '2024-04-01', 26.0, 'height'),
(34, 2, '2024-05-01', 28.0, 'height'),
(35, 2, '2024-06-01', 29.0, 'height'),
(36, 2, '2024-07-01', 30.0, 'height'),
(37, 2, '2024-08-01', 31.0, 'height'),
(38, 2, '2024-09-01', 31.5, 'height'),
(39, 2, '2024-10-01', 32.0, 'height'),
(40, 2, '2024-11-01', 32.5, 'height');

-- Insert for PetID 3 (Large Dog)
INSERT INTO measurements (MeasurementID, PetID, RecordDate, Value, MeasurementType)
VALUES
(41, 3, '2024-02-01', 10.0, 'weight'),
(42, 3, '2024-03-01', 14.2, 'weight'),
(43, 3, '2024-04-01', 18.5, 'weight'),
(44, 3, '2024-05-01', 22.5, 'weight'),
(45, 3, '2024-06-01', 25.0, 'weight'),
(46, 3, '2024-07-01', 28.0, 'weight'),
(47, 3, '2024-08-01', 30.5, 'weight'),
(48, 3, '2024-09-01', 32.0, 'weight'),
(49, 3, '2024-10-01', 33.5, 'weight'),
(50, 3, '2024-11-01', 35.0, 'weight'),
(51, 3, '2024-02-01', 40.0, 'height'),
(52, 3, '2024-03-01', 45.0, 'height'),
(53, 3, '2024-04-01', 50.0, 'height'),
(54, 3, '2024-05-01', 55.0, 'height'),
(55, 3, '2024-06-01', 58.0, 'height'),
(56, 3, '2024-07-01', 60.0, 'height'),
(57, 3, '2024-08-01', 62.0, 'height'),
(58, 3, '2024-09-01', 63.0, 'height'),
(59, 3, '2024-10-01', 64.0, 'height'),
(60, 3, '2024-11-01', 65.0, 'height');

CREATE TABLE todo (
    id INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(36) NOT NULL,
    text VARCHAR(255) NOT NULL,
    color VARCHAR(50),
    done BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

select * from todo;
ALTER TABLE todo RENAME COLUMN id TO todo_id;

drop table todo;
delete from todo where user_id = 'eb0c9283-a79f-11ef-8d99-00155dc4b958';

CREATE TABLE vaccinations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    species VARCHAR(50) NOT NULL,
    vaccination_name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    due_age_description VARCHAR(100) NOT NULL
);

-- Insert sample data for dogs
INSERT INTO vaccinations (species, vaccination_name, description, due_age_description)
VALUES
('Dog', 'Canine Distemper', 'Protects against a viral disease affecting the respiratory, gastrointestinal, and nervous systems.', 'First dose at 6-8 weeks, boosters every 3-4 weeks until 16 weeks.'),
('Dog', 'Canine Parvovirus', 'Protects against a highly contagious virus causing severe diarrhea and vomiting.', 'Start at 6-8 weeks, boosters every 3-4 weeks until 16 weeks.'),
('Dog', 'Rabies', 'Protects against a fatal viral disease transmissible to humans.', 'Administered at 12-16 weeks, booster every 1-3 years.'),
('Dog', 'Leptospirosis', 'Prevents a bacterial infection affecting the liver and kidneys.', 'First dose at 12 weeks, second dose after 2-4 weeks, annual booster.'),
('Dog', 'Bordetella', 'Protects against kennel cough, a respiratory infection.', 'First dose at 8 weeks, booster annually.');

-- Insert sample data for cats
INSERT INTO vaccinations (species, vaccination_name, description, due_age_description)
VALUES
('Cat', 'Feline Panleukopenia', 'Protects against a severe and highly contagious viral disease.', 'Start at 6-8 weeks, boosters every 3-4 weeks until 16 weeks.'),
('Cat', 'Feline Herpesvirus', 'Protects against a respiratory disease caused by herpesvirus.', 'First dose at 6-8 weeks, boosters every 3-4 weeks until 16 weeks.'),
('Cat', 'Rabies', 'Protects against a fatal viral disease transmissible to humans.', 'Administered at 12-16 weeks, booster every 1-3 years.'),
('Cat', 'Feline Leukemia Virus (FeLV)', 'Protects against a virus causing leukemia and immune suppression.', 'Administer at 8 weeks, second dose after 3-4 weeks, annual booster.');

-- Insert sample data for rabbits
INSERT INTO vaccinations (species, vaccination_name, description, due_age_description)
VALUES
('Rabbit', 'Rabbit Hemorrhagic Disease Virus (RHDV)', 'Protects against a highly fatal viral disease affecting the liver.', 'Administer at 4-6 weeks, booster annually.'),
('Rabbit', 'Myxomatosis', 'Protects against a viral disease causing swelling and skin lesions.', 'Administer at 4-6 weeks, booster every 6-12 months.');

-- Insert sample data for horses
INSERT INTO vaccinations (species, vaccination_name, description, due_age_description)
VALUES
('Horse', 'Tetanus', 'Protects against a bacterial infection causing muscle stiffness and spasms.', 'First dose at 4-6 months, booster 4-6 weeks later, then annually.'),
('Horse', 'Rabies', 'Protects against a fatal viral disease transmissible to humans.', 'Administer at 6 months or older, booster annually.'),
('Horse', 'Eastern/Western Equine Encephalomyelitis', 'Protects against viral diseases causing brain inflammation.', 'First dose at 4-6 months, booster 4 weeks later, then annually.');

-- Insert sample data for ferrets
INSERT INTO vaccinations (species, vaccination_name, description, due_age_description)
VALUES
('Ferret', 'Canine Distemper', 'Protects against a highly fatal viral disease in ferrets.', 'First dose at 6-8 weeks, second dose at 10-12 weeks, booster annually.'),
('Ferret', 'Rabies', 'Protects against a fatal viral disease transmissible to humans.', 'Administer at 12-16 weeks, booster annually.');


select * from vaccinations;

CREATE TABLE vaccination_records (
    pet_id INT NOT NULL,
    vac_id INT NOT NULL,
    vaccination_date DATE,
    status varchar(20) NOT NULL,
    PRIMARY KEY (pet_id, vac_id),  -- Composite primary key
    FOREIGN KEY (pet_id) REFERENCES pet(PetID) ON DELETE CASCADE,
    FOREIGN KEY (vac_id) REFERENCES vaccinations(id) ON DELETE CASCADE
);

select * from measurements;

SELECT *
        FROM measurements m
        WHERE m.PetID = 1 AND m.MeasurementType = 'weight'
        ORDER BY m.RecordDate DESC
        LIMIT 1;
        
CREATE TABLE `clinics` (
  `clinicID` VARCHAR(36) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `name` VARCHAR(128) NOT NULL,
  `rating` DECIMAL(2,1) default 4,
  `address` VARCHAR(256) NOT NULL,
  `joinDate` DATE,
  `locationURL` VARCHAR(1024),
  `availableHours` VARCHAR(32),
  PRIMARY KEY (`clinicID`)
);

INSERT INTO clinics (clinicID, email, password, name, rating, address, joinDate, locationURL,availableHours) VALUES
(UUID(), 'pethouse@happytails.org', '12121212', 'Pet House - Animal Hospital', 4.6, 'No. 123, Main Street, Colombo 01, Sri Lanka', '2023-01-15', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','8 AM - 5 PM'),
(UUID(), 'care4pets@happytails.org', '12121212', 'Care4Pets Veterinary Clinic', 4.8, 'No. 45, Flower Road, Colombo 07, Sri Lanka', '2023-05-10', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','9 AM - 5 PM'),
(UUID(), 'vethaven@happytails.org', '12121212', 'Vet Haven Clinic', 4.5, 'No. 89, Peradeniya Road, Kandy, Sri Lanka', '2023-04-20', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','10 AM - 6 PM'),
(UUID(), 'furryfriends@happytails.org', '12121212', 'Furry Friends Veterinary Center', 4.7, 'No. 12, Sea View Avenue, Galle, Sri Lanka', '2023-03-01', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','8 AM - 8 PM'),
(UUID(), 'animalcare@happytails.org', '12121212', 'Animal Care Clinic', 4.3, 'No. 67, Station Road, Jaffna, Sri Lanka', '2023-06-25', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','8 AM - 5 PM'),
(UUID(), 'pawspital@happytails.org', '12121212', 'Pawspital Veterinary Center', 4.9, 'No. 34, Hill Street, Nuwara Eliya, Sri Lanka', '2023-07-15', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','8 AM - 5 PM'),
(UUID(), 'happyvet@happytails.org', '12121212', 'Happy Vet Clinic', 4.6, 'No. 78, Lake Road, Kurunegala, Sri Lanka', '2023-02-05', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','7 AM - 9 PM'),
(UUID(), 'petsafe@happytails.org', '12121212', 'PetSafe Animal Hospital', 4.4, 'No. 25, Fort Street, Trincomalee, Sri Lanka', '2023-09-12', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','8 AM - 9 PM'),
(UUID(), 'vetzone@happytails.org', '12121212', 'VetZone Clinic', 4.5, 'No. 90, Canal Road, Batticaloa, Sri Lanka', '2023-08-22', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','7 AM - 7 PM'),
(UUID(), 'provet@happytails.org', '12121212', 'ProVet Veterinary Hospital', 4.7, 'No. 56, City Center, Anuradhapura, Sri Lanka', '2023-10-30', 'https://maps.app.goo.gl/Wc5uiNzij6jxcU1a9','6 AM - 10 PM');



CREATE TABLE appointments (
    appointmentID INT AUTO_INCREMENT PRIMARY KEY,
    ownerID VARCHAR(36) NOT NULL,
    contactNo VARCHAR(15) NOT NULL,
    petName VARCHAR(128) NOT NULL,
    reason VARCHAR(128) NOT NULL,
    clinicID VARCHAR(36) NOT NULL,
    date DATE NOT NULL,
    startTime VARCHAR(10) NOT NULL,
    endTime VARCHAR(10) NOT NULL,
    FOREIGN KEY (ownerID) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (clinicID) REFERENCES clinics(clinicID) ON DELETE CASCADE
);

select * from appointments;