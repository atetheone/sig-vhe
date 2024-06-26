package sn.esp.dgi.sig_vhe_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Entity
@SuperBuilder
@Data
@RequiredArgsConstructor
public class Patient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long patientId;

  private String name;
  private String firstName;
  private LocalDate birthDate;
  private String sex;
  private String address;
  private String contact;

  //@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
  //private Set<HepatitisCase> cases;

}
