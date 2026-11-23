package ma.emsi.eljazouly.tp3;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * Classe de ressource generee par IntelliJ, remise pour l'introduction a JAX-RS.
 */
@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    /**
     * Version de hello avec un parametre donne dans l'URL.
     * Exemple : GET .../api/hello-world/personnes/Mohamed affiche "Hello, Mohamed".
     * Le type retour Response permet de donner plus d'informations au client HTTP
     * (code retour, headers, cookies...).
     * Pour finir l'introduction, le return "ok" est en commentaire et la methode renvoie
     * une erreur "Internal Server Error" (statut 500).
     *
     * @param nom la valeur du segment {nom} de l'URL (lien fait par @PathParam("nom")).
     * @return une reponse avec le statut 500 (Internal Server Error).
     */
    @GET
    @Path("personnes/{nom}")
    @Produces("text/plain")
    public Response hello(@PathParam("nom") String nom) {
        // return Response.ok("Hello, " + nom).build();
        return Response.serverError().build();
    }
}
