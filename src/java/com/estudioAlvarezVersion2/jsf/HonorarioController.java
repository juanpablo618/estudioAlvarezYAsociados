package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Expediente;
import com.estudioAlvarezVersion2.jpa.Honorario;
import com.estudioAlvarezVersion2.jpa.Turno;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.HonorarioFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("honorarioController")
@SessionScoped
public class HonorarioController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.HonorarioFacade ejbFacade;
    
    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    private List<Honorario> items = null;
    
    private Honorario selected;
    
    private Date fechaParaFiltrar = new Date();
    private List<Honorario> filteredHonorarios;
    
    public HonorarioController() {
    }

    public Date getFechaParaFiltrar() {
        return fechaParaFiltrar;
    }
    
    public void setFechaParaFiltrar(Date fechaParaFiltrar) {
        this.fechaParaFiltrar = fechaParaFiltrar;
    }

    public Honorario getSelected() {
        return selected;
    }

    public void setSelected(Honorario selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private HonorarioFacade getFacade() {
        return ejbFacade;
    }

    public Honorario prepareCreate() {
        selected = new Honorario();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void create() {

            persist(PersistAction.CREATE, "Honorario creado exitosamente");
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    
    public void update() {

            persist(PersistAction.UPDATE, "Honorario actualizado exitosamente");

            if (!JsfUtil.isValidationFailed()) {
                items = null;
            }
        }

    public void destroy() {
        persist(PersistAction.DELETE, "Honorario no borrado exitosamente");

        if (!JsfUtil.isValidationFailed()) {

            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.  
        }

    }

    public List<Honorario> getItems() {
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
                    JsfUtil.addErrorMessage(ex, "Honoranio no creado exitosamente");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "Honoranio no creado exitosamente");
            }
        }
    }
    
    public Honorario getHonorario(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Honorario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Honorario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Honorario.class)
    public static class HonorarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HonorarioController controller = (HonorarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "honorarioController");
            return controller.getHonorario(getKey(value));
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
            if (object instanceof Honorario) {
                Honorario o = (Honorario) object;
                return getStringKey(o.getIdHonorario());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Honorario.class.getName()});
                return null;
            }
        }

    }

    public List<Honorario> getFilteredHonorarios() {
        return filteredHonorarios;
    }

    public void setFilteredHonorarios(List<Honorario> filteredHonorarios) {
        this.filteredHonorarios = filteredHonorarios;
    }

    public void handleDateSelect(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('honorariosTable').filter()");
    }

    public void clearAllFilters() {

        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":HonorarioListForm:datalist");
        if (!dataTable.getFilters().isEmpty()) {
            dataTable.reset();

            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":HonorarioListForm:datalist");
        }
        
        dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":HonorarioListForm:datalistHonorario");
        if (!dataTable.getFilters().isEmpty()) {
            dataTable.reset();

            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":HonorarioListForm:datalistHonorario");
        }
        
        
    }
    
    
    public List<Honorario> getItemsBySelectedExp(int orden){
        System.out.println("getItemsBySelectedExp: "+orden);
        if (items == null) {
            items = getFacade().findAll();
        }
        List<Honorario> cloned_list;
        
        cloned_list = new ArrayList<>(this.items);

        List<Honorario> resultados = new ArrayList<>();

        for (Honorario honorario : cloned_list) {

            if (orden == honorario.getOrden() ) {
                resultados.add(honorario);
            }
        }
            
        return resultados;
    }

}
