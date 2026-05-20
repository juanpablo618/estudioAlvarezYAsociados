package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpa.EventoDeCausasJudiciales;
import com.estudioAlvarezVersion2.jpacontroller.AgendaFacade;
import com.estudioAlvarezVersion2.jpacontroller.EventoDeCausasJudicialesFacade;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author juanpablo618@hotmail.com
 */
@Named("eventoDeCausasJudicialesController")
@SessionScoped
public class EventoDeCausasJudicialesController implements Serializable {

    @EJB
    private EventoDeCausasJudicialesFacade ejbFacade;
    @EJB
    private AgendaFacade agendaFacade;
    private List<EventoDeCausasJudiciales> items = null;
    private EventoDeCausasJudiciales selected;
    private EventoDeCausasJudiciales selectedParaEventoJudicialNuevo;
    private boolean mostrarAgendasSentenciaPrimeraInstancia;
    private List<AgendaSentenciaPrimeraInstancia> agendasSentenciaPrimeraInstancia;

    private static final String SENTENCIA_1RA_INSTANCIA = "Sentencia 1ra instancia";

    public EventoDeCausasJudicialesController() {
        inicializarAgendasSentenciaPrimeraInstancia();
    }

    public static class AgendaSentenciaPrimeraInstancia implements Serializable {
        private boolean seleccionada;
        private Date fecha;
        private String responsable;
        private String descripcion;

        public boolean isSeleccionada() { return seleccionada; }
        public void setSeleccionada(boolean seleccionada) { this.seleccionada = seleccionada; }
        public Date getFecha() { return fecha; }
        public void setFecha(Date fecha) { this.fecha = fecha; }
        public String getResponsable() { return responsable; }
        public void setResponsable(String responsable) { this.responsable = responsable; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    }

    public EventoDeCausasJudiciales getSelected() {
        return selected;
    }

    public void setSelected(EventoDeCausasJudiciales selected) {
        this.selected = selected;
    }

    public EventoDeCausasJudiciales getSelectedParaEventoJudicialNuevo() {
        return selectedParaEventoJudicialNuevo;
    }

    public void setSelectedParaEventoJudicialNuevo(EventoDeCausasJudiciales selectedParaEventoJudicialNuevo) {
        this.selectedParaEventoJudicialNuevo = selectedParaEventoJudicialNuevo;
    }
    
    

    private EventoDeCausasJudicialesFacade getFacade() {
        return ejbFacade;
    }

    public List<EventoDeCausasJudiciales> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public EventoDeCausasJudiciales prepareCreate() {
        selected = new EventoDeCausasJudiciales();
        return selected;
    }

    public void create() {
        getFacade().create(selected);
        items = null; // Invalidate list of items to trigger re-query
    }

    public void update() {
        getFacade().edit(selected);
        items = null; // Invalidate list of items to trigger re-query
    }

    public void destroy() {
        getFacade().remove(selected);
        selected = null; // Remove selection
        items = null; // Invalidate list of items to trigger re-query
    }

    public EventoDeCausasJudiciales getEventoDeCausasJudiciales(Integer id) {
        return getFacade().find(id);
    }

    public List<EventoDeCausasJudiciales> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EventoDeCausasJudiciales> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    @FacesConverter(forClass = EventoDeCausasJudiciales.class)
    public static class EventoDeCausasJudicialesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EventoDeCausasJudicialesController controller = (EventoDeCausasJudicialesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "eventoDeCausasJudicialesController");
            return controller.getEventoDeCausasJudiciales(getKey(value));
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
            if (object instanceof EventoDeCausasJudiciales) {
                EventoDeCausasJudiciales o = (EventoDeCausasJudiciales) object;
                return getStringKey(o.getIdEventoDeCausasJudiciales());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EventoDeCausasJudiciales.class.getName()});
                return null;
            }
        }
    }
    
    public void createEventoJudicial(Date fecha, String tipoDeFecha, int orden) {
        
        selectedParaEventoJudicialNuevo.setFecha(fecha);
        selectedParaEventoJudicialNuevo.setTipoDeFecha(tipoDeFecha);
        selectedParaEventoJudicialNuevo.setOrden(orden);
        
        persistParaEventoJudicialNuevo(JsfUtil.PersistAction.CREATE, "Evento Judicial creado exitosamente para el nro de orden:"+ orden);
        crearAgendasSentenciaPrimeraInstanciaSiCorresponde();
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    private void persistParaEventoJudicialNuevo(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selectedParaEventoJudicialNuevo != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getFacade().edit(selectedParaEventoJudicialNuevo);
                } else {
                    getFacade().remove(selectedParaEventoJudicialNuevo);
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
    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }
    
    public EventoDeCausasJudiciales prepareCreateEventoJudicial(int orden) {
        selectedParaEventoJudicialNuevo = new EventoDeCausasJudiciales();
        selectedParaEventoJudicialNuevo.setOrden(orden);
        selectedParaEventoJudicialNuevo.setTipoDeFecha(null);
        mostrarAgendasSentenciaPrimeraInstancia = false;
        inicializarAgendasSentenciaPrimeraInstancia();
        initializeEmbeddableKey();

        return selectedParaEventoJudicialNuevo;
    }

    public boolean isMostrarAgendasSentenciaPrimeraInstancia() {
        return mostrarAgendasSentenciaPrimeraInstancia;
    }

    public List<AgendaSentenciaPrimeraInstancia> getAgendasSentenciaPrimeraInstancia() {
        return agendasSentenciaPrimeraInstancia;
    }

    public void onTipoDeFechaChange() {
        String tipo = selectedParaEventoJudicialNuevo != null ? selectedParaEventoJudicialNuevo.getTipoDeFecha() : null;
        mostrarAgendasSentenciaPrimeraInstancia = SENTENCIA_1RA_INSTANCIA.equals(tipo);
    }

    private void inicializarAgendasSentenciaPrimeraInstancia() {
        agendasSentenciaPrimeraInstancia = new ArrayList<>();
        Date siguienteDia = convertLocalDateToDate(sumarDiasHabiles(LocalDate.now(), 1));
        agendasSentenciaPrimeraInstancia.add(crearAgendaTemplate("Avisar a cliente que salió sentencia de primera instancia favorable pero debemos esperar si ANSES apela + explicar pasos a seguir.", siguienteDia));
        agendasSentenciaPrimeraInstancia.add(crearAgendaTemplate("Avisar a cliente que salió sentencia de primera instancia pero debemos apelar.", siguienteDia));
        agendasSentenciaPrimeraInstancia.add(crearAgendaTemplate("Anotar en honorarios que salió sentencia y regulen costas.", siguienteDia));
        agendasSentenciaPrimeraInstancia.add(crearAgendaTemplate("Hacer apelación.", siguienteDia));
        agendasSentenciaPrimeraInstancia.add(crearAgendaTemplate("Verificar si ANSES apeló sino solicitar sentencia firme.", convertLocalDateToDate(sumarDiasHabiles(LocalDate.now(), 5))));
    }

    private AgendaSentenciaPrimeraInstancia crearAgendaTemplate(String descripcion, Date fecha) {
        AgendaSentenciaPrimeraInstancia agenda = new AgendaSentenciaPrimeraInstancia();
        agenda.setDescripcion(descripcion);
        agenda.setFecha(fecha);
        return agenda;
    }

    private LocalDate sumarDiasHabiles(LocalDate fechaBase, int diasHabiles) {
        LocalDate fecha = fechaBase;
        int agregados = 0;
        while (agregados < diasHabiles) {
            fecha = fecha.plusDays(1);
            if (fecha.getDayOfWeek() != DayOfWeek.SATURDAY && fecha.getDayOfWeek() != DayOfWeek.SUNDAY) {
                agregados++;
            }
        }
        return fecha;
    }

    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private void crearAgendasSentenciaPrimeraInstanciaSiCorresponde() {
        if (!mostrarAgendasSentenciaPrimeraInstancia || selectedParaEventoJudicialNuevo == null) {
            return;
        }
        for (AgendaSentenciaPrimeraInstancia agendaConfig : agendasSentenciaPrimeraInstancia) {
            if (!agendaConfig.isSeleccionada() || agendaConfig.getResponsable() == null || agendaConfig.getResponsable().trim().isEmpty()) {
                continue;
            }
            Agenda agenda = new Agenda();
            agenda.setFecha(agendaConfig.getFecha());
            agenda.setResponsable(agendaConfig.getResponsable());
            agenda.setDescripcion(agendaConfig.getDescripcion());
            agenda.setOrden(selectedParaEventoJudicialNuevo.getOrden());
            agenda.setRealizado("No");
            agenda.setPrioridad("No");
            agendaFacade.edit(agenda);
        }
    }

}
