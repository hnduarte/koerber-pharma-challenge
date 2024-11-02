package com.koerber.pharma.koerber_pharma_challenge.hospital.controller;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Consult;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.ConsultService;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.DoctorService;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consults")
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Consult> getAllConsults() {
        return consultService.getAllConsults();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consult> getConsultById(@PathVariable Long id) {
        Optional<Consult> consult = consultService.getConsultById(id);
        return consult.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Consult> createConsult(@RequestBody Consult consult) {
        if (consult.getDoctor() == null || consult.getPatient() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Validate doctor and patient existence
        if (!doctorService.existsById(consult.getDoctor().getId()) ||
                !patientService.existsById(consult.getPatient().getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Consult savedConsult = consultService.saveConsult(consult);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedConsult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consult> updateConsult(@PathVariable Long id, @RequestBody Consult consult) {
        consult.setId(id);
        Consult updatedConsult = consultService.saveConsult(consult);
        return ResponseEntity.ok(updatedConsult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsult(@PathVariable Long id) {
        consultService.deleteConsult(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}