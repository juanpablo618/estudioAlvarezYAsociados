package com.estudioAlvarezVersion2.jpa;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
@Entity
@Table(name = "Comunicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comunicacion.findAll", query = "SELECT a FROM Comunicacion a")
    , @NamedQuery(name = "Comunicacion.findByIdComunicacion", query = "SELECT a FROM Comunicacion a WHERE a.idComunicacion = :idComunicacion")
    , @NamedQuery(name = "Comunicacion.findByFecha", query = "SELECT a FROM Comunicacion a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "Comunicacion.findByDescripcion", query = "SELECT a FROM Comunicacion a WHERE a.descripcion = :descripcion")
    })
public class Comunicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idComunicacion")
    private Integer idComunicacion;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "orden")
    private Integer orden;
    @Basic(optional = false)
    @Column(name = "responsable")
    private String responsable;
    
    public Comunicacion() {
    }

    public Comunicacion(Integer idComunicacion) {
        this.idComunicacion = idComunicacion;
    }

    public Comunicacion(Integer idComunicacion, Date fecha, String descripcion, int orden, String responsable) {
        this.idComunicacion = idComunicacion;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.orden = orden;
        this.responsable = responsable;
    }

    public Integer getIdComunicacion() {
        return idComunicacion;
    }

    public void setIdComunicacion(Integer idComunicacion) {
        this.idComunicacion = idComunicacion;
    }

    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
   
    public Integer getOrden() {
        
        if(orden != null){
        return orden;}
        else{
            return 0;
        }
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
    public String getDiaMesAnio() {
        
        if(fecha != null){
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        return formatoFecha.format(fecha);
        }
        return "";
        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComunicacion != null ? idComunicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comunicacion)) {
            return false;
        }
        Comunicacion other = (Comunicacion) object;
        if ((this.idComunicacion == null && other.idComunicacion != null) || (this.idComunicacion != null && !this.idComunicacion.equals(other.idComunicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comunicacion{" + "idComunicacion=" + idComunicacion + ", fecha=" + fecha + ", descripcion=" + descripcion + ", orden=" + orden + '}';
    }

}
