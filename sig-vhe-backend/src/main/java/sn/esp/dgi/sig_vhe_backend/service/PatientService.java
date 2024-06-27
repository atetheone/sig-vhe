package sn.esp.dgi.sig_vhe_backend.service;

import sn.esp.dgi.sig_vhe_backend.domain.Patient;
import sn.esp.dgi.sig_vhe_backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

  @Autowired
  private PatientRepository repository;

  public List<Patient> findAll() {
    return repository.findAll();
  }

  public Patient save(Patient patient) {
    return repository.save(patient);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public Patient findById(Long id) {
    return repository.findById(id).orElse(null);
  }
}
