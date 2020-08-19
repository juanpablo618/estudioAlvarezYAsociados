/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
@Entity
@Table(name = "turno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t"),
    @NamedQuery(name = "Turno.findByIdTurno", query = "SELECT t FROM Turno t WHERE t.idTurno = :idTurno"),
    @NamedQuery(name = "Turno.findByHoraYDia", query = "SELECT t FROM Turno t WHERE t.horaYDia = :horaYDia"),
    @NamedQuery(name = "Turno.findByNombre", query = "SELECT t FROM Turno t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Turno.findByApellido", query = "SELECT t FROM Turno t WHERE t.apellido = :apellido"),
    @NamedQuery(name = "Turno.findByNroDeTelefono", query = "SELECT t FROM Turno t WHERE t.nroDeTelefono = :nroDeTelefono"),
    @NamedQuery(name = "Turno.findByProcedencia", query = "SELECT t FROM Turno t WHERE t.procedencia = :procedencia"),
    @NamedQuery(name = "Turno.findByObservacion", query = "SELECT t FROM Turno t WHERE t.observacion = :observacion")})
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTurno")
    private Integer idTurno;

    @Basic(optional = false)
    @Column(name = "horaYDia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaYDia;

    @Column(name = "nombre")
    private String nombre;

    @Size(max = 300)
    @Column(name = "apellido")
    private String apellido;
    
    @Size(max = 150)
    @Column(name = "nroDeTelefono")
    private String nroDeTelefono;
    @Size(max = 500)
    @Column(name = "procedencia")
    private String procedencia;
    @Size(max = 500)
    @Column(name = "observacion")
    private String observacion;

    @Column(name = "responsable")
    private String responsable;

    @Column(name = "realizado")
    private String realizado;
    
    @Column(name = "oficina")
    private String oficina;
    
    @Column(name = "tipoDeTurno")
    private String tipoDeTurno;
    
    
    public Turno() {
    }

    public Turno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public Turno(Integer idTurno, Date horaYDia, String responsable) {
        this.idTurno = idTurno;
        this.horaYDia = horaYDia;
        this.responsable = responsable;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public Date getHoraYDia() {
        return horaYDia;
    }

    public String getDiaMesAnio() {
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        return formatoFecha.format(horaYDia);
    }

    public void setHoraYDia(Date horaYDia) {
        this.horaYDia = horaYDia;
    }

    public String getNombre() {
        if(nombre != null){
        return nombre;}
        else{
            return "";
        }
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

    public String getNroDeTelefono() {
        return nroDeTelefono;
    }

    public void setNroDeTelefono(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public String getTipoDeTurno() {
        return tipoDeTurno;
    }

    public void setTipoDeTurno(String tipoDeTurno) {
        this.tipoDeTurno = tipoDeTurno;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurno != null ? idTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.idTurno == null && other.idTurno != null) || (this.idTurno != null && !this.idTurno.equals(other.idTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.estudioAlvarezVersion2.jpa.Turno[ idTurno=" + idTurno + " ]";
    }

}
