package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.FechasRestringidas;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.FechasRestringidasFacade;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("fechasRestringidasController")
@SessionScoped
public class FechasRestringidasController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.FechasRestringidasFacade ejbFacade;
    
    private List<FechasRestringidas> items = null;
    private FechasRestringidas selected;

    public FechasRestringidasController() {
    }

    public FechasRestringidas getSelected() {
        return selected;
    }

    public void setSelected(FechasRestringidas selected) {
        this.selected = selected;
    }

    private FechasRestringidasFacade getFacade() {
        return ejbFacade;
    }

    public FechasRestringidas prepareCreate() {
        selected = new FechasRestringidas();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Fecha Restringida creada exitosamente");
        if (!JsfUtil.isValidationFailed()) {
            items = null; // Invalidar la lista para que se recargue.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Fecha Restringida actualizada exitosamente");
        if (!JsfUtil.isValidationFailed()) {
            items = null; // Invalidar la lista para que se recargue.
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Fecha Restringida eliminada exitosamente");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Eliminar la selección
            items = null; // Invalidar la lista para que se recargue.
        }
    }

    public List<FechasRestringidas> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
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
                    JsfUtil.addErrorMessage(ex, "Error de persistencia");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "Error de persistencia");
            }
        }
    }

    public FechasRestringidas getFechasRestringidas(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<FechasRestringidas> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<FechasRestringidas> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = FechasRestringidas.class)
    public static class FechasRestringidasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, javax.faces.component.UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FechasRestringidasController controller = (FechasRestringidasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "fechasRestringidasController");
            return controller.getFechasRestringidas(getKey(value));
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
        public String getAsString(FacesContext facesContext, javax.faces.component.UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FechasRestringidas) {
                FechasRestringidas o = (FechasRestringidas) object;
                return getStringKey(o.getIdFechaRestringida());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Objeto {0} es del tipo {1}; se esperaba: {2}", new Object[]{object, object.getClass().getName(), FechasRestringidas.class.getName()});
                return null;
            }
        }

    }

}
