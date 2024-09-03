package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.Login.SessionUtils;
import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpa.DAO;
import com.estudioAlvarezVersion2.jpa.Expediente;
import com.estudioAlvarezVersion2.jpa.ExpedienteDAO;
import com.estudioAlvarezVersion2.jpa.Comunicacion;
import com.estudioAlvarezVersion2.jpa.Consulta;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.ExpedienteFacade;
import com.estudioAlvarezVersion2.jpacontroller.util.ExpedienteUtils;
import java.io.IOException;

import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("expedienteController")
@SessionScoped
public class ExpedienteController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.ExpedienteFacade ejbFacade;
    
    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    private List<Expediente> items = null;
    private Expediente selected;
    private Expediente selectedParaHonorarios;
    private Expediente selectedDesdeWithSession;
    
    private Expediente selectedParaVerExp;
    private String estadoDelTramiteSelected;
    private String fechaDeCumpleSelected;
    private String responsableSelected;
    private String tipoDeExpedienteSelected;
    private String sexoSelected;
    private String tipoDeTramiteSelected;

    private List<Expediente> filteredExpedientes;
    private List<Agenda> filteredAgendasParaHoy;
    private List<Agenda> filteredAgendasAnteriores;
    private List<Agenda> filteredAgendasFuturas;
    private List<Comunicacion> filteredComunicacionesPorNroDeOrden;
    
    private Date dateSelected;
    
    private static final String ADMINISTRATIVO = "administrativo";
    private static final String JUDICIAL = "judicial";
    private static final String SIN_CARPETA = "sin carpeta";

    public Date getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(Date dateSelected) {
        this.dateSelected = dateSelected;
    }

    public ExpedienteController() {
    }

    public Expediente getSelected() {
        return selected;
    }

    public Expediente getSelectedParaHonorarios() {
        return selectedParaHonorarios;
    }

    public Expediente getSelectedDesdeWithSession() {
        return selectedDesdeWithSession;
    }

    public void setSelectedDesdeWithSession(Expediente selectedDesdeWithSession) {
        this.selectedDesdeWithSession = selectedDesdeWithSession;
    }
    
    public void setSelectedParaHonorarios(Expediente selectedParaHonorarios) {
        this.selectedParaHonorarios = selectedParaHonorarios;
    }

    public List<Expediente> getFilteredExpedientes() {
        return filteredExpedientes;
    }

    public String getEstadoDelTramiteSelected() {
        return estadoDelTramiteSelected;
    }

    public void setEstadoDelTramiteSelected(String estadoDelTramiteSelected) {
        this.estadoDelTramiteSelected = estadoDelTramiteSelected;
    }

    public void setFilteredExpedientes(List<Expediente> filteredExpedientes) {
        this.filteredExpedientes = filteredExpedientes;
    }

    public List<Agenda> getFilteredAgendasParaHoy() {
        return filteredAgendasParaHoy;
    }

    public void setFilteredAgendasParaHoy(List<Agenda> filteredAgendasParaHoy) {
        this.filteredAgendasParaHoy = filteredAgendasParaHoy;
    }

    public List<Agenda> getFilteredAgendasAnteriores() {
            return filteredAgendasAnteriores;
    }

    public void setFilteredAgendasAnteriores(List<Agenda> filteredAgendasAnteriores) {
        this.filteredAgendasAnteriores = filteredAgendasAnteriores;
    }

    public List<Agenda> getFilteredAgendasFuturas() {
        return filteredAgendasFuturas;
    }

    public void setFilteredAgendasFuturas(List<Agenda> filteredAgendasFuturas) {
        this.filteredAgendasFuturas = filteredAgendasFuturas;
    }

    public List<Comunicacion> getFilteredComunicacionesPorNroDeOrden() {
        return filteredComunicacionesPorNroDeOrden;
    }

    public void setFilteredComunicacionesPorNroDeOrden(List<Comunicacion> filteredComunicacionesPorNroDeOrden) {
        this.filteredComunicacionesPorNroDeOrden = filteredComunicacionesPorNroDeOrden;
    }

    public void setSelected(Expediente selected) {
        this.selected = selected;
    }

    public void handleDateSelect(Date selected) {
        this.dateSelected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ExpedienteFacade getFacade() {
        return ejbFacade;
    }

    public Expediente getSelectedParaVerExp() {
        return selectedParaVerExp;
    }

    public void setSelectedParaVerExp(Expediente selectedParaVerExp) {
        this.selectedParaVerExp = selectedParaVerExp;
    }

    public String getFechaDeCumpleSelected() {
        return fechaDeCumpleSelected;
    }

    public void setFechaDeCumpleSelected(String fechaDeCumpleSelected) {
        this.fechaDeCumpleSelected = fechaDeCumpleSelected;
    }

    public String getResponsableSelected() {
        return responsableSelected;
    }

    public void setResponsableSelected(String responsable) {
        this.responsableSelected = responsable;
    }

    public String getTipoDeExpedienteSelected() {
        return tipoDeExpedienteSelected;
    }

    public void setTipoDeExpedienteSelected(String tipoDeExpedienteSelected) {
        this.tipoDeExpedienteSelected = tipoDeExpedienteSelected;
    }

    public String getSexoSelected() {
        return sexoSelected;
    }

    public void setSexoSelected(String sexoSelected) {
        this.sexoSelected = sexoSelected;
    }

    public String getTipoDeTramiteSelected() {
        return tipoDeTramiteSelected;
    }

    public void setTipoDeTramiteSelected(String tipoDeTramiteSelected) {
        this.tipoDeTramiteSelected = tipoDeTramiteSelected;
    }

    public Expediente prepareCreateExpAdministrativo() {
        selected = new Expediente();
        selected.setTipoDeExpediente(ADMINISTRATIVO);
        Date date = new Date();
        selected.setFechaDeAtencion(date);
        //ingresarOrdenAutoincremental();
        //ingresarOrdenAutoincrementalSaltandoExpedientesSinCarpeta();

        selected.setTablaDeHonorariosYGastos("fecha | concepto | debe | haber | saldo |");

        selected.setOrden(buscarMayorIdAdmOrJudicial());

        initializeEmbeddableKey();
        return selected;
    }

    public Expediente prepareCreateExpJudicial() {
        selected = new Expediente();
        selected.setTipoDeExpediente(JUDICIAL);
        Date date = new Date();
        selected.setFechaDeAtencion(date);
        //ingresarOrdenAutoincremental();
        //ingresarOrdenAutoincrementalSaltandoExpedientesSinCarpeta();
        selected.setOrden(buscarMayorIdAdmOrJudicial());

        selected.setTablaDeHonorariosYGastos("fecha | concepto | debe | haber | saldo |");

        initializeEmbeddableKey();
        return selected;
    }

    public Expediente prepareCreateExpSinCarpeta() {
        selected = new Expediente();
        selected.setTipoDeExpediente(SIN_CARPETA);
        Date date = new Date();
        selected.setFechaDeAtencion(date);
        selected.setOrden(null);
        selected.setTablaDeHonorariosYGastos("fecha | concepto | debe | haber | saldo |");

        initializeEmbeddableKey();
        return selected;
    }

    public Expediente prepareCreate() {
        selected = new Expediente();
        initializeEmbeddableKey();
        return selected;
    }

    //no se usaría más fué el 1er approach
    public Expediente prepareViewParaExpedientePorNombreYApellido(Agenda agendaAnterior) {
        selectedParaVerExp = new Expediente();

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        for (Expediente expediente : expedienteControllerBean.getItems()) {

            if (expediente.getApellido() != null && expediente.getNombre() != null) {

                if (expediente.getApellido().equals(agendaAnterior.getApellido()) && expediente.getNombre().equals(agendaAnterior.getNombre())) {

                    selectedParaVerExp = expediente;
                    return selectedParaVerExp;

                }
            }
        }
        initializeEmbeddableKey();
        return selectedParaVerExp;
    }

    public Expediente prepareViewParaExpediente(Agenda agenda) {

        try {
            if (agenda == null) {
                return selectedParaVerExp;
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
                selectedParaVerExp = new Expediente();

                if (agenda.getOrden() != null && agenda.getOrden() != 0) {
                    for (Expediente expediente : expedienteControllerBean.getItems()) {

                        if (expediente.getOrden() != null) {
                            if (Integer.compare(expediente.getOrden(), agenda.getOrden()) == 0) {
                                selectedParaVerExp = expediente;
                            }
                        }
                    }
                } else {
                    if ((agenda.getApellido() != null && agenda.getNombre() != null)
                            && (agenda.getOrden() == null || agenda.getOrden() == 0)) {

                        for (Expediente expediente : expedienteControllerBean.getItems()) {
                            if (expediente.getApellido() != null && expediente.getNombre() != null) {
                                if (expediente.getApellido().equals(agenda.getApellido()) && expediente.getNombre().equals(agenda.getNombre())) {
                                    selectedParaVerExp = expediente;

                                }
                            }
                        }
                    }

                }
                initializeEmbeddableKey();
                return selectedParaVerExp;

            }
        } catch (ELException e) {
            System.out.println("error en el metodo prepareViewParaExpediente en expedienteController: " + e.getMessage());
        }
        return null;
    }
    
    /*
        este metodo va a devolver
        administrativo
        judicial
        sin carpeta
            para poder luego hacer un view dependiendo del tipo de Exp
     */
    public String buscarTipoDeExpediente(Agenda agenda) {

        if (agenda == null) {
            // esto lo hago por que la 1ra vez q se renderiza la pagina agendas y turnos , no hay un obj. "agenda" de agendas seleccionado
            return "AgendaSinExpAsociado";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
            String tipoDeExp = "AgendaSinExpAsociado";

            if (agenda.getOrden() != null && agenda.getOrden() != 0) {

                for (Expediente expediente : expedienteControllerBean.getItems()) {

                    if (expediente.getOrden() != null) {
                        if (Integer.compare(expediente.getOrden(), agenda.getOrden()) == 0) {

                            tipoDeExp = expediente.getTipoDeExpediente();
                        }

                    }
                }

            } else {
                if ((agenda.getApellido() != null && agenda.getNombre() != null)
                        && (agenda.getOrden() == null || agenda.getOrden() == 0)) {

                    for (Expediente expediente : expedienteControllerBean.getItems()) {
                        if (expediente.getApellido() != null && expediente.getNombre() != null) {
                            if (expediente.getApellido().equals(agenda.getApellido()) && expediente.getNombre().equals(agenda.getNombre())) {

                                tipoDeExp = expediente.getTipoDeExpediente();
                            }

                        }
                    }
                }
                return tipoDeExp;
            }

            initializeEmbeddableKey();
            return tipoDeExp;
        }
    }

    /*public String verDatosPersonalesYDelExp(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        String datosExp = null;

        for (Expediente expediente : expedienteControllerBean.getItems()) {
            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {

                    if (expediente.toString() != null) {

                        datosExp = expediente.toStringWithDatosPersonalesYDelExp();
                        return datosExp;

                    } else {
                        datosExp = "no posee datos";
                        return datosExp;

                    }
                }
            }
        }
        return datosExp;
    }*/

    public void create() {

        ingresarEdad();

        ingresarDni();
        
        if(selected.getFechaDeNacimiento() != null){ 
            crearAgendaSaludoPorCumpleaños(selected.getFechaDeNacimiento(), selected.getOrden(), selected.getNombre(), selected.getApellido());
        }

        String successMessage = "Expediente del tipo: ".concat(selected.getTipoDeExpediente().toUpperCase()).concat(" creado exitosamente");

        if (selected.getTipoDeExpediente() == null ? SIN_CARPETA != null : !selected.getTipoDeExpediente().equals(SIN_CARPETA)) {
            successMessage = "Expediente del tipo: ".concat(selected.getTipoDeExpediente().toUpperCase()).concat(" creado exitosamente").concat(" con el nro de Orden: ") + selected.getOrden();
        }
        
        persist(PersistAction.CREATE, successMessage);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void ingresarEdad() {
        if (selected.getFechaDeNacimiento() != null) {
            Date fechaAntigua = selected.getFechaDeNacimiento();
            LocalDate fechaNueva = fechaAntigua.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate start = fechaNueva;
            LocalDate end = LocalDate.now(); // use for age-calculation: LocalDate.now()
            long years = ChronoUnit.YEARS.between(start, end);
            selected.setEdad(Math.toIntExact(years));

        } else {
            //TODO: review this code to drop off a notificacion We need to give an avise that date is not been setted!
            selected.setEdad(0);
        }
    }

    public void ingresarDni() {

        if (selected.getCuit().length() > 8) {

            String dni = selected.getCuit();
            dni = dni.replace(" ", "");

            dni = dni.substring(2, 10);

            selected.setDni(dni);
        } else {
            selected.setDni(null);

        }
    }

    public void ingresarOrdenAutoincremental() {

        selected.setOrden(autoIncrementarOrden());
    }

    public void update() {

        //Calendar fecha = new GregorianCalendar();
        //int añoActual = fecha.get(Calendar.YEAR);
        //  int añoDeNacimiento = selected.getFechaDeNacimiento().getYear();
        //    añoDeNacimiento = añoDeNacimiento + 1900;
        //      int edad = 0;
        //        edad = añoActual - añoDeNacimiento;
        //selected.setEdad(edad);
        Date fechaAntigua = new Date();

        if (selected.getFechaDeNacimiento() != null) {
            fechaAntigua = selected.getFechaDeNacimiento();
        }

        LocalDate fechaNueva = fechaAntigua.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = fechaNueva;
        LocalDate end = LocalDate.now(); // use for age-calculation: LocalDate.now()
        long years = ChronoUnit.YEARS.between(start, end);
        selected.setEdad(Math.toIntExact(years));

        if (selected.getCuit() != null) {
            String cuit = selected.getCuit();
            cuit = cuit.substring(2, 9);
            selected.setDni(cuit);
        }

        String successMessage = "Expediente actualizado exitosamente";

        if (selected.getTipoDeExpediente() != null) {
            successMessage = "Expediente ".concat(selected.getTipoDeExpediente().toUpperCase()).concat(" actualizado exitosamente.");
        }

        persist(PersistAction.UPDATE, successMessage);
    }

    public void updateConCambioParaAdministrativo() {

        Date fechaAntigua = new Date();

        if (selected.getFechaDeNacimiento() != null) {
            fechaAntigua = selected.getFechaDeNacimiento();
        }

        LocalDate fechaNueva = fechaAntigua.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = fechaNueva;
        LocalDate end = LocalDate.now(); // use for age-calculation: LocalDate.now()
        long years = ChronoUnit.YEARS.between(start, end);
        selected.setEdad(Math.toIntExact(years));

        if (selected.getCuit() != null) {
            String cuit = selected.getCuit();
            cuit = cuit.substring(2, 9);
            selected.setDni(cuit);
        }

        int mayorOrden = buscarMayorIdAdmOrJudicial();

        selected.setOrden(mayorOrden);
        selected.setTipoDeExpediente(ADMINISTRATIVO);

        Connection con = null;
        PreparedStatement ps = null;
            
        try {       
            
            long cuitLong = 0;
            
            if (!"0".equals(selected.getCuit()) && selected.getCuit() != null && !"".equals(selected.getCuit()))  {
                cuitLong = Long.parseLong(selected.getCuit());
            } 
            
                con = DAO.getConnection();
                ps = con.prepareStatement("INSERT INTO documentosFrenteDni (documento, nroDeOrden, nombreDelDocumento) SELECT  documento, ?, nombreDelDocumento from frenteDniExpSinCarpeta where nroDeCuit = ? ;");
                ps.setInt(1, selected.getOrden());
                ps.setLong(2, cuitLong);
                
                ps.execute();

                ps = con.prepareStatement("INSERT INTO documentosDorsoDni (documento, nroDeOrden, nombreDelDocumento) SELECT  documento, ?, nombreDelDocumento from dorsoDniExpSinCarpeta where nroDeCuit = ? ;");
                ps.setInt(1, selected.getOrden());
                ps.setLong(2, cuitLong);
                
                
                ps.execute();

                con.close();

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage("ERROR", "no se pudo transpasar frente y dorso de dni de este exp. transformando a administrativo");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        persist(PersistAction.UPDATE, "Expediente transformado a ADMINISTRATIVO con el nro de orden: " + mayorOrden);
    }
    
    public void updateConCambioParaJudicial() {
            
        Date fechaAntigua = new Date();

        if (selected.getFechaDeNacimiento() != null) {
            fechaAntigua = selected.getFechaDeNacimiento();
        }

        LocalDate fechaNueva = fechaAntigua.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = fechaNueva;
        LocalDate end = LocalDate.now(); // use for age-calculation: LocalDate.now()
        long years = ChronoUnit.YEARS.between(start, end);
        selected.setEdad(Math.toIntExact(years));

        if (selected.getCuit() != null) {
            String cuit = selected.getCuit();
            cuit = cuit.substring(2, 9);
            selected.setDni(cuit);
        }

        int mayorOrden = buscarMayorIdAdmOrJudicial();

        selected.setOrden(mayorOrden);
        selected.setTipoDeExpediente(JUDICIAL);
        
        Connection con = null;
        PreparedStatement ps = null;
            
        try {       
            
            long cuitLong = 0;
            
            if (!"0".equals(selected.getCuit()) && selected.getCuit() != null && !"".equals(selected.getCuit()))  {
                cuitLong = Long.parseLong(selected.getCuit());
            } 
            
                con = DAO.getConnection();
                ps = con.prepareStatement("INSERT INTO documentosFrenteDni (documento, nroDeOrden, nombreDelDocumento) SELECT  documento, ?, nombreDelDocumento from frenteDniExpSinCarpeta where nroDeCuit = ? ;");
                ps.setInt(1, selected.getOrden());
                ps.setLong(2, cuitLong);

                
                ps.execute();

                ps = con.prepareStatement("INSERT INTO documentosDorsoDni (documento, nroDeOrden, nombreDelDocumento) SELECT  documento, ?, nombreDelDocumento from dorsoDniExpSinCarpeta where nroDeCuit = ? ;");
                ps.setInt(1, selected.getOrden());
                ps.setLong(2, cuitLong);

                
                ps.execute();

                
                con.close();

        } catch (SQLException e) {
            System.out.println(e.toString());
            FacesMessage msg = new FacesMessage("ERROR", "no se pudo transpasar frente y dorso de dni de este exp.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        
        persist(PersistAction.UPDATE, "Expediente transformado a JUDICIAL con el nro de orden: " + mayorOrden);
    }

    public int buscarMayorIdAdmOrJudicial() {

         Integer maxOrden = em.createQuery("SELECT MAX(e.orden) FROM Expediente e", Integer.class)
            .getSingleResult();
        
        // Si no hay expedientes en la base de datos, el máximo valor será null
        // En ese caso, asignar el valor 1 para el primer expediente
        if (maxOrden == null) {
            return 0;
        }
        
        // Agregar 1 al máximo valor para generar un nuevo valor único
        return maxOrden + 1;
        
    }

    public void update2() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteUpdated"));
    }

    public void destroy() {
        
        HttpSession session = SessionUtils.getSession();
        
        if(selected.getOrden() != null) System.out.println("nro de orden: "+selected.getOrden()+"borrado por: "+ session.getAttribute("username"));
        
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
   public List<Expediente> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Expediente getExpediente(java.lang.Integer id) {
        return getFacade().find(id);
    }
    
    public Expediente getExpedienteByOrden(java.lang.Integer orden) {
         List<Expediente> listExpedientes = getItemsAvailableSelectMany();
         
        for(int i = 0; i< listExpedientes.size(); i++){
            if(listExpedientes.get(i).getOrden() != null){
                if(listExpedientes.get(i).getOrden().equals(orden)) return listExpedientes.get(i);
            }
         }
         return null;
    }

    public List<Expediente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Expediente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<Expediente> getItemsAvailableSelectOneOnlyJudiciales() {
        List<Expediente> expedientes = getFacade().findAll();
        List<Expediente> expedientesJudiciales = new ArrayList<>();

        for (Expediente expediente : expedientes) {
            if ("judicial".equalsIgnoreCase(expediente.getTipoDeExpediente())) {
                expedientesJudiciales.add(expediente);
            }
        }

        return expedientesJudiciales;
    }

 private void crearAgendaSaludoPorCumpleaños(Date fechaDeNacimientoDelExp, Integer orden, String nombre, String apellido) {
        // Convertir Date a LocalDate
        LocalDate fechaNacimiento = fechaDeNacimientoDelExp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        
        // Calcular el próximo cumpleaños
        LocalDate proximoCumpleaños = fechaNacimiento.withYear(fechaActual.getYear());
        if (proximoCumpleaños.isBefore(fechaActual) || proximoCumpleaños.isEqual(fechaActual)) {
            proximoCumpleaños = proximoCumpleaños.plusYears(1);
        }
        
        // Convertir el próximo cumpleaños a Date
        Date fechaProximoCumpleaños = Date.from(proximoCumpleaños.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        // Crear la agenda
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        agendaControllerBean.prepareCreate();
        agendaControllerBean.getSelected().setFecha(fechaProximoCumpleaños);
        agendaControllerBean.getSelected().setDescripcion("Saludar por fecha de Cumpleaños");
        agendaControllerBean.getSelected().setOrden(orden);
        agendaControllerBean.getSelected().setNombre(nombre);
        agendaControllerBean.getSelected().setApellido(apellido);
        
        agendaControllerBean.getSelected().setResponsable("Natali D Agostino");
        
        agendaControllerBean.create(nombre, apellido, orden);
    }
 
    @FacesConverter(forClass = Expediente.class)
    public static class ExpedienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExpedienteController controller = (ExpedienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "expedienteController");
            return controller.getExpediente(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Expediente) {
                Expediente o = (Expediente) object;
                return getStringKey(o.getIdExpediente());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Expediente.class.getName()});
                return null;
            }
        }
    }

    public String getNombreYApellidoPorOrden(int orden) {

        String nombreYApellidoBuscado = null;

        for (Expediente i : items) {
            if (i.getOrden() == orden) {
                nombreYApellidoBuscado = i.getNombre() + i.getApellido();
            }
        }
        return nombreYApellidoBuscado;
    }

    public void handleDateSelect(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('expedientesTable').filter()");
    }

    public String getClaveCidi(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedientetrollerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        String claveCidi = null;

        for (Expediente i : expedientetrollerBean.getItems()) {

            if (i.getOrden() == orden) {
                claveCidi = i.getClaveCidi();
                return claveCidi;
            } else {
                claveCidi = "no se encontro";
            }
        }
        return claveCidi;
    }

    public String getClaveFiscal(int orden) {
        String claveFiscal = null;

        for (Expediente buscado : items) {
            if (buscado.getOrden() == orden) {
                claveFiscal = buscado.getClaveFiscal();
            }
        }
        return claveFiscal;
    }

    public String getClaveDeSeguridadSocial(int orden) {
        String claveDeSeguridadSocial = null;

        for (Expediente buscado : items) {
            if (buscado.getOrden() == orden) {
                claveDeSeguridadSocial = buscado.getClaveSeguridadSocial();
            }
        }
        return claveDeSeguridadSocial;
    }

    public String verProximaAgenda(Integer orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());

        for (Agenda agenda : agendaControllerBean.getItems()) {
            if (orden != null) {
                if (agenda.getOrden() != null) {
                    if (Objects.equals(agenda.getOrden(), orden)) {
                        if (agenda.getFecha() != null) {
                            String date2 = sdf.format(agenda.getFecha());
                            if (date.equals(date2)) {
                                return agenda.toString();
                            }
                        }
                    }
                }
            }
        }

        return "no existen agendas para hoy";
    }

     public List verAgendasParaHoyPorNroDeOrden(Integer orden) {
      List<Agenda> agendasParaHoy = new ArrayList<Agenda>();
      
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());

        for (Agenda agenda : agendaControllerBean.getItems()) {
            if (orden != null) {
                if (agenda.getOrden() != null) {
                    if (Objects.equals(agenda.getOrden(), orden)) {
                        if (agenda.getFecha() != null) {
                            String date2 = sdf.format(agenda.getFecha());
                            if (date.equals(date2)) {
                                agendasParaHoy.add(agenda);
                            }
                        }
                    }
                }
            }
        }

        return agendasParaHoy;
      
     }
    
    public List verAgendasFuturasPorNroDeOrden(Integer orden) {
        
        List<Agenda> verAgendasFuturas = new ArrayList<Agenda>();

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        Date date = new Date();

        for (Agenda agenda : agendaControllerBean.getItemsByOrder(orden)) {
            if (orden != null) {
                if (agenda.getOrden() != null) {
                    if (Objects.equals(agenda.getOrden(), orden)) {
                        if (agenda.getFecha() != null) {
                            if (agenda.getFecha().after(date)) {
                                verAgendasFuturas.add(agenda);
                            }
                        }
                    }
                }
            }
        }
        
        //por que ahora lo hacemos por base de datos con order by
        //agendaControllerBean.ordenarListItems(verAgendasFuturas);

        return verAgendasFuturas;
    }

    public List verAgendasPasadasPorNroDeOrden(Integer orden) {

        List<Agenda> verAgendasPasadas = new ArrayList<Agenda>();

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        Date date = new Date();

        for (Agenda agenda : agendaControllerBean.getItemsByOrder(orden)) {
            if (orden != null) {
                if (agenda.getOrden() != null) {
                        if (agenda.getFecha() != null) {
                            if (agenda.getFecha().before(date)) {
                                verAgendasPasadas.add(agenda);
                            }
                        }
                }
            }
        }
        
        //por que ahora lo hacemos por base de datos con order by
        //agendaControllerBean.ordenarListItems(verAgendasPasadas);
        
        Collections.reverse(verAgendasPasadas);
        
        return verAgendasPasadas;
    }
    
    public List verComunicacionesPorNroDeOrden(Integer orden) {
        
        List<Comunicacion> comunicaciones;
        comunicaciones = new ArrayList<>();

        FacesContext context = FacesContext.getCurrentInstance();
        ComunicacionController comunicacionControllerBean = context.getApplication().evaluateExpressionGet(context, "#{comunicacionController}", ComunicacionController.class);
            
        comunicacionControllerBean.getItems().stream().filter(Comunicacion ->
                (orden != null)).filter(Comunicacion ->
                        (Comunicacion.getOrden() != null)).filter(Comunicacion ->
                                (Objects.equals(Comunicacion.getOrden(), orden))).forEachOrdered(Comunicacion ->
                                {
            comunicaciones.add(Comunicacion);
        });
        
        //Collections.sort(comunicaciones, (Comunicacion o1, Comunicacion o2) -> o1.getFecha().compareTo(o2.getFecha()));
        
         // Ordenar de la más reciente a la más antigua
        Collections.sort(comunicaciones, (Comunicacion o1, Comunicacion o2) -> o2.getFecha().compareTo(o1.getFecha()));

        
        return comunicaciones;
    }

    /*public String verClaveCidi(int orden) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        String noPoseeClave = "no posee clave CIDI";

        for (Expediente expediente : expedienteControllerBean.getItems()) {
            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {
                    if (expediente.getClaveCidi() != null) {
                        return expediente.getClaveCidi();
                    } else {
                        return noPoseeClave;
                    }
                }
            }
        }
        return noPoseeClave;
    }*/

    /*public String verClaveFiscal(int orden) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        String noPoseeClaveFiscal = "no posee clave FISCAL";

        for (Expediente expediente : expedienteControllerBean.getItems()) {
            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {
                    if (expediente.getClaveFiscal() != null) {
                        return expediente.getClaveFiscal();
                    } else {
                        return noPoseeClaveFiscal;
                    }
                }
            }
        }
        return noPoseeClaveFiscal;
    }*/
    
    public String verClaveFiscal(int orden) {
        String claveFiscal = getFacade().findClaveFiscalByOrden(orden);
        return claveFiscal != null ? claveFiscal : "No posee clave Fiscal";
    }


    /*public String verClaveDeSeguridadSocial(int orden) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        String noPoseeClave = "no posee clave de Seguridad Social";

        for (Expediente expediente : expedienteControllerBean.getItems()) {
            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {
                    if (expediente.getClaveSeguridadSocial() != null) {
                        return expediente.getClaveSeguridadSocial();
                    } else {
                        return noPoseeClave;
                    }
                }
            }
        }
        return noPoseeClave;
    }*/
    
    public String verClaveDeSeguridadSocial(int orden) {
        String claveSeguridadSocial = getFacade().findClaveSeguridadSocialByOrden(orden);
        return claveSeguridadSocial != null ? claveSeguridadSocial : "No posee clave de Seguridad Social";
    }

    public int autoIncrementarOrdenSaltandoExpedientesSinCarpeta() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        int orden = 0;

        for (int i = 1; i <= expedienteControllerBean.getItemsAvailableSelectOne().size(); i++) {
            if (expedienteControllerBean.getItemsAvailableSelectOne().get(expedienteControllerBean.getItemsAvailableSelectOne().size() - i).getTipoDeExpediente().equalsIgnoreCase(ADMINISTRATIVO) || expedienteControllerBean.getItemsAvailableSelectOne().get(expedienteControllerBean.getItemsAvailableSelectOne().size() - i).getTipoDeExpediente().equalsIgnoreCase(JUDICIAL)) {
                orden = expedienteControllerBean.getItemsAvailableSelectOne().get(expedienteControllerBean.getItemsAvailableSelectOne().size() - i).getOrden() + 1;
                break;
            }
        }
        return orden;
    }

    public int autoIncrementarOrden() {
        ExpedienteDAO expedienteDao = new ExpedienteDAO();

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        int orden = expedienteControllerBean.getItemsAvailableSelectOne().get(expedienteControllerBean.getItemsAvailableSelectOne().size() - 1).getOrden() + 1;

        return orden;
    }

    public void limpiarFiltros() {

        this.fechaDeCumpleSelected = null;
        this.estadoDelTramiteSelected = null;
        this.responsableSelected = null;
        this.tipoDeTramiteSelected = null;
        this.tipoDeExpedienteSelected = null;
        this.sexoSelected = null;

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('expedientesTable').clearFilters()");
    }

    public void filtrarPorTipoDeTramite(String tipoDeTramiteSelected) {
        
        this.filteredExpedientes = new ArrayList<Expediente>();

        if (tipoDeTramiteSelected != null) {
            for (Expediente expediente : getItems()) {
                if (expediente.getTipoDeTramite() != null) {

                    if (expediente.getTipoDeTramite().equals(tipoDeTramiteSelected)) {
                        filteredExpedientes.add(expediente);
                    }
                }
            }
        }
    }
    
    public void filtroCompuesto(String tipoDeTramiteSelected,
            String tipoDeExpedienteSelected,
            String responsableSelected,
            String estadoDelTramiteSelected,
            String fechaDeCumpleSelected,
            String sexoSelected) {

         this.filteredExpedientes = getItems().stream()
                 .filter(ExpedienteUtils.filtroTipoDeTramite(tipoDeTramiteSelected))
                 .filter(ExpedienteUtils.filtroTipoDeExpediente(tipoDeExpedienteSelected))
                 .filter(ExpedienteUtils.filtroResponsable(responsableSelected))
                 .filter(ExpedienteUtils.filtroEstadoDelTramite(estadoDelTramiteSelected))
                 .filter(ExpedienteUtils.filtroFechaDeCumple(fechaDeCumpleSelected))
                 .filter(ExpedienteUtils.filtroSexo(sexoSelected))
                 .collect(Collectors.toList());
        

        JsfUtil.addSuccessMessage("Cantidad de ocurrencias: "+this.filteredExpedientes.size());
        
    }

    public void filtrarPorSexo(String sexoSelected) {
        
        this.filteredExpedientes = new ArrayList<Expediente>();

        if (sexoSelected != null) {
            for (Expediente expediente : getItems()) {
                if (expediente.getSexo() != null) {

                    if (expediente.getSexo().equals(sexoSelected)) {
                        filteredExpedientes.add(expediente);
                    }
                }
            }
        }
    }

    public void filtrarPorTipoDeExpediente(String tipoDeExpedienteSelected) {
        
        this.filteredExpedientes = new ArrayList<Expediente>();

        if (tipoDeExpedienteSelected != null) {
            for (Expediente expediente : getItems()) {
                if (expediente.getTipoDeExpediente() != null) {

                    if (expediente.getTipoDeExpediente().equals(tipoDeExpedienteSelected)) {
                        filteredExpedientes.add(expediente);
                    }
                }
            }
        }
    }

    public void filtrarPorResponsable(String estadoDelTramiteSelected) {

        this.filteredExpedientes = new ArrayList<Expediente>();

        if (responsableSelected != null) {
            for (Expediente expediente : getItems()) {
                if (expediente.getResponsable()!= null) {

                    if (expediente.getResponsable().equals(responsableSelected)) {
                        filteredExpedientes.add(expediente);
                    }
                }
            }
        }
    }

    public void filtrarPorEstadoDelTramite(String estadoDelTramiteSelected) {
        this.filteredExpedientes = new ArrayList<Expediente>();
        if (estadoDelTramiteSelected != null) {
            for (Expediente expediente : getItems()) {
                if (expediente.getEstadoDelTramite() != null) {
                    if (expediente.getEstadoDelTramite().equals(estadoDelTramiteSelected)) {
                        filteredExpedientes.add(expediente);
                    }
                }
            }
        }
    }

    public void filtrarPorFechaDeCumple(String fechaDeCumpleSelected) {

        this.filteredExpedientes = new ArrayList<Expediente>();

        if (fechaDeCumpleSelected != null) {
            for (Expediente expediente : getItems()) {
                if (expediente.getFechaDeNacimiento() != null) {
                    Date date = expediente.getFechaDeNacimiento();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(date);

                    String sSubCadena = strDate.substring(0, 5);
                    
                    if (sSubCadena.equals(fechaDeCumpleSelected)) {
                        filteredExpedientes.add(expediente);
                    }
                }
            }
        }
    }

    public boolean filterByApellidoYNombre(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String apellidoYNombre = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        return apellidoYNombre.contains(filterText);
    }
    
    
    public void cambiarConsultaAExpAdm(Consulta consultaSelected) {
        FacesContext context = FacesContext.getCurrentInstance();
        ConsultaController consultaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{consultaController}", ConsultaController.class);

        Expediente expAInsertar = prepareCreateExpAdministrativo();
        Date fechaAntigua = new Date();

        if (consultaSelected.getFechaDeNacimiento() != null) {
            fechaAntigua = consultaSelected.getFechaDeNacimiento();
        }

        if (consultaSelected.getFechaDeNacimiento() != null) {
            LocalDate fechaNueva = fechaAntigua.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate start = fechaNueva;
            LocalDate end = LocalDate.now();
            long years = ChronoUnit.YEARS.between(start, end);
            expAInsertar.setEdad(Math.toIntExact(years));
        } else {
            // Manejar el caso donde la fecha de nacimiento es nula
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Fecha de nacimiento es nula", "No se puede calcular la edad");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            expAInsertar.setEdad(0); // o algún valor predeterminado
        }

    if ("0".equals(consultaSelected.getCuit()) || consultaSelected.getCuit() == null || "".equals(consultaSelected.getCuit()) || consultaSelected.getCuit().isEmpty() ) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta consulta no tiene cuit", "No es posible pasarla a exp. administrativo");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } else {
        if (consultaSelected.getCuit() != null) {
            String cuit = consultaSelected.getCuit();
            cuit = cuit.substring(2, 9);
            expAInsertar.setDni(cuit);
            expAInsertar.setCuit(consultaSelected.getCuit());
        }

        expAInsertar.setNombre(consultaSelected.getNombre());
        expAInsertar.setTipoDeDocumento(consultaSelected.getTipoDeDocumento());
        expAInsertar.setSexo(consultaSelected.getSexo());
        expAInsertar.setApellido(consultaSelected.getApellido());
        expAInsertar.setDireccion(consultaSelected.getDireccion());
        expAInsertar.setNroDeAltura(consultaSelected.getNroDeAltura());
        expAInsertar.setPiso(consultaSelected.getPiso());
        expAInsertar.setDepto(consultaSelected.getDepto());
        expAInsertar.setBarrio(consultaSelected.getBarrio());
        expAInsertar.setTelefono(consultaSelected.getTelefono());
        expAInsertar.setTelefonoAuxiliar(consultaSelected.getTelefonoAuxiliar());
        expAInsertar.setFechaDeNacimiento(consultaSelected.getFechaDeNacimiento());
        expAInsertar.setClaveSeguridadSocial(consultaSelected.getClaveSeguridadSocial());
        expAInsertar.setClaveFiscal(consultaSelected.getClaveFiscal());
        expAInsertar.setClaveCidi(consultaSelected.getClaveCidi());
        expAInsertar.setCobraBeneficio(consultaSelected.getCobraBeneficio());
        expAInsertar.setCodigoPostal(consultaSelected.getCodigoPostal());
        expAInsertar.setLocalidad(consultaSelected.getLocalidad());
        expAInsertar.setTipoDeTramite(consultaSelected.getTipoDeTramite());
        expAInsertar.setProcedencia(consultaSelected.getProcedencia());
        expAInsertar.setEstadoDelTramite(consultaSelected.getEstadoDelTramite());
        expAInsertar.setFechaDeCobro(consultaSelected.getFechaDeCobro());
        expAInsertar.setNacionalidad(consultaSelected.getNacionalidad());
        expAInsertar.setCaratula(consultaSelected.getCaratula());
        expAInsertar.setJuzgadoODependencia(consultaSelected.getJuzgadoODependencia());
        expAInsertar.setObservaciones(consultaSelected.getObservaciones());
        expAInsertar.setResponsable(consultaSelected.getResponsable());
        expAInsertar.setApoderado(consultaSelected.getApoderado());
        expAInsertar.setComunicaciones(consultaSelected.getComunicaciones());
        expAInsertar.setFechaDeAtencion(consultaSelected.getFechaDeAtencion());
        expAInsertar.setFechaDeAltaDeExpediente(fechaAntigua);
        expAInsertar.setConvenioDeHonorarios(consultaSelected.getConvenioDeHonorarios());
        expAInsertar.setPoderFirmado(consultaSelected.getPoderFirmado());
        expAInsertar.setTipo(consultaSelected.getTipo());
        expAInsertar.setJurisdiccion(consultaSelected.getJurisdiccion());
        expAInsertar.setEtapaProcesal(consultaSelected.getEtapaProcesal());
        expAInsertar.setDetalleDeEstadoDeTramite(consultaSelected.getDetalleDeEstadoDeTramite());
        expAInsertar.setTablaDeHonorariosYGastos(consultaSelected.getTablaDeHonorariosYGastos());
        expAInsertar.setSubCategoriasDeTipo(consultaSelected.getSubCategoriasDeTipo());
        expAInsertar.setCantidadDeHijos(consultaSelected.getCantidadDeHijos());
        expAInsertar.setCantidadDeHijosConDiscapacidad(consultaSelected.getCantidadDeHijosConDiscapacidad());
        expAInsertar.setCantidadDeHijosAdoptivos(consultaSelected.getCantidadDeHijosAdoptivos());
        expAInsertar.setCantidadDeHijosPercibioAuh(consultaSelected.getCantidadDeHijosPercibioAuh());
        expAInsertar.setEstadoCivil(consultaSelected.getEstadoCivil());
        expAInsertar.setDatosDelConyuge(consultaSelected.getDatosDelConyuge());
        expAInsertar.setTipoDeBeneficio(consultaSelected.getTipoDeBeneficio());
        expAInsertar.setAportes(consultaSelected.getAportes());
        expAInsertar.setDetalleDeAportes(consultaSelected.getDetalleDeAportes());
        expAInsertar.setTrabajando(consultaSelected.getTrabajando());
        expAInsertar.setObraSocial(consultaSelected.getObraSocial());
        expAInsertar.setInscripcionAut(consultaSelected.getInscripcionAut());
        expAInsertar.setReclamoArt(consultaSelected.getReclamoArt());
        expAInsertar.setEquipo(consultaSelected.getEquipo());
        
        consultaControllerBean.getSelected().setEstadoConsulta("CONSULTA PASADA A EXP. ADMINISTRATIVO");
        consultaControllerBean.getSelected().setOrden(expAInsertar.getOrden());
        consultaControllerBean.update();
        persist(JsfUtil.PersistAction.CREATE, "Consulta transformada a ADMINISTRATIVO con el nro de orden: " + expAInsertar.getOrden());
        
        if(expAInsertar.getFechaDeNacimiento() != null){
        crearAgendaSaludoPorCumpleaños(expAInsertar.getFechaDeNacimiento(), expAInsertar.getOrden(), expAInsertar.getNombre(), expAInsertar.getApellido());
        }else{
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exp a insertar no tiene fecha de Nacimiento", "No es posible crear agenda de saludo de cumpleaños");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        }
        
        items = null;
        }
    }


    public String abrirWhatsApp(String telefono) throws IOException {
        if (telefono == null || telefono.isEmpty()) {
            // Manejar el caso de teléfono nulo o vacío
            return null;
        }

        // Eliminar espacios en blanco y caracteres no numéricos
        telefono = telefono.replaceAll("[^0-9]", "");

        // Construir la URL de WhatsApp
        String url = "https://wa.me/" + telefono+"?text=hola%20me%20comunico%20del%20Estudio%20Alvarez";
        return url;
    }
    
    public String verClaveCidi(int orden) {
        String claveCidi = getFacade().findClaveCidiByOrden(orden);
        return claveCidi != null ? claveCidi : "No posee clave CIDI";
    }

    public String verNroDeCuil(int orden) {
        String cuit = getFacade().findCuitByOrden(orden);
        return cuit != null ? cuit : "No posee CUIL/CUIT";
    }
    
}
