# Koerber Pharma Backend Challenge
Backend API for managing hospital consults, patients, and doctors, created as part of the Koerber Pharma technical exercise.

Instructions:
- To install this application firstly you have to use the command mvn clean install to create the jar file, optional -DskipTests to skip the test files;
- Once the target and jar file is created you can build the docker container with the commands : 'docker build -t koerberapi .' and 'docker run -p 8080:8080 koerberapi'
- This will create the container and run it, from here on you can use for example postman, which is what I used to test;
- Run 'docker-compose up' to use docker compose file for the logging.
- In the request after the GET PUT POST and DELETE be sure to use 'localhost:8000' and for example to create a patient '/api/patients' I will explain in more detail in the next points;
- To use this software firstly let's go over the basic functions, we can add patients, doctors consults and pathologies to the patients;
- Firstly use the Post /api/patients to add Patients;
- Use the Post /api/doctors to add doctors;
- Use the Post /api/consults to add consults between your patients and your doctors;
- Now we can add pathologies to each patient with the Post /api/patients/{patientId}/pathologies, we have to specify the patient id and also put it in the request body
along with the ids of the symptoms and their description, for each pathology we add;
- Now we can use the main 4 objectives, one we already covered which is to add consults, now we can check the consults and symptoms of each patient by using
the Get /api/patients/{id}/consults-and-symptoms we should get 2 lists one with the consults and another with the symptoms;
- We can consult the specialty that had the most consults in the Get /api/doctors/top-specialties;
- Finally, in the Post /api/patients/search we can put some parameters like page, size, sort, minimum age and name to get the patients that fit these query
parameters;
- Lastly, there are some other basic methods like delete and get for each of the controllers in case the hospital would want more basic operations in their database.

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
- For the sake of testing in the swagger page some methods that weren't specified were still kept, like add patient, delete patient and so on,
so it was possible to do various operations and check if the 4 main ones were working correctly.

Get patient consults and symptoms should work on a specific patient and return a list of all the consults the patient had. Also needs
to return the symptoms.
Get top specialties should return which ones have more than 2 unique patients.
Get all patients should have filter or pagination(needs parameters)

Explanation of the thought process of each objective:
- Create consults creates a consult only if the patient and doctor exist, if not there will be a need to create them first;
- Get patient consults and symptoms gets the list of the consults linked to this patient and the symptoms linked to the patient pathologies;
- Get top specialties will get all doctors and their specialties and then filter the ones that have at least 2 unique patients, it will also sort in descending order;
- Get all patients with pagination for now only gets all patients with a minimum age filter;


Versions:
This section will summarize the changes as they will be added in each commit:
- v1:
    - Initial commits with the structure of the project;
    - Creation of the entities that were identified in the project statement and its respective repositories
    - Creation of the README and some information was filled to provide a better user experience.
- v2:
    - Added the services and controllers with the general endpoints to test if everything was working correctly;
    - Modified the controllers and services to follow the objectives of the project;
    - Creation of the test classes to test some of the controllers and services;
    - Checked on OpenAPI if the requests could be done;
    - Noticed lack of relationship between pathologies and the rest so the model Patient and Pathology were updated and there was new logic created for them;
    - Creation of a performance test to test the Get in the patient controller;
    - Checked the OpenAPI swagger page and manually tested the endpoints;
    - Corrected bugs related to the logic of the controllers, services and models, added some JsonIgnore to validate the correct json we want to use;
    - Added an instructions section in this document.
- v3:
    - Added javadoc and some comments for better understanding of the controllers and services.
    - Added the flyway dependency and db migration package.

- Entities for the database:
    - Patient;
    - Doctor;
    - Consult;
    - Pathology;
    - Symptom;
        - Each Patient can have multiple consults and each consult has 1 unique patient(One to many)
        - Each doctor has many consults and each consult in this solution for simplicity’s sake has only 1 doctor(One to many)
        - Each Pathology can have multiple symptoms(one to many)
        - For now let's also assume each Symptom is only linked with 1 Pathology( Many to one)
