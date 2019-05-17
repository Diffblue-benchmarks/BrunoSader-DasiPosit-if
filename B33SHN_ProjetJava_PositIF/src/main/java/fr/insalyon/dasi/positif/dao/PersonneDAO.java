package fr.insalyon.dasi.positif.dao;

import fr.insalyon.dasi.positif.metier.modele.Personne;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * The Data Access Object of Personne
 * 
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class PersonneDAO {
    
    /**
     * Récupère dans la table Personne (base de donnée) la personne
     * possédant l'email passé en paramètre.
     * @param email l'email de la personne recherchée
     * @return la Personne recherchée si elle est présente, null sinon
     */
    public static Personne obtenir(String email) {
         EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            Query q = em.createQuery("SELECT p "
                                   + "FROM Personne p "
                                   + "WHERE p.email = :email");
            q.setParameter("email", email);
            return (Personne) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
}
