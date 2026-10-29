package tn.esprit.autolocapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;

final class Validations {

    private Validations() {
    }

    static void requireText(String valeur, String champ) {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Le champ '" + champ + "' est obligatoire");
        }
    }

    static void requireNonNull(Object valeur, String champ) {
        if (valeur == null) {
            throw new IllegalArgumentException("Le champ '" + champ + "' est obligatoire");
        }
    }

    static void requireNonNegative(BigDecimal valeur, String champ) {
        requireNonNull(valeur, champ);
        if (valeur.signum() < 0) {
            throw new IllegalArgumentException("Le champ '" + champ + "' ne peut pas être négatif");
        }
    }

    static void requireNotBefore(LocalDate debut, LocalDate fin) {
        if (debut != null && fin != null && fin.isBefore(debut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
