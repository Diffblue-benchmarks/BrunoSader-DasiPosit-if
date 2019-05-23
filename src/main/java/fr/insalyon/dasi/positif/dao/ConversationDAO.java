package fr.insalyon.dasi.positif.dao;

import fr.insalyon.dasi.positif.metier.modele.Conversation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
    
    public static Conversation obtenirConversationParId(long id) {
         EntityManager em = JpaUtil.obtenirEntityManager();
         return em.find(Conversation.class, id);
    }
    
    public static List <Conversation> obtenirConvParMedium(long client_id,long idMedium) {
         EntityManager em = JpaUtil.obtenirEntityManager();
         try {
            Query q = em.createQuery("SELECT c "
                    + "FROM Conversation c "
                    + "WHERE c.client_id= :client_id ");
                    // + "AND c.medium_id= :idMedium");
            q.setParameter("client_id", client_id);
            // q.setParameter("idMedium", idMedium);
            System.out.println("*******************************************************************************"+((q.getResultList()).get(0)));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
