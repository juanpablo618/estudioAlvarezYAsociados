package com.estudioAlvarezVersion2.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
@Entity
@Table(name = "fechasRestringidas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FechasRestringidas.findAll", query = "SELECT f FROM FechasRestringidas f"),
    @NamedQuery(name = "FechasRestringidas.findByIdFechaRestringida", query = "SELECT f FROM FechasRestringidas f WHERE f.idFechaRestringida = :idFechaRestringida"),
    @NamedQuery(name = "FechasRestringidas.findByFecha", query = "SELECT f FROM FechasRestringidas f WHERE f.fecha = :fecha")
})
public class FechasRestringidas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFechaRestringida")
    private Integer idFechaRestringida;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public FechasRestringidas() {
    }

    public FechasRestringidas(Integer idFechaRestringida) {
        this.idFechaRestringida = idFechaRestringida;
    }

    public FechasRestringidas(Integer idFechaRestringida, Date fecha) {
        this.idFechaRestringida = idFechaRestringida;
        this.fecha = fecha;
    }

    public Integer getIdFechaRestringida() {
        return idFechaRestringida;
    }

    public void setIdFechaRestringida(Integer idFechaRestringida) {
        this.idFechaRestringida = idFechaRestringida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFechaRestringida != null ? idFechaRestringida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FechasRestringidas)) {
            return false;
        }
        FechasRestringidas other = (FechasRestringidas) object;
        if ((this.idFechaRestringida == null && other.idFechaRestringida != null) || (this.idFechaRestringida != null && !this.idFechaRestringida.equals(other.idFechaRestringida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.estudioAlvarezVersion2.jpa.FechasRestringidas[ idFechaRestringida=" + idFechaRestringida + " ]";
    }
}
