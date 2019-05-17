package fr.insalyon.dasi.positif.dao;

import fr.insalyon.dasi.positif.metier.modele.Voyant;
import javax.persistence.EntityManager;

/**
 * Le Data Access Object de Voyant
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class VoyantDAO extends MediumDAO{
    
    /**
     * Créé le voyant dans la base de donnée
     * @param v le voyant à insérer dans la base
     */
    public static void creer(Voyant v) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(v);
    }
}
