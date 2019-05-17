package fr.insalyon.dasi.positif.metier.modele;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Classe représentant un astrologue.
 * Super classe : Medium
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
@Entity
@DiscriminatorValue("A")
public class Astrologue extends Medium implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * La formation de l'astrologue.
     */
    private String formation;
    
    /**
     * La promotion de l'astrologue.
     */
    private String promotion;

    /**
     * Constructeur par défaut
     */
    public Astrologue() {
    }

    /**
     * Constructeur
     * @param nom Le nom de l'astrologue
     * @param descriptif Le descriptif de l'astrologue
     * @param formation La formation de l'astrologue
     * @param promotion La date de promotion de l'astrologue
     */
    public Astrologue(String nom, String descriptif, String formation, String promotion) {
        super(nom, descriptif);
        this.formation = formation;
        this.promotion = promotion;
    }

/*********** GETTERS ET SETTERS *************/
    public String getFormation() {    
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getPromotion() {
        return promotion;
    }
    
    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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
        if (!(object instanceof Astrologue)) {
            return false;
        }
        Astrologue other = (Astrologue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
