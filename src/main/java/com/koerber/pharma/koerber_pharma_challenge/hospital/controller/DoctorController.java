package com.koerber.pharma.koerber_pharma_challenge.hospital.controller;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Doctor;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Doctor controller.
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * Gets all doctors.
     *
     * @return the all doctors
     */
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    /**
     * Gets doctor by id.
     *
     * @param id the id
     * @return the doctor by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Create doctor response entity.
     *
     * @param doctor the doctor
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    /**
     * Update doctor response entity.
     *
     * @param id     the id
     * @param doctor the doctor
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        doctor.setId(id);
        Doctor updatedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     * Delete doctor response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Gets top specialties.
     *
     * @return the top specialties
     */
    @GetMapping("/top-specialties")
    public ResponseEntity<List<Map<String, Object>>> getTopSpecialties() {
        List<Map<String, Object>> topSpecialties = doctorService.getTopSpecialties();
        return ResponseEntity.ok(topSpecialties);
    }
}
