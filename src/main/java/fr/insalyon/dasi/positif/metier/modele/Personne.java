package fr.insalyon.dasi.positif.metier.modele;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Classe représentant une personne.
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="person_type")  // Spécifie s'il s'agit d'un client ou d'un employé
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    /**
     * Le nom de la Personne.
     */
    private String nom;
    
    /**
     * Le prénom de la Personne.
     */
    private String prenom;
    
    /**
     * Le mot de passe de la Personne.
     */
    private String motDePasse;
    
    /**
     * L'adresse email de la Personne.
     */
    @Column(unique=true)
    private String email;
    
    /**
     * Le numéro de téléphone de la Personne.
     */
    private String numeroTel;
    
    /**
     * Constructeur par défaut
     */
    public Personne(){ }
    
    /**
     * Constructeur
     * @param nom Le nom de la personne
     * @param prenom Le prénom de la personne
     * @param motDePasse Le mot de passe de la personne
     * @param email L'email de la personne
     * @param numeroTel Le numéro de téléphone de la personne
     */
    public Personne(String nom, String prenom, String motDePasse, String email, String numeroTel) {
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.email = email;
        this.numeroTel = numeroTel;
    }

// ********** GETTERS AND SETTERS ***********
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTel() {
        return numeroTel;
    }
    
    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Personne)) {
            return false;
        }
        Personne other = (Personne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.dasi.positif.metier.modele.Personne[ id=" + id + " ]";
    }
}
