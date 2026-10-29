package tn.esprit.autolocapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Long idClient;

    @Column(nullable = false, length = 50)
    String nom;

    @Column(nullable = false, length = 50)
    String prenom;

    @Column(nullable = false, unique = true, length = 100)
    String email;

    @Column(length = 20)
    String telephone;

    @Column(nullable = false, unique = true, length = 30)
    String numPermis;

    @Column(nullable = false)
    LocalDate dateInscription;
    // atelier2:
    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();
}