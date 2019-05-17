package fr.insalyon.dasi.positif.dao;


import fr.insalyon.dasi.positif.metier.modele.Client;
import fr.insalyon.dasi.positif.metier.modele.Medium;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Le Data Access Object de Medium
 * 
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class MediumDAO {

    /**
     * Récupère la liste de tous les Mediums dans la base de données
     * @return La liste des Mediums
     */
    public static List<Medium> obtenirTous() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("SELECT m FROM Medium m").getResultList();
    }

    /**
     * Modifie le Medium passé en paramètre dans la base de donnée
     * @param m le Medium à modifier
     */
    public static void modifier(Medium m) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(m);
    }
    
    public static  Medium obtenirMediumParId(long id) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            Query q = em.createQuery("SELECT p "
                                   + "FROM Medium p "
                                   + "WHERE p.id = :id");
            q.setParameter("id", id);
            return (Medium) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
