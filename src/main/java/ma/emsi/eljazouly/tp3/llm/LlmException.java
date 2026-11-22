package ma.emsi.eljazouly.tp3.llm;

/**
 * Exception lancée quand l'échange avec le LLM échoue
 * (clé absente, erreur réseau, quota dépassé...).
 * Non contrôlée : la classe de ressource la traite pour renvoyer une erreur HTTP propre.
 */
public class LlmException extends RuntimeException {

    public LlmException(String message) {
        super(message);
    }

    public LlmException(String message, Throwable cause) {
        super(message, cause);
    }
}
