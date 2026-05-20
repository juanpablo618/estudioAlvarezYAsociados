package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.EventoDeCausasJudiciales;
import com.estudioAlvarezVersion2.jpacontroller.EventoDeCausasJudicialesFacade;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
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
    private List<EventoDeCausasJudiciales> items = null;
    private EventoDeCausasJudiciales selected;
    private EventoDeCausasJudiciales selectedParaEventoJudicialNuevo;

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
        initializeEmbeddableKey();

        return selectedParaEventoJudicialNuevo;
    }

}
