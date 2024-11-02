package com.koerber.pharma.koerber_pharma_challenge.hospital.service;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Consult;
import com.koerber.pharma.koerber_pharma_challenge.hospital.repository.ConsultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultService {

    @Autowired
    private ConsultRepository consultRepository;

    public List<Consult> getAllConsults() {
        return consultRepository.findAll();
    }

    public Optional<Consult> getConsultById(Long id) {
        return consultRepository.findById(id);
    }

    public Consult saveConsult(Consult consult) {
        return consultRepository.save(consult);
    }

    public void deleteConsult(Long id) {
        consultRepository.deleteById(id);
    }

    public List<Consult> getConsultsByPatientId(Long patientId) {
        return consultRepository.findByPatientId(patientId);
    }
}
