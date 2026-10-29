package tn.esprit.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Vehicule;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.repository.IVehiculeRepository;

import static tn.esprit.autolocapi.service.Validations.*;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule entite) {
        if (entite.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return vehiculeRepository.save(entite);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule entite) {
        Vehicule existant = findById(id);
        valider(entite);
        existant.setImmatriculation(entite.getImmatriculation());
        existant.setMarque(entite.getMarque());
        existant.setModele(entite.getModele());
        existant.setCategorie(entite.getCategorie());
        existant.setTarifJournalier(entite.getTarifJournalier());
        existant.setStatut(entite.getStatut());
        // identifiant et associations non modifiés ici (liens : Chapitre 9)
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void valider(Vehicule e) {
        requireText(e.getImmatriculation(), "immatriculation");
        requireText(e.getMarque(), "marque");
        requireText(e.getModele(), "modele");
        requireNonNull(e.getCategorie(), "categorie");
        requireNonNull(e.getStatut(), "statut");
        requireNonNegative(e.getTarifJournalier(), "tarifJournalier");
    }
}

