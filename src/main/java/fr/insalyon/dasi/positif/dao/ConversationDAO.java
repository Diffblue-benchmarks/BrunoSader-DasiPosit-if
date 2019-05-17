package fr.insalyon.dasi.positif.dao;

import fr.insalyon.dasi.positif.metier.modele.Conversation;
import javax.persistence.EntityManager;

/**
 * Le Data Access Object de Conversation
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class ConversationDAO {

    /**
     * Créé une conversation dans la base de donnée
     * @param c la nouvelle conversation
     */
    public static void creer(Conversation c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(c);
    }

    /**
     * Permet de modifier la conversation passée en paramètre dans la base de donnée
     * @param c la conversation à modifier
     */
    public static void modifier(Conversation c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(c);
    }
    
}
