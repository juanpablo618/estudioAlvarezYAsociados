package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Empleado;
import com.estudioAlvarezVersion2.jpa.Equipo;
import com.estudioAlvarezVersion2.jpacontroller.EquipoFacade;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("equipoController")
@SessionScoped
public class EquipoController implements Serializable {

    @EJB
    private EquipoFacade ejbFacade;
    private List<Equipo> items = null;
    private Equipo selected;

    public EquipoController() {
    }

    public Equipo getSelected() {
        return selected;
    }

    public void setSelected(Equipo selected) {
        this.selected = selected;
    }

    private EquipoFacade getFacade() {
        return ejbFacade;
    }

    public List<Equipo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public Equipo prepareCreate() {
        selected = new Equipo();
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

    public Equipo getEquipo(Integer id) {
        return getFacade().find(id);
    }

    public List<Equipo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Equipo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    @FacesConverter(forClass = Equipo.class)
    public static class EquipoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EquipoController controller = (EquipoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "equipoController");
            return controller.getEquipo(getKey(value));
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
            if (object instanceof Equipo) {
                Equipo o = (Equipo) object;
                return getStringKey(o.getIdEquipo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Equipo.class.getName()});
                return null;
            }
        }

    }
    
    
    
}
