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
@Table(name = "Procedencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Procedencia.findAll", query = "SELECT p FROM Procedencia p")
    , @NamedQuery(name = "Procedencia.findByIdProcedencia", query = "SELECT p FROM Procedencia p WHERE p.idProcedencia = :idProcedencia")
    , @NamedQuery(name = "Procedencia.findByNombreProcedencia", query = "SELECT p FROM Procedencia p WHERE p.nombreProcedencia = :nombreProcedencia")})
public class Procedencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProcedencia")
    private Integer idProcedencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "nombreProcedencia")
    private String nombreProcedencia;

    public Procedencia() {
    }

    public Procedencia(Integer idProcedencia) {
        this.idProcedencia = idProcedencia;
    }

    public Procedencia(Integer idProcedencia, String nombreProcedencia) {
        this.idProcedencia = idProcedencia;
        this.nombreProcedencia = nombreProcedencia;
    }

    public Integer getIdProcedencia() {
        return idProcedencia;
    }

    public void setIdProcedencia(Integer idProcedencia) {
        this.idProcedencia = idProcedencia;
    }

    public String getNombreProcedencia() {
        return nombreProcedencia;
    }

    public void setNombreProcedencia(String nombreProcedencia) {
        this.nombreProcedencia = nombreProcedencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcedencia != null ? idProcedencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Procedencia)) {
            return false;
        }
        Procedencia other = (Procedencia) object;
        if ((this.idProcedencia == null && other.idProcedencia != null) || (this.idProcedencia != null && !this.idProcedencia.equals(other.idProcedencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.estudioAlvarezVersion2.jpa.Procedencia[ idProcedencia=" + idProcedencia + " ]";
    }
    
}
