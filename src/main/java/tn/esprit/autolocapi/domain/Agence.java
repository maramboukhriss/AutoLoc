package tn.esprit.autolocapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity  //classe = entité JPA
@Table(name = "agence")  //nom de la table
@Getter  //crée les getters
@Setter  //crée les setters
@ToString
@EqualsAndHashCode
@NoArgsConstructor  //constructeur vide
@AllArgsConstructor //constructeur avec tous les attributs
@FieldDefaults(level = AccessLevel.PRIVATE)  //tous les attributs sont private
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Long idAgence;

    @Column(nullable = false, length = 100)
    String nom;

    @Column(nullable = false, length = 50)
    String ville;

    @Column(nullable = false, length = 150)
    String adresse;

    @Column(length = 20)
    String telephone;
    // atelier2:
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Employe> employes = new ArrayList<>();
}