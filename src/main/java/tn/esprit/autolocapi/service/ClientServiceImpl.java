package tn.esprit.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Client;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.repository.IClientRepository;

import static tn.esprit.autolocapi.service.Validations.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    public Client create(Client entite) {
        if (entite.getIdClient() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau client ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return clientRepository.save(entite);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, Client entite) {
        Client existant = findById(id);
        valider(entite);
        existant.setNom(entite.getNom());
        existant.setPrenom(entite.getPrenom());
        existant.setEmail(entite.getEmail());
        existant.setTelephone(entite.getTelephone());
        existant.setNumPermis(entite.getNumPermis());
        existant.setDateInscription(entite.getDateInscription());
        // identifiant et associations non modifiés ici (liens : Chapitre 9)
        return clientRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private void valider(Client e) {
        requireText(e.getNom(), "nom");
        requireText(e.getPrenom(), "prenom");
        requireText(e.getEmail(), "email");
        requireText(e.getNumPermis(), "numPermis");
        requireNonNull(e.getDateInscription(), "dateInscription");
    }
}

