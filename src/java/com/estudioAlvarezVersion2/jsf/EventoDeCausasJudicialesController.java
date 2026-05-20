package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.EventoDeCausasJudiciales;
import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpa.Expediente;
import com.estudioAlvarezVersion2.jpacontroller.AgendaFacade;
import com.estudioAlvarezVersion2.jpacontroller.EventoDeCausasJudicialesFacade;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Calendar;

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
    private List<AgendaPredeterminadaSentencia> agendasPredeterminadasSentencia = new ArrayList<>();

    public EventoDeCausasJudicialesController() {
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
        crearAgendasPredeterminadasSiCorresponde(fecha, tipoDeFecha, orden);
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
        agendasPredeterminadasSentencia = new ArrayList<>();
        initializeEmbeddableKey();

        return selectedParaEventoJudicialNuevo;
    }
    
    public void onTipoDeFechaChange() {
        if (selectedParaEventoJudicialNuevo == null) {
            return;
        }
        if ("Sentencia 1ra instancia".equals(selectedParaEventoJudicialNuevo.getTipoDeFecha())) {
            inicializarAgendasPredeterminadasSentencia();
        } else {
            agendasPredeterminadasSentencia = new ArrayList<>();
        }
    }
    
    private void inicializarAgendasPredeterminadasSentencia() {
        agendasPredeterminadasSentencia = new ArrayList<>();
        String responsablePredeterminado = obtenerResponsablePredeterminadoExpediente();
        agendasPredeterminadasSentencia.add(new AgendaPredeterminadaSentencia("Avisar a cliente que salió sent. favorable y debemos esperar si ANSES apela + explicar pasos a seguir.", 1, false, responsablePredeterminado));
        agendasPredeterminadasSentencia.add(new AgendaPredeterminadaSentencia("Avisar a cliente que salió sentencia primera instancia pero debemos apelar.", 1, false, responsablePredeterminado));
        agendasPredeterminadasSentencia.add(new AgendaPredeterminadaSentencia("Anotar en honorarios que salió sentencia y régimen de costas.", 1, false, responsablePredeterminado));
        agendasPredeterminadasSentencia.add(new AgendaPredeterminadaSentencia("Hacer apelación.", 1, false, responsablePredeterminado));
        agendasPredeterminadasSentencia.add(new AgendaPredeterminadaSentencia("Verificar si ANSES apeló; si no, solicitar sentencia firme.", 5, true, responsablePredeterminado));
    }
    
    private String obtenerResponsablePredeterminadoExpediente() {
        ExpedienteController expedienteController = (ExpedienteController) FacesContext.getCurrentInstance().getApplication()
                .getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "expedienteController");
        if (expedienteController != null && expedienteController.getSelected() != null) {
            return expedienteController.getSelected().getResponsable();
        }
        if (expedienteController != null && expedienteController.getSelectedParaVerExp() != null) {
            return expedienteController.getSelectedParaVerExp().getResponsable();
        }
        return "";
    }
    
    private void crearAgendasPredeterminadasSiCorresponde(Date fechaEvento, String tipoDeFecha, int orden) {
        if (!"Sentencia 1ra instancia".equals(tipoDeFecha) || fechaEvento == null || agendasPredeterminadasSentencia == null) {
            return;
        }
        ExpedienteController expedienteController = (ExpedienteController) FacesContext.getCurrentInstance().getApplication()
                .getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "expedienteController");
        Expediente expediente = expedienteController != null ? expedienteController.getExpedienteByOrden(orden) : null;
        if (expediente == null) {
            return;
        }
        for (AgendaPredeterminadaSentencia agendaConfig : agendasPredeterminadasSentencia) {
            if (!agendaConfig.isSeleccionada()) continue;
            Agenda agenda = new Agenda();
            agenda.setFecha(agendaConfig.isDiasHabiles() ? sumarDiasHabiles(fechaEvento, agendaConfig.getDiasOffset()) : sumarDiasCorridos(fechaEvento, agendaConfig.getDiasOffset()));
            agenda.setDescripcion(agendaConfig.getDescripcion());
            agenda.setNombre(expediente.getNombre());
            agenda.setApellido(expediente.getApellido());
            agenda.setOrden(orden);
            agenda.setResponsable(agendaConfig.getResponsable());
            agenda.setRealizado("No");
            agenda.setPrioridad("No");
            agendaFacade.create(agenda);
        }
    }
    
    private Date sumarDiasCorridos(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DATE, dias);
        return calendar.getTime();
    }
    
    private Date sumarDiasHabiles(Date fecha, int diasHabiles) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        int agregados = 0;
        while (agregados < diasHabiles) {
            calendar.add(Calendar.DATE, 1);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (day != Calendar.SATURDAY && day != Calendar.SUNDAY) {
                agregados++;
            }
        }
        return calendar.getTime();
    }
    
    public List<AgendaPredeterminadaSentencia> getAgendasPredeterminadasSentencia() {
        return agendasPredeterminadasSentencia;
    }
    
    public static class AgendaPredeterminadaSentencia implements Serializable {
        private String descripcion;
        private int diasOffset;
        private boolean diasHabiles;
        private boolean seleccionada;
        private String responsable;
        public AgendaPredeterminadaSentencia(String descripcion, int diasOffset, boolean diasHabiles, String responsable) {
            this.descripcion = descripcion;
            this.diasOffset = diasOffset;
            this.diasHabiles = diasHabiles;
            this.responsable = responsable;
        }
        public String getDescripcion() { return descripcion; }
        public int getDiasOffset() { return diasOffset; }
        public boolean isDiasHabiles() { return diasHabiles; }
        public boolean isSeleccionada() { return seleccionada; }
        public void setSeleccionada(boolean seleccionada) { this.seleccionada = seleccionada; }
        public String getResponsable() { return responsable; }
        public void setResponsable(String responsable) { this.responsable = responsable; }
        public String getEtiquetaPlazo() { return diasHabiles ? "A los " + diasOffset + " días hábiles" : "Al día siguiente"; }
    }

}
