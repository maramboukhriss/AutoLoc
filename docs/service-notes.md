# Notes : Atelier 4 (couche Service)

## A. Injection par constructeur ou par attribut

Injecter, c'est donner au service l'objet dont il a besoin (ici, le repository).

| Question | Constructeur | Attribut (`@Autowired`) |
|---|---|---|
| Champ `final` possible ? | Oui | Non |
| Voit-on ce dont la classe a besoin ? | Oui, dans le constructeur | Non, c'est caché |
| Et avec `new` hors Spring ? | Le constructeur exige le repository | L'objet est créé, mais le repository est `null` |
| SonarQube le signale ? | Non | Oui |

**Mon choix : toujours le constructeur.**

## B. Les services

Tous les services sont faits pareil : un repository injecté par le constructeur, un champ `final`, et aucune donnée gardée dans la classe.

| Service | Repository injecté |
|---|---|
| AgenceServiceImpl | IAgenceRepository |
| EmployeServiceImpl | IEmployeRepository |
| VehiculeServiceImpl | IVehiculeRepository |
| EquipementServiceImpl | IEquipementRepository |
| ClientServiceImpl | IClientRepository |
| ReservationServiceImpl | IReservationRepository |
| ContratServiceImpl | IContratRepository (supprimer un contrat supprime ses paiements) |
| PaiementServiceImpl | IPaiementRepository (lecture seule : les paiements se gèrent depuis le contrat) |
| MaintenanceServiceImpl | IMaintenanceRepository |

## C. Les erreurs de Spring

**1. `IContratService` introuvable** (`NoSuchBeanDefinitionException`)
- Cause : `@Service` oublié, ou classe en dehors du package `tn.esprit.autolocapi`.
- Correction : ajouter `@Service` ou déplacer la classe.

**2. Deux beans possibles** (`NoUniqueBeanDefinitionException`)
- Spring ne sait pas choisir entre `emailNotificateur` et `smsNotificateur`.
- Correction 1 : `@Primary` sur la classe à utiliser par défaut.
- Correction 2 : `@Qualifier("emailNotificateur")` sur le paramètre du constructeur.

**3. Dépendance circulaire** (`clientServiceImpl` et `reservationServiceImpl`)
- Chacun a besoin de l'autre pour être créé, donc aucun ne peut l'être en premier.
- Solution : mettre ce qu'ils ont en commun dans un troisième service, ou garder la dépendance dans un seul sens.

## D. Problèmes trouvés par SonarQube

| Règle | Problème | Ce que j'ai fait |
|---|---|---|
| S6813 | `@Autowired` sur un champ est déconseillé | Retour au constructeur avec `final` |
| S112 | `RuntimeException` trop générale | J'ai utilisé `ResourceNotFoundException` |
| S1128 | Imports inutiles dans `Vehicule` | Je les ai supprimés |

## E. Questions

**1. Où est le `new` de `ContratServiceImpl` ?**
Nulle part dans notre code. Spring le fait au démarrage : il voit `@Service`, crée l'objet et lui donne le repository.

**2. Pourquoi le contrôleur utilise `IContratService` ?**
On peut changer l'implémentation sans toucher au contrôleur. Les deux classes restent peu liées.

**3. Pourquoi un service ne doit rien garder en mémoire ?**
Le service est partagé par toutes les requêtes. S'il garde un `contratCourant`, la requête d'un utilisateur peut l'écraser pendant qu'un autre s'en sert. Le deuxième enregistrerait alors le contrat du premier.

**4. Pourquoi `update` charge l'ancien objet avant `save` ?**
Sinon tout ce qui n'est pas rempli devient `null` en base, et on pourrait perdre les paiements du contrat. En chargeant l'ancien objet, on ne change que ce qu'on veut.

**5. À connaître**
- `@Component` : sur notre classe, pour que Spring la trouve. `@Bean` : sur une méthode, pour une classe qu'on ne peut pas modifier.
- `@Primary` : choisit le bean par défaut (sur la classe). `@Qualifier` : choisit un bean précis à l'endroit où on l'utilise.
- `IPaiementService` n'a ni création, ni modification, ni suppression : un paiement appartient à un contrat et est supprimé avec lui.