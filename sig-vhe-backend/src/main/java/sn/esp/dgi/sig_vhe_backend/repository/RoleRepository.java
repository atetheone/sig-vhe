package sn.esp.dgi.sig_vhe_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esp.dgi.sig_vhe_backend.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByName(String name);
}
