package sn.esp.dgi.sig_vhe_backend.controller;

import sn.esp.dgi.sig_vhe_backend.domain.User;
import sn.esp.dgi.sig_vhe_backend.jwt.JwtRequest;
import sn.esp.dgi.sig_vhe_backend.jwt.JwtResponse;
import sn.esp.dgi.sig_vhe_backend.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sn.esp.dgi.sig_vhe_backend.service.JwtUserDetailsService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(token));
  }

  @PostMapping("/register")
  public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
    return ResponseEntity.ok(userDetailsService.save(user));
  }

  @PostMapping("/register-admin")
  public ResponseEntity<?> saveAdmin(@RequestBody User user) throws Exception {
    return ResponseEntity.ok(userDetailsService.saveAdmin(user));
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}
