package sn.esp.dgi.sig_vhe_backend.repository;


import sn.esp.dgi.sig_vhe_backend.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
