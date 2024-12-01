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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanpablo618@hotmail.com
 */
@Entity
@Table(name = "eventoDeCausasJudiciales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventoDeCausasJudiciales.findAll", query = "SELECT e FROM EventoDeCausasJudiciales e"),
    @NamedQuery(name = "EventoDeCausasJudiciales.findByIdEventoDeCausasJudiciales", query = "SELECT e FROM EventoDeCausasJudiciales e WHERE e.idEventoDeCausasJudiciales = :idEventoDeCausasJudiciales"),
    @NamedQuery(name = "EventoDeCausasJudiciales.findByOrden", query = "SELECT e FROM EventoDeCausasJudiciales e WHERE e.orden = :orden"),
    @NamedQuery(name = "EventoDeCausasJudiciales.findByNombre", query = "SELECT e FROM EventoDeCausasJudiciales e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EventoDeCausasJudiciales.findByApellido", query = "SELECT e FROM EventoDeCausasJudiciales e WHERE e.apellido = :apellido"),
    @NamedQuery(name = "EventoDeCausasJudiciales.findByTipoDeFecha", query = "SELECT e FROM EventoDeCausasJudiciales e WHERE e.tipoDeFecha = :tipoDeFecha"),
    @NamedQuery(name = "EventoDeCausasJudiciales.findByFecha", query = "SELECT e FROM EventoDeCausasJudiciales e WHERE e.fecha = :fecha")
})
public class EventoDeCausasJudiciales implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEventoDeCausasJudiciales")
    private Integer idEventoDeCausasJudiciales;

    @Column(name = "orden")
    private Integer orden;

    @Size(max = 500)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 500)
    @Column(name = "apellido")
    private String apellido;

    @Size(max = 500)
    @Column(name = "tipoDeFecha")
    private String tipoDeFecha;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Transient
    private Long diasHastaProximaFecha;

    public Long getDiasHastaProximaFecha() {
        return diasHastaProximaFecha;
    }

    public void setDiasHastaProximaFecha(Long diasHastaProximaFecha) {
        this.diasHastaProximaFecha = diasHastaProximaFecha;
    }
    
    public EventoDeCausasJudiciales() {
    }

    public EventoDeCausasJudiciales(Integer idEventoDeCausasJudiciales) {
        this.idEventoDeCausasJudiciales = idEventoDeCausasJudiciales;
    }

    public Integer getIdEventoDeCausasJudiciales() {
        return idEventoDeCausasJudiciales;
    }

    public void setIdEventoDeCausasJudiciales(Integer idEventoDeCausasJudiciales) {
        this.idEventoDeCausasJudiciales = idEventoDeCausasJudiciales;
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

    public String getNombre() {
        return nombre != null ? nombre : "";
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

    public String getTipoDeFecha() {
        return tipoDeFecha;
    }

    public void setTipoDeFecha(String tipoDeFecha) {
        this.tipoDeFecha = tipoDeFecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEventoDeCausasJudiciales != null ? idEventoDeCausasJudiciales.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EventoDeCausasJudiciales)) {
            return false;
        }
        EventoDeCausasJudiciales other = (EventoDeCausasJudiciales) object;
        if ((this.idEventoDeCausasJudiciales == null && other.idEventoDeCausasJudiciales != null) || (this.idEventoDeCausasJudiciales != null && !this.idEventoDeCausasJudiciales.equals(other.idEventoDeCausasJudiciales))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.estudioAlvarezVersion2.jpa.EventoDeCausasJudiciales[ idEventoDeCausasJudiciales=" + idEventoDeCausasJudiciales + " ]";
    }
}
