
package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Consulta;
import com.estudioAlvarezVersion2.jpacontroller.ConsultaFacade;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private List<Consulta> itemsExceptArchivadoODesistido = null;
    
    private Consulta selected;
    private List<Consulta> filteredConsultas;

    //para filtros en tablas
    private String estadoSeleccionadoEnTabla;
    private String equipoSeleccionadoEnTabla;
    private String equipoSeleccionadoEnTablaDesdeViewEmpleados;
    
    
    private String fechaSeleccionadaEnConsultaTable;
    
     private Date filterDay;
    private String filterMonth;
    private String filterYear;
    private List<String> months;
    private List<String> years;
    
    private boolean cuitAlreadyExists;
    
    @PostConstruct
    public void init() {
        // Inicializar meses y años
        months = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
       // Inicializar años dinámicamente
        years = generateYears(10); // Generar los próximos 10 años
    }

    public ConsultaController() {
    }

    public String getEquipoSeleccionadoEnTablaDesdeViewEmpleados() {
        return equipoSeleccionadoEnTablaDesdeViewEmpleados;
    }

    public void setEquipoSeleccionadoEnTablaDesdeViewEmpleados(String equipoSeleccionadoEnTablaDesdeViewEmpleados) {
        this.equipoSeleccionadoEnTablaDesdeViewEmpleados = equipoSeleccionadoEnTablaDesdeViewEmpleados;
    }

    
    
    public void filterConsultas() {
        // Lógica para filtrar las consultas basadas en filterDay, filterMonth, filterYear
        filteredConsultas = getFacade().findAll(); // Reemplaza con la lógica de filtrado
        if (filterDay != null) {
            filteredConsultas = filteredConsultas.stream()
                    .filter(consulta -> consulta.getFechaDeAtencion().equals(filterDay))
                    .collect(Collectors.toList());
        }
        if (filterMonth != null && !filterMonth.isEmpty()) {
            filteredConsultas = filteredConsultas.stream()
                    .filter(consulta -> {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(consulta.getFechaDeAtencion());
                        return String.format("%02d", cal.get(Calendar.MONTH) + 1).equals(filterMonth);
                    })
                    .collect(Collectors.toList());
        }
        if (filterYear != null && !filterYear.isEmpty()) {
            filteredConsultas = filteredConsultas.stream()
                    .filter(consulta -> {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(consulta.getFechaDeAtencion());
                        return String.valueOf(cal.get(Calendar.YEAR)).equals(filterYear);
                    })
                    .collect(Collectors.toList());
        }
    }

    // Getters y Setters

    
    
    public Date getFilterDay() {
        return filterDay;
    }

    public void setFilterDay(Date filterDay) {
        this.filterDay = filterDay;
    }

    public String getFilterMonth() {
        return filterMonth;
    }

    public void setFilterMonth(String filterMonth) {
        this.filterMonth = filterMonth;
    }

    public String getFilterYear() {
        return filterYear;
    }

    public void setFilterYear(String filterYear) {
        this.filterYear = filterYear;
    }

    public List<String> getMonths() {
        return months;
    }

    public List<String> getYears() {
        return years;
    }

    public List<Consulta> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<Consulta> getItemsExceptArchivadoODesistido() {
        if (items == null) {
            items = getFacade().findAllExceptArchivedOrDismissed();
        }
        return items;

    }

    public void loadFilteredConsultas() {
        filteredConsultas = getFacade().findAllExceptArchivedOrDismissed();
    }

    public void loadAllConsultas() {
        filteredConsultas = getFacade().findAll();
    }


    public String getFechaSeleccionadaEnConsultaTable() {
        return fechaSeleccionadaEnConsultaTable;
    }

    public void setFechaSeleccionadaEnConsultaTable(String fechaSeleccionadaEnConsultaTable) {
        this.fechaSeleccionadaEnConsultaTable = fechaSeleccionadaEnConsultaTable;
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
        selected.setFechaDeAtencion(new Date());
        return selected;
    }

    public String getEstadoSeleccionadoEnTabla() {
        return estadoSeleccionadoEnTabla;
    }

    public void setEstadoSeleccionadoEnTabla(String estadoSeleccionadoEnTabla) {
        this.estadoSeleccionadoEnTabla = estadoSeleccionadoEnTabla;
    }
    
    protected void initializeEmbeddableKey() {
    }

    public String getEquipoSeleccionadoEnTabla() {
        return equipoSeleccionadoEnTabla;
    }

    public void setEquipoSeleccionadoEnTabla(String equipoSeleccionadoEnTabla) {
        this.equipoSeleccionadoEnTabla = equipoSeleccionadoEnTabla;
    }
    
    public void create() {
        if (selected.getNombre() != null) {
            selected.setNombre(selected.getNombre().toUpperCase());
        }

        if (selected.getApellido() != null) {
            selected.setApellido(selected.getApellido().toUpperCase());
        }

        String successMessage = "Consulta creada exitosamente";

        if (!selected.getCuit().isEmpty()) {
            if (isCuitAlreadyRegistered(selected.getCuit())) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Este CUIT ya existe en otra consulta.", "");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            }

            if (isPhoneAlreadyRegistered(selected.getTelefono())) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Este teléfono ya existe en otra consulta.", "");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            }

            persist(JsfUtil.PersistAction.CREATE, successMessage);

            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        } else {
            if (isPhoneAlreadyRegistered(selected.getTelefono())) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Este teléfono ya existe en otra consulta.", "");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            }

            persist(JsfUtil.PersistAction.CREATE, successMessage);

            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
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

     private List<String> generateYears(int numberOfYears) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> yearsList = new ArrayList<>();
        for (int i = 0; i < numberOfYears; i++) {
            yearsList.add(String.valueOf(currentYear + i));
        }
        return yearsList;
    }

     public boolean isCuitAlreadyRegistered(String cuit) {
        // Lógica para verificar si el CUIT ya está registrado
        return getFacade().isCuitExisting(cuit); // Asumiendo que tienes un servicio para esto
    
     
     }
     
     public boolean isPhoneAlreadyRegistered(String telefono) {
        // Lógica para verificar si el CUIT ya está registrado
        return getFacade().isPhoneExisting(telefono); // Asumiendo que tienes un servicio para esto
    
     
     }
     
     public boolean filterInsensitive(Object value, Object filter, Locale locale) {
    if (filter == null || filter.toString().trim().isEmpty()) {
        return true;
    }

    String filterText = filter.toString().toLowerCase();
    String fieldValue = value.toString().toLowerCase();

    return fieldValue.contains(filterText);
}

     
    
}
