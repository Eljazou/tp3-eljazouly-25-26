package ma.emsi.eljazouly.tp3.resource;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ma.emsi.eljazouly.tp3.llm.LlmClientForGuideTouristique;
import ma.emsi.eljazouly.tp3.llm.LlmException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ressource REST du guide touristique : c'est le LLM (Gemini) qui fait le guide.
 */
@Path("/guide")
public class GuideTouristiqueResource {

    private static final Logger LOGGER = Logger.getLogger(GuideTouristiqueResource.class.getName());

    @Inject
    private LlmClientForGuideTouristique llmClient;

    /**
     * Endpoint GET qui donne les 2 principaux endroits à visiter dans une ville ou un pays,
     * et le prix moyen d'un repas dans la devise du pays.
     * Exemple : GET .../api/guide/lieu/Nice
     *
     * Retourne une Response (plutôt qu'un String) pour pouvoir ajouter des en-têtes :
     * pas de cache, et Access-Control-Allow-Origin pour qu'une page HTML locale puisse
     * appeler l'API (CORS).
     *
     * @param villeOuPays nom de la ville ou du pays (dernier segment du chemin).
     * @return le document JSON du LLM, par exemple
     *         {"ville_ou_pays": "Nice", "endroits_a_visiter": ["...", "..."], "prix_moyen_repas": "25 EUR"} ;
     *         en cas d'erreur avec le LLM, le statut 503 avec un document JSON {"erreur": "..."}.
     */
    @GET
    @Path("lieu/{ville_ou_pays}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endroitsDeVisite(@PathParam("ville_ou_pays") String villeOuPays) {
        try {
            String reponseJson = llmClient.getInfos(villeOuPays);
            return avecEnTetes(Response.ok(reponseJson)).build();
        } catch (LlmException e) {
            LOGGER.log(Level.WARNING, "Echec de la requete au LLM pour " + villeOuPays, e);
            return erreur(Response.Status.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    /**
     * Construit une réponse d'erreur avec un document JSON {"erreur": "message"}.
     */
    private static Response erreur(Response.Status status, String message) {
        String json = Json.createObjectBuilder().add("erreur", message).build().toString();
        return avecEnTetes(Response.status(status).entity(json)).build();
    }

    /**
     * Ajoute les en-têtes donnés dans l'énoncé : pas de cache, et CORS.
     */
    private static Response.ResponseBuilder avecEnTetes(Response.ResponseBuilder responseBuilder) {
        responseBuilder.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        responseBuilder.header("Pragma", "no-cache");
        responseBuilder.header("Expires", "0");
        responseBuilder.header("Access-Control-Allow-Origin", "*");
        return responseBuilder;
    }
}
