/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author developer
 */
@Entity
@Table(name = "EstadoDelTramite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoDelTramite.findAll", query = "SELECT e FROM EstadoDelTramite e")
    , @NamedQuery(name = "EstadoDelTramite.findByIdEstadoDelTramite", query = "SELECT e FROM EstadoDelTramite e WHERE e.idEstadoDelTramite = :idEstadoDelTramite")
    , @NamedQuery(name = "EstadoDelTramite.findByEstado", query = "SELECT e FROM EstadoDelTramite e WHERE e.estado = :estado")})
public class EstadoDelTramite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEstadoDelTramite")
    private Integer idEstadoDelTramite;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "estado")
    private String estado;

    public EstadoDelTramite() {
    }

    public EstadoDelTramite(Integer idEstadoDelTramite) {
        this.idEstadoDelTramite = idEstadoDelTramite;
    }

    public EstadoDelTramite(Integer idEstadoDelTramite, String estado) {
        this.idEstadoDelTramite = idEstadoDelTramite;
        this.estado = estado;
    }

    public Integer getIdEstadoDelTramite() {
        return idEstadoDelTramite;
    }

    public void setIdEstadoDelTramite(Integer idEstadoDelTramite) {
        this.idEstadoDelTramite = idEstadoDelTramite;
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
        hash += (idEstadoDelTramite != null ? idEstadoDelTramite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoDelTramite)) {
            return false;
        }
        EstadoDelTramite other = (EstadoDelTramite) object;
        if ((this.idEstadoDelTramite == null && other.idEstadoDelTramite != null) || (this.idEstadoDelTramite != null && !this.idEstadoDelTramite.equals(other.idEstadoDelTramite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.estudioAlvarezVersion2.jpa.EstadoDelTramite[ idEstadoDelTramite=" + idEstadoDelTramite + " ]";
    }
    
}
