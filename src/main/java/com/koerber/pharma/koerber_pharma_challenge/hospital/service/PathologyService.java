package com.koerber.pharma.koerber_pharma_challenge.hospital.service;


import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Pathology;
import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Symptom;
import com.koerber.pharma.koerber_pharma_challenge.hospital.repository.PathologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathologyService {

    @Autowired
    private PathologyRepository pathologyRepository;

    public List<Pathology> getPathologiesByPatientId(Long patientId) {
        return pathologyRepository.findByPatientId(patientId);
    }

    public Pathology addPathology(Pathology pathology) {
        return pathologyRepository.save(pathology);
    }

    public void deletePathology(Long id) {
        pathologyRepository.deleteById(id);
    }
}
