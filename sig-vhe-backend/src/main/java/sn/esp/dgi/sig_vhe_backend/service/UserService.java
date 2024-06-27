package sn.esp.dgi.sig_vhe_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.esp.dgi.sig_vhe_backend.domain.User;
import sn.esp.dgi.sig_vhe_backend.repository.UserRepository;

import java.util.List;


@Service
public class UserService {
  @Autowired
  private UserRepository repository;

  public List<User> findAll() {
    return repository.findAll();
  }

  public User save(User user) {
    return repository.save(user);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }

}
