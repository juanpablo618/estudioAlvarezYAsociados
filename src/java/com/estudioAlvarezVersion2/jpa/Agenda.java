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
@Table(name = "Agenda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a")
    , @NamedQuery(name = "Agenda.findByIdAgenda", query = "SELECT a FROM Agenda a WHERE a.idAgenda = :idAgenda")
    , @NamedQuery(name = "Agenda.findByFecha", query = "SELECT a FROM Agenda a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "Agenda.findByDescripcion", query = "SELECT a FROM Agenda a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "Agenda.findByNombre", query = "SELECT a FROM Agenda a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Agenda.findByResponsableAndFecha", query = "SELECT a FROM Agenda a WHERE a.responsable = :responsable AND a.fecha = :fecha ORDER BY a.fecha")
    , @NamedQuery(name = "Agenda.findByOrder", query = "SELECT a FROM Agenda a WHERE a.orden = :orden ORDER BY a.fecha")
    , @NamedQuery(name = "Agenda.findByResponsablesAndFecha", query = "SELECT a FROM Agenda a WHERE a.responsable IN :responsables AND a.fecha = :fecha ORDER BY a.fecha")
    
    
    })
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAgenda")
    private Integer idAgenda;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "nombre")
    private String nombre;
        
    @Column(name = "apellido")
    private String apellido;
    
    @Basic(optional = false)
    @Column(name = "responsable")
    private String responsable;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "realizado")
    private String realizado;

    public Agenda() {
    }

    public Agenda(Integer idAgenda) {
        this.idAgenda = idAgenda;
    }

    public Agenda(Integer idAgenda, Date fecha, String descripcion, String nombre, String apellido, String responsable, int orden, String realizado) {
        this.idAgenda = idAgenda;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.responsable = responsable;
        this.orden = orden;
        this.realizado = realizado;
    }

    public Integer getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Integer idAgenda) {
        this.idAgenda = idAgenda;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
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

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
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
        hash += (idAgenda != null ? idAgenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agenda)) {
            return false;
        }
        Agenda other = (Agenda) object;
        if ((this.idAgenda == null && other.idAgenda != null) || (this.idAgenda != null && !this.idAgenda.equals(other.idAgenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agenda{" + ", fecha=" + fecha + ", descripcion=" + descripcion + ", apellido=" + apellido + ", nombre=" + nombre + ", responsable=" + responsable + ", orden=" + orden + ", realizado=" + realizado + '}';
    }
    
}
