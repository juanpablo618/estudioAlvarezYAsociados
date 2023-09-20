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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
@Entity
@Table(name = "Honorario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Honorario.findAll", query = "SELECT a FROM Honorario a")
    , @NamedQuery(name = "Honorario.findByIdHonorario", query = "SELECT a FROM Honorario a WHERE a.idHonorario = :idHonorario")
    , @NamedQuery(name = "Honorario.findByNombre", query = "SELECT a FROM Honorario a WHERE a.nombre = :nombre")})
public class Honorario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHonorario")
    private Integer idHonorario;
    
    @Column(name = "nombre")
    private String nombre;
        
    @Column(name = "apellido")
    private String apellido;
    
    @Column(name = "juzgadoFederal")
    private String juzgadoFederal;

    @Column(name = "tipoDeJuicio")
    private String tipoDeJuicio;

    @Column(name = "orden")
    private int orden;
    
    @Column(name = "tipoDeHonorario")
    private String tipoDeHonorario;

    @Column(name = "regimenDeCostas")
    private String regimenDeCostas;

    @Column(name = "regulacionDeNotificacion")
    private String regulacionDeNotificacion;

    @Column(name = "aNombreDeQuien")
    private String aNombreDeQuien;

    @Column(name = "porcentaje")
    private int porcentaje;

    @Column(name = "costas")
    private String costas;
    
    @Column(name = "estado")
    private String estado;
    
    @Temporal(TemporalType.DATE)
    private Date fechaRegulacion;


    
    public Honorario() {
    }

    public Honorario(Integer idHonorario) {
        this.idHonorario = idHonorario;
    }

    public Honorario(Integer idHonorario, String nombre, String apellido, String juzgadoFederal, String tipoDeJuicio, int orden, String tipoDeHonorario, String regimenDeCostas, String regulacionDeNotificacion, String aNombreDeQuien, int porcentaje, String costas, String estado, Date fechaRegulacion) {
        this.idHonorario = idHonorario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.juzgadoFederal = juzgadoFederal;
        this.tipoDeJuicio = tipoDeJuicio;
        this.orden = orden;
        this.tipoDeHonorario = tipoDeHonorario;
        this.regimenDeCostas = regimenDeCostas;
        this.regulacionDeNotificacion = regulacionDeNotificacion;
        this.aNombreDeQuien = aNombreDeQuien;
        this.porcentaje = porcentaje;
        this.costas = costas;
        this.estado = estado;
        this.fechaRegulacion = fechaRegulacion;
    }

    

    public Integer getIdHonorario() {
        return idHonorario;
    }

    public void setIdHonorario(Integer idHonorario) {
        this.idHonorario = idHonorario;
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
    
    public String getjuzgadoFederal() {
        return juzgadoFederal;
    }

    public void setjuzgadoFederal(String juzgadoFederal) {
        this.juzgadoFederal = juzgadoFederal;
    }

    public String getJuzgadoFederal() {
        return juzgadoFederal;
    }

    public void setJuzgadoFederal(String juzgadoFederal) {
        this.juzgadoFederal = juzgadoFederal;
    }

    public String getTipoDeJuicio() {
        return tipoDeJuicio;
    }

    public void setTipoDeJuicio(String tipoDeJuicio) {
        this.tipoDeJuicio = tipoDeJuicio;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getTipoDeHonorario() {
        return tipoDeHonorario;
    }

    public void setTipoDeHonorario(String tipoDeHonorario) {
        this.tipoDeHonorario = tipoDeHonorario;
    }

    public String getRegimenDeCostas() {
        return regimenDeCostas;
    }

    public void setRegimenDeCostas(String regimenDeCostas) {
        this.regimenDeCostas = regimenDeCostas;
    }

    public String getRegulacionDeNotificacion() {
        return regulacionDeNotificacion;
    }

    public void setRegulacionDeNotificacion(String regulacionDeNotificacion) {
        this.regulacionDeNotificacion = regulacionDeNotificacion;
    }

    public String getAnombreDeQuien() {
        return aNombreDeQuien;
    }

    public void setAnombreDeQuien(String aNombreDeQuien) {
        this.aNombreDeQuien = aNombreDeQuien;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getCostas() {
        return costas;
    }

    public void setCostas(String costas) {
        this.costas = costas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaRegulacion() {
        return fechaRegulacion;
    }

    public void setFechaRegulacion(Date fechaRegulacion) {
        this.fechaRegulacion = fechaRegulacion;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHonorario != null ? idHonorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Honorario)) {
            return false;
        }
        Honorario other = (Honorario) object;
        if ((this.idHonorario == null && other.idHonorario != null) || (this.idHonorario != null && !this.idHonorario.equals(other.idHonorario))) {
            return false;
        }
        return true;
    }
    
}
