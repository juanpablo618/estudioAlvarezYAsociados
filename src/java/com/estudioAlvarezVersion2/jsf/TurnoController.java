package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.Login.SessionUtils;
import com.estudioAlvarezVersion2.jpa.Turno;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.TurnoFacade;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("turnoController")
@SessionScoped
public class TurnoController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.TurnoFacade ejbFacade;
    private List<Turno> items = null;
    private Turno selected;

    private static final String DD_MM_YYYY = "dd/MM/yyyy";
    private static final String LA_FECHA_SELECCIONADA_NO_ES_VALIDA = "la fecha selecionada no es válida";
    private static final String POR_SER_FERIADO = " por ser feriado";

    private String responsableSeleccionadoEnTurno;
    private String realizadoSeleccionadoEnTurno;
    private String apellidoYNombreSeleccionadoEnTurno;
    private String fechaSeleccionadaEnTurno;
    private String ordenSeleccionadoEnTurno;
    private String fechaParaFiltrar;

    private List<Turno> filteredturnos;
    private List<Turno> filteredTurnosConSesion;
    
    private Date dateSelected;

    private String nombreResponsableSelected;

    public String getNombreResponsableSelected() {
        return nombreResponsableSelected;
    }

    public void setNombreResponsableSelected(String nombreResponsableSelected) {
        this.nombreResponsableSelected = nombreResponsableSelected;
    }

    public String getRealizadoSeleccionadoEnTurno() {
        return realizadoSeleccionadoEnTurno;
    }

    public void setRealizadoSeleccionadoEnTurno(String realizadoSeleccionadoEnTurno) {
        this.realizadoSeleccionadoEnTurno = realizadoSeleccionadoEnTurno;
    }

    public Date getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(Date dateSelected) {
        this.dateSelected = dateSelected;
    }

    public TurnoController() {
    }

    public Turno getSelected() {
        return selected;
    }

    public void setSelected(Turno selected) {
        this.selected = selected;
    }

    public String getFechaParaFiltrar() {
        return fechaParaFiltrar;
    }

    public void setFechaParaFiltrar(String fechaParaFiltrarNueva) {
        this.fechaParaFiltrar = fechaParaFiltrarNueva;
    }

    public List<Turno> getFilteredTurnosConSesion() {
        return filteredTurnosConSesion;
    }

    public void setFilteredTurnosConSesion(List<Turno> filteredTurnosConSesion) {
        this.filteredTurnosConSesion = filteredTurnosConSesion;
    }

    public List<Turno> getFilteredturnos() {
        List<Turno> cloned_list = null;

        if (this.filteredturnos != null) {
            cloned_list = new ArrayList<Turno>(this.filteredturnos);
            Collections.sort(cloned_list, new SortByDate());
            //Collections.sort(cloned_list, (Turno o1, Turno o2) -> o1.getHoraYDia().compareTo(o2.getHoraYDia()));

        }

        return cloned_list;
    }

    static class SortByDate implements Comparator<Turno> {

        @Override
        public int compare(Turno a, Turno b) {
            /*if(a.getHoraYDia() == null && b.getHoraYDia() == null){
                return 0;
            }else if(a.getHoraYDia() != null && b.getHoraYDia() != null){
                        if(a.getHoraYDia().compareTo(b.getHoraYDia()) >= 0){
                            return 1;
                        }
            }else{
                return -1;
            }
            return 0;*/

            if (a.getHoraYDia() == null) {
                return 1;
            }

            if (b.getHoraYDia() == null) {
                return -1;
            }

            if (a.getHoraYDia() == null && b.getHoraYDia() == null) {
                return 0;
            }

            if (a.getHoraYDia().equals(b.getHoraYDia())) {
                return a.getHoraYDia().compareTo(b.getHoraYDia());
            }

            return a.getHoraYDia().compareTo(b.getHoraYDia());

        }
    }

    public void setFilteredturnos(List<Turno> filteredturnos) {
        this.filteredturnos = filteredturnos;
    }

    public String getResponsableSeleccionadoEnTurno() {
        return responsableSeleccionadoEnTurno;
    }

    public void setResponsableSeleccionadoEnTurno(String responsableSeleccionadoEnTurno) {
        this.responsableSeleccionadoEnTurno = responsableSeleccionadoEnTurno;
    }

    public String getApellidoYNombreSeleccionadoEnTurno() {
        return apellidoYNombreSeleccionadoEnTurno;
    }

    public void setApellidoYNombreSeleccionadoEnTurno(String apellidoYNombreSeleccionadoEnTurno) {
        this.apellidoYNombreSeleccionadoEnTurno = apellidoYNombreSeleccionadoEnTurno;
    }

    public String getFechaSeleccionadaEnTurno() {
        return fechaSeleccionadaEnTurno;
    }

    public void setFechaSeleccionadaEnTurno(String fechaSeleccionadaEnTurno) {
        this.fechaSeleccionadaEnTurno = fechaSeleccionadaEnTurno;
    }

    public String getOrdenSeleccionadoEnTurno() {
        return ordenSeleccionadoEnTurno;
    }

    public void setOrdenSeleccionadoEnTurno(String ordenSeleccionadoEnTurno) {
        this.ordenSeleccionadoEnTurno = ordenSeleccionadoEnTurno;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TurnoFacade getFacade() {
        return ejbFacade;
    }

    public String getUserName() {
        HttpSession session = SessionUtils.getSession();
        return (String) session.getAttribute("username");
    }

    private Boolean validateHolidays(String date) {
        //lista sacada de https://www.argentina.gob.ar/interior/feriados-nacionales-2023
        String feriadosArg[] = {"20/02/2023", "21/02/2023", "24/03/2023", "02/04/2023", "07/04/2023",
            "01/05/2023", "25/05/2023", "20/06/2023", "09/07/2023", "08/12/2023", "25/12/2023", "17/06/2023", "21/08/2023", "16/10/2023", "22/11/2023"};

        return Arrays.asList(feriadosArg).contains(date);
    }

    public Turno prepareCreate() {
        selected = new Turno();
        selected.setRealizado("No");
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
        String date = sdf.format(selected.getHoraYDia());

        if (validateHolidays(date)) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
            items = null;
        } else {

            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TurnoCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void createConNombreYApellido(String nombre, String apellido, String telefono) {

        selected.setNombre(nombre);
        selected.setApellido(apellido);
        selected.setNroDeTelefono(telefono);

        SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
        String date = sdf.format(selected.getHoraYDia());

        if (validateHolidays(date)) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
            items = null;
        } else {

            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TurnoCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        }
    }

    public void update() {
        SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
        String date = sdf.format(selected.getHoraYDia());

        if (validateHolidays(date)) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, LA_FECHA_SELECCIONADA_NO_ES_VALIDA, POR_SER_FERIADO);
            FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
            items = null;
        } else {

            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TurnoUpdated"));
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TurnoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Turno> getItems() {

        if (items == null) {
            items = getFacade().findAll();
        }
        List<Turno> cloned_list;

        cloned_list = new ArrayList<>(this.items);
        Collections.sort(cloned_list, new SortByDate());
        //Collections.sort(cloned_list, (Turno o1, Turno o2) -> o1.getHoraYDia().compareTo(o2.getHoraYDia()));

        return cloned_list;

    }
    
    public void cambiarFiltroIzquierda(String userNombreCompleto){
         System.out.println("Cambiar filtro derecha");

    // Obtener el valor actual de fechaParaFiltrar
    String fechaActual = getFechaParaFiltrar();

    // Concatenar "+1" al valor actual
    String nuevoValor = fechaActual + "-11";

    // Actualizar fechaParaFiltrar con el nuevo valor
    setFechaParaFiltrar(nuevoValor);

    System.out.println(getFechaParaFiltrar());
    }
    
    public void cambiarFiltroDerecha(String userNombreCompleto) throws ParseException {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    Date fecha = formato.parse(fechaParaFiltrar);

    if (fecha != null) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        fecha = calendar.getTime();
    }

    String fechaString = formato.format(fecha);
    this.fechaParaFiltrar = fechaString;

    System.out.println("fecha: " + fechaParaFiltrar);

    this.getItemsBySessionUser(userNombreCompleto, fechaParaFiltrar);
}

    public List<Turno> getItemsBySessionUser(String userNombreCompleto, String date) {
        if (items == null) {
            items = getFacade().findAll();
        }
        List<Turno> cloned_list;
        setFechaParaFiltrar(date);
        
        cloned_list = new ArrayList<>(this.items);
        Collections.sort(cloned_list, new SortByDate());

        List<Turno> resultados = new ArrayList<>();

        for (Turno turno : cloned_list) {
            String nombreCompletoResponsable = turno.getResponsable();

            if (nombreCompletoResponsable.equals(userNombreCompleto) && turno.getDiaMesAnio().equals(date)) {
                resultados.add(turno);
            }
        }
            
        return resultados;

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

    public Turno getTurno(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Turno> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Turno> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Turno.class)
    public static class TurnoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TurnoController controller = (TurnoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "turnoController");
            return controller.getTurno(getKey(value));
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
            if (object instanceof Turno) {
                Turno o = (Turno) object;
                return getStringKey(o.getIdTurno());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Turno.class.getName()});
                return null;
            }
        }

    }

    public void handleDateSelect(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('turnosTable').filter()");
    }

    public void filtrarAgendasYTurnos(Date dateSelected) {
        this.filteredturnos = new ArrayList<Turno>();

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        agendaController.filtrarPorFecha(dateSelected);

        if (dateSelected != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
            String date = sdf.format(dateSelected);

            for (Turno turno : getItems()) {
                SimpleDateFormat sdf2 = new SimpleDateFormat(DD_MM_YYYY);
                String date2 = sdf2.format(turno.getHoraYDia());

                if (date2.equals(date)) {
                    filteredturnos.add(turno);
                }
            }
        }
        this.dateSelected = null;
    }

}
