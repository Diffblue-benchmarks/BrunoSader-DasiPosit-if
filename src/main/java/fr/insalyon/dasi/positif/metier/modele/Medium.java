package fr.insalyon.dasi.positif.metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Classe représentant un Medium.
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="talent")
public class Medium implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    /**
     * La liste des employés pouvant incarner le Medium
     */
    @ManyToMany
    private List<Employe> employes = new ArrayList<Employe>();
    
    /**
     * La liste des conversations du Medium
     */
    @OneToMany
    private List<Conversation> conversation = new ArrayList<Conversation>();
    
    /**
     * Le nom du medium
     */
    private String nom;
    
    /**
     * Le descriptif du medium
     */
    private String descriptif;

    /**
     * Constructeur par défaut
     */
    public Medium() {
    }

    /**
     * Constructeur
     * @param nom Le nom du Medium
     * @param descriptif Le descriptif du Medium
     */
    public Medium(String nom, String descriptif) {
        this.nom = nom;
        this.descriptif = descriptif;
    }
    
/****** GETTERS ET SETTERS *********/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }  

    public List<Employe> getEmployes() {
        return employes;
    }
    public List<Conversation> getConversations() {
        return conversation;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Conversation> getConversation() {
        return conversation;
    }

    public void setConversation(List<Conversation> conversation) {
        this.conversation = conversation;
    }

    public void addConversation (Conversation conversation){
        this.conversation.add(conversation); 
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
        if (!(object instanceof Medium)) {
            return false;
        }
        Medium other = (Medium) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nom : " + this.nom;
    }
}
