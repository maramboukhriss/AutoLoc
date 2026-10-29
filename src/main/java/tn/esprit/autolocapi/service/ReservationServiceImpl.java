package tn.esprit.autolocapi.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Reservation;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.repository.IReservationRepository;

import static tn.esprit.autolocapi.service.Validations.*;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;

    @Override
    public Reservation create(Reservation entite) {
        if (entite.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau reservation ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return reservationRepository.save(entite);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation entite) {
        Reservation existant = findById(id);
        valider(entite);
        existant.setDateDebut(entite.getDateDebut());
        existant.setDateFin(entite.getDateFin());
        existant.setStatut(entite.getStatut());
        // identifiant et associations non modifiés ici (liens : Chapitre 9)
        return reservationRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private void valider(Reservation e) {
        requireNonNull(e.getDateDebut(), "dateDebut");
        requireNonNull(e.getDateFin(), "dateFin");
        requireNonNull(e.getStatut(), "statut");
        requireNotBefore(e.getDateDebut(), e.getDateFin());
    }
}

