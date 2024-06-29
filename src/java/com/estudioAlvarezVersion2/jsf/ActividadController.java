package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Actividad;
import com.estudioAlvarezVersion2.jpa.EstadoDelTramite;
import com.estudioAlvarezVersion2.jpacontroller.ActividadFacade;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("actividadController")
@SessionScoped
public class ActividadController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.ActividadFacade ejbFacade;
    private List<Actividad> items = null;
    private Actividad selected;

    public ActividadController() {
    }

    public Actividad getSelected() {
        return selected;
    }

    public void setSelected(Actividad selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ActividadFacade getFacade() {
        return ejbFacade;
    }

    public Actividad prepareCreate() {
        selected = new Actividad();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Actividad creada exitosamente.");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Actividad actualizada exitosamente.");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Actividad borrada exitosamente.");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Actividad> getItems() {
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
                    JsfUtil.addErrorMessage(ex, "Actividad no guardado.");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Actividad getActividad(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Actividad> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Actividad> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Actividad.class)
    public static class ActividadControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ActividadController controller = (ActividadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "actividadController");
            return controller.getActividad(getKey(value));
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
            if (object instanceof EstadoDelTramite) {
                EstadoDelTramite o = (EstadoDelTramite) object;
                return getStringKey(o.getIdEstadoDelTramite());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Actividad.class.getName()});
                return null;
            }
        }

    }

}
