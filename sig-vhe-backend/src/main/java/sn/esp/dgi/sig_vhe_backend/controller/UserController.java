package sn.esp.dgi.sig_vhe_backend.controller;



import sn.esp.dgi.sig_vhe_backend.domain.User;
import sn.esp.dgi.sig_vhe_backend.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class UserController {

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @PostMapping("/add-user")
  @PreAuthorize("hasRole(ROLE_ADMIN)")
  public ResponseEntity<?> addUser(@RequestBody User user) {
    return ResponseEntity.ok(userDetailsService.save(user));
  }

  @PutMapping("/update-user-roles/{id}")
  @PreAuthorize("hasRole(ROLE_ADMIN)")
  public ResponseEntity<?> updateUserRoles(@PathVariable Long id, @RequestBody Set<String> roles) {
    return ResponseEntity.ok(userDetailsService.updateUserRoles(id, roles));
  }

  @GetMapping("/all-users")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userDetailsService.findAllUsers());
  }
}
