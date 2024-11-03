package com.koerber.pharma.koerber_pharma_challenge.hospital.service;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Pathology;
import com.koerber.pharma.koerber_pharma_challenge.hospital.repository.PathologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Pathology service.
 */
@Service
public class PathologyService {

    @Autowired
    private PathologyRepository pathologyRepository;

    /**
     * Gets pathologies by patient id.
     *
     * @param patientId the patient id
     * @return the pathologies by patient id
     */
    public List<Pathology> getPathologiesByPatientId(Long patientId) {
        return pathologyRepository.findByPatientId(patientId);
    }

    /**
     * Add pathology.
     *
     * @param pathology the pathology
     * @return the pathology
     */
    public Pathology addPathology(Pathology pathology) {
        return pathologyRepository.save(pathology);
    }

    /**
     * Delete pathology.
     *
     * @param id the id
     */
    public void deletePathology(Long id) {
        pathologyRepository.deleteById(id);
    }
}
