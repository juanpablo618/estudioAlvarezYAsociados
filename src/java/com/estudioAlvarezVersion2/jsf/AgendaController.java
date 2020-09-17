package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpa.Expediente;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.AgendaFacade;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("agendaController")
@SessionScoped
public class AgendaController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.AgendaFacade ejbFacade;
    private List<Agenda> items = null;
    private List ItemsTODOS = null;
    
    private Agenda selected;
    private Agenda selectedParaCrearUnaNueva;
    private Date fechaParaFiltrar = new Date();
    private List<Agenda> filteredAgendas;
    
    public AgendaController() {
    }

    public Date getFechaParaFiltrar() {
        return fechaParaFiltrar;
    }

    public void setFechaParaFiltrar(Date fechaParaFiltrar) {
        this.fechaParaFiltrar = fechaParaFiltrar;
    }
    
    public Agenda getSelected() {
        return selected;
    }

    public void setSelected(Agenda selected) {
        this.selected = selected;
    }

    public Agenda getSelectedParaCrearUnaNueva() {
        return selectedParaCrearUnaNueva;
    }

    public void setSelectedParaCrearUnaNueva(Agenda selectedParaCrearUnaNueva) {
        this.selectedParaCrearUnaNueva = selectedParaCrearUnaNueva;
    }
    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AgendaFacade getFacade() {
        return ejbFacade;
    }

    public Agenda prepareCreate() {
        selected = new Agenda();
        selected.setRealizado("No");
        initializeEmbeddableKey();
        return selected;
    }
    
    public Agenda prepareReagendar(Agenda agendaAnterior) {
        selectedParaCrearUnaNueva = new Agenda();
        
        selectedParaCrearUnaNueva.setNombre(agendaAnterior.getNombre());
        selectedParaCrearUnaNueva.setApellido(agendaAnterior.getApellido());
        selectedParaCrearUnaNueva.setDescripcion(agendaAnterior.getDescripcion());
        selectedParaCrearUnaNueva.setFecha(agendaAnterior.getFecha());
        selectedParaCrearUnaNueva.setOrden(agendaAnterior.getOrden());
        selectedParaCrearUnaNueva.setRealizado("No");
        selectedParaCrearUnaNueva.setResponsable(agendaAnterior.getResponsable());
        
        initializeEmbeddableKey();
        return selectedParaCrearUnaNueva;
    }
    
    public Agenda prepareCreateConApellidoYNombre(String nombreYapellido) {
        selected = new Agenda();
        selected.setNombre(nombreYapellido);
        initializeEmbeddableKey();
        return selected;
    }

    
    public void createParaActividad() {
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreatedParaActividad"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void createAgendaConFiltroPorNombreYApellido() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        ExpedienteController expedienteController = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        Integer idExpediente = null;
        
        if(agendaController.getSelected().getApellido() != null){
            idExpediente = Integer.parseInt(agendaController.getSelected().getApellido());
         
             agendaController.getSelected().setApellido(expedienteController.getExpediente(idExpediente).getApellido());
             agendaController.getSelected().setNombre(expedienteController.getExpediente(idExpediente).getNombre());
             
        }
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void create() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        
        ExpedienteController expedienteController = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        Integer idExpediente = null;
                
        if(agendaController.getSelected().getApellido() != null){
            idExpediente = Integer.parseInt(agendaController.getSelected().getApellido());
            agendaController.getSelected().setApellido(expedienteController.getExpediente(idExpediente).getApellido());
         }
                       
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void create(String nombre, String apellido, int orden) {
        selected.setNombre(nombre);
        selected.setApellido(apellido);
        selected.setOrden(orden);
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void createReagendado(String nombre, String apellido, String responsable, String realizado, Integer orden, Date fecha, String descripcion ) {
        
        selectedParaCrearUnaNueva.setNombre(nombre);
        selectedParaCrearUnaNueva.setApellido(apellido);
        selectedParaCrearUnaNueva.setOrden(orden);
        selectedParaCrearUnaNueva.setDescripcion(descripcion);
        selectedParaCrearUnaNueva.setFecha(fecha);
        selectedParaCrearUnaNueva.setOrden(orden);
        selectedParaCrearUnaNueva.setRealizado(realizado);
        selectedParaCrearUnaNueva.setResponsable(responsable);
                
        persistReagendado(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreatedReagendada"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AgendaUpdated"));
        
        if (!JsfUtil.isValidationFailed()) {
            items = null; 
            //filteredAgendas = null;    // Invalidate list of items to trigger re-query.  BASICAMENTE ELIMINE ESTo PARA Q LUEGO DE QUE EL USUARIO HAGA UN UPDATE NO PIERDA LA LISTA FILTRADA EN LA TABLA
        }
        
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AgendaDeleted"));
        
        if (!JsfUtil.isValidationFailed()) {

                    for(int i = 0 ; i< filteredAgendas.size();i++){
                        if(Objects.equals(filteredAgendas.get(i).getIdAgenda(), selected.getIdAgenda())){
                            filteredAgendas.remove(i);
                        }
                    }
                    
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.  
        }
        
    }

    public List<Agenda> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<Agenda> getItemsTODOS() {
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
    
    private void persistReagendado(PersistAction persistAction, String successMessage) {
        if (selectedParaCrearUnaNueva != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selectedParaCrearUnaNueva);
                } else {
                    getFacade().remove(selectedParaCrearUnaNueva);
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

    public Agenda getAgenda(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Agenda> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Agenda> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Agenda.class)
    public static class AgendaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AgendaController controller = (AgendaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "agendaController");
            return controller.getAgenda(getKey(value));
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
            if (object instanceof Agenda) {
                Agenda o = (Agenda) object;
                return getStringKey(o.getIdAgenda());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Agenda.class.getName()});
                return null;
            }
        }

    }

    public List<Agenda> getFilteredAgendas() {
        return filteredAgendas;
    }

    public void setFilteredAgendas(List<Agenda> filteredAgendas) {
        this.filteredAgendas = filteredAgendas;
    }
    
    public void handleDateSelect(SelectEvent event) {
        System.out.println("paso por el handle Date Selec de angenda controller");
    RequestContext.getCurrentInstance().execute("PF('agendasTable').filter()");
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
    
    public void filtrarPorFecha(Date fechaParaFiltrar){
        this.filteredAgendas = new ArrayList<Agenda>();
                
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(fechaParaFiltrar); 
        
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

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
        
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        ExpedienteController expedienteController = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        Integer idExpediente = null;
        
        if(agendaController.getSelected().getApellido() != null){
         idExpediente = Integer.parseInt(agendaController.getSelected().getApellido());
          System.out.println("EN TRANSFERIR METODO idExpediente: "+idExpediente);
         agendaController.getSelected().setNombre(expedienteController.getExpediente(idExpediente).getNombre());
         agendaController.getSelected().setOrden(expedienteController.getExpediente(idExpediente).getOrden());
         agendaController.getSelected().setApellido(expedienteController.getExpediente(idExpediente).getApellido());
        }
        
       System.out.println("EN TRANSFERIR METODO: "+agendaController.getSelected().getApellido());
    }
    
}
