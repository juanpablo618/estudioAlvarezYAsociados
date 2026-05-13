package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.EventoDeCausasJudiciales;
import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpacontroller.AgendaFacade;
import com.estudioAlvarezVersion2.jpacontroller.EventoDeCausasJudicialesFacade;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
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
    private List<SugerenciaAgendaJudicial> agendasSugeridas = new ArrayList<>();
    private boolean agendasSugeridasYaProcesadas;

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

    public List<SugerenciaAgendaJudicial> getAgendasSugeridas() {
        return agendasSugeridas;
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
        if (!JsfUtil.isValidationFailed()) {
            crearAgendasSugeridasSeleccionadas();
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void onTipoFechaChange() {
        agendasSugeridas = construirSugerenciasPorTipo(selectedParaEventoJudicialNuevo != null ? selectedParaEventoJudicialNuevo.getTipoDeFecha() : null);
    }

    private List<SugerenciaAgendaJudicial> construirSugerenciasPorTipo(String tipoDeFecha) {
        List<SugerenciaAgendaJudicial> sugerencias = new ArrayList<>();
        if (tipoDeFecha == null || !tipoDeFecha.toLowerCase(Locale.ROOT).contains("sentencia")) {
            return sugerencias;
        }
        sugerencias.add(new SugerenciaAgendaJudicial("Avisar a cliente resultado de sentencia y estrategia", "Natali D Agostino", 1));
        sugerencias.add(new SugerenciaAgendaJudicial("Anotar en honorarios salida de sentencia y próximos pasos", "Daniela Correa", 1));
        sugerencias.add(new SugerenciaAgendaJudicial("Preparar apelación", "Juan Pablo Cuello", 1));
        sugerencias.add(new SugerenciaAgendaJudicial("Verificar si ANSES apeló o solicitar sentencia firme", "Natali D Agostino", 5));
        return sugerencias;
    }

    private void crearAgendasSugeridasSeleccionadas() {
        if (agendasSugeridasYaProcesadas || selectedParaEventoJudicialNuevo == null || selectedParaEventoJudicialNuevo.getFecha() == null) {
            return;
        }
        Integer orden = selectedParaEventoJudicialNuevo.getOrden();
        if (orden == null) {
            return;
        }
        ExpedienteController expedienteController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{expedienteController}", ExpedienteController.class);
        if (expedienteController == null || expedienteController.getSelected() == null) {
            return;
        }
        String nombre = expedienteController.getSelected().getNombre();
        String apellido = expedienteController.getSelected().getApellido();

        for (SugerenciaAgendaJudicial sugerida : agendasSugeridas) {
            if (!sugerida.isSeleccionada()) {
                continue;
            }
            Date fechaAgenda = sumarDiasHabiles(selectedParaEventoJudicialNuevo.getFecha(), sugerida.getDiasHabilesOffset());
            if (existeAgendaDuplicada(orden, sugerida.getDescripcion(), fechaAgenda)) {
                continue;
            }
            Agenda agenda = new Agenda();
            agenda.setOrden(orden);
            agenda.setNombre(nombre);
            agenda.setApellido(apellido);
            agenda.setDescripcion(sugerida.getDescripcion());
            agenda.setFecha(fechaAgenda);
            agenda.setResponsable(sugerida.getResponsable());
            agenda.setRealizado("No");
            agenda.setPrioridad("No");
            agendaFacade.create(agenda);
        }
        agendasSugeridasYaProcesadas = true;
    }

    private boolean existeAgendaDuplicada(Integer orden, String descripcion, Date fecha) {
        List<Agenda> agendasPorOrden = agendaFacade.getItemsByOrder(orden);
        for (Agenda agenda : agendasPorOrden) {
            if (agenda.getFecha() != null && agenda.getDescripcion() != null
                    && Objects.equals(agenda.getDescripcion().trim(), descripcion.trim())
                    && agenda.getFecha().compareTo(fecha) == 0) {
                return true;
            }
        }
        return false;
    }

    private Date sumarDiasHabiles(Date base, int diasHabiles) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(base);
        int agregados = 0;
        while (agregados < diasHabiles) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            if (diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY) {
                agregados++;
            }
        }
        return calendar.getTime();
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
        agendasSugeridas = new ArrayList<>();
        agendasSugeridasYaProcesadas = false;
        initializeEmbeddableKey();

        return selectedParaEventoJudicialNuevo;
    }

    public static class SugerenciaAgendaJudicial implements Serializable {
        private String descripcion;
        private String responsable;
        private int diasHabilesOffset;
        private boolean seleccionada;

        public SugerenciaAgendaJudicial(String descripcion, String responsable, int diasHabilesOffset) {
            this.descripcion = descripcion;
            this.responsable = responsable;
            this.diasHabilesOffset = diasHabilesOffset;
        }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public String getResponsable() { return responsable; }
        public void setResponsable(String responsable) { this.responsable = responsable; }
        public int getDiasHabilesOffset() { return diasHabilesOffset; }
        public void setDiasHabilesOffset(int diasHabilesOffset) { this.diasHabilesOffset = diasHabilesOffset; }
        public boolean isSeleccionada() { return seleccionada; }
        public void setSeleccionada(boolean seleccionada) { this.seleccionada = seleccionada; }
    }

}
