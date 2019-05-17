package fr.insalyon.dasi.positif.metier.modele;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Classe représentant un Tarologue.
 * Super classe : Medium
 * @author Liam BETTE, Alexis BOSIO, Thibault REMY
 */
@Entity
@DiscriminatorValue("T")
public class Tarologue extends Medium implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Constructeur par défaut.
     */
    public Tarologue() {
        super();
    }

    /**
     * Constructeur
     * @param nom Le nom du Tarologue
     * @param descriptif Le descriptif du Tarologue
     */
    public Tarologue(String nom, String descriptif) {
        super(nom, descriptif);
    }
    
/******* GETTERS ET SETTERS ******/
    
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
        if (!(object instanceof Tarologue)) {
            return false;
        }
        Tarologue other = (Tarologue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
