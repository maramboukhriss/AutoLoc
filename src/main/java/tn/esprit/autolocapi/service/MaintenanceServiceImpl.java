package tn.esprit.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Maintenance;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.repository.IMaintenanceRepository;

import static tn.esprit.autolocapi.service.Validations.*;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance entite) {
        if (entite.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau maintenance ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return maintenanceRepository.save(entite);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance entite) {
        Maintenance existant = findById(id);
        valider(entite);
        existant.setDateDebut(entite.getDateDebut());
        existant.setDateFin(entite.getDateFin());
        existant.setDescription(entite.getDescription());
        // identifiant et associations non modifiés ici (liens : Chapitre 9)
        return maintenanceRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private void valider(Maintenance e) {
        requireNonNull(e.getDateDebut(), "dateDebut");
        requireNotBefore(e.getDateDebut(), e.getDateFin());
    }
}

