package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Expediente;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.ExpedienteFacade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import static java.util.Objects.nonNull;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
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

    public Expediente prepareCreate() {
        selected = new Expediente();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {

        ingresarEdad();

        ingresarDni();

        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void ingresarEdad() {
        Calendar fecha = new GregorianCalendar();
        int añoActual = fecha.get(Calendar.YEAR);

        int añoDeNacimiento = selected.getFechaDeNacimiento().getYear();

        añoDeNacimiento = añoDeNacimiento + 1900;

        int edad = 0;

        edad = añoActual - añoDeNacimiento;

        selected.setEdad(edad);

    }

    public void ingresarDni() {
        String cuit = selected.getCuit();
        cuit = cuit.substring(2, 9);

        selected.setDni(cuit);

    }

    public void update() {

        Calendar fecha = new GregorianCalendar();
        int añoActual = fecha.get(Calendar.YEAR);

        int añoDeNacimiento = selected.getFechaDeNacimiento().getYear();

        añoDeNacimiento = añoDeNacimiento + 1900;

        int edad = 0;

        edad = añoActual - añoDeNacimiento;

        selected.setEdad(edad);

        String cuit = selected.getCuit();
        cuit = cuit.substring(2, 9);
        selected.setDni(cuit);

        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteUpdated"));
    }

    public void update2() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ExpedienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void calcularEdades() {
        Calendar fecha = new GregorianCalendar();
        int añoActual = fecha.get(Calendar.YEAR);

        System.out.println("entro al calcular edades");

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

    public void redirect() throws Exception {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("http://stackoverflow.com");
    }

    public void handleDateSelect(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('expedientesTable').filter()");
    }

    public String getClaveCidi(int orden) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExpedienteController expedientetrollerBean = context.getApplication().evaluateExpressionGet(context, "#{expedienteController}", ExpedienteController.class);

        System.out.println("PASO POR CIDI" + orden);

        String claveCidi = null;

        for (Expediente i : expedientetrollerBean.getItems()) {
            System.out.println(i.getOrden());

            if (i.getOrden() == orden) {
                System.out.println(i.getOrden());
                claveCidi = i.getClaveCidi();
                return claveCidi;
            } else {
                System.out.println("no macheo nada");
                claveCidi = "no se encontro";
            }
        }

        return claveCidi;
    }

    public String getClaveFiscal(int orden) {
        System.out.println("PASO POR FISCAL");

        String claveFiscal = null;

        for (Expediente buscado : items) {
            if (buscado.getOrden() == orden) {
                claveFiscal = buscado.getClaveFiscal();
            }
        }
        return claveFiscal;
    }

    public String getClaveDeSeguridadSocial(int orden) {
        System.out.println("PASO POR SEguridad Social");

        String claveDeSeguridadSocial = null;

        for (Expediente buscado : items) {
            if (buscado.getOrden() == orden) {
                claveDeSeguridadSocial = buscado.getClaveSeguridadSocial();
            }
        }
        return claveDeSeguridadSocial;

    }

}
