package fr.insalyon.dasi.positif.metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Classe représentant un employé. 
 * Super classe : Personne.
 *
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
@Entity
@DiscriminatorValue("E")
public class Employe extends Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
     * La liste des Mediums que peut incarner l'employé.
     */
    @ManyToMany
    private List<Medium> mediums = new ArrayList<Medium>();
    
    /**
     * La liste des conversations de l'employé
     */
    @OneToMany
    private List<Conversation> conversations = new ArrayList<Conversation>();
    
    /**
     * La disponibilité de l'employé.
     */
    private boolean disponible;

    /**
     * Constructeur par défaut
     */
    public Employe() {
        super();
    }

    /**
     * Constructeur
     *
     * @param dispo Disponibilité de l'employé
     * @param nom Nom de l'employé
     * @param prenom Prénom de l'employé
     * @param motDePasse Mot de passe de l'employé
     * @param email Adresse email de l'employé
     * @param numeroTel Numéro de téléphone de l'employé
     */
    public Employe(boolean dispo, String nom, String prenom, String motDePasse, String email, String numeroTel) {
        super(nom, prenom, motDePasse, email, numeroTel);
        this.disponible = dispo;
    }

    /**
     * **** GETTERS ET SETTERS **********
     */


    public boolean isDispo() {
        return disponible;
    }

    public List<Medium> getMedium() {
        return mediums;
    }
    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setMediums(List<Medium> mediums) {
        this.mediums = mediums;
    }

    public void setDisponible(boolean dispo) {
        this.disponible = dispo;
    }
    
    public void addConversation (Conversation conversation){
        this.conversations.add(conversation); 
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employe)) {
            return false;
        }
        Employe other = (Employe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getPrenom() + " " + this.getNom();
    }
}
