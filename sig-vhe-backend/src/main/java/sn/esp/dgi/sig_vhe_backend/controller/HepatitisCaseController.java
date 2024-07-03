package sn.esp.dgi.sig_vhe_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sn.esp.dgi.sig_vhe_backend.domain.HepatitisCase;
import sn.esp.dgi.sig_vhe_backend.service.HepatitisCaseService;

import java.util.List;

@RestController
@RequestMapping("/api/hepatitis-cases")
public class HepatitisCaseController {

  @Autowired
  private HepatitisCaseService hepatitisCaseService;

  @GetMapping
  public List<HepatitisCase> getAllCases() {
    return hepatitisCaseService.findAll();
  }

  @PostMapping
  public HepatitisCase createPatient(@RequestBody HepatitisCase hepatitisCase) {
    return hepatitisCaseService.save(hepatitisCase);
  }

  @DeleteMapping("/{id}")
  public void deletePatient(@PathVariable Long id) {
    hepatitisCaseService.deleteById(id);
  }

  @GetMapping("/{id}")
  public HepatitisCase getPatientById(@PathVariable Long id) {
    return hepatitisCaseService.findById(id);
  }
}