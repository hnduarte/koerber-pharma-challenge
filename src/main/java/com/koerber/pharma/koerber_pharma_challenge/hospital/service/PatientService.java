package com.koerber.pharma.koerber_pharma_challenge.hospital.service;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Patient;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Symptom;
import com.koerber.pharma.koerber_pharma_challenge.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.criteria.Predicate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Patient service.
 */
@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Get all patients list.
     *
     * @return the list
     */
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    /**
     * Get patient by id.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Patient> getPatientById(Long id){
        return patientRepository.findById(id);
    }

    /**
     * Save patient.
     *
     * @param patient the patient
     * @return the patient
     */
    public Patient savePatient(Patient patient){
        return patientRepository.save(patient);
    }

    /**
     * Delete patient.
     *
     * @param id the id
     */
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    /**
     * Gets filtered patients.
     *
     * @param name     the name
     * @param minAge   the min age
     * @param pageable the pageable
     * @return the filtered patients
     */
    public Page<Patient> getFilteredPatients(String name, Integer minAge, Pageable pageable) {
        return patientRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //Verify name and minAge to be used for filtering
            if (name != null) predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            if (minAge != null)  predicates.add(cb.greaterThanOrEqualTo(root.get("age"), minAge));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    /**
     * Method to check if Patient exists by id.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean existsById(Long id) {
        return patientRepository.existsById(id);
    }

    /**
     * Gets patient consults and symptoms.
     *
     * @param patientId the patient id
     * @return the patient consults and symptoms
     */
    public Optional<Map<String, Object>> getPatientConsultsAndSymptoms(Long patientId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            return Optional.empty();
        }

        Patient patient = patientOpt.get();

        // Get consults for the patient
        List<Map<String, Object>> consults = patient.getConsults().stream()
                .map(consult -> {
                    Map<String, Object> consultData = new HashMap<>();
                    consultData.put("ConsultId", consult.getId());
                    consultData.put("Doctor", consult.getDoctor().getName());
                    consultData.put("Specialty", consult.getDoctor().getSpecialty());
                    return consultData;
                }).collect(Collectors.toList());

        // Get symptoms from the patient's pathologies
        List<Symptom> symptoms = patient.getPathologies().stream()
                .flatMap(pathology -> pathology.getSymptoms().stream())
                .distinct() // Ensure unique symptoms if needed
                .collect(Collectors.toList());

        // Prepare the response structure
        Map<String, Object> response = new HashMap<>();
        response.put("Consults", consults);
        response.put("Symptoms", symptoms);

        return Optional.of(response);
    }
}
