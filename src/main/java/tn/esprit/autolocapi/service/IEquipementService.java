package tn.esprit.autolocapi.service;

import java.util.List;

import tn.esprit.autolocapi.domain.Employe;
import tn.esprit.autolocapi.domain.Equipement;

public interface IEquipementService {

    Equipement create(Equipement entite);

    Equipement findById(Long id);

    List<Equipement> findAll();

    Equipement update(Long id,Equipement entite);

    void deleteById(Long id);
}
