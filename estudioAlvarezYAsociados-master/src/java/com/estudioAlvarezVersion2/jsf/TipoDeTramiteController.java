package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.EstadoDelTramite;
import com.estudioAlvarezVersion2.jpa.TipoDeTramite;
import com.estudioAlvarezVersion2.jpacontroller.TipoDeTramiteFacade;
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

@Named("tipoDeTramiteController")
@SessionScoped
public class TipoDeTramiteController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.TipoDeTramiteFacade ejbFacade;
    private List<TipoDeTramite> items = null;
    private TipoDeTramite selected;

    public TipoDeTramiteController() {
    }

    public TipoDeTramite getSelected() {
        return selected;
    }

    public void setSelected(TipoDeTramite selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoDeTramiteFacade getFacade() {
        return ejbFacade;
    }

    public TipoDeTramite prepareCreate() {
        selected = new TipoDeTramite();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Tipo de tramite creado exitosamente.");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Tipo de tramite actualizado exitosamente.");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Tipo de tramite borrado exitosamente.");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoDeTramite> getItems() {
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
                    JsfUtil.addErrorMessage(ex, "tipo de tramite no guardado.");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public TipoDeTramite getTipoDeTramite(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<TipoDeTramite> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TipoDeTramite> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TipoDeTramite.class)
    public static class TipoDeTramiteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoDeTramiteController controller = (TipoDeTramiteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoDeTramiteController");
            return controller.getTipoDeTramite(getKey(value));
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoDeTramite.class.getName()});
                return null;
            }
        }

    }

}
