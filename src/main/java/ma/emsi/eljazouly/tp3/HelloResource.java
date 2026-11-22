package ma.emsi.eljazouly.tp3;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

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
     *
     * @param nom la valeur du segment {nom} de l'URL (lien fait par @PathParam("nom")).
     * @return "Hello, " suivi du nom.
     */
    @GET
    @Path("personnes/{nom}")
    @Produces("text/plain")
    public String hello(@PathParam("nom") String nom) {
        return "Hello, " + nom;
    }
}
