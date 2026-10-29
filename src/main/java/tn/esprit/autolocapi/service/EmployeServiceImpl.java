package tn.esprit.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Employe;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.repository.IEmployeRepository;

import static tn.esprit.autolocapi.service.Validations.*;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    public Employe create(Employe entite) {
        if (entite.getIdEmploye() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau employe ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return employeRepository.save(entite);
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe entite) {
        Employe existant = findById(id);
        valider(entite);
        existant.setNom(entite.getNom());
        existant.setPrenom(entite.getPrenom());
        existant.setRole(entite.getRole());
        // identifiant et associations non modifiés ici (liens : Chapitre 9)
        return employeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private void valider(Employe e) {
        requireText(e.getNom(), "nom");
        requireText(e.getPrenom(), "prenom");
        requireNonNull(e.getRole(), "role");
    }
}
