package tn.esprit.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolocapi.domain.Equipement;
import tn.esprit.autolocapi.service.IEquipementService;

@Tag(name = "Équipements", description = "Gestion des équipements")
@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    @Operation(summary = "Lister les equipements")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<Equipement> findAll() {
        return equipementService.findAll();
    }

    @Operation(summary = "Consulter un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource trouvée"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public Equipement findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return equipementService.findById(id);
    }

    @Operation(summary = "Créer un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ressource créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipement create(@RequestBody Equipement equipement) {
        return equipementService.create(equipement);
    }

    @Operation(summary = "Modifier un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource modifiée"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/{id}")
    public Equipement update(@Parameter(description = "Identifiant") @PathVariable Long id,
                             @RequestBody Equipement equipement) {
        return equipementService.update(id, equipement);
    }

    @Operation(summary = "Supprimer un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ressource supprimée"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
