
package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Consulta;
import com.estudioAlvarezVersion2.jpacontroller.ConsultaFacade;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author juanpablo618@hotmail.com
 */

@Named("consultaController")
@SessionScoped
public class ConsultaController implements Serializable {
    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.ConsultaFacade ejbFacade;
    
    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    private List<Consulta> items = null;
    private Consulta selected;
    private List<Consulta> filteredConsultas;

    public ConsultaController() {
    }

    public List<Consulta> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Consulta> items) {
        this.items = items;
    }

    public Consulta getSelected() {
        return selected;
    }

    public void setSelected(Consulta selected) {
        this.selected = selected;
    }

    public List<Consulta> getFilteredConsultas() {
        return filteredConsultas;
    }

    public void setFilteredConsultas(List<Consulta> filteredConsultas) {
        this.filteredConsultas = filteredConsultas;
    }
    
      public Consulta prepareCreate() {
        selected = new Consulta();
        
        initializeEmbeddableKey();
        return selected;
    }
      
    protected void initializeEmbeddableKey() {
    }
    
    
    public void create() {

        String successMessage = "Consulta".concat(" creada exitosamente");

        persist(JsfUtil.PersistAction.CREATE, successMessage);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
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
    
    protected void setEmbeddableKeys() {
    }
    
    private ConsultaFacade getFacade() {
        return ejbFacade;
    }
    
    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, "Consulta borrada exitosamente");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {

         persist(JsfUtil.PersistAction.UPDATE, "Consulta actualizada exitosamente");
    }


    
}
