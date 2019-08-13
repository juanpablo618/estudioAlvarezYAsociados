/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author developer
 */
@Entity
@Table(name = "Agenda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a")
    , @NamedQuery(name = "Agenda.findByIdAgenda", query = "SELECT a FROM Agenda a WHERE a.idAgenda = :idAgenda")
    , @NamedQuery(name = "Agenda.findByFecha", query = "SELECT a FROM Agenda a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "Agenda.findByDescripcion", query = "SELECT a FROM Agenda a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "Agenda.findByNombre", query = "SELECT a FROM Agenda a WHERE a.nombre = :nombre")})
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
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)

    @Size(min = 1, max = 500)
    @Column(name = "nombre")
    private String nombre;
        
    @Size(min = 1, max = 500)
    @Column(name = "apellido")
    private String apellido;
    
    @Basic(optional = false)
    @Column(name = "responsable")
    private String responsable;

    @Column(name = "orden")
    private int orden;

        
    public Agenda() {
    }

    public Agenda(Integer idAgenda) {
        this.idAgenda = idAgenda;
    }

    public Agenda(Integer idAgenda, Date fecha, String descripcion, String nombre, String apellido, String responsable, int orden) {
        this.idAgenda = idAgenda;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.responsable = responsable;
        this.orden = orden;
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

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
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
        return "com.estudioAlvarezVersion2.jpa.Agenda[ idAgenda=" + idAgenda + " ]";
    }
    
}
