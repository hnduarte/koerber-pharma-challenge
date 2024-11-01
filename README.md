# Koerber Pharma Backend Challenge
Backend API for managing hospital consults, patients, and doctors, created as part of the Koerber Pharma technical exercise.

Instructions:

Summary of this software:
- Log the consults that happen in the hospital to keep track of the patient's consults, pathologies and symptoms.
- Add new consults, patients
- Types of specialties:
  - Dermatology
  - Ophthalmology
  - Radiology
  - Family Medicine
  - Pediatrics
- Main rules:
  - Each consult has a doctor, a specialty and a patient(and pathology and symptom).
  - Only one doctor per specialty.
  - A Patient can go to multiple consults in different specialties and to the same specialty more than once.
  - Each patient can have multiple pathologies.
  - Each pathology can have multiple symptoms.
Objectives:
  - 4 endpoints;
    - Create consults;
    - Get patient consults and symptoms;
    - Get top specialties;
    - Get all patients;

Get patient consults and symptoms should work on a specific patient and return a list of all the consults the patient had. Also needs
to return the symptoms.
Get top specialties should return which ones have more than 2 unique patients.
Get all patients should have filter or pagination(needs parameters)


Versions:
This section will summarize the changes as they will be added in each commit:
v1:
    - Initial commits with the structure of the project;
    - Creation of the entities that were identified in the project statement and its respective repositories
    - Creation of the README and some information was filled to provide a better user experience.


Entities for the database:
    - Patient;
    - Doctor;
    - Consult;
    - Pathology;
    - Symptom;
        - Each Patient can have multiple consults and each consult has 1 unique patient(One to many)
        - Each doctor has many consults and each consult in this solution for simplicity’s sake has only 1 doctor(One to many)
        - Each Pathology can have multiple symptoms(one to many)
        - For now let's also assume each Symptom is only linked with 1 Pathology( Many to one)
