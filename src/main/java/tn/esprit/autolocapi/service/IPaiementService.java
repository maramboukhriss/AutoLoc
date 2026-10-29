package tn.esprit.autolocapi.service;

import java.util.List;
import tn.esprit.autolocapi.domain.Paiement;

public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();
}