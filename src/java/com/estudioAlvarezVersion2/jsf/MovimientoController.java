package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Expediente;
import com.estudioAlvarezVersion2.jpa.Movimiento;
import com.estudioAlvarezVersion2.jpacontroller.MovimientoFacade;
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

@Named("movimientoController")
@SessionScoped
public class MovimientoController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.MovimientoFacade ejbFacade;
    private List<Movimiento> items = null;
    private List ItemsTODOS = null;
    
    private Movimiento selected;
    private Movimiento selectedParaMovimientoNuevo;
    
    private Date fechaParaFiltrar = new Date();
    private List<Movimiento> filteredMovimientos;
    
    public MovimientoController() {
    }

    public Date getFechaParaFiltrar() {
        return fechaParaFiltrar;
    }

    public void setFechaParaFiltrar(Date fechaParaFiltrar) {
        this.fechaParaFiltrar = fechaParaFiltrar;
    }
    
    public Movimiento getSelected() {
        return selected;
    }

    public void setSelected(Movimiento selected) {
        this.selected = selected;
    }

    public Movimiento getSelectedParaMovimientoNuevo() {
        return selectedParaMovimientoNuevo;
    }

    public void setSelectedParaMovimientoNuevo(Movimiento selectedParaMovimientoNuevo) {
        this.selectedParaMovimientoNuevo = selectedParaMovimientoNuevo;
    }

    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MovimientoFacade getFacade() {
        return ejbFacade;
    }

    public Movimiento prepareCreate() {
        selected = new Movimiento();
        initializeEmbeddableKey();
        return selected;
    }
    
    public Movimiento prepareCreateMovimiento(int orden) {
        selectedParaMovimientoNuevo = new Movimiento();
        selectedParaMovimientoNuevo.setOrden(orden);
        initializeEmbeddableKey();

        return selectedParaMovimientoNuevo;
    }
    
    
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MovimientoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void createMovimiento(Date fecha, String movimiento, int orden, String realizado) {
        
        selectedParaMovimientoNuevo.setFecha(fecha);
        selectedParaMovimientoNuevo.setMovimiento(movimiento);
        selectedParaMovimientoNuevo.setOrden(orden);
        selectedParaMovimientoNuevo.setRealizado(realizado);
        
        
        persistParaMovimientoNuevo(PersistAction.CREATE, "Movimiento creado exitosamente para el nro de orden:"+ orden);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void create(String nombre, String apellido, int orden) {
        selected.setOrden(orden);
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MovimientoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AgendaUpdated"));
        
        if (!JsfUtil.isValidationFailed()) {
            items = null; 
        }
        
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Movimiento borrado exitosamente");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Movimiento> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<Movimiento> getItemsTODOS() {
        if (items == null) {
            items = getFacade().findAll();
        }
        ItemsTODOS = new ArrayList();
        ItemsTODOS.addAll(items);
        FacesContext context = FacesContext.getCurrentInstance();
        TurnoController turnoControllerBean = context.getApplication().evaluateExpressionGet(context, "#{turnoController}", TurnoController.class);
        ItemsTODOS.addAll(turnoControllerBean.getItems());
        
        
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
    
    private void persistParaMovimientoNuevo(PersistAction persistAction, String successMessage) {
        if (selectedParaMovimientoNuevo != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selectedParaMovimientoNuevo);
                } else {
                    getFacade().remove(selectedParaMovimientoNuevo);
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

    public Movimiento getMovimiento(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Movimiento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Movimiento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Movimiento.class)
    public static class MovimientoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovimientoController controller = (MovimientoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movimientoController");
            return controller.getMovimiento(getKey(value));
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
            if (object instanceof Movimiento) {
                Movimiento o = (Movimiento) object;
                return getStringKey(o.getIdMovimiento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Movimiento.class.getName()});
                return null;
            }
        }

    }

    public List<Movimiento> getFilteredMovimientos() {
        return filteredMovimientos;
    }

    public void setFilteredMovimientos(List<Movimiento> filteredMovimientos) {
        this.filteredMovimientos = filteredMovimientos;
    }
    
    public void handleDateSelect(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('movimientosTable').filter()");
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
    
    public String verDatosPersonalesYDelExp(int orden){
        
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
    }
    
    public String verNroDeCuil(int orden){
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        for(Expediente expediente: expedienteControllerBean.getItems()){
            if(expediente.getOrden() != null){
                    if(Integer.compare(expediente.getOrden(), orden) == 0){
                        
                        if(expediente.getCuit() !=null){
                            return expediente.getCuit();
                        }else{
                            return "No posee CUIT/CUIL";
                        }
                    }
            }        
        }
        return "No posee CUIT/CUIL";
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
    
   /* public void filtrarPorFecha(Date fechaParaFiltrar){
        this.filteredMovimientos = new ArrayList<Movimiento>();
                
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(fechaParaFiltrar); 
        
        FacesContext context = FacesContext.getCurrentInstance();
        MovimientoController agendaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", MovimientoController.class);

        if(fechaParaFiltrar != null){
                for(Agenda agenda: agendaControllerBean.getItems()){
                    if(agenda.getFecha() != null){
                        String date2 = sdf.format(agenda.getFecha()); 
                        if(date.equals(date2)){
                                        agendaControllerBean.getFilteredAgendas().add(agenda);
                        }
                    }
                }
            }        
    }
    
    public void clearAllFilters() {

    DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":AgendaListForm:datalist");
    if (!dataTable.getFilters().isEmpty()) {
        dataTable.reset();

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update(":AgendaListForm:datalist");
    }
}
    
    public void transferir(){
    
        FacesContext context = FacesContext.getCurrentInstance();
        
        MovimientoController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", MovimientoController.class);
        ExpedienteController expedienteController = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        Integer idExpediente = null;
        
        if(agendaController.getSelected().getApellido() != null){
         idExpediente = Integer.parseInt(agendaController.getSelected().getApellido());
         agendaController.getSelected().setNombre(expedienteController.getExpediente(idExpediente).getNombre());
         agendaController.getSelected().setOrden(expedienteController.getExpediente(idExpediente).getOrden());
         agendaController.getSelected().setApellido(expedienteController.getExpediente(idExpediente).getApellido());
        }
        
    }*/
    
}
