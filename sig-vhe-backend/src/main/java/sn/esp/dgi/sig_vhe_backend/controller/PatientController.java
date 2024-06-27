package sn.esp.dgi.sig_vhe_backend.controller;


import sn.esp.dgi.sig_vhe_backend.domain.Patient;
import sn.esp.dgi.sig_vhe_backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

  @Autowired
  private PatientService service;

  @GetMapping
  public List<Patient> getAllPatients() {
    return service.findAll();
  }

  @PostMapping
  public Patient createPatient(@RequestBody Patient patient) {
    return service.save(patient);
  }

  @DeleteMapping("/{id}")
  public void deletePatient(@PathVariable Long id) {
    service.deleteById(id);
  }

  @GetMapping("/{id}")
  public Patient getPatientById(@PathVariable Long id) {
    return service.findById(id);
  }
}
