package com.koerber.pharma.koerber_pharma_challenge.hospital.service;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Consult;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Doctor;
import com.koerber.pharma.koerber_pharma_challenge.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The type Doctor service.
 */
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Get all doctors list.
     *
     * @return the list
     */
    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    /**
     * Get doctor by id.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Doctor> getDoctorById(Long id){
        return doctorRepository.findById(id);
    }

    /**
     * Save doctor.
     *
     * @param doctor the doctor
     * @return the doctor
     */
    public Doctor saveDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    /**
     * Delete doctor.
     *
     * @param id the id
     */
    public void deleteDoctor(Long id){
        doctorRepository.deleteById(id);
    }

    /**
     * Gets top specialties (the specialties with at least 2 patients).
     *
     * @return the top specialties
     */
    public List<Map<String, Object>> getTopSpecialties() {
        // Step 1: Get all doctors and initialize a Map to count unique patients per specialty
        List<Doctor> doctors = doctorRepository.findAll();
        Map<String, Set<Long>> specialtyPatientMap = new HashMap<>();

        // Step 2: Populate the map with unique patients for each specialty
        for (Doctor doctor : doctors) {
            String specialty = doctor.getSpecialty();
            specialtyPatientMap.putIfAbsent(specialty, new HashSet<>());

            for (Consult consult : doctor.getConsults()) {
                Long patientId = consult.getPatient().getId();
                specialtyPatientMap.get(specialty).add(patientId); // Adds unique patient ID per specialty
            }
        }

        List<Map<String, Object>> topSpecialties = new ArrayList<>();
        for (Map.Entry<String, Set<Long>> entry : specialtyPatientMap.entrySet()) {
            if (entry.getValue().size() >= 2) {
                Map<String, Object> specialtyData = new HashMap<>();
                specialtyData.put("SpecialtyName", entry.getKey());
                specialtyData.put("NumberOfPatients", entry.getValue().size());
                topSpecialties.add(specialtyData);
            }
        }
        return topSpecialties;
    }

    /**
     * Method to check if doctor exists by id.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean existsById(Long id) {
        return doctorRepository.existsById(id);
    }
}
