package sn.esp.dgi.sig_vhe_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.esp.dgi.sig_vhe_backend.domain.HepatitisCase;
import sn.esp.dgi.sig_vhe_backend.repository.HepatitsCaseRepository;

import java.util.List;

@Service
public class HepatitisCaseService {

  @Autowired
  private HepatitsCaseRepository hepatitsCaseRepository;

  public List<HepatitisCase> findAll() {
    return hepatitsCaseRepository.findAll();
  }

  public HepatitisCase save(HepatitisCase patient) {
    return hepatitsCaseRepository.save(patient);
  }

  public void deleteById(Long id) {
    hepatitsCaseRepository.deleteById(id);
  }

  public HepatitisCase findById(Long id) {
    return hepatitsCaseRepository.findById(id).orElse(null);
  }
}
