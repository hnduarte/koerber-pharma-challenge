package com.koerber.pharma.koerber_pharma_challenge.hospital.service;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Consult;
import com.koerber.pharma.koerber_pharma_challenge.hospital.repository.ConsultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The type Consult service.
 */
@Service
public class ConsultService {

    @Autowired
    private ConsultRepository consultRepository;

    /**
     * Gets all consults.
     *
     * @return the all consults
     */
    public List<Consult> getAllConsults() {
        return consultRepository.findAll();
    }

    /**
     * Gets consult by id.
     *
     * @param id the id
     * @return the consult by id
     */
    public Optional<Consult> getConsultById(Long id) {
        return consultRepository.findById(id);
    }

    /**
     * Save consult.
     *
     * @param consult the consult
     * @return the consult
     */
    @Transactional
    public Consult saveConsult(Consult consult) {
        /*
        Consult consult = mapToEntity(consultDTO);
    consult = consultRepository.save(consult);

    // Update doctor's availability status after consult creation
    Doctor doctor = consult.getDoctor();
    doctor.setAvailable(false);
    doctorRepository.save(doctor);

    return mapToDTO(consult);
         */
        return consultRepository.save(consult);
    }

    /**
     * Delete consult.
     *
     * @param id the id
     */
    public void deleteConsult(Long id) {
        consultRepository.deleteById(id);
    }

    /**
     * Gets consults by patient id.
     *
     * @param patientId the patient id
     * @return the consults by patient id
     */
    public List<Consult> getConsultsByPatientId(Long patientId) {
        return consultRepository.findByPatientId(patientId);
    }
}
