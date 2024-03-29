package com.estudioAlvarezVersion2.downloadPDf;

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

@Named("configuracionesGeneralesController")
@SessionScoped
public class ConfiguracionesGeneralesController implements Serializable {

    @EJB
    private ConfiguracionesGeneralesFacade ejbFacade;
    private List<ConfiguracionesGenerales> items = null;
    private ConfiguracionesGenerales selected;
    private static final String foto_home = "condicionIVA/resources/images/home.jpg";
    private static final String foto_caja = "resources/images/caja.jpg";
    private static final String foto_users = "resources/images/userss.jpg";
    private static final String foto_product = "resources/images/product.jpg";
    private static final String foto_cart = "resources/images/cart.jpg";
    private static final String foto_production = "resources/images/production.jpg";
    
    public ConfiguracionesGeneralesFacade getEjbFacade() {
        return ejbFacade;
    }

    public static String getFoto_home() {
        return foto_home;
    }

    public static String getFoto_caja() {
        return foto_caja;
    }

    public static String getFoto_users() {
        return foto_users;
    }

    public static String getFoto_product() {
        return foto_product;
    }

    public static String getFoto_cart() {
        return foto_cart;
    }

    public static String getFoto_production() {
        return foto_production;
    }

    public ConfiguracionesGeneralesController() {
    }

    public ConfiguracionesGenerales getSelected() {
        return selected;
    }

    public void setSelected(ConfiguracionesGenerales selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ConfiguracionesGeneralesFacade getFacade() {
        return ejbFacade;
    }

    public ConfiguracionesGenerales prepareCreate() {
        selected = new ConfiguracionesGenerales();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ConfiguracionesGeneralesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ConfiguracionesGeneralesUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ConfiguracionesGeneralesDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ConfiguracionesGenerales> getItems() {
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

    public ConfiguracionesGenerales getConfiguracionesGenerales(java.lang.Integer id) {
        return getFacade().find(id);
    }
    
    
    public List<ConfiguracionesGenerales> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ConfiguracionesGenerales> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ConfiguracionesGenerales.class)
    public static class ConfiguracionesGeneralesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConfiguracionesGeneralesController controller = (ConfiguracionesGeneralesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "configuracionesGeneralesController");
            return controller.getConfiguracionesGenerales(getKey(value));
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
            if (object instanceof ConfiguracionesGenerales) {
                ConfiguracionesGenerales o = (ConfiguracionesGenerales) object;
                return getStringKey(o.getIdConfiguracionsGenerales());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ConfiguracionesGenerales.class.getName()});
                return null;
            }
        }

    }

}
