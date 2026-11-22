package ma.emsi.eljazouly.tp3.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Ressource REST du guide touristique.
 */
@Path("/guide")
public class GuideTouristiqueResource {

    /**
     * Endpoint GET qui donne les principaux endroits a visiter dans une ville ou un pays.
     * Pour le moment, retourne un tableau dont le seul element est le lieu passe dans le chemin ;
     * c'est JAX-RS qui le transforme en tableau JSON, a cause de l'annotation @Produces.
     *
     * @param villeOuPays nom de la ville ou du pays (dernier segment du chemin).
     * @return un tableau qui contient seulement le lieu.
     */
    @GET
    @Path("lieu/{ville_ou_pays}")
    @Produces(MediaType.APPLICATION_JSON)
    public String[] endroitsDeVisite(@PathParam("ville_ou_pays") String villeOuPays) {
        return new String[]{villeOuPays};
    }
}
