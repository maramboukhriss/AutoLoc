package tn.esprit.autolocapi.service;

import java.util.List;
import tn.esprit.autolocapi.domain.Agence;

public interface IAgenceService {

    Agence create(Agence entite);

    Agence findById(Long id);

    List<Agence> findAll();

    Agence update(Long id,Agence  entite);

    void deleteById(Long id);
}
