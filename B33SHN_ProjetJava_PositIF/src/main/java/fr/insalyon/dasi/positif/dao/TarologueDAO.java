package fr.insalyon.dasi.positif.dao;

import fr.insalyon.dasi.positif.metier.modele.Tarologue;
import javax.persistence.EntityManager;

/**
 * Le Data Access Object de Tarologue
 * 
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class TarologueDAO extends MediumDAO {
    
    /**
     * Créé le voyant dans la base de donnée
     * @param t le voyant à insérer dans la base
     */
    public static void creer(Tarologue t) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(t);
    }
}
