package ma.emsi.eljazouly.tp3.llm;

import dev.langchain4j.service.SystemMessage;

/**
 * Interface du guide touristique.
 * On n'ecrit pas d'implementation : c'est AiServices (LangChain4j) qui la genere.
 * Le role systeme (unique : guide touristique) est donne par l'annotation @SystemMessage ;
 * il est envoye au LLM avec chaque requete.
 */
public interface GuideTouristique {

    /**
     * Demande au LLM des informations touristiques sur un lieu.
     *
     * @param lieu nom d'une ville ou d'un pays (message utilisateur).
     * @return la reponse du LLM, un document JSON.
     */
    @SystemMessage("""
            Tu es un guide touristique. Pour la ville ou le pays que l'utilisateur te donne, \
            indique les 2 principaux endroits à visiter dans ce lieu, ainsi que le prix moyen \
            d'un repas dans la devise du pays.
            N'utilise pas Markdown, réponds uniquement avec du JSON, avec exactement ce format :
            {"ville_ou_pays": "nom de la ville ou du pays", "endroits_a_visiter": ["endroit 1", "endroit 2"], "prix_moyen_repas": "<prix> <devise du pays>"}
            """)
    String chat(String lieu);
}
