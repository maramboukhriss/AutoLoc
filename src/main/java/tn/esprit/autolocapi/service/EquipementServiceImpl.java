package tn.esprit.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Equipement;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.repository.IEquipementRepository;

import static tn.esprit.autolocapi.service.Validations.*;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement create(Equipement entite) {
        if (entite.getIdEquipement() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau equipement ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return equipementRepository.save(entite);
    }

    @Override
    public Equipement findById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement update(Long id, Equipement entite) {
        Equipement existant = findById(id);
        valider(entite);
        existant.setLibelle(entite.getLibelle());
        // identifiant et associations non modifiés ici (liens : Chapitre 9)
        return equipementRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private void valider(Equipement e) {
        requireText(e.getLibelle(), "libelle");
    }
}

