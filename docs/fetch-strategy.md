# Stratégie de fetch et de cascade : AutoLoc

## Les règles

- Toutes les associations sont en "LAZY" : on charge les données liées seulement quand on en a besoin.
- La "cascade" s'utilise seulement si l'enfant ne peut pas exister sans son parent, ou pour enregistrer plusieurs objets d'un coup.

## Les associations

| Association | Cascade | Pourquoi |
|---|---|---|
| Contrat → Paiement | ALL + orphanRemoval | Un paiement n'existe pas sans son contrat. Supprimer le contrat supprime ses paiements. |
| Agence → Vehicule | Aucune | Un véhicule existe même sans son agence. |
| Agence → Employe | Aucune | Un employé peut changer d'agence. |
| Vehicule ↔ Equipement | Aucune | Un équipement peut servir à plusieurs véhicules. On utilise un `Set` pour éviter les doublons. |
| Client → Reservation | PERSIST | Les nouvelles réservations sont enregistrées avec le client. On ne supprime pas en cascade, pour garder l'historique. |
| Reservation → Vehicule | Aucune | La réservation ne fait que pointer vers un véhicule qui existe déjà. |
| Reservation ↔ Contrat | ALL (côté Reservation) | Le contrat découle de la réservation et suit sa vie. |
| Vehicule → Maintenance | PERSIST | Une maintenance est enregistrée avec son véhicule. On garde l'historique. |

## LAZY ou EAGER (exemple : Contrat → Paiement)

| | LAZY (mon choix) | EAGER |
|---|---|---|
| Chargement | Les paiements sont chargés quand on appelle `getPaiements()` | Les paiements sont toujours chargés avec le contrat |
| Avantage | Lecture légère | Plus simple à utiliser |
| Risque | `LazyInitializationException` si on lit hors transaction | Chaque lecture est plus lourde |

Je garde LAZY dans AutoLoc pour éviter des requêtes inutiles.

## À retenir

- Le côté `mappedBy` est exclu de `toString`, `equals` et `hashCode`, sinon les deux classes s'appellent sans fin (`StackOverflowError`).