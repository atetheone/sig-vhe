package sn.esp.dgi.sig_vhe_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sn.esp.dgi.sig_vhe_backend.jwt.JwtAuthenticationEntryPoint;
import sn.esp.dgi.sig_vhe_backend.jwt.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private UserDetailsService userDetailService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //configuration
    http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.
                    requestMatchers("/api/patients/**").authenticated().
                    requestMatchers("/api/admin/**").hasRole("ADMIN").
                    requestMatchers("/api/admin/all-users").permitAll().
                    requestMatchers("/api/register").permitAll())
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // if any exception came
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // nothing to save on server
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
  @Bean
  public DaoAuthenticationProvider doDaoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    return daoAuthenticationProvider;
  }

}