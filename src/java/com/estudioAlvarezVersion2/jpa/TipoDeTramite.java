package com.estudioAlvarezVersion2.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
@Entity
@Table(name = "TipoDeTramite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDeTramite.findAll", query = "SELECT e FROM TipoDeTramite e")
    , @NamedQuery(name = "TipoDeTramite.findByIdTipoDeTramite", query = "SELECT e FROM TipoDeTramite e WHERE e.idTipoDeTramite = :idTipoDeTramite")
    , @NamedQuery(name = "TipoDeTramite.findByEstado", query = "SELECT e FROM TipoDeTramite e WHERE e.estado = :estado")})
public class TipoDeTramite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoDeTramite")
    private Integer idTipoDeTramite;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "estado")
    private String estado;

    public TipoDeTramite() {
    }

    public TipoDeTramite(Integer idTipoDeTramite) {
        this.idTipoDeTramite = idTipoDeTramite;
    }

    public TipoDeTramite(Integer idTipoDeTramite, String estado) {
        this.idTipoDeTramite = idTipoDeTramite;
        this.estado = estado;
    }

    public Integer getIdTipoDeTramite() {
        return idTipoDeTramite;
    }

    public void setIdTipoDeTramite(Integer idTipoDeTramite) {
        this.idTipoDeTramite = idTipoDeTramite;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoDeTramite != null ? idTipoDeTramite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDeTramite)) {
            return false;
        }
        TipoDeTramite other = (TipoDeTramite) object;
        if ((this.idTipoDeTramite == null && other.idTipoDeTramite != null) || (this.idTipoDeTramite != null && !this.idTipoDeTramite.equals(other.idTipoDeTramite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.estudioAlvarezVersion2.jpa.EstadoDelTramite[ idTipoDeTramite=" + idTipoDeTramite + " ]";
    }
    
}
