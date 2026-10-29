package tn.esprit.autolocapi.web.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // A MODIFIER
    private static final String EMAIL_GMAIL = "mboukhriss34@gmail.com";           // destinataire du lien Gmail
    private static final String OUTLOOK_DESTINATAIRE = "Maram.Boukhriss@esprit.tn"; // destinataire du lien Outlook
    private static final String GITHUB = "https://github.com/maramboukhriss/AutoLoc.git";

    @Bean
    public OpenAPI autolocOpenApi() {
        String description = "API REST de gestion de location de véhicules.\n\n"
                + "**Nous contacter :** "
                + "[Gmail](https://mail.google.com/mail/?view=cm&fs=1&to=" + EMAIL_GMAIL + "&su=AutoLoc-API) · "
                + "[Outlook](https://outlook.live.com/mail/0/deeplink/compose?to=" + OUTLOOK_DESTINATAIRE + "&subject=AutoLoc-API) · "
                + "[GitHub](" + GITHUB + ")";

        return new OpenAPI().info(new Info()
                .title("AutoLoc API")
                .description(description)
                .version("2.0")
                .contact(new Contact()
                        .name("Contacter l'équipe AutoLoc")
                        .email(EMAIL_GMAIL)));
    }

    // ---- Liste « Select a definition » : une entrée par entité ----

    @Bean
    public GroupedOpenApi agencesApi() {
        return groupe("agences", "Agences", "/agences/**");
    }

    @Bean
    public GroupedOpenApi clientsApi() {
        return groupe("clients", "Clients", "/clients/**");
    }

    @Bean
    public GroupedOpenApi vehiculesApi() {
        return groupe("vehicules", "Véhicules", "/vehicules/**");
    }

    @Bean
    public GroupedOpenApi equipementsApi() {
        return groupe("equipements", "Équipements", "/equipements/**");
    }

    @Bean
    public GroupedOpenApi employesApi() {
        return groupe("employes", "Employés", "/employes/**");
    }

    @Bean
    public GroupedOpenApi reservationsApi() {
        return groupe("reservations", "Réservations", "/reservations/**");
    }

    @Bean
    public GroupedOpenApi maintenancesApi() {
        return groupe("maintenances", "Maintenances", "/maintenances/**");
    }

    @Bean
    public GroupedOpenApi contratsApi() {
        return groupe("contrats", "Contrats", "/contrats/**");
    }

    @Bean
    public GroupedOpenApi paiementsApi() {
        return groupe("paiements", "Paiements", "/paiements/**");
    }

    private GroupedOpenApi groupe(String nom, String titre, String chemin) {
        return GroupedOpenApi.builder()
                .group(nom)
                .displayName(titre)
                .pathsToMatch(chemin)
                .build();
    }
}