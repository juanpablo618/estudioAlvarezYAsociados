package com.estudioAlvarezVersion2.jpa;

/**
 *
 * @author juanpablo618@hotmail.com
 */
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "SituacionPrevisional")
@XmlRootElement
public class SituacionPrevisional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSituacionPrevisional")
    private Integer idSituacionPrevisional;

    @Basic(optional = false)
    @Column(name = "orden")
    private Integer orden;

    @NotNull
    @Size(max = 255)
    @Column(name = "empleador")
    private String empleador;

    @NotNull
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column(name = "anios")
    private Integer anios;

    @Column(name = "meses")
    private Integer meses;

    @Column(name = "dias")
    private Integer dias;
    
    @Column(name = "observaciones")
    private String observaciones;

    public SituacionPrevisional() {
    }

    public SituacionPrevisional(Integer idSituacionPrevisional, Integer orden, String empleador, Date fechaInicio, Date fechaFin, Integer anios, Integer meses, Integer dias, String observaciones) {
        this.idSituacionPrevisional = idSituacionPrevisional;
        this.orden = orden;
        this.empleador = empleador;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.anios = anios;
        this.meses = meses;
        this.dias = dias;
        this.observaciones = observaciones;
    }
    
    // Getters y Setters
    public Integer getIdSituacionPrevisional() {
        return idSituacionPrevisional;
    }

    public void setIdSituacionPrevisional(Integer id) {
        this.idSituacionPrevisional = idSituacionPrevisional;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getEmpleador() {
        return empleador;
    }

    public void setEmpleador(String empleador) {
        this.empleador = empleador;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getAnios() {
        return anios;
    }

    public void setAnios(Integer anios) {
        this.anios = anios;
    }

    public Integer getMeses() {
        return meses;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }
    
    public void setMeses(Integer meses) {
        this.meses = meses;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // hashCode, equals y toString
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSituacionPrevisional != null ? idSituacionPrevisional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SituacionPrevisional)) {
            return false;
        }
        SituacionPrevisional other = (SituacionPrevisional) object;
        return !((this.idSituacionPrevisional == null && other.idSituacionPrevisional != null) || (this.idSituacionPrevisional != null && !this.idSituacionPrevisional.equals(other.idSituacionPrevisional)));
    }

    @Override
    public String toString() {
        return idSituacionPrevisional != null ? idSituacionPrevisional.toString() : "Sin ID";
    }
}
