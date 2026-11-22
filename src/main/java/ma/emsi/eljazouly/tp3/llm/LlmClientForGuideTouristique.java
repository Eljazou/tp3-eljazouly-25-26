package ma.emsi.eljazouly.tp3.llm;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Classe métier qui gère les échanges avec le LLM (Gemini) pour le guide touristique,
 * grâce à LangChain4j. Adaptée de la classe LlmClient du TP 2.
 *
 * Différences avec le TP 2 :
 * - pas de setter pour le rôle système : il n'y a qu'un seul rôle, donné par @SystemMessage
 *   dans l'interface GuideTouristique ;
 * - pas de mémoire : chaque requête REST est indépendante (REST est sans état) ; il ne faut pas
 *   partager une conversation entre des requêtes (et des clients) différents.
 *
 * Portée application : sans mémoire, le service ne garde aucun état propre à un client, et le
 * modèle de LangChain4j peut être utilisé par plusieurs threads. Une seule instance suffit donc
 * pour toutes les requêtes (inutile de recréer le modèle à chaque requête).
 */
@ApplicationScoped
public class LlmClientForGuideTouristique {

    /**
     * Nom du modèle Gemini (le même qu'aux TP 1 et 2).
     */
    private static final String NOM_MODELE = "gemini-3.5-flash-lite";

    /**
     * Le guide touristique, dont l'implémentation est générée par AiServices.
     */
    private GuideTouristique guideTouristique;

    /**
     * Crée le modèle et le service. Fait dans @PostConstruct plutôt que dans le constructeur,
     * car CDI peut instancier la classe pour créer un proxy.
     */
    @PostConstruct
    void init() {
        // Clé secrète de l'API, dans une variable d'environnement du système d'exploitation.
        String key = System.getenv("GEMINI_KEY");
        if (key == null || key.isBlank()) {
            // Pas d'exception ici (l'injection échouerait avec une erreur 500 peu claire) :
            // getInfos lancera une LlmException.
            return;
        }
        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(key)
                .modelName(NOM_MODELE)
                // Température basse : on veut des réponses factuelles et un format respecté.
                .temperature(0.3)
                // Le moyen le plus sûr d'avoir du JSON sans Markdown (```json ... ```).
                .responseFormat(ResponseFormat.JSON)
                .build();
        this.guideTouristique = AiServices.builder(GuideTouristique.class)
                .chatModel(model)
                .build();
    }

    /**
     * Demande au LLM des informations touristiques sur un lieu.
     *
     * @param lieu nom d'une ville ou d'un pays.
     * @param nb   nombre d'endroits a visiter.
     * @return la réponse JSON du LLM.
     * @throws LlmException si l'échange avec le LLM échoue.
     */
    public String getInfos(String lieu, int nb) {
        if (guideTouristique == null) {
            throw new LlmException("La variable d'environnement GEMINI_KEY n'est pas définie.");
        }
        try {
            return guideTouristique.chat(lieu, nb);
        } catch (RuntimeException e) {
            throw new LlmException("Erreur pendant l'échange avec le LLM : " + e.getMessage(), e);
        }
    }
}
