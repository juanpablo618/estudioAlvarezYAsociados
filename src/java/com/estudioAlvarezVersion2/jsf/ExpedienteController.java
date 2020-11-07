package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpa.Expediente;
import com.estudioAlvarezVersion2.jpa.ExpedienteDAO;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.ExpedienteFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import static java.util.Objects.nonNull;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("expedienteController")
@SessionScoped
public class ExpedienteController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.ExpedienteFacade ejbFacade;
    private List<Expediente> items = null;
    private Expediente selected;

    private Expediente selectedParaVerExp;

    
    private String estadoDelTramiteSelected;

    private List<Expediente> filteredExpedientes;

    private Date dateSelected;

    public Date getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(Date dateSelected) {
        this.dateSelected = dateSelected;
    }

    public ExpedienteController() {
    }

    public Expediente getSelected() {
        return selected;
    }

    public List<Expediente> getFilteredExpedientes() {
        return filteredExpedientes;
    }

    public String getEstadoDelTramiteSelected() {
        return estadoDelTramiteSelected;
    }

    public void setEstadoDelTramiteSelected(String estadoDelTramiteSelected) {
        this.estadoDelTramiteSelected = estadoDelTramiteSelected;
    }

    public void setFilteredExpedientes(List<Expediente> filteredExpedientes) {
        this.filteredExpedientes = filteredExpedientes;
    }

    public void setSelected(Expediente selected) {
        this.selected = selected;
    }

    public void handleDateSelect(Date selected) {
        this.dateSelected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ExpedienteFacade getFacade() {
        return ejbFacade;
    }

    public Expediente getSelectedParaVerExp() {
        return selectedParaVerExp;
    }

    public void setSelectedParaVerExp(Expediente selectedParaVerExp) {
        this.selectedParaVerExp = selectedParaVerExp;
    }
    
   
    public Expediente prepareCreateExpAdministrativo() {
        selected = new Expediente();
        selected.setTipoDeExpediente("administrativo");
        Date date = new Date();
        selected.setFechaDeAtencion(date);
        //ingresarOrdenAutoincremental();
       selected.setTablaDeHonorariosYGastos("fecha | concepto | debe | haber | saldo |");

        ingresarOrdenAutoincrementalSaltandoExpedientesSinCarpeta();

        initializeEmbeddableKey();
        return selected;
    }

    public Expediente prepareCreateExpJudicial() {
        selected = new Expediente();
        selected.setTipoDeExpediente("judicial");
        Date date = new Date();
        selected.setFechaDeAtencion(date);
        //ingresarOrdenAutoincremental();
        ingresarOrdenAutoincrementalSaltandoExpedientesSinCarpeta();
        selected.setTablaDeHonorariosYGastos("fecha | concepto | debe | haber | saldo |");

        initializeEmbeddableKey();
        return selected;
    }

    public Expediente prepareCreateExpSinCarpeta() {
        selected = new Expediente();
        selected.setTipoDeExpediente("sin carpeta");
        Date date = new Date();
        selected.setFechaDeAtencion(date);
        selected.setOrden(null);
        selected.setTablaDeHonorariosYGastos("fecha | concepto | debe | haber | saldo |");

        initializeEmbeddableKey();
        return selected;
    }

    public Expediente prepareCreate() {
        selected = new Expediente();
        initializeEmbeddableKey();
        return selected;
    }
    
    //no se usaría más fué el 1er approach
    public Expediente prepareViewParaExpedientePorNombreYApellido(Agenda agendaAnterior) {
        selectedParaVerExp = new Expediente();
        System.out.println("ACA Apellido: "+agendaAnterior.getApellido());
        System.out.println("ACA Nombre: "+agendaAnterior.getNombre());
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        for(Expediente expediente: expedienteControllerBean.getItems()){
                System.out.println("Apellido: "+expediente.getApellido() + " Nombre: "+expediente.getNombre());
                        
            if(expediente.getApellido()!= null && expediente.getNombre() != null){
                
                    if(expediente.getApellido().equals(agendaAnterior.getApellido()) && expediente.getNombre().equals(agendaAnterior.getNombre())){
                        System.out.println("expediente apellido: "+expediente.getApellido());
                        System.out.println("expediente Nombre: "+expediente.getNombre());
                        
                            System.out.println("LLEGO ACA"+ expediente.toStringWithDatosPersonalesYDelExp());
                            selectedParaVerExp = expediente;
                            return selectedParaVerExp;
                        
                    }
            }        
        }
        initializeEmbeddableKey();
        return selectedParaVerExp;
    }
    
    
    
    /* 
            Dejo esto por que fué el 1er approach que funcionó luego borrar
        selectedParaVerExp = new Expediente();
        System.out.println("ACA: "+agendaAnterior.getOrden());
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        for(Expediente expediente: expedienteControllerBean.getItems()){
            if(expediente.getOrden() != null){
                    if(Integer.compare(expediente.getOrden(), agendaAnterior.getOrden()) == 0){
                        
                        if(expediente.getOrden() !=null){
                            System.out.println("LLEGO ACA"+expediente.toStringWithDatosPersonalesYDelExp());
                            selectedParaVerExp = expediente;
                            return selectedParaVerExp;
                        }else{
                            return selectedParaVerExp;
                        }
                    }
            }        
        }
        initializeEmbeddableKey();
        return selectedParaVerExp;
        */
    
    public Expediente prepareViewParaExpediente(Agenda agendaAnterior) {
       
       try{
       if(agendaAnterior == null){
           return selectedParaVerExp;
       }else{
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        selectedParaVerExp = new Expediente();
        
        if(agendaAnterior.getOrden() != null && agendaAnterior.getOrden() != 0){
            for(Expediente expediente: expedienteControllerBean.getItems()){
                        
                if(expediente.getOrden()!= null ){
                        if(Integer.compare(expediente.getOrden(), agendaAnterior.getOrden()) == 0){
                                selectedParaVerExp = expediente;
                        }
                }        
            }
        }else{
             if( (agendaAnterior.getApellido() != null && agendaAnterior.getNombre()  != null) &&
                     ( agendaAnterior.getOrden() == null || agendaAnterior.getOrden() == 0)){
                 
                     for(Expediente expediente: expedienteControllerBean.getItems()){
                        if(expediente.getApellido() != null && expediente.getNombre() != null){
                                if( expediente.getApellido().equals(agendaAnterior.getApellido()) && expediente.getNombre().equals(agendaAnterior.getNombre()) ){
                                            selectedParaVerExp = expediente;

                                }
                        }        
                    }
             }
        
        }
        initializeEmbeddableKey();
        return selectedParaVerExp;
        
        }}catch(ELException e){
            System.out.println("error en el metodo prepareViewParaExpediente en expedienteController: " + e.getMessage());
        }
        return null;
    }

    /*
        este metodo va a devolver
        administrativo
        judicial
        sin carpeta
            para poder luego hacer un view dependiendo del tipo de Exp
    */
    public String buscarTipoDeExpediente(Agenda agendaAnterior) {
       
        if (agendaAnterior == null ){
            // esto lo hago por que la 1ra vez q se renderiza la pagina agendas y turnos , no hay un obj. "agendaAnterior" de agendas seleccionado
            return "AgendaSinExpAsociado";
        }else{
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        String tipoDeExp = "AgendaSinExpAsociado";
        
        
            if(  agendaAnterior.getOrden() != null && agendaAnterior.getOrden() != 0){
                
                    for(Expediente expediente: expedienteControllerBean.getItems()){

                        if(expediente.getOrden()!= null ){
                                if(Integer.compare(expediente.getOrden(), agendaAnterior.getOrden()) == 0){
                                        System.out.println("LLEGO ACA 1 por ORDEN");
                                        System.out.println("expediente.getTipoDeExpediente() : "+expediente.getTipoDeExpediente());
                                        
                                        tipoDeExp = expediente.getTipoDeExpediente();
                                }

                        }        
                    }
                
            }else{
                 if( (agendaAnterior.getApellido() != null && agendaAnterior.getNombre()  != null) &&
                         ( agendaAnterior.getOrden() == null || agendaAnterior.getOrden() == 0)){

                         for(Expediente expediente: expedienteControllerBean.getItems()){
                            if(expediente.getApellido() != null && expediente.getNombre() != null){
                                    if( expediente.getApellido().equals(agendaAnterior.getApellido()) && expediente.getNombre().equals(agendaAnterior.getNombre()) ){
                                        System.out.println("LLEGO ACA 2 por apellido y nombre");
                                        System.out.println("expediente.getTipoDeExpediente() : "+expediente.getTipoDeExpediente());
                                            
                                        tipoDeExp = expediente.getTipoDeExpediente();
                                    }

                            }        
                        }
                 }
                return tipoDeExp;
            }
        
       
       
        initializeEmbeddableKey();
        return tipoDeExp;
        }
    }
    
    
      public String verDatosPersonalesYDelExp(int orden){
        System.out.println("ACA: "+orden);
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        String datosExp = null;
        
        for(Expediente expediente: expedienteControllerBean.getItems()){
            if(expediente.getOrden() != null){
                    if(Integer.compare(expediente.getOrden(), orden) == 0){
                        
                        if(expediente.toString() !=null){
                            System.out.println("LLEGO ACA"+expediente.toStringWithDatosPersonalesYDelExp());
        
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
    
    public void create() {

        ingresarEdad();

        ingresarDni();

        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteCreated").concat(selected.getTipoDeExpediente()));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void ingresarEdad() {
        if (selected.getFechaDeNacimiento() != null) {
            Calendar fecha = new GregorianCalendar();
            int añoActual = fecha.get(Calendar.YEAR);

            int añoDeNacimiento = selected.getFechaDeNacimiento().getYear();

            añoDeNacimiento = añoDeNacimiento + 1900;

            int edad = 0;

            edad = añoActual - añoDeNacimiento;

            int mesDeNacimiento = selected.getFechaDeNacimiento().getMonth();
            if (mesDeNacimiento > 6) {
            selected.setEdad(edad-1);
            }else{
            selected.setEdad(edad);
            }
            
        } else {
            //TODO: review this code to drop off a notificacion We need to give an avise that date is not been setted!
            selected.setEdad(0);

        }

    }

    public void ingresarDni() {

        if (selected.getCuit().length() > 8) {

            String dni = selected.getCuit();
            dni = dni.replace(" ", "");

            dni = dni.substring(2, 10);

            selected.setDni(dni);
        } else {
            selected.setDni(null);

        }
    }

    public void ingresarOrdenAutoincrementalSaltandoExpedientesSinCarpeta() {
        selected.setOrden(autoIncrementarOrdenSaltandoExpedientesSinCarpeta());

    }

    public void ingresarOrdenAutoincremental() {

        selected.setOrden(autoIncrementarOrden());
    }

    public void update() {

        Calendar fecha = new GregorianCalendar();
        int añoActual = fecha.get(Calendar.YEAR);

        int añoDeNacimiento = selected.getFechaDeNacimiento().getYear();

        añoDeNacimiento = añoDeNacimiento + 1900;

        int edad = 0;

        edad = añoActual - añoDeNacimiento;

        selected.setEdad(edad);

        if(selected.getCuit() != null){
            String cuit = selected.getCuit();
            cuit = cuit.substring(2, 9);
            selected.setDni(cuit);
        }
        
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteUpdated"));
                System.out.println("ACA UPDATE ");
    }

    public void update2() {
        System.out.println("ACA UPDATE 2");
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    //TODO : this method is not been used, then remove
    public void calcularEdades() {
        Calendar fecha = new GregorianCalendar();
        int añoActual = fecha.get(Calendar.YEAR);

        for (Expediente ex : items) {
            System.out.println("entro al for");
            if (nonNull(ex.getFechaDeNacimiento())) {
                System.out.println("entro al if");
                System.out.println("Orden: " + ex.getOrden());

                int añoDeNacimiento = ex.getFechaDeNacimiento().getYear();

                añoDeNacimiento = añoDeNacimiento + 1900;

                int edad = 0;

                edad = añoActual - añoDeNacimiento;

                int mesDeNacimiento = ex.getFechaDeNacimiento().getMonth();
                if (mesDeNacimiento > 6) {
                    ex.setEdad(edad - 1);
                    selected = ex;
                    update2();
                } else {
                    ex.setEdad(edad);
                    selected = ex;
                    update2();
                }
            }
        }

    }

    public List<Expediente> getItems() {
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
                System.out.println("vino hasta el exception de exp. controller");
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Expediente getExpediente(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Expediente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Expediente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Expediente.class)
    public static class ExpedienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExpedienteController controller = (ExpedienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "expedienteController");
            return controller.getExpediente(getKey(value));
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
            if (object instanceof Expediente) {
                Expediente o = (Expediente) object;
                return getStringKey(o.getIdExpediente());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Expediente.class.getName()});
                return null;
            }
        }
    }

    public String getNombreYApellidoPorOrden(int orden) {

        String nombreYApellidoBuscado = null;

        for (Expediente i : items) {
            if (i.getOrden() == orden) {
                nombreYApellidoBuscado = i.getNombre() + i.getApellido();
            }
        }
        return nombreYApellidoBuscado;
    }

    public void handleDateSelect(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('expedientesTable').filter()");
    }

    public String getClaveCidi(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedientetrollerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        String claveCidi = null;

        for (Expediente i : expedientetrollerBean.getItems()) {

            if (i.getOrden() == orden) {
                claveCidi = i.getClaveCidi();
                return claveCidi;
            } else {
                claveCidi = "no se encontro";
            }
        }
        return claveCidi;
    }

    public String getClaveFiscal(int orden) {
        String claveFiscal = null;

        for (Expediente buscado : items) {
            if (buscado.getOrden() == orden) {
                claveFiscal = buscado.getClaveFiscal();
            }
        }
        return claveFiscal;
    }

    public String getClaveDeSeguridadSocial(int orden) {
        String claveDeSeguridadSocial = null;

        for (Expediente buscado : items) {
            if (buscado.getOrden() == orden) {
                claveDeSeguridadSocial = buscado.getClaveSeguridadSocial();
            }
        }
        return claveDeSeguridadSocial;
    }

    public String verProximaAgenda(Integer orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());

        for (Agenda agenda : agendaControllerBean.getItems()) {
            if (orden != null) {
                if (agenda.getOrden() == orden) {
                    String date2 = sdf.format(agenda.getFecha());

                    if (date.equals(date2)) {
                        return agenda.toString();
                    }
                }
            }
        }

        return "no existen agendas para hoy";
    }

    public String verClaveCidi(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        for (Expediente expediente : expedienteControllerBean.getItems()) {
            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {
                    if (expediente.getClaveCidi() != null) {
                        return expediente.getClaveCidi();
                    } else {
                        return "no posee clave CIDI";
                    }
                }
            }
        }
        return "no posee clave CIDI";
    }

    public String verClaveFiscal(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        for (Expediente expediente : expedienteControllerBean.getItems()) {
            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {
                    if (expediente.getClaveFiscal() != null) {
                        return expediente.getClaveFiscal();
                    } else {
                        return "no posee clave FISCAL";
                    }
                }
            }
        }
        return "no posee clave FISCAL";
    }

    public String verClaveDeSeguridadSocial(int orden) {
        System.out.println("ACAA: "+orden);
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        for (Expediente expediente : expedienteControllerBean.getItems()) {
            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {
                    if (expediente.getClaveSeguridadSocial() != null) {
                        return expediente.getClaveSeguridadSocial();
                    } else {
                        return "no posee clave de Seguridad Social";
                    }
                }
            }
        }
        return "no posee clave de Seguridad Social";
    }

    public int autoIncrementarOrdenSaltandoExpedientesSinCarpeta() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        int orden = 0;

        for (int i = 1; i <= expedienteControllerBean.getItemsAvailableSelectOne().size(); i++) {
            if (expedienteControllerBean.getItemsAvailableSelectOne().get(expedienteControllerBean.getItemsAvailableSelectOne().size() - i).getTipoDeExpediente().equalsIgnoreCase("administrativo") || expedienteControllerBean.getItemsAvailableSelectOne().get(expedienteControllerBean.getItemsAvailableSelectOne().size() - i).getTipoDeExpediente().equalsIgnoreCase("judicial")) {
                orden = expedienteControllerBean.getItemsAvailableSelectOne().get(expedienteControllerBean.getItemsAvailableSelectOne().size() - i).getOrden() + 1;
                break;
            }
        }
        System.out.println("orden: " + orden);
        return orden;
    }

    public int autoIncrementarOrden() {
        ExpedienteDAO expedienteDao = new ExpedienteDAO();

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        int orden = expedienteControllerBean.getItemsAvailableSelectOne().get(expedienteControllerBean.getItemsAvailableSelectOne().size() - 1).getOrden() + 1;

        System.out.println("orden: " + orden);

        return orden;
    }

    public void limpiarFiltros() {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('expedientesTable').clearFilters()");
    }

    public void filtrarPorEstadoDelTramite(String estadoDelTramiteSelected) {
        this.filteredExpedientes = new ArrayList<Expediente>();
        if (estadoDelTramiteSelected != null) {
            System.out.print("getItems(): " + getItems().size());
            for (Expediente expediente : getItems()) {
                System.out.print("expediente: " + expediente.getEstadoDelTramite());
                if (expediente.getEstadoDelTramite() != null) {
                    if (expediente.getEstadoDelTramite().equals(estadoDelTramiteSelected)) {
                        filteredExpedientes.add(expediente);
                    }
                }
            }
        }
    }

    
    public boolean filterByApellidoYNombre(Object value, Object filter, Locale locale) {
    String filterText = (filter == null) ? null : filter.toString().trim();
    if (filterText == null || filterText.equals("")) {
        return true;
    }

    if (value == null) {
        return false;
    }

    String apellidoYNombre = value.toString().toUpperCase();
    filterText = filterText.toUpperCase();

    if (apellidoYNombre.contains(filterText)) {
        return true;
    } else {
        return false;
    }
}
    
}
