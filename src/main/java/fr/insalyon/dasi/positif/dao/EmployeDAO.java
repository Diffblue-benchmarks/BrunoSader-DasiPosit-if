package fr.insalyon.dasi.positif.dao;

import fr.insalyon.dasi.positif.metier.modele.Client;
import fr.insalyon.dasi.positif.metier.modele.Employe;
import fr.insalyon.dasi.positif.metier.modele.Medium;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Le Data Access Object d'Employé.
 * 
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
public class EmployeDAO extends PersonneDAO {

    /**
     * Permet d'obtenir tous les Employés de la base de donnée
     * @return la liste de tous les Employés
     */
    public static List<Employe> obtenirTous() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("SELECT e FROM Employe e").getResultList();
    }

    public static  Employe obtenirEmployeParId(long id) {
         EntityManager em = JpaUtil.obtenirEntityManager();
         return em.find(Employe.class, id);
    }
    /**
     * Permet de Modifier l'Employé passé en paramètre dans la base de donnée
     * @param e l'Employé à modifier
     */
    public static void modifier(Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(e);
    }

    /**
     * Permet de Créer un nouvel Employé dans la base de donnée.
     * @param e le nouvel Employé
     */
    public static void creer(Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(e);
    }

    /**
     * Permet de récupérer l'Employé pouvant incarner le médium passé
     * en paramètre et qui a le moins d'affectations.
     * 
     * @param medium Le médium à incarner
     * @return l'Employé qui va effectuer la voyance
     */
    public static Employe obtenirEmployePourVoyance(Medium medium) {
        EntityManager em = JpaUtil.obtenirEntityManager();

        Query q = em.createQuery("SELECT e FROM Employe e "
                + "WHERE :medium MEMBER OF e.mediums and e.disponible = TRUE");
        q.setParameter("medium", medium);

        List<Employe> l = q.getResultList();

        if (l.isEmpty()) {
            return null;
        } else {
            int min = 99999;
            int i = 0;
            int j = 0;
            for (Employe e : l) {
                if (min > e.getConversations().size()) {
                    min = e.getConversations().size();
                    i = j;
                }
                j++;
            }
        
            return (Employe) l.get(i);
        }

    }
}
