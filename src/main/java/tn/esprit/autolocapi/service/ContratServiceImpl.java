package tn.esprit.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Contrat;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.repository.IContratRepository;

import static tn.esprit.autolocapi.service.Validations.*;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    @Override
    public Contrat create(Contrat entite) {
        if (entite.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return contratRepository.save(entite);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat entite) {
        Contrat existant = findById(id);
        valider(entite);
        existant.setDateSignature(entite.getDateSignature());
        existant.setMontantTotal(entite.getMontantTotal());
        existant.setValide(entite.isValide());
        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    private void valider(Contrat e) {
        requireNonNull(e.getDateSignature(), "dateSignature");
        requireNonNegative(e.getMontantTotal(), "montantTotal");
    }
}
