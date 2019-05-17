package fr.insalyon.dasi.positif.dao;

import fr.insalyon.dasi.positif.metier.modele.Astrologue;
import javax.persistence.EntityManager;

/**
 * Le Data Access Object d'Astrologue
 * 
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class AstrologueDAO extends MediumDAO {
    
    /**
     * Permet de créer un nouvel astrologue dans la base de données.
     * @param a le nouvel astrologue
     */
    public static void creer(Astrologue a) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(a);
    }
    
}
