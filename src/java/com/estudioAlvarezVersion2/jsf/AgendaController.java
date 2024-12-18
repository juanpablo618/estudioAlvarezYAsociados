package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpa.FechasRestringidas;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("agendaController")
@SessionScoped
public class AgendaController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.AgendaFacade ejbFacade;
    
    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    private List<Agenda> items = null;
    private List<Agenda> itemsitemsWithSession = null;
    
    private static final String DD_MM_YYYY = "dd/MM/yyyy";
    private static final String LA_FECHA_SELECCIONADA_NO_ES_VALIDA = "la fecha selecionada no es válida";
    private static final String POR_SER_FERIADO = " por ser feriado";
    private static final String SI = "Si";
    private static final String NO = "No";
    private static final String ESTA_PERSONA_PARA_ESTE_DIA_YA_TIENE_40_AGENDAS = "Esta persona para este día ya tiene 40 o más agendas ";

    private Agenda selected;
    private Agenda selectedActividad;
    private Agenda selectedAgendaPasada;
    private Agenda selectedAgendaFutura;

    private Agenda selectedTurnoPasado;
    private Agenda selectedTurnoFuturo;

    
    private Agenda selectedParaCrearUnaNueva;
    private Date fechaParaFiltrar = new Date();
    private List<Agenda> filteredAgendas;
    private List<Agenda> filteredAgendasConSesion;
    private List<Agenda> filteredAgendasConSesionOnlyAdminUsers;
    
    private String responsable;
    private Date fechaDesde;
    private Date fechaHasta;
    private String realizado;
    private List<Agenda> selectedItems;

    // Mapa de líderes a sus respectivos empleados
    private static final Map<String, String[]> lideresEmpleadosMap = new HashMap<>();
    
    static {
        lideresEmpleadosMap.put("Mateo Francisco Alvarez", new String[]{
            "María Emilia Campos", "Paula Alvarez", "Paola Maldonado", "Ayelen Brizzio",
            "Mateo Novau", "Carla Juez", "Natali D Agostino", "Maria Jose Alaye",
            "Liliana Romero", "Ezequiel Brener", "Camila A Ruiz Diaz", "Amparo Alanis Toledo",
            "Pilar Boglione", "juan cuello"});
        lideresEmpleadosMap.put("María Emilia Campos", new String[]{
            "Mateo Francisco Alvarez", "Paula Alvarez", "Paola Maldonado", "Ayelen Brizzio",
            "Mateo Novau", "Carla Juez", "Natali D Agostino", "Maria Jose Alaye",
            "Liliana Romero", "Ezequiel Brener", "Camila A Ruiz Diaz", "Amparo Alanis Toledo",
            "Pilar Boglione"});
        lideresEmpleadosMap.put("Paula Alvarez", new String[]{
            "Mateo Novau", "Natali D Agostino", "Maria Jose Alaye", 
            "Liliana Romero", "Pilar Boglione"});
        lideresEmpleadosMap.put("Paola Maldonado", new String[]{
            "Carla Juez", "Ezequiel Brener", "Camila A Ruiz Diaz", "Amparo Alanis Toledo",
            "Maria Jose Alaye"});
        lideresEmpleadosMap.put("Ayelen Brizzio", new String[]{
            "Mateo Novau"});
        // Agregar más líderes y sus empleados según sea necesario
    }
    
    public boolean isLeader(String nombre) {
        return lideresEmpleadosMap.containsKey(nombre);
    }

    public Agenda getSelectedTurnoPasado() {
        return selectedTurnoPasado;
    }

    public void setSelectedTurnoPasado(Agenda selectedTurnoPasado) {
        this.selectedTurnoPasado = selectedTurnoPasado;
    }

    public Agenda getSelectedTurnoFuturo() {
        return selectedTurnoFuturo;
    }

    public void setSelectedTurnoFuturo(Agenda selectedTurnoFuturo) {
        this.selectedTurnoFuturo = selectedTurnoFuturo;
    }
    
    
    
    public List<Agenda> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Agenda> selectedItems) {
        this.selectedItems = selectedItems;
    }

    private String selectionMode = "single"; // Por defecto, modo de selección único

    public String getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }
    
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

    public Agenda getSelectedAgendaPasada() {
        return selectedAgendaPasada;
    }

    public void setSelectedAgendaPasada(Agenda selectedAgendaPasada) {
        this.selectedAgendaPasada = selectedAgendaPasada;
    }

    public Agenda getSelectedAgendaFutura() {
        return selectedAgendaFutura;
    }
    
    public void setSelectedAgendaFutura(Agenda selectedAgendaFutura) {
        this.selectedAgendaFutura = selectedAgendaFutura;
    }

    public Agenda getSelectedParaCrearUnaNueva() {
        return selectedParaCrearUnaNueva;
    }

    public void setSelectedParaCrearUnaNueva(Agenda selectedParaCrearUnaNueva) {
        this.selectedParaCrearUnaNueva = selectedParaCrearUnaNueva;
    }

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }
    
    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
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
    
    public Agenda prepareCreateActividad() {
        selectedActividad = new Agenda();
        selectedActividad.setRealizado("No");
        initializeEmbeddableKey();
        return selectedActividad;
    }

    public Agenda getSelectedActividad() {
        return selectedActividad;
    }

    public void setSelectedActividad(Agenda selectedActividad) {
        this.selectedActividad = selectedActividad;
    }
    
    public List<Agenda> getFilteredAgendasConSesion() {
        return filteredAgendasConSesion;
    }

    public void setFilteredAgendasConSesion(List<Agenda> filteredAgendasConSesion) {
        this.filteredAgendasConSesion = filteredAgendasConSesion;
    }

    public List<Agenda> getFilteredAgendasConSesionOnlyAdminUsers() {
        return filteredAgendasConSesionOnlyAdminUsers;
    }

    public void setFilteredAgendasConSesionOnlyAdminUsers(List<Agenda> filteredAgendasConSesionOnlyAdminUsers) {
        this.filteredAgendasConSesionOnlyAdminUsers = filteredAgendasConSesionOnlyAdminUsers;
    }
    
    public Agenda prepareReagendar(Agenda agendaAnterior) {
        
        selected.setRealizado("Reagendada");
        
        selectedParaCrearUnaNueva = new Agenda();

        selectedParaCrearUnaNueva.setNombre(agendaAnterior.getNombre());
        selectedParaCrearUnaNueva.setApellido(agendaAnterior.getApellido());
        selectedParaCrearUnaNueva.setDescripcion(agendaAnterior.getDescripcion());
        selectedParaCrearUnaNueva.setFecha(agendaAnterior.getFecha());
        selectedParaCrearUnaNueva.setOrden(agendaAnterior.getOrden());
        selectedParaCrearUnaNueva.setRealizado("No");
        selectedParaCrearUnaNueva.setResponsable(agendaAnterior.getResponsable());
        selectedParaCrearUnaNueva.setPrioridad(agendaAnterior.getPrioridad());

        initializeEmbeddableKey();
        return selectedParaCrearUnaNueva;
    }

    public Agenda prepareCreateConApellidoYNombre(String nombreYapellido) {
        selected = new Agenda();
        selected.setNombre(nombreYapellido);
        initializeEmbeddableKey();
        return selected;
    }
    
    public void actualizarTodasLasAgendas(){
        items = null;
    }

    private Boolean validateHolidays(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formato correcto
    String dateString = sdf.format(date); // Convertir la fecha a String en el formato deseado

    // Obtener el controlador de FechasRestringidas
    FacesContext context = FacesContext.getCurrentInstance();
    FechasRestringidasController fechasRestringidasController = context.getApplication()
        .evaluateExpressionGet(context, "#{fechasRestringidasController}", FechasRestringidasController.class);

    Boolean result = false;

    for (FechasRestringidas item : fechasRestringidasController.getItems()) {
        // Formatear cada fecha de item a String en el mismo formato y compararlas
        String itemDateString = sdf.format(item.getFecha()); // Convertir la fecha del item a String
        if (itemDateString.equals(dateString)) {
            result = true;
            break; // No es necesario seguir buscando si ya encontramos una coincidencia
        }
    }

    return result;
}

    
    public void createParaActividad() {
        
        if (validateHolidays(selectedActividad.getFecha())) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA+POR_SER_FERIADO, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            items = null;
            filteredAgendas = null;
            filteredAgendasConSesion = null; 
        } else {

            persistActividad(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreatedParaActividad"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
                filteredAgendas = null;
                filteredAgendasConSesion = null; 

            }
        }
    }

    public void createAgendaConFiltroPorNombreYApellido() {

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        ExpedienteController expedienteController = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        Integer idExpediente;

        if (agendaController.getSelected().getApellido() != null) {
            idExpediente = Integer.parseInt(agendaController.getSelected().getApellido());

            agendaController.getSelected().setApellido(expedienteController.getExpediente(idExpediente).getApellido());
            agendaController.getSelected().setNombre(expedienteController.getExpediente(idExpediente).getNombre());

        }

        if (validateHolidays(agendaController.getSelected().getFecha())) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA+POR_SER_FERIADO, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            items = null;
        } else {
            
            if (validateAmountOfAgendas(agendaController.getSelected().getResponsable(), agendaController.getSelected().getFecha())) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ESTA_PERSONA_PARA_ESTE_DIA_YA_TIENE_40_AGENDAS, "");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            
            }
            
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }
    
    public void create() {

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        ExpedienteController expedienteController = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        Integer idExpediente;

        if (agendaController.getSelected().getApellido() != null) {
            idExpediente = Integer.parseInt(agendaController.getSelected().getApellido());
            agendaController.getSelected().setApellido(expedienteController.getExpediente(idExpediente).getApellido());
        }

        if (validateHolidays(agendaController.getSelected().getFecha())) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA+POR_SER_FERIADO, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            items = null;
        } else {
            
            if (validateAmountOfAgendas(agendaController.getSelected().getResponsable(), agendaController.getSelected().getFecha())) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ESTA_PERSONA_PARA_ESTE_DIA_YA_TIENE_40_AGENDAS, "");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            }
            
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void create(String nombre, String apellido, int orden) {
        selected.setNombre(nombre);
        selected.setApellido(apellido);
        selected.setOrden(orden);

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        if (validateHolidays(selected.getFecha())) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA+POR_SER_FERIADO, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            items = null;
        } else {
            
            if (validateAmountOfAgendas(agendaController.getSelected().getResponsable(), agendaController.getSelected().getFecha())) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ESTA_PERSONA_PARA_ESTE_DIA_YA_TIENE_40_AGENDAS, "");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            }
            
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void createReagendado(String nombre, String apellido, String responsable,
            String realizado, Integer orden, Date fecha, String descripcion, String prioridad) {
        
        if(selected.getRealizado().equalsIgnoreCase("Reagendada")){
        marcarComoReagendada();        
        }

        if(selected.getRealizado().equalsIgnoreCase("Si")){
        marcarComoRealizadaSi();        
        }
        
        if(selected.getRealizado().equalsIgnoreCase("No")){
        marcarComoRealizadaNo();        
        }
        
        
        selectedParaCrearUnaNueva.setNombre(nombre);
        selectedParaCrearUnaNueva.setApellido(apellido);
        selectedParaCrearUnaNueva.setOrden(orden);
        selectedParaCrearUnaNueva.setDescripcion(descripcion);
        selectedParaCrearUnaNueva.setPrioridad(prioridad);
        selectedParaCrearUnaNueva.setFecha(fecha);
        selectedParaCrearUnaNueva.setOrden(orden);
        selectedParaCrearUnaNueva.setRealizado(realizado);
        selectedParaCrearUnaNueva.setResponsable(responsable);

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        
        if (validateHolidays(selectedParaCrearUnaNueva.getFecha())) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA+POR_SER_FERIADO, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            items = null;
        } else {
            
            if (validateAmountOfAgendas(agendaController.getSelected().getResponsable(), agendaController.getSelected().getFecha())) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ESTA_PERSONA_PARA_ESTE_DIA_YA_TIENE_40_AGENDAS, "");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            }
            
            persistReagendado(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendaCreatedReagendada"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void update() {

        if (validateHolidays(selected.getFecha())) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA+POR_SER_FERIADO, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            items = null;
            filteredAgendas = null;
        } else {

            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AgendaUpdated"));

            if (!JsfUtil.isValidationFailed()) {
                items = null;
                filteredAgendas = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void updateAgendaPasada() {

        if (validateHolidays(selectedAgendaPasada.getFecha())) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA+POR_SER_FERIADO, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            items = null;
            filteredAgendas = null;
        } else {

            persistAgendaPasada(PersistAction.UPDATE, "Agenda pasada actualizada exitosamente");

            if (!JsfUtil.isValidationFailed()) {
                items = null;
                filteredAgendas = null;    // Invalidate list of items to trigger re-query.  
            }
        }
    }

    public void updateAgendaFutura() {

        if (validateHolidays(selectedAgendaFutura.getFecha())) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA+POR_SER_FERIADO, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            items = null;
            filteredAgendas = null;
        } else {

            persistAgendaFutura(PersistAction.UPDATE, "Agenda futura actualizada exitosamente");

            if (!JsfUtil.isValidationFailed()) {
                items = null;
                filteredAgendas = null;    // Invalidate list of items to trigger re-query.  BASICAMENTE ELIMINE ESTo PARA Q LUEGO DE QUE EL USUARIO HAGA UN UPDATE NO PIERDA LA LISTA FILTRADA EN LA TABLA
            }
        }

    }
    
    public void marcarComoRealizadaNo() {
        selected.setRealizado(NO);
        this.update();
    }

    public void marcarComoRealizadaSi() {
        selected.setRealizado(SI);
        this.update();
    }
    
    public void marcarComoReagendada(){
        selected.setRealizado("Reagendada");
        this.update();
    }
    
    public void marcarComoRealizadaSiAgendaPasada() {
        selectedAgendaPasada.setRealizado(SI);
        this.updateAgendaPasada();
    }
    
    public void marcarComoRealizadaSiAgendaFutura() {
        selectedAgendaFutura.setRealizado(SI);
        this.updateAgendaFutura();
    }
    
    /*
    el botón amarillo debe:
        1)a la agenda seleccionada = pasarla a reagendada
        2) debe crear una agenda nueva con todos los mismos datos que la anterior
        solo que realizado = no
        3) fecha = 1 día hábil màs q la actual (seleccionada)    
    */
    public void reagendarProximoDiaHabil() {
    selected.setRealizado("Reagendada");
    this.prepareReagendar(selected);
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(selected.getFecha());

    do {
        // Sumamos un día
        calendar.add(Calendar.DAY_OF_WEEK, 1);

        // Verificamos si es sábado
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            // Sumamos dos días para llegar al lunes
            calendar.add(Calendar.DAY_OF_WEEK, 2);
        }
        
    } while (validateHolidays(calendar.getTime()));

    selectedParaCrearUnaNueva.setFecha(calendar.getTime());

    SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
    String dateReagendada = sdf.format(selectedParaCrearUnaNueva.getFecha());

    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "agenda reagendada para el día " + dateReagendada, " ");
    FacesContext.getCurrentInstance().addMessage(null, facesMsg);

    this.update();
    this.createReagendado(selected.getNombre(), selected.getApellido(), selected.getResponsable(),
            selectedParaCrearUnaNueva.getRealizado(), selected.getOrden(), selectedParaCrearUnaNueva.getFecha(), selected.getDescripcion(), selected.getPrioridad());
}
    
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AgendaDeleted"));

        if (!JsfUtil.isValidationFailed()) {

            if (filteredAgendas != null && !filteredAgendas.isEmpty()) {
                for (int i = 0; i < filteredAgendas.size(); i++) {
                    if (selected != null) {
                        if (Objects.equals(filteredAgendas.get(i).getIdAgenda(), selected.getIdAgenda())) {
                            filteredAgendas.remove(i);
                        }
                    } else {
                        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No seleccionó ninguna fila", "debe seleccionar alguno");
                        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                    }
                }
            }

            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.  
        }

    }

    public void destroyAgendaPasada() {
        persistAgendaPasada(PersistAction.DELETE, "Agenda Anterior borrada exitosamente");
        if (!JsfUtil.isValidationFailed()) {
            selectedAgendaPasada = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void destroyAgendaFutura() {
        persistAgendaFutura(PersistAction.DELETE, "Agenda Futura borrada exitosamente");
        if (!JsfUtil.isValidationFailed()) {
            selectedAgendaPasada = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Agenda> getItems() {
        if (items == null) {
            items = getFacade().findAllSortedByDate();
        }
        //List<Agenda> cloned_list;

        //cloned_list = new ArrayList<>(this.items);
        //Collections.sort(cloned_list, new SortByDate());
        return items;
    }
    
    public List<Agenda> getItemsByOrder(Integer orden) {
        return getFacade().getItemsByOrder(orden);
    }
    
    /*
    por que entiendo q lo vamos a hacer por order by
    public void ordenarListItems(List<Agenda> listaDeAgendas) {
        Collections.sort(listaDeAgendas, new SortByDate());
    }*/

    /*
    metodo viejo backup por las dudas, luego borrar 13/07/2024
    public List<Agenda> getItemsBySessionUser(String userNombreCompleto, String date) {
        if (items == null) {
            items = getFacade().findAll();
        }
        List<Agenda> cloned_list;
        
        cloned_list = new ArrayList<>(this.items);
        Collections.sort(cloned_list, new SortByDate());
        
        List<Agenda> resultados = new ArrayList<>();

        for (Agenda agenda : cloned_list) {
            String nombreCompletoResponsable = agenda.getResponsable();
                
            if (nombreCompletoResponsable.equals(userNombreCompleto) && agenda.getDiaMesAnio().equals(date)) {
                resultados.add(agenda);
            }
        }

        return resultados;

    }*/
    
    public List<Agenda> getItemsBySessionUser(String userNombreCompleto, String dateStr) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            System.err.println("error en getItemsBySessionUser" + e);
        }
        
        if (date == null) {
            return Collections.emptyList();
        }

        itemsitemsWithSession = getFacade().findByResponsableAndFecha(userNombreCompleto, date);

        //aca pasamos el  ordenamiento a la bd con order by
        //Collections.sort(itemsitemsWithSession, new SortByDate());

        return itemsitemsWithSession;
    }



    /*public List<Agenda> getItemsByLeader(String userNombreCompleto, String date) {
        if (items == null) {
            items = getFacade().findAll();
        }

        // Mapa de líderes a sus respectivos empleados
        Map<String, String[]> lideresEmpleadosMap = new HashMap<>();
        lideresEmpleadosMap.put("Mateo Francisco Alvarez", new String[]{
            "María Emilia Campos", "Paula Alvarez", "Paola Maldonado", "Ayelen Brizzio",
            "Mateo Novau", "Carla Juez", "Natali D Agostino", "Maria Jose Alaye",
            "Liliana Romero", "Ezequiel Brener", "Camila A Ruiz Diaz", "Amparo Alanis Toledo",
            "Pilar Boglione", "juan cuello"});
        lideresEmpleadosMap.put("María Emilia Campos", new String[]{
            "Mateo Francisco Alvarez", "Paula Alvarez", "Paola Maldonado", "Ayelen Brizzio",
            "Mateo Novau", "Carla Juez", "Natali D Agostino", "Maria Jose Alaye",
            "Liliana Romero", "Ezequiel Brener", "Camila A Ruiz Diaz", "Amparo Alanis Toledo",
            "Pilar Boglione"});
        lideresEmpleadosMap.put("Paula Alvarez", new String[]{
            "Mateo Novau", "Natali D Agostino",
            "Liliana Romero", "Pilar Boglione"});
        lideresEmpleadosMap.put("Paola Maldonado", new String[]{
            "Carla Juez", "Ezequiel Brener", "Camila A Ruiz Diaz", "Amparo Alanis Toledo",
            "Maria Jose Alaye", "Natali D Agostino"});
        lideresEmpleadosMap.put("Ayelen Brizzio", new String[]{
            "Mateo Novau", "Natali D Agostino"});

        // Obtener la lista de empleados del líder
        Set<String> nombresEmpleados = new HashSet<>();
        if (lideresEmpleadosMap.containsKey(userNombreCompleto)) {
            nombresEmpleados.addAll(Arrays.asList(lideresEmpleadosMap.get(userNombreCompleto)));
        } else {
            System.out.println("Líder no encontrado en el mapa: " + userNombreCompleto);
        }

        // Filtrar los elementos antes de ordenar
        List<Agenda> filteredList = new ArrayList<>();
        for (Agenda agenda : items) {
            String nombreCompletoResponsable = agenda.getResponsable();
            if (nombresEmpleados.contains(nombreCompletoResponsable) && agenda.getDiaMesAnio().equals(date)) {
                filteredList.add(agenda);
            }
        }

        // Ordenar la lista filtrada
        Collections.sort(filteredList, new SortByDate());

        return filteredList;
    }*/
    
    public List<Agenda> getItemsByLeader(String userNombreCompleto, String dateStr) {
        // Obtener la lista de empleados asociados al líder
        Set<String> nombresEmpleados = obtenerEmpleadosPorLider(userNombreCompleto);

        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            System.err.println("error en getItemsBySessionUser" + e);
        }
        
        List<Agenda> agendas = getFacade().findByResponsablesAndFecha(nombresEmpleados, date);

        // Ordenar la lista
        // No es necesario ordenar la lista aquí, ya que la consulta en la base de datos ya está ordenada
        // Collections.sort(agendas, new SortByDate());

        return agendas;
    }

    private Set<String> obtenerEmpleadosPorLider(String lider) {
        Map<String, String[]> lideresEmpleadosMap = new HashMap<>();
        lideresEmpleadosMap.put("Mateo Francisco Alvarez", new String[]{
            "María Emilia Campos", "Paula Alvarez", "Paola Maldonado", "Ayelen Brizzio",
            "Mateo Novau", "Carla Juez", "Natali D Agostino", "Maria Jose Alaye",
            "Liliana Romero", "Ezequiel Brener", "Camila A Ruiz Diaz", "Amparo Alanis Toledo",
            "Pilar Boglione", "juan cuello"});
        lideresEmpleadosMap.put("María Emilia Campos", new String[]{
            "Mateo Francisco Alvarez", "Paula Alvarez", "Paola Maldonado", "Ayelen Brizzio",
            "Mateo Novau", "Carla Juez", "Natali D Agostino", "Maria Jose Alaye",
            "Liliana Romero", "Ezequiel Brener", "Camila A Ruiz Diaz", "Amparo Alanis Toledo",
            "Pilar Boglione", "juan cuello"});
        lideresEmpleadosMap.put("Paula Alvarez", new String[]{
            "Mateo Novau", "Natali D Agostino", "Liliana Romero", "Pilar Boglione"});
        lideresEmpleadosMap.put("Paola Maldonado", new String[]{
            "Carla Juez", "Ezequiel Brener", "Camila A Ruiz Diaz", "Amparo Alanis Toledo",
            "Maria Jose Alaye"});
        lideresEmpleadosMap.put("Ayelen Brizzio", new String[]{
            "Mateo Novau", "Natali D Agostino",});

        Set<String> empleados = new HashSet<>();
        if (lideresEmpleadosMap.containsKey(lider)) {
            empleados.addAll(Arrays.asList(lideresEmpleadosMap.get(lider)));
        } else {
            System.out.println("Líder no encontrado en el mapa: " + lider);
        }

        return empleados;
    }

    private boolean validateAmountOfAgendas(String responsable, Date fecha) {
        
        String consulta = "SELECT COUNT(a) FROM Agenda a WHERE a.responsable = :responsable AND a.fecha = :fecha";
        TypedQuery<Long> query = em.createQuery(consulta, Long.class);
        query.setParameter("responsable", responsable);
        query.setParameter("fecha", fecha);

        Long cantidadDeAgendasPorDia = query.getSingleResult();
        
        if(cantidadDeAgendasPorDia>=40){
            return true;
        }else{
            return false;
        }
    }

    static class SortByDate implements Comparator<Agenda> {

        @Override
        public int compare(Agenda a, Agenda b) {

            if (a.getFecha() == null) {
                return 1;
            }

            if (b.getFecha() == null) {
                return -1;
            }

            if (a.getFecha() == null && b.getFecha() == null) {
                return 0;
            }

            if (a.getFecha().equals(b.getFecha())) {
                return 0;
            }

            return a.getFecha().compareTo(b.getFecha());

        }
    }

    /*public List<Agenda> getItemsTODOS() {
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
    
    private void persistActividad(PersistAction persistAction, String successMessage) {
        if (selectedActividad != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selectedActividad);
                } else {
                    getFacade().remove(selectedActividad);
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

    private void persistAgendaPasada(PersistAction persistAction, String successMessage) {
        if (selectedAgendaPasada != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selectedAgendaPasada);
                } else {
                    getFacade().remove(selectedAgendaPasada);
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

    private void persistAgendaFutura(PersistAction persistAction, String successMessage) {
        if (selectedAgendaFutura != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selectedAgendaFutura);
                } else {
                    getFacade().remove(selectedAgendaFutura);
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
        RequestContext.getCurrentInstance().execute("PF('agendasTable').filter()");
    }
    
    public void handleDateSelectWithSessionOnlyAdmin(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('agendasTableWithSessionOnlyAdmin').filter()");
    }

    /*public String verClaveCidi(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        for (Expediente expediente : expedienteControllerBean.getItems()) {
            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {
                    if (expediente.getClaveCidi() != null) {
                        return expediente.getClaveCidi();
                    } else {
                        return "No posee clave CIDI";
                    }
                }
            }
        }
        return NO_POSEE_CLAVE_CIDI;
    }*/
    
    public String verClaveCidi(int orden) {
    FacesContext context = FacesContext.getCurrentInstance();
    ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

    return expedienteControllerBean.verClaveCidi(orden);
    }
    
    /*public String verDatosPersonalesYDelExp(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        String datosExp = null;

        for (Expediente expediente : expedienteControllerBean.getItems()) {
            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {

                    if (expediente.toString() != null) {

                        datosExp = expediente.toStringWithDatosPersonalesYDelExp();
                        return datosExp;

                    } else {
                        datosExp = "no posee datos";
                        return datosExp;

                    }
                }
            }
        }
        return datosExp;
    }*/

    public String verNroDeCuil(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        
        return expedienteControllerBean.verNroDeCuil(orden);
    }

    /*
    fecha 14/06/2024 luego borrar
    
    public String verClaveFiscal(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        for (Expediente expediente : expedienteControllerBean.getItems()) {

            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {

                    if (expediente.getClaveFiscal() != null) {
                        return expediente.getClaveFiscal();
                    } else {
                        return "No posee clave FISCAL";
                    }
                }
            }
        }
        return "no posee clave FISCAL";

    }*/

    public String verClaveFiscal(int orden) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = 
        context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        return expedienteControllerBean.verClaveFiscal(orden);
    
    }
    
    /*
    fecha 14/06/2024 luego borrar
    public String verClaveDeSeguridadSocial(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = 
        context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        for (Expediente expediente : expedienteControllerBean.getItems()) {

            if (expediente.getOrden() != null) {
                if (Integer.compare(expediente.getOrden(), orden) == 0) {

                    if (expediente.getClaveSeguridadSocial() != null) {
                        return expediente.getClaveSeguridadSocial();
                    } else {
                        return "No posee clave de Seguridad Social";

                    }
                }
            }
        }
        return "no posee clave de Seguridad Social";
    }*/

    public String verClaveDeSeguridadSocial(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedienteControllerBean = 
        context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);
        
        return expedienteControllerBean.verClaveDeSeguridadSocial(orden);
    }
    
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {

        Document pdf = (Document) document;

        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String logo = servletContext.getRealPath("") + File.separator + "resources/images"
                + File.separator + "cutmypic.png";

        pdf.add(Image.getInstance(logo));

    }

    /*public void filtrarPorFecha(Date fechaParaFiltrar) {
        this.filteredAgendas = new ArrayList<Agenda>();

        SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
        String date = sdf.format(fechaParaFiltrar);

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaControllerBean = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        if (fechaParaFiltrar != null) {
            for (Agenda agenda : agendaControllerBean.getItems()) {
                if (agenda.getFecha() != null) {
                    String date2 = sdf.format(agenda.getFecha());
                    if (date.equals(date2)) {
                        agendaControllerBean.getFilteredAgendas().add(agenda);
                    }
                }
            }
        }
    }*/

    public void clearAllFilters() {

        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":AgendaListForm:datalist");
        if (!dataTable.getFilters().isEmpty()) {
            dataTable.reset();

            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":AgendaListForm:datalist");
        }
        
        dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":AgendaListForm:datalistAgenda");
        if (!dataTable.getFilters().isEmpty()) {
            dataTable.reset();

            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":AgendaListForm:datalistAgenda");
        }
        
    }

    public void transferir() {

        FacesContext context = FacesContext.getCurrentInstance();

        AgendaController agendaController = 
        context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        
        ExpedienteController expedienteController = 
        context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        Integer idExpediente;

        if (agendaController.getSelected().getApellido() != null) {
            idExpediente = Integer.parseInt(agendaController.getSelected().getApellido());
            agendaController.getSelected().setNombre(expedienteController.getExpediente(idExpediente).getNombre());
            agendaController.getSelected().setOrden(expedienteController.getExpediente(idExpediente).getOrden());
            agendaController.getSelected().setApellido(expedienteController.getExpediente(idExpediente).getApellido());
        }

    }
    
    public void toggleSelectionMode() {
        if (selectionMode.equals("single")) {
           selectionMode = "multiple";
           selectedItems = new ArrayList<>(); 
       } else {
           selectionMode = "single";
       }
    }
    
}
