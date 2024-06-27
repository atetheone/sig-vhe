package sn.esp.dgi.sig_vhe_backend.repository;
import sn.esp.dgi.sig_vhe_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
}

