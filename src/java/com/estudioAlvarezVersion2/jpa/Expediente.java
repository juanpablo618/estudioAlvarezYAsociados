package com.estudioAlvarezVersion2.jpa;

import java.io.Serializable;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
@Entity
@Table(name = "Expediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Expediente.findAll", query = "SELECT e FROM Expediente e")
    , @NamedQuery(name = "Expediente.findByIdExpediente", query = "SELECT e FROM Expediente e WHERE e.idExpediente = :idExpediente")
    , @NamedQuery(name = "Expediente.findByOrden", query = "SELECT e FROM Expediente e WHERE e.orden = :orden")
    , @NamedQuery(name = "Expediente.findByCuit", query = "SELECT e FROM Expediente e WHERE e.cuit = :cuit")
    , @NamedQuery(name = "Expediente.findByDni", query = "SELECT e FROM Expediente e WHERE e.cuit = :dni")
    , @NamedQuery(name = "Expediente.findByNombre", query = "SELECT e FROM Expediente e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Expediente.findByTipoDeDocumento", query = "SELECT e FROM Expediente e WHERE e.tipoDeDocumento = :tipoDeDocumento")
    , @NamedQuery(name = "Expediente.findBySexo", query = "SELECT e FROM Expediente e WHERE e.sexo = :sexo")
    , @NamedQuery(name = "Expediente.findByApellido", query = "SELECT e FROM Expediente e WHERE e.apellido = :apellido")
    , @NamedQuery(name = "Expediente.findByDireccion", query = "SELECT e FROM Expediente e WHERE e.direccion = :direccion")
    , @NamedQuery(name = "Expediente.findByNroDeAltura", query = "SELECT e FROM Expediente e WHERE e.nroDeAltura = :nroDeAltura")
    , @NamedQuery(name = "Expediente.findByPiso", query = "SELECT e FROM Expediente e WHERE e.piso = :piso")
    , @NamedQuery(name = "Expediente.findByDepto", query = "SELECT e FROM Expediente e WHERE e.depto = :depto")
    , @NamedQuery(name = "Expediente.findByBarrio", query = "SELECT e FROM Expediente e WHERE e.barrio = :barrio")
    , @NamedQuery(name = "Expediente.findByTelefono", query = "SELECT e FROM Expediente e WHERE e.telefono = :telefono")
    , @NamedQuery(name = "Expediente.findByFechaDeNacimiento", query = "SELECT e FROM Expediente e WHERE e.fechaDeNacimiento = :fechaDeNacimiento")
    , @NamedQuery(name = "Expediente.findByEdad", query = "SELECT e FROM Expediente e WHERE e.edad = :edad")
    , @NamedQuery(name = "Expediente.findClaveCidiByOrden", query = "SELECT e.claveCidi FROM Expediente e WHERE e.orden = :orden")
    , @NamedQuery(name = "Expediente.findClaveSeguridadSocialByOrden", query = "SELECT e.claveSeguridadSocial FROM Expediente e WHERE e.orden = :orden")
    , @NamedQuery(name = "Expediente.findClaveFiscalByOrden", query = "SELECT e.claveFiscal FROM Expediente e WHERE e.orden = :orden")
    , @NamedQuery(name = "Expediente.findCuitByOrden", query = "SELECT e.cuit FROM Expediente e WHERE e.orden = :orden")
})
public class Expediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idExpediente")
    private Integer idExpediente;
    
    @Basic(optional = false)
    @Column(name = "orden")
    private Integer orden;

    @Basic(optional = false)
    @Column(name = "cuit")
    private String cuit;

    @Basic(optional = false)
    @Column(name = "dni")
    private String dni;

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
    
    @Basic(optional = false)
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
    @Column(name = "fechaDeAltaDeExpediente")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeAltaDeExpediente;

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
    @Column(name = "tipoDeExpediente")
    private String tipoDeExpediente;

    @Basic(optional = false)
    @Column(name = "caratula")
    private String caratula;

    @Basic(optional = false)
    @Column(name = "nroDeExpediente")
    private String nroDeExpediente;

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
    private String[]  subCategoriasDeTipo;
    
    @Column(name = "cantidadDeHijos")
    private int  cantidadDeHijos;
    
    @Column(name = "cantidadDeHijosConDiscapacidad")
    private int  cantidadDeHijosConDiscapacidad;
    
    @Column(name = "cantidadDeHijosAdoptivos")
    private int  cantidadDeHijosAdoptivos;
    
    @Column(name = "cantidadDeHijosPercibioAuh")
    private int  cantidadDeHijosPercibioAuh;
    
    @Column(name = "estadoCivil")
    private String  estadoCivil;

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
    
    @Column(name = "equipo")    
    private String equipo;
    
    @Column(name = "activo")    
    private String activo;
    
    public Expediente() {
    }

    public Expediente(Integer idExpediente) {
        this.idExpediente = idExpediente;
    }

    public Expediente(Integer idExpediente, int orden, String cuit, String dni,
            String nombre, String tipoDeDocumento, String sexo, String apellido,
            String direccion, int nroDeAltura, String piso, String depto,
            String barrio, String telefono, String telefonoAuxiliar, Date fechaDeNacimiento, int edad,
            String claveSeguridadSocial, String claveFiscal, String claveCidi,
            String cobraBeneficio, Date fechaDeAltaDeExpediente, String codigoPostal,
            String localidad, String tipoDeTramite, String procedencia,
            String estadoDelTramite, Date fechaDeCobro, String nacionalidad,
            String tipoDeExpediente, String caratula, String nroDeExpediente,
            String juzgadoODependencia, String observaciones, String responsable, 
            String apoderado, String comunicaciones, Date fechaDeAtencion,
            String convenioDeHonorarios, String jurisdiccion, String poderFirmado,
            String tipo, String etapaProcesal, String detalleDeEstadoDeTramite, 
            String tablaDeHonorariosYGastos, String[]  subCategoriasDeTipo,
            int cantidadDeHijos, int cantidadDeHijosConDiscapacidad,
            int cantidadDeHijosAdoptivos, int cantidadDeHijosPercibioAuh,
            String estadoCivil, String datosDelConyuge, String tipoDeBeneficio,
            String aportes, String detalleDeAportes, String trabajando, 
            String obraSocial, String inscripcionAut, String reclamoArt,
            String equipo, String activo
    
    ) {
        this.idExpediente = idExpediente;
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
        this.fechaDeAltaDeExpediente = fechaDeAltaDeExpediente;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.tipoDeTramite = tipoDeTramite;
        this.procedencia = procedencia;
        this.estadoDelTramite = estadoDelTramite;
        this.fechaDeCobro = fechaDeCobro;
        this.nacionalidad = nacionalidad;
        this.tipoDeExpediente = tipoDeExpediente;
        this.caratula = caratula;
        this.nroDeExpediente = nroDeExpediente;
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
        this.equipo = equipo;
        this.activo = activo;
        
        
    }
    
    public String[]  getSubCategoriasDeTipo() {
        return  subCategoriasDeTipo;
    }

    public void setSubCategoriasDeTipo(String[]  subCategoriasDeTipo) {
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
    
    public Integer getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(Integer idExpediente) {
        this.idExpediente = idExpediente;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getCuit() {
        if(cuit != null){
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
        if(cuit !=null && cuit.length()>=10){
        String documento = cuit.replace(" ", "");
        documento = documento.substring(2, 10);
        return documento;
        }else{
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
        if(apellido != null && nombre !=null){
        return apellido.concat(" ").concat(nombre);
        }else{
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

    public Date getFechaDeAltaDeExpediente() {
        return fechaDeAltaDeExpediente;
    }

    public void setFechaDeAltaDeExpediente(Date fechaDeAltaDeExpediente) {
        this.fechaDeAltaDeExpediente = fechaDeAltaDeExpediente;
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

    public String getTipoDeExpediente() {
        return tipoDeExpediente;
    }

    public String getTablaDeHonorariosYGastos() {
        return tablaDeHonorariosYGastos;
    }

    public void setTablaDeHonorariosYGastos(String tablaDeHonorariosYGastos) {
        this.tablaDeHonorariosYGastos = tablaDeHonorariosYGastos;
    }
    
    public String getTipoDeExpedienteParaEstilo() {
        String tipoDeExpedienteParaEstilo = "no tiene tipo de exp.";
        if(tipoDeExpediente != null){
         tipoDeExpedienteParaEstilo = tipoDeExpediente.replace(" ", "");
        }
        return tipoDeExpedienteParaEstilo;
    }

    public void setTipoDeExpediente(String tipoDeExpediente) {
        this.tipoDeExpediente = tipoDeExpediente;
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public String getNroDeExpediente() {
        return nroDeExpediente;
    }

    public void setNroDeExpediente(String nroDeExpediente) {
        this.nroDeExpediente = nroDeExpediente;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExpediente != null ? idExpediente.hashCode() : 0);
        return hash;
    }

    public Date getFechaDeAtencion() {
        return fechaDeAtencion;
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

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
    
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expediente)) {
            return false;
        }
        Expediente other = (Expediente) object;
        if ((this.idExpediente == null && other.idExpediente != null) || (this.idExpediente != null && !this.idExpediente.equals(other.idExpediente))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return idExpediente.toString() ;
    }
    
    public String toStringWithAllData() {
        return "Expediente{" + "orden=" + orden + ", cuit=" + cuit + ", dni=" + dni + ", nombre=" + nombre + ", tipoDeDocumento=" + tipoDeDocumento + ", sexo=" + sexo + ", apellido=" + apellido + ", direccion=" + direccion + ", nroDeAltura=" + nroDeAltura + ", piso=" + piso + ", depto=" + depto + ", barrio=" + barrio + ", telefono=" + telefono + ", fechaDeNacimiento=" + fechaDeNacimiento + ", edad=" + edad + ", claveSeguridadSocial=" + claveSeguridadSocial + ", claveFiscal=" + claveFiscal + ", claveCidi=" + claveCidi + ", cobraBeneficio=" + cobraBeneficio + ", fechaDeAltaDeExpediente=" + fechaDeAltaDeExpediente + ", codigoPostal=" + codigoPostal + ", localidad=" + localidad + ", tipoDeTramite=" + tipoDeTramite + ", procedencia=" + procedencia + ", estadoDelTramite=" + estadoDelTramite + ", fechaDeCobro=" + fechaDeCobro + ", nacionalidad=" + nacionalidad + ", tipoDeExpediente=" + tipoDeExpediente + ", caratula=" + caratula + ", nroDeExpediente=" + nroDeExpediente + ", juzgadoODependencia=" + juzgadoODependencia + ", observaciones=" + observaciones + ", comunicaciones=" + comunicaciones + ", fechaDeAtencion=" + fechaDeAtencion + ", convenioDeHonorarios=" + convenioDeHonorarios + ", poderFirmado=" + poderFirmado + ", etapaProcesal=" + etapaProcesal + ", jurisdiccion=" + jurisdiccion + ", tipo=" + tipo + ", detalleDeEstadoDeTramite=" + detalleDeEstadoDeTramite + ", tablaDeHonorariosYGastos=" + tablaDeHonorariosYGastos + '}';
    }

    public String toStringWithDatosPersonalesYDelExp() {
        return "orden=" + orden + "\n, cuit=" + cuit + ", dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido +", tipo de documento=" + tipoDeDocumento + ", sexo=" + sexo + ", direccion=" + direccion + ", nro de altura=" + nroDeAltura + ", piso=" + piso + ", depto=" + depto + ", barrio=" + barrio + ", telefono=" + telefono + ", fecha de nacimiento=" + fechaDeNacimiento + ", edad=" + edad + ", cobraBeneficio=" + cobraBeneficio + ", fecha de alta del Expediente=" + fechaDeAltaDeExpediente + ", codigo postal=" + codigoPostal + ", localidad=" + localidad + ", tipo de tramite=" + tipoDeTramite + ", procedencia=" + procedencia + ", estadoDelTramite=" + estadoDelTramite + ", fechaDeCobro=" + fechaDeCobro + ", nacionalidad=" + nacionalidad + ", tipoDeExpediente=" + tipoDeExpediente + ", caratula=" + caratula + ", nroDeExpediente=" + nroDeExpediente + ", juzgadoODependencia=" + juzgadoODependencia + ", observaciones=" + observaciones + ", comunicaciones=" + comunicaciones + ", fechaDeAtencion=" + fechaDeAtencion + ", convenioDeHonorarios=" + convenioDeHonorarios + ", poderFirmado=" + poderFirmado + ", etapaProcesal=" + etapaProcesal + ", jurisdiccion=" + jurisdiccion + ", tipo=" + tipo + ", detalle De estado de tramite=" + detalleDeEstadoDeTramite + ", tabla de honorarios y gastos=" + tablaDeHonorariosYGastos + '}';
    }
    
}
