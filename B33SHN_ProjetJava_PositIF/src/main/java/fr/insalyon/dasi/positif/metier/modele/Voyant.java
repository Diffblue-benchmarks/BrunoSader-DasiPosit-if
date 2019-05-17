package fr.insalyon.dasi.positif.metier.modele;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Classe représentant un Voyant.
 * Super Classe : Medium
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
@Entity
@DiscriminatorValue("V")
public class Voyant extends Medium implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * La spécialité du Voyant.
     */
    private String specialite;

    /**
     * Constructeur par défaut
     */
    public Voyant() {
        super();
    }

    /**
     * Constructeur
     * @param specialite La specialité du Voyant
     * @param nom Le nom du Voyant
     * @param descriptif Le descriptif du Voyant.
     */
    public Voyant(String specialite, String nom, String descriptif) {
        super(nom, descriptif);
        this.specialite = specialite;
    }
    
//******* GETTERS SETTERS *********/
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
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
        if (!(object instanceof Voyant)) {
            return false;
        }
        Voyant other = (Voyant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
