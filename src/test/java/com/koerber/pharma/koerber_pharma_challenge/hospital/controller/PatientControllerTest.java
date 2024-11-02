package com.koerber.pharma.koerber_pharma_challenge.hospital.controller;

import com.koerber.pharma.koerber_pharma_challenge.hospital.model.Patient;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.ConsultService;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.PathologyService;
import com.koerber.pharma.koerber_pharma_challenge.hospital.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private PathologyService pathologyService;

    @MockBean
    private ConsultService consultService;

    @Test
    void testGetAllPatients() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Henrique Duarte");
        patient.setAge(29);

        when(patientService.getAllPatients()).thenReturn(Arrays.asList(patient));

        mockMvc.perform(get("/api/patients/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Henrique Duarte"))
                .andExpect(jsonPath("$[0].age").value(29));
    }

    @Test
    void testGetPatientById() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Henrique Duarte");
        patient.setAge(29);

        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Henrique Duarte"))
                .andExpect(jsonPath("$.age").value(29));
    }

    @Test
    void testCreatePatient() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Henrique Duarte");
        patient.setAge(29);

        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Henrique Duarte\", \"age\":29}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Henrique Duarte"))
                .andExpect(jsonPath("$.age").value(29));
    }

    @Test
    void testDeletePatient() throws Exception {
        doNothing().when(patientService).deletePatient(1L);

        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent());
    }
}
