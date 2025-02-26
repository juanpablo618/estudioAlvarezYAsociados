package com.estudioAlvarezVersion2.jpa;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
@Table(name = "Consulta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consulta.findAll", query = "SELECT e FROM Consulta e ORDER BY e.fechaDeAtencion DESC"),
    @NamedQuery(name = "Consulta.findAllExceptArchivedOrDismissed",
            query = "SELECT e FROM Consulta e WHERE e.estadoConsulta NOT IN ('ARCHIVADO / DESISTIDO') ORDER BY e.fechaDeAtencion DESC"),
    @NamedQuery(name = "Consulta.findByIdConsulta", query = "SELECT e FROM Consulta e WHERE e.idConsulta = :idConsulta"),
    @NamedQuery(name = "Consulta.findByOrden", query = "SELECT e FROM Consulta e WHERE e.orden = :orden"),
    @NamedQuery(name = "Consulta.findByCuit", query = "SELECT e FROM Consulta e WHERE e.cuit = :cuit"),
    @NamedQuery(name = "Consulta.findByDni", query = "SELECT e FROM Consulta e WHERE e.cuit = :dni"),
    @NamedQuery(name = "Consulta.findByNombre", query = "SELECT e FROM Consulta e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Consulta.findByTipoDeDocumento", query = "SELECT e FROM Consulta e WHERE e.tipoDeDocumento = :tipoDeDocumento"),
    @NamedQuery(name = "Consulta.findBySexo", query = "SELECT e FROM Consulta e WHERE e.sexo = :sexo"),
    @NamedQuery(name = "Consulta.findByApellido", query = "SELECT e FROM Consulta e WHERE e.apellido = :apellido"),
    @NamedQuery(name = "Consulta.findByDireccion", query = "SELECT e FROM Consulta e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Consulta.findByNroDeAltura", query = "SELECT e FROM Consulta e WHERE e.nroDeAltura = :nroDeAltura"),
    @NamedQuery(name = "Consulta.findByPiso", query = "SELECT e FROM Consulta e WHERE e.piso = :piso"),
    @NamedQuery(name = "Consulta.findByDepto", query = "SELECT e FROM Consulta e WHERE e.depto = :depto"),
    @NamedQuery(name = "Consulta.findByBarrio", query = "SELECT e FROM Consulta e WHERE e.barrio = :barrio"),
    @NamedQuery(name = "Consulta.findByTelefono", query = "SELECT e FROM Consulta e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Consulta.findByFechaDeNacimiento", query = "SELECT e FROM Consulta e WHERE e.fechaDeNacimiento = :fechaDeNacimiento"),
    @NamedQuery(name = "Consulta.findByEdad", query = "SELECT e FROM Consulta e WHERE e.edad = :edad")})
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idConsulta")
    private Integer idConsulta;

    @Basic(optional = false)
    @Column(name = "orden")
    private Integer orden;

    @Column(name = "cuit")
    private String cuit;

    @Basic(optional = false)
    @Column(name = "dni")
    private String dni;

    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @Size(min = 1, max = 200)
    @Column(name = "tipoDeDocumento")
    private String tipoDeDocumento;

    @Basic(optional = false)
    @Column(name = "sexo")
    private String sexo;
    @Basic(optional = false)

    @Column(name = "apellido")
    private String apellido;

    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;

    @Basic(optional = false)
    @Column(name = "nroDeAltura")
    private int nroDeAltura;

    @Basic(optional = false)
    @Column(name = "piso")
    private String piso;

    @Basic(optional = false)
    @Column(name = "depto")
    private String depto;

    @Basic(optional = false)
    @Column(name = "barrio")
    private String barrio;

    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "telefonoAuxiliar")
    private String telefonoAuxiliar;

    @Basic(optional = false)
    @Column(name = "fechaDeNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaDeNacimiento;

    @Basic(optional = false)
    @Column(name = "edad")
    private int edad;

    @Basic(optional = false)
    @Column(name = "claveSeguridadSocial")
    private String claveSeguridadSocial;

    @Basic(optional = false)
    @Column(name = "claveFiscal")
    private String claveFiscal;

    @Basic(optional = false)
    @Column(name = "claveCidi")
    private String claveCidi;

    @Basic(optional = false)
    @Column(name = "cobraBeneficio")
    private String cobraBeneficio;

    @Basic(optional = false)
    @Column(name = "fechaDeAltaDeConsulta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeAltaDeConsulta;

    @Basic(optional = false)
    @Column(name = "codigoPostal")
    private String codigoPostal;

    @Basic(optional = false)
    @Column(name = "localidad")
    private String localidad;

    @Basic(optional = false)
    @Column(name = "tipoDeTramite")
    private String tipoDeTramite;

    @Basic(optional = false)
    @Column(name = "procedencia")
    private String procedencia;

    @Basic(optional = false)
    @Column(name = "estadoDelTramite")
    private String estadoDelTramite;

    @Basic(optional = false)
    @Column(name = "fechaDeCobro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCobro;

    @Basic(optional = false)
    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Basic(optional = false)
    @Column(name = "caratula")
    private String caratula;

    @Basic(optional = false)
    @Column(name = "nroDeConsulta")
    private String nroDeConsulta;

    @Basic(optional = false)
    @Column(name = "juzgadoODependencia")
    private String juzgadoODependencia;

    @Basic(optional = false)
    @Column(name = "observaciones")
    private String observaciones;

    @Basic(optional = false)
    @Column(name = "responsable")
    private String responsable;

    @Basic(optional = false)
    @Column(name = "apoderado")
    private String apoderado;

    @Basic(optional = false)
    @Column(name = "comunicaciones")
    private String comunicaciones;

    @Basic(optional = false)
    @Column(name = "fechaDeAtencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeAtencion;

    @Basic(optional = false)
    @Column(name = "convenioDeHonorarios")
    private String convenioDeHonorarios;

    @Basic(optional = false)
    @Column(name = "poderFirmado")
    private String poderFirmado;

    @Basic(optional = false)
    @Column(name = "etapaProcesal")
    private String etapaProcesal;

    @Basic(optional = false)
    @Column(name = "jurisdiccion")
    private String jurisdiccion;

    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;

    @Basic(optional = false)
    @Column(name = "detalleDeEstadoDeTramite")
    private String detalleDeEstadoDeTramite;

    @Basic(optional = false)
    @Column(name = "tablaDeHonorariosYGastos")
    private String tablaDeHonorariosYGastos;

    @Column(name = "subCategoriasDeTipo")
    private String[] subCategoriasDeTipo;

    @Column(name = "cantidadDeHijos")
    private int cantidadDeHijos;

    @Column(name = "cantidadDeHijosConDiscapacidad")
    private int cantidadDeHijosConDiscapacidad;

    @Column(name = "cantidadDeHijosAdoptivos")
    private int cantidadDeHijosAdoptivos;

    @Column(name = "cantidadDeHijosPercibioAuh")
    private int cantidadDeHijosPercibioAuh;

    @Column(name = "estadoCivil")
    private String estadoCivil;

    @Column(name = "datosDelConyuge")
    private String datosDelConyuge;

    @Column(name = "tipoDeBeneficio")
    private String tipoDeBeneficio;

    @Column(name = "aportes")
    private String aportes;

    @Column(name = "detalleDeAportes")
    private String detalleDeAportes;

    @Column(name = "trabajando")
    private String trabajando;

    @Column(name = "obraSocial")
    private String obraSocial;

    @Column(name = "inscripcionAut")
    private String inscripcionAut;

    @Column(name = "reclamoArt")
    private String reclamoArt;

    @Column(name = "estadoConsulta")
    private String estadoConsulta;

    @Column(name = "equipo")
    private String equipo;

    @Column(name = "quienTomoConsulta")
    private String quienTomoConsulta;

    public Consulta() {
    }

    public Consulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Consulta(Integer idConsulta, int orden, String cuit, String dni,
            String nombre, String tipoDeDocumento, String sexo, String apellido,
            String direccion, int nroDeAltura, String piso, String depto,
            String barrio, String telefono, String telefonoAuxiliar, Date fechaDeNacimiento, int edad,
            String claveSeguridadSocial, String claveFiscal, String claveCidi,
            String cobraBeneficio, Date fechaDeAltaDeConsulta, String codigoPostal,
            String localidad, String tipoDeTramite, String procedencia,
            String estadoDelTramite, Date fechaDeCobro, String nacionalidad,
            String tipoDeConsulta, String caratula, String nroDeConsulta,
            String juzgadoODependencia, String observaciones, String responsable,
            String apoderado, String comunicaciones, Date fechaDeAtencion,
            String convenioDeHonorarios, String jurisdiccion, String poderFirmado,
            String tipo, String etapaProcesal, String detalleDeEstadoDeTramite,
            String tablaDeHonorariosYGastos, String[] subCategoriasDeTipo,
            int cantidadDeHijos, int cantidadDeHijosConDiscapacidad,
            int cantidadDeHijosAdoptivos, int cantidadDeHijosPercibioAuh,
            String estadoCivil, String datosDelConyuge, String tipoDeBeneficio,
            String aportes, String detalleDeAportes, String trabajando,
            String obraSocial, String inscripcionAut, String reclamoArt,
            String estadoConsulta, String equipo, String quienTomoConsulta
    ) {
        this.idConsulta = idConsulta;
        this.orden = orden;
        this.cuit = cuit;
        this.dni = dni;
        this.nombre = nombre;
        this.tipoDeDocumento = tipoDeDocumento;
        this.sexo = sexo;
        this.apellido = apellido;
        this.direccion = direccion;
        this.nroDeAltura = nroDeAltura;
        this.piso = piso;
        this.depto = depto;
        this.barrio = barrio;
        this.telefono = telefono;
        this.telefonoAuxiliar = telefonoAuxiliar;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.edad = edad;
        this.claveSeguridadSocial = claveSeguridadSocial;
        this.claveFiscal = claveFiscal;
        this.claveCidi = claveCidi;
        this.cobraBeneficio = cobraBeneficio;
        this.fechaDeAltaDeConsulta = fechaDeAltaDeConsulta;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.tipoDeTramite = tipoDeTramite;
        this.procedencia = procedencia;
        this.estadoDelTramite = estadoDelTramite;
        this.fechaDeCobro = fechaDeCobro;
        this.nacionalidad = nacionalidad;
        this.caratula = caratula;
        this.nroDeConsulta = nroDeConsulta;
        this.juzgadoODependencia = juzgadoODependencia;
        this.observaciones = observaciones;
        this.responsable = responsable;
        this.apoderado = apoderado;
        this.comunicaciones = comunicaciones;
        this.fechaDeAtencion = fechaDeAtencion;
        this.convenioDeHonorarios = convenioDeHonorarios;
        this.poderFirmado = poderFirmado;
        this.tipo = tipo;
        this.jurisdiccion = jurisdiccion;
        this.etapaProcesal = etapaProcesal;
        this.detalleDeEstadoDeTramite = detalleDeEstadoDeTramite;
        this.tablaDeHonorariosYGastos = tablaDeHonorariosYGastos;
        this.subCategoriasDeTipo = subCategoriasDeTipo;
        this.cantidadDeHijos = cantidadDeHijos;
        this.cantidadDeHijosConDiscapacidad = cantidadDeHijosConDiscapacidad;
        this.cantidadDeHijosAdoptivos = cantidadDeHijosAdoptivos;
        this.cantidadDeHijosPercibioAuh = cantidadDeHijosPercibioAuh;
        this.estadoCivil = estadoCivil;
        this.datosDelConyuge = datosDelConyuge;
        this.tipoDeBeneficio = tipoDeBeneficio;
        this.aportes = aportes;

        this.detalleDeAportes = detalleDeAportes;
        this.trabajando = trabajando;
        this.obraSocial = obraSocial;
        this.inscripcionAut = inscripcionAut;
        this.reclamoArt = reclamoArt;
        this.estadoConsulta = estadoConsulta;
        this.equipo = equipo;
        this.quienTomoConsulta = quienTomoConsulta;
    }

    public String getQuienTomoConsulta() {
        return quienTomoConsulta;
    }

    public void setQuienTomoConsulta(String quienTomoConsulta) {
        this.quienTomoConsulta = quienTomoConsulta;
    }
    
    

    public String[] getSubCategoriasDeTipo() {
        return subCategoriasDeTipo;
    }

    public void setSubCategoriasDeTipo(String[] subCategoriasDeTipo) {
        this.subCategoriasDeTipo = subCategoriasDeTipo;
    }

    public String getSelectedValueString() {
        return Arrays.toString(subCategoriasDeTipo);
    }

    public String getApoderado() {
        return apoderado;
    }

    public void setApoderado(String apoderado) {
        this.apoderado = apoderado;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getCuit() {
        if (cuit != null) {
            return cuit;
        }
        return "";
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getDni() {
        return dni;
    }

    //ESTO LO TENGO Q HACER POR Q LOS DNI ESTÁN MAL CARGADOS LA GRAN MAYORíA
    public String getCuitRecordado() {
        if (cuit != null && cuit.length() >= 10) {
            String documento = cuit.replace(" ", "");
            documento = documento.substring(2, 10);
            return documento;
        } else {
            return dni;
        }
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public void setTipoDeDocumento(String tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getApellido() {
        return apellido;
    }

    public String getApellidoYNombre() {
        if (apellido != null && nombre != null) {
            return apellido.concat(" ").concat(nombre);
        } else {
            return null;
        }
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNroDeAltura() {
        return nroDeAltura;
    }

    public void setNroDeAltura(int nroDeAltura) {
        this.nroDeAltura = nroDeAltura;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefonoAuxiliar() {
        return telefonoAuxiliar;
    }

    public void setTelefonoAuxiliar(String telefonoAuxiliar) {
        this.telefonoAuxiliar = telefonoAuxiliar;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getClaveSeguridadSocial() {
        return claveSeguridadSocial;
    }

    public void setClaveSeguridadSocial(String claveSeguridadSocial) {
        this.claveSeguridadSocial = claveSeguridadSocial;
    }

    public String getClaveFiscal() {
        return claveFiscal;
    }

    public void setClaveFiscal(String claveFiscal) {
        this.claveFiscal = claveFiscal;
    }

    public String getClaveCidi() {
        return claveCidi;
    }

    public void setClaveCidi(String claveCidi) {
        this.claveCidi = claveCidi;
    }

    public String getCobraBeneficio() {
        return cobraBeneficio;
    }

    public void setCobraBeneficio(String cobraBeneficio) {
        this.cobraBeneficio = cobraBeneficio;
    }

    public Date getFechaDeAltaDeConsulta() {
        return fechaDeAltaDeConsulta;
    }

    public void setFechaDeAltaDeConsulta(Date fechaDeAltaDeConsulta) {
        this.fechaDeAltaDeConsulta = fechaDeAltaDeConsulta;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTipoDeTramite() {
        return tipoDeTramite;
    }

    public void setTipoDeTramite(String tipoDeTramite) {
        this.tipoDeTramite = tipoDeTramite;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getEstadoDelTramite() {
        return estadoDelTramite;
    }

    public void setEstadoDelTramite(String estadoDelTramite) {
        this.estadoDelTramite = estadoDelTramite;
    }

    public Date getFechaDeCobro() {
        return fechaDeCobro;
    }

    public void setFechaDeCobro(Date fechaDeCobro) {
        this.fechaDeCobro = fechaDeCobro;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getTablaDeHonorariosYGastos() {
        return tablaDeHonorariosYGastos;
    }

    public void setTablaDeHonorariosYGastos(String tablaDeHonorariosYGastos) {
        this.tablaDeHonorariosYGastos = tablaDeHonorariosYGastos;
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public String getNroDeConsulta() {
        return nroDeConsulta;
    }

    public void setNroDeConsulta(String nroDeConsulta) {
        this.nroDeConsulta = nroDeConsulta;
    }

    public String getJuzgadoODependencia() {
        return juzgadoODependencia;
    }

    public void setJuzgadoODependencia(String juzgadoODependencia) {
        this.juzgadoODependencia = juzgadoODependencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getComunicaciones() {
        return comunicaciones;
    }

    public void setComunicaciones(String comunicaciones) {
        this.comunicaciones = comunicaciones;
    }

    public String getDetalleDeEstadoDeTramite() {
        return detalleDeEstadoDeTramite;
    }

    public void setDetalleDeEstadoDeTramite(String detalleDeEstadoDeTramite) {
        this.detalleDeEstadoDeTramite = detalleDeEstadoDeTramite;
    }

    public String getEstadoConsulta() {
        return estadoConsulta;
    }

    public void setEstadoConsulta(String estadoConsulta) {
        this.estadoConsulta = estadoConsulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConsulta != null ? idConsulta.hashCode() : 0);
        return hash;
    }

    public Date getFechaDeAtencion() {
        return fechaDeAtencion;
    }
    
    public String getFechaDeAtencionFormateada() {
        if (fechaDeAtencion != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fechaDeAtencion);
        }
        return ""; // Devuelve una cadena vacía en lugar de null
    }

    public String getDiaMesAnioParaFechaDeAtencion() {

        if (fechaDeAtencion != null) {
            DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            return formatoFecha.format(fechaDeAtencion);
        }
        return "";

    }

    public void setFechaDeAtencion(Date fechaDeAtencion) {
        this.fechaDeAtencion = fechaDeAtencion;
    }

    public String getConvenioDeHonorarios() {
        return convenioDeHonorarios;
    }

    public void setConvenioDeHonorarios(String convenioDeHonorarios) {
        this.convenioDeHonorarios = convenioDeHonorarios;
    }

    public String getPoderFirmado() {
        return poderFirmado;
    }

    public void setPoderFirmado(String poderFirmado) {
        this.poderFirmado = poderFirmado;
    }

    public String getEtapaProcesal() {
        return etapaProcesal;
    }

    public void setEtapaProcesal(String etapaProcesal) {
        this.etapaProcesal = etapaProcesal;
    }

    public String getJurisdiccion() {
        return jurisdiccion;
    }

    public void setJurisdiccion(String jurisdiccion) {
        this.jurisdiccion = jurisdiccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidadDeHijos() {
        return cantidadDeHijos;
    }

    public void setCantidadDeHijos(int cantidadDeHijos) {
        this.cantidadDeHijos = cantidadDeHijos;
    }

    public int getCantidadDeHijosConDiscapacidad() {
        return cantidadDeHijosConDiscapacidad;
    }

    public void setCantidadDeHijosConDiscapacidad(int cantidadDeHijosConDiscapacidad) {
        this.cantidadDeHijosConDiscapacidad = cantidadDeHijosConDiscapacidad;
    }

    public int getCantidadDeHijosAdoptivos() {
        return cantidadDeHijosAdoptivos;
    }

    public void setCantidadDeHijosAdoptivos(int cantidadDeHijosAdoptivos) {
        this.cantidadDeHijosAdoptivos = cantidadDeHijosAdoptivos;
    }

    public int getCantidadDeHijosPercibioAuh() {
        return cantidadDeHijosPercibioAuh;
    }

    public void setCantidadDeHijosPercibioAuh(int cantidadDeHijosPercibioAuh) {
        this.cantidadDeHijosPercibioAuh = cantidadDeHijosPercibioAuh;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getDatosDelConyuge() {
        return datosDelConyuge;
    }

    public void setDatosDelConyuge(String datosDelConyuge) {
        this.datosDelConyuge = datosDelConyuge;
    }

    public String getTipoDeBeneficio() {
        return tipoDeBeneficio;
    }

    public void setTipoDeBeneficio(String tipoDeBeneficio) {
        this.tipoDeBeneficio = tipoDeBeneficio;
    }

    public String getAportes() {
        return aportes;
    }

    public void setAportes(String aportes) {
        this.aportes = aportes;
    }

    public String getDetalleDeAportes() {
        return detalleDeAportes;
    }

    public void setDetalleDeAportes(String detalleDeAportes) {
        this.detalleDeAportes = detalleDeAportes;
    }

    public String getTrabajando() {
        return trabajando;
    }

    public void setTrabajando(String trabajando) {
        this.trabajando = trabajando;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getInscripcionAut() {
        return inscripcionAut;
    }

    public void setInscripcionAut(String inscripcionAut) {
        this.inscripcionAut = inscripcionAut;
    }

    public String getReclamoArt() {
        return reclamoArt;
    }

    public void setReclamoArt(String reclamoArt) {
        this.reclamoArt = reclamoArt;
    }

    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consulta)) {
            return false;
        }
        Consulta other = (Consulta) object;
        if ((this.idConsulta == null && other.idConsulta != null) || (this.idConsulta != null && !this.idConsulta.equals(other.idConsulta))) {
            return false;
        }
        return true;
    }

}
