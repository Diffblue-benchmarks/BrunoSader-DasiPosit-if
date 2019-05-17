package fr.insalyon.dasi.positif.dao;

import fr.insalyon.dasi.positif.metier.modele.Client;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Le Data Access Object de Personne
 * 
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class ClientDAO extends PersonneDAO {

    /**
     * Permet d'obtenir la liste de tous les clients de la base de données
     * @return  la liste de tous les clients.
     */
    public static List<Client> obtenirTous() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("SELECT c FROM Client c").getResultList();
    }

    /**
     * Crée un nouveau client dans la base de données
     * @param c le client à créer
     */
    public static void creer(Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(c);
    }

    /**
     * Permet de modifier le client passé en paramètre dans la base de donnée
     * @param c le client à modifier
     */
    public static void modifier(Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(c);
    }

}
