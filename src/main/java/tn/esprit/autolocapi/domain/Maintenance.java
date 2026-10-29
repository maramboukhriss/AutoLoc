package tn.esprit.autolocapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Long idMaintenance;

    @Column(nullable = false)
    LocalDate dateDebut;

    @Column
    LocalDate dateFin;  //une maintenance peut être en cours (optionnelle)

    @Column(length = 255)
    String description;
    // atelier2:
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicule vehicule;
}