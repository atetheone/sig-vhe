package sn.esp.dgi.sig_vhe_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import sn.esp.dgi.sig_vhe_backend.domain.User;
import sn.esp.dgi.sig_vhe_backend.domain.Role;
import sn.esp.dgi.sig_vhe_backend.repository.RoleRepository;
import sn.esp.dgi.sig_vhe_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  @Lazy
  private final PasswordEncoder passwordEncoder;



  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
            user.getAuthorities());
  }

  public User save(User user) {
    user.setHash(passwordEncoder.encode(user.getPassword()));

    // Assign default role USER if no roles are assigned
    if (user.getRoles() == null || user.getRoles().isEmpty()) {
      Role userRole = roleRepository.findByName("ROLE_USER");
      Set<Role> roles = new HashSet<>();
      roles.add(userRole);
      user.setRoles(roles);
    }

    return userRepository.save(user);
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public User saveAdmin(User user) {
    user.setHash(passwordEncoder.encode(user.getPassword()));

    // Assign role ADMIN
    Role adminRole = roleRepository.findByName("ROLE_ADMIN");
    Set<Role> roles = new HashSet<>();
    roles.add(adminRole);
    user.setRoles(roles);

    return userRepository.save(user);
  }

  public User updateUserRoles(Long userId, Set<String> roleNames) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    Set<Role> roles = new HashSet<>();
    for (String roleName : roleNames) {
      Role role = roleRepository.findByName(roleName);
      if (role == null) {
        throw new RuntimeException("Role not found");
      }
      roles.add(role);
    }
    user.setRoles(roles);
    return userRepository.save(user);
  }


}
