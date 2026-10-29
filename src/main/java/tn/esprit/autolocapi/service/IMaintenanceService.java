package tn.esprit.autolocapi.service;

import java.util.List;
import tn.esprit.autolocapi.domain.Maintenance;

public interface IMaintenanceService {

    Maintenance create(Maintenance entite);

    Maintenance findById(Long id);

    List<Maintenance> findAll();

    Maintenance update(Long id, Maintenance entite);

    void deleteById(Long id);
}
