package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Expediente;
import com.estudioAlvarezVersion2.jpa.Comunicacion;
import com.estudioAlvarezVersion2.jpacontroller.ComunicacionFacade;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("comunicacionController")
@SessionScoped
public class ComunicacionController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.ComunicacionFacade ejbFacade;
    private List<Comunicacion> items = null;
    private List ItemsTODOS = null;
    
    private Comunicacion selected;
    private Comunicacion selectedParaComunicacionNuevo;
    
    private Date fechaParaFiltrar = new Date();
    private List<Comunicacion> filteredComunicaciones;
    
    public ComunicacionController() {
    }

    public Date getFechaParaFiltrar() {
        return fechaParaFiltrar;
    }

    public void setFechaParaFiltrar(Date fechaParaFiltrar) {
        this.fechaParaFiltrar = fechaParaFiltrar;
    }
    
    public Comunicacion getSelected() {
        return selected;
    }

    public void setSelected(Comunicacion selected) {
        this.selected = selected;
    }

    public Comunicacion getSelectedParaComunicacionNuevo() {
        return selectedParaComunicacionNuevo;
    }

    public void setSelectedParaComunicacionNuevo(Comunicacion selectedParaComunicacionNuevo) {
        this.selectedParaComunicacionNuevo = selectedParaComunicacionNuevo;
    }

    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ComunicacionFacade getFacade() {
        return ejbFacade;
    }

    public Comunicacion prepareCreate() {
        selected = new Comunicacion();
        initializeEmbeddableKey();
        return selected;
    }
    
    public Comunicacion prepareCreateComunicacion(int orden) {
        selectedParaComunicacionNuevo = new Comunicacion();
        selectedParaComunicacionNuevo.setOrden(orden);
        initializeEmbeddableKey();

        return selectedParaComunicacionNuevo;
    }
    
    
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ComunicacionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void createComunicacion(Date fecha, String descripcion, String responsable, int orden) {
        
        selectedParaComunicacionNuevo.setFecha(fecha);
        selectedParaComunicacionNuevo.setDescripcion(descripcion);
        selectedParaComunicacionNuevo.setResponsable(responsable);
        selectedParaComunicacionNuevo.setOrden(orden);
        
        
        persistParaComunicacionNuevo(PersistAction.CREATE, "Comunicación creada exitosamente para el nro de orden:"+ orden);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void create(String nombre, String apellido, int orden) {
        selected.setOrden(orden);
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ComunicacionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, "comunicación actualizada correctamente");
        
        if (!JsfUtil.isValidationFailed()) {
            items = null; 
        }
        
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Comunicación borrada exitosamente");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Comunicacion> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    /*public List<Comunicacion> getItemsTODOS() {
        if (items == null) {
            items = getFacade().findAll();
        }
        ItemsTODOS = new ArrayList();
        ItemsTODOS.addAll(items);
        FacesContext context = FacesContext.getCurrentInstance();
        TurnoController turnoControllerBean = context.getApplication().evaluateExpressionGet(context, "#{turnoController}", TurnoController.class);
        ItemsTODOS.addAll(turnoControllerBean.getItems());
        
        
        return items;
    }*/
    
    
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
    
    private void persistParaComunicacionNuevo(PersistAction persistAction, String successMessage) {
        if (selectedParaComunicacionNuevo != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selectedParaComunicacionNuevo);
                } else {
                    getFacade().remove(selectedParaComunicacionNuevo);
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

    public Comunicacion getComunicacion(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Comunicacion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Comunicacion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Comunicacion.class)
    public static class ComunicacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ComunicacionController controller = (ComunicacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "comunicacionController");
            return controller.getComunicacion(getKey(value));
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
            if (object instanceof Comunicacion) {
                Comunicacion o = (Comunicacion) object;
                return getStringKey(o.getIdComunicacion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Comunicacion.class.getName()});
                return null;
            }
        }

    }

    public List<Comunicacion> getFilteredComunicacion() {
        return filteredComunicaciones;
    }

    public void setFilteredComunicaciones(List<Comunicacion> filteredComunicaciones) {
        this.filteredComunicaciones = filteredComunicaciones;
    }
    
    public void handleDateSelect(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('ComunicacionesTable').filter()");
    }
    
    public String verClaveCidi(int orden){
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        for(Expediente expediente: expedienteControllerBean.getItems()){
            if(expediente.getOrden() != null){
                    if(Integer.compare(expediente.getOrden(), orden) == 0){
                        if(expediente.getClaveCidi() !=null){
                            return expediente.getClaveCidi();
                        }else{
                            return "No posee clave CIDI";
                        }
                    }
            }        
        }
        return "no posee clave CIDI";
    }
    
    /*public String verDatosPersonalesYDelExp(int orden){
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        String datosExp = null;
        
        for(Expediente expediente: expedienteControllerBean.getItems()){
            if(expediente.getOrden() != null){
                    if(Integer.compare(expediente.getOrden(), orden) == 0){
                        
                        if(expediente.toString() !=null){
        
                            datosExp = expediente.toStringWithDatosPersonalesYDelExp();
                            return datosExp;
                            
                        }else{
                            datosExp = "no posee datos";
                            return datosExp;
                            
                        }
                    }
            }        
        }
        return datosExp;
    }*/
    
    public String verNroDeCuil(int orden){
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);        
        return expedienteControllerBean.verNroDeCuil(orden);
    }
    
    
    public String verClaveFiscal(int orden){
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        for(Expediente expediente: expedienteControllerBean.getItems()){

                    if(expediente.getOrden() != null){
                        if(Integer.compare(expediente.getOrden(), orden) == 0){

                            if(expediente.getClaveFiscal() !=null){
                                return expediente.getClaveFiscal();
                            }else{
                                return "No posee clave FISCAL";
                            }
                        }
                    }
        }
        return "no posee clave FISCAL";
        
    }
    
    public String verClaveDeSeguridadSocial(int orden){
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        for(Expediente expediente: expedienteControllerBean.getItems()){
            
                    if(expediente.getOrden() != null){
                        if(Integer.compare(expediente.getOrden(), orden) == 0){

                            if(expediente.getClaveSeguridadSocial()!=null){
                                return expediente.getClaveSeguridadSocial();
                            }else{
                                return "No posee clave de Seguridad Social";

                            }
                        }
                    }
        }
        return "no posee clave de Seguridad Social";
        
    }
    
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {

    Document pdf = (Document) document;
    
    pdf.open();
    pdf.setPageSize(PageSize.A4);
    
         ServletContext servletContext = (ServletContext)
         FacesContext.getCurrentInstance().getExternalContext().getContext();
    
    String logo = servletContext.getRealPath("") + File.separator + "resources/images" +
    File.separator + "cutmypic.png";
    
    pdf.add(Image.getInstance(logo));
    
}
    
    
}
