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

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id){
        return patientRepository.findById(id);
    }

    public Patient savePatient(Patient patient){
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public Page<Patient> getFilteredPatients(String name, Integer minAge, Pageable pageable) {
        return patientRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            if (minAge != null)  predicates.add(cb.greaterThanOrEqualTo(root.get("age"), minAge));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    public boolean existsById(Long id) {
        return patientRepository.existsById(id);
    }

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
