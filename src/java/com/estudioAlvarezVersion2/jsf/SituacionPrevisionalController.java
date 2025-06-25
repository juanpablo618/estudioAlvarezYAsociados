package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.SituacionPrevisional;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.SituacionPrevisionalFacade;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;


@Named("situacionPrevisionalController")
@SessionScoped
public class SituacionPrevisionalController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.SituacionPrevisionalFacade ejbFacade;
    private List<SituacionPrevisional> items = null;
    private SituacionPrevisional selected;
    private SituacionPrevisional selectedNuevo;

     private UploadedFile archivoCsv;

     private String totalTiempoConAportes;
     
     private String tiempoInactivoHasta1993;
    private String tiempoInactivoDesde1993;

    private int totalDiasConMoratoria24476 = 0; // valor acumulado (se puede mostrar luego)


public String getTotalTiempoConAportes() {
    return totalTiempoConAportes;
}

public void calcularTotalTiempoConAportes(int orden) {
    List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);

    int totalDias = 0;
    int totalMeses = 0;
    int totalAnios = 0;

    for (SituacionPrevisional s : lista) {
        if (s.getEmpleador() != null && !s.getEmpleador().trim().isEmpty()) {
            totalDias += s.getDias();
            totalMeses += s.getMeses();
            totalAnios += s.getAnios();
        }
    }

    totalMeses += totalDias / 30;
    totalDias = totalDias % 30;

    totalAnios += totalMeses / 12;
    totalMeses = totalMeses % 12;

    totalTiempoConAportes = totalAnios + " años, " + totalMeses + " meses, " + totalDias + " días";
}

    public SituacionPrevisionalController() {
    }

    public SituacionPrevisional getSelected() {
        return selected;
    }

    public void setSelected(SituacionPrevisional selected) {
        this.selected = selected;
    }

    public SituacionPrevisional getSelectedNuevo() {
        return selectedNuevo;
    }

    public void setSelectedNuevo(SituacionPrevisional selectedNuevo) {
        this.selectedNuevo = selectedNuevo;
    }
    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SituacionPrevisionalFacade getFacade() {
        return ejbFacade;
    }

    public String getTiempoInactivoHasta1993() {
        return tiempoInactivoHasta1993;
    }

    public String getTiempoInactivoDesde1993() {
        return tiempoInactivoDesde1993;
    }
    
    public SituacionPrevisional prepareCreate() {
        selected = new SituacionPrevisional();
        initializeEmbeddableKey();
        return selected;
    }
    
     public SituacionPrevisional prepareCreateSituacionPrevisional(int orden) {
        selectedNuevo = new SituacionPrevisional();
        selectedNuevo.setOrden(orden);
        initializeEmbeddableKey();
        return selectedNuevo;
    }

    public void create() {
        persist(PersistAction.CREATE, "situaciòn previsional creada");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SituacionPrevisionalUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Situaciòn Previsional eliminada exitosamente");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SituacionPrevisional> getItems() {
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

    public SituacionPrevisional getSituacionPrevisional(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<SituacionPrevisional> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SituacionPrevisional> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SituacionPrevisional.class)
    public static class SituacionPrevisionalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SituacionPrevisionalController controller = (SituacionPrevisionalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "situacionPrevisionalController");
            return controller.getSituacionPrevisional(getKey(value));
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
            if (object instanceof SituacionPrevisional) {
                SituacionPrevisional o = (SituacionPrevisional) object;
                return getStringKey(o.getIdSituacionPrevisional());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SituacionPrevisional.class.getName()});
                return null;
            }
        }

    }
    
    public List verSituacionPrevisionalesPorNroDeOrden(Integer orden) {
        
        List<SituacionPrevisional> situacionesPrevisionales;
        situacionesPrevisionales = new ArrayList<>();

        FacesContext context = FacesContext.getCurrentInstance();
        SituacionPrevisionalController situacionPrevisionalControllerBean = context.getApplication().evaluateExpressionGet(context, "#{situacionPrevisionalController}", SituacionPrevisionalController.class);
            
        situacionPrevisionalControllerBean.getItems().stream().filter(SituacionPrevisional ->
                (orden != null)).filter(SituacionPrevisional ->
                        (SituacionPrevisional.getOrden() != null)).filter(SituacionPrevisional ->
                                (Objects.equals(SituacionPrevisional.getOrden(), orden))).forEachOrdered(SituacionPrevisional ->
                                {
            situacionesPrevisionales.add(SituacionPrevisional);
        });
        
         // Ordenar la lista por fechaInicio
            situacionesPrevisionales.sort(Comparator.comparing(s -> s.getFechaInicio()));

        
        return situacionesPrevisionales;
    }
    
    public UploadedFile getArchivoCsv() {
        return archivoCsv;
    }

    public void setArchivoCsv(UploadedFile archivoCsv) {
        this.archivoCsv = archivoCsv;
    }

    public void createSituacionPrevisional(Date fechaInicio, Date fechaFin, String observaciones, int orden) {
        
        selectedNuevo.setFechaInicio(fechaInicio);
                selectedNuevo.setFechaFin(fechaFin);
        selectedNuevo.setObservaciones(observaciones);
        selectedNuevo.setOrden(orden);
        
        if (fechaInicio != null && fechaFin != null) {
        // Convertir Date a LocalDate
        LocalDate inicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Period periodo = Period.between(inicio, fin);

        selectedNuevo.setAnios(periodo.getYears());
        selectedNuevo.setMeses(periodo.getMonths());
        selectedNuevo.setDias(periodo.getDays());
        
        } else {
            selectedNuevo.setAnios(0);
            selectedNuevo.setMeses(0);
        }
        
        
        persistParaSituacionPrevisionalNuevo(PersistAction.CREATE, "Situacion previsional creada exitosamente para el nro de orden:"+ orden);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    private void persistParaSituacionPrevisionalNuevo(PersistAction persistAction, String successMessage) {
        if (selectedNuevo != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selectedNuevo);
                } else {
                    getFacade().remove(selectedNuevo);
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
    
    public void crearPeriodosInactivos(int orden, String sexo, Date fechaNacimientoDate) {

        List<SituacionPrevisional> situaciones = verSituacionPrevisionalesPorNroDeOrden(orden);

        // Convertir la fecha de nacimiento a LocalDate
        LocalDate fechaNacimiento = fechaNacimientoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaInicioInactividad = fechaNacimiento.plusYears(18);
        LocalDate fechaJubilacion = sexo.equalsIgnoreCase("masculino") 
            ? fechaNacimiento.plusYears(65) 
            : fechaNacimiento.plusYears(60);

            // Ordenar la lista por fechaInicio
            situaciones.sort(Comparator.comparing(s -> s.getFechaInicio()));

        // Convertir fechas de cada SituacionPrevisional a LocalDate
        List<SituacionPrevisional> situacionesOrdenadas = new ArrayList<>(situaciones);

        // 1. Inactividad desde los 18 hasta el primer trabajo
        if (!situacionesOrdenadas.isEmpty()) {
            LocalDate primerInicio = toLocalDate(situacionesOrdenadas.get(0).getFechaInicio());
            if (fechaInicioInactividad.isBefore(primerInicio)) {
                insertarInactividad(orden, fechaInicioInactividad, primerInicio.minusDays(1));
            }
        } else {
            // No hay situaciones activas, inactividad completa hasta la jubilación
            insertarInactividad(orden, fechaInicioInactividad, fechaJubilacion);
            return;
        }

        // 2. Inactividad entre trabajos
        for (int i = 0; i < situacionesOrdenadas.size() - 1; i++) {
            LocalDate finActual = toLocalDate(situacionesOrdenadas.get(i).getFechaFin());
            LocalDate inicioSiguiente = toLocalDate(situacionesOrdenadas.get(i + 1).getFechaInicio());
            if (finActual.plusDays(1).isBefore(inicioSiguiente)) {
                insertarInactividad(orden, finActual.plusDays(1), inicioSiguiente.minusDays(1));
            }
        }

        // 3. Inactividad desde el último trabajo hasta la jubilación
        LocalDate finUltimo = toLocalDate(situacionesOrdenadas.get(situacionesOrdenadas.size() - 1).getFechaFin());
        if (finUltimo.isBefore(fechaJubilacion)) {
            insertarInactividad(orden, finUltimo.plusDays(1), fechaJubilacion);
        }
    
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    /*
            VERSIÓN 1 QUE SI FUNCIONA
    private void insertarInactividad(int orden, LocalDate desde, LocalDate hasta) {
        if (desde.isAfter(hasta)) return; // Evitar períodos inválidos

        Period periodo = Period.between(desde, hasta.plusDays(1)); // incluir el día final

        SituacionPrevisional nueva = prepareCreate();
        nueva.setEmpleador(""); // vacío
        nueva.setObservaciones("tiempo inactivo");
        nueva.setAnios(periodo.getYears());
        nueva.setMeses(periodo.getMonths());
        nueva.setDias(periodo.getDays());
        nueva.setFechaInicio(Date.from(desde.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        nueva.setFechaFin(Date.from(hasta.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        nueva.setOrden(orden); // si hay un campo para relacionar con el orden

        create(); // insertar en la base de datos
    }*/
    
    private void insertarInactividad(int orden, LocalDate desde, LocalDate hasta) {
        if (desde.isAfter(hasta)) return; // Evitar períodos inválidos

        Period periodo = Period.between(desde, hasta.plusDays(1)); // incluir el día final

        SituacionPrevisional nueva = prepareCreate();
        nueva.setEmpleador(""); // vacío

        // Lógica para las observaciones de moratoria
        LocalDate fecha1 = LocalDate.of(1993, 9, 30);
        LocalDate fecha2 = LocalDate.of(1993, 10, 1);
        LocalDate fecha3 = LocalDate.of(2012, 3, 31);
        LocalDate fecha4 = LocalDate.of(2012, 4, 1);

        String observacion;
        if (!hasta.isAfter(fecha1)) {
            observacion = "tiempo inactivo (moratoria 24476 o 27705)";
        } else if (!hasta.isBefore(fecha2) && !hasta.isAfter(fecha3)) {
            observacion = "tiempo inactivo (moratoria 27705)";
        } else if (!hasta.isBefore(fecha4)) {
            observacion = "tiempo inactivo (moratoria 27705)";
        } else {
            observacion = "tiempo inactivo";
        }

        nueva.setObservaciones(observacion);
        nueva.setAnios(periodo.getYears());
        nueva.setMeses(periodo.getMonths());
        nueva.setDias(periodo.getDays());
        nueva.setFechaInicio(Date.from(desde.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        nueva.setFechaFin(Date.from(hasta.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        nueva.setOrden(orden); // si hay un campo para relacionar con el orden

        create(); // insertar en la base de datos
    }


    public String getTotalTiempoConAportes(int orden) {
    List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);
    
    int totalDias = 0;
    int totalMeses = 0;
    int totalAnios = 0;

    for (SituacionPrevisional s : lista) {
        if (s.getEmpleador() != null && !s.getEmpleador().trim().isEmpty()) {
            totalDias += s.getDias();
            totalMeses += s.getMeses();
            totalAnios += s.getAnios();
        }
    }

    // Normalizamos días y meses
    totalMeses += totalDias / 30;
    totalDias = totalDias % 30;

    totalAnios += totalMeses / 12;
    totalMeses = totalMeses % 12;

    return totalAnios + " años, " + totalMeses + " meses, " + totalDias + " días";
}

    public void calcularTiempoInactivoHasta1993(int orden) {
        calcularTiempoInactivo(orden, LocalDate.MIN, LocalDate.of(1993, 9, 30), true);
    }

    public void calcularTiempoInactivoDesde1993(int orden) {
        calcularTiempoInactivo(orden, LocalDate.of(1993, 10, 1), LocalDate.of(2012, 3, 31), false);
    }

    private void calcularTiempoInactivo(int orden, LocalDate desde, LocalDate hasta, boolean esAnterior) {
    List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);
    int totalDias = 0;
    int totalMeses = 0;
    int totalAnios = 0;

    for (SituacionPrevisional s : lista) {
        if ((s.getEmpleador() == null || s.getEmpleador().trim().isEmpty()) &&
            s.getFechaInicio() != null && s.getFechaFin() != null) {

            LocalDate inicio = toLocalDate(s.getFechaInicio());
            LocalDate fin = toLocalDate(s.getFechaFin());

            if (fin.isBefore(desde) || inicio.isAfter(hasta)) {
                continue;
            }

            LocalDate inicioRecortado = inicio.isBefore(desde) ? desde : inicio;
            LocalDate finRecortado = fin.isAfter(hasta) ? hasta : fin;

            Period periodo = Period.between(inicioRecortado, finRecortado.plusDays(1));
            totalDias += periodo.getDays();
            totalMeses += periodo.getMonths();
            totalAnios += periodo.getYears();
        }
    }

    totalMeses += totalDias / 30;
    totalDias %= 30;
    totalAnios += totalMeses / 12;
    totalMeses %= 12;

    String resultado = totalAnios + " años, " + totalMeses + " meses, " + totalDias + " días";
    if (esAnterior) {
        tiempoInactivoHasta1993 = resultado;
    } else {
        tiempoInactivoDesde1993 = resultado;
    }
}
    
    
    private static final int TOTAL_MESES_OBJETIVO = 360; // 30 años * 12

            private int calcularMesesConAportes(List<SituacionPrevisional> lista) {
                int totalMeses = 0;
                for (SituacionPrevisional s : lista) {
                    if (tieneAportes(s)) {
                        Period p = Period.between(toLocalDate(s.getFechaInicio()), toLocalDate(s.getFechaFin()).plusDays(1));
                        totalMeses += p.getYears() * 12 + p.getMonths();
                    }
                }
                return totalMeses;
            }

        private boolean tieneAportes(SituacionPrevisional s) {
            return s.getEmpleador() != null && !s.getEmpleador().trim().isEmpty();
        }


        public void completarMoratoria24476(int orden) {
            List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);

            int totalDiasAportes = 0;

            // 1. Calcular días con aportes reales
            for (SituacionPrevisional s : lista) {
                if (tieneAportes(s)) {
                    totalDiasAportes += calcularDiasPeriodo(s);
                }
            }

            int diasMaximos = 10950; // 30 años
            int diasDisponibles = diasMaximos - totalDiasAportes;
            if (diasDisponibles <= 0) {
                this.totalDiasConMoratoria24476 = totalDiasAportes;
                return;
            }

            // 2. Obtener períodos inactivos hasta 30/09/1993 sin observaciones
            List<SituacionPrevisional> inactivosElegibles = lista.stream()
                .filter(s -> !tieneAportes(s))
                .filter(s -> s.getObservaciones() == null || s.getObservaciones().isEmpty())
                .filter(s -> toLocalDate(s.getFechaInicio()).isBefore(LocalDate.of(1993, 10, 1)))
                .sorted(Comparator.comparing(SituacionPrevisional::getFechaInicio))
                .collect(Collectors.toList());

            int totalDiasMoratoria = 0;

            for (SituacionPrevisional s : inactivosElegibles) {
                if (diasDisponibles <= 0) break;

                int diasPeriodo = calcularDiasPeriodo(s);

                if (diasPeriodo > diasDisponibles) {
                    // Recortar el período
                    LocalDate nuevaFechaFin = toLocalDate(s.getFechaInicio()).plusDays(diasDisponibles - 1);
                    s.setFechaFin(toDate(nuevaFechaFin));
                    diasPeriodo = diasDisponibles;
                }

                s.setObservaciones("MORATORIA 24476");
                totalDiasMoratoria += diasPeriodo;
                diasDisponibles -= diasPeriodo;
            }

            this.totalDiasConMoratoria24476 = totalDiasAportes + totalDiasMoratoria;

            guardarLista(lista);
        }
        
        
        public String getTiempoTotalConMoratoria24476(int orden) {
            int dias = this.totalDiasConMoratoria24476;
            int anios = dias / 365;
            int meses = (dias % 365) / 30;
            int restoDias = (dias % 365) % 30;

            return anios + " años " + meses + " meses " + restoDias + " días";
        }
        
        private Date toDate(LocalDate localDate) {
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        private int calcularDiasPeriodo(SituacionPrevisional s) {
            LocalDate inicio = toLocalDate(s.getFechaInicio());
            LocalDate fin = toLocalDate(s.getFechaFin());
            return (int) ChronoUnit.DAYS.between(inicio, fin) + 1;
        }
        
        public void completarMoratoria27705(int orden) {
            List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);
            int mesesActuales = calcularMesesConAportes(lista) + contarMesesConMoratoria(lista, "MORATORIA 24476");
            int faltantes = TOTAL_MESES_OBJETIVO - mesesActuales;
            if (faltantes <= 0) return;

            List<SituacionPrevisional> inactivos = lista.stream()
                .filter(s -> !tieneAportes(s) &&
                             (s.getObservaciones()== null || s.getObservaciones().isEmpty()) &&
                             !estaBloqueado(s))
                .sorted(Comparator.comparing(SituacionPrevisional::getFechaInicio))
                .collect(Collectors.toList());

            for (SituacionPrevisional s : inactivos) {
                LocalDate inicio = toLocalDate(s.getFechaInicio());
                LocalDate fin = toLocalDate(s.getFechaFin()).plusDays(1);
                int duracionMeses = Period.between(inicio, fin).getYears() * 12 + Period.between(inicio, fin).getMonths();

                if (faltantes <= 0) break;

                s.setObservaciones("MORATORIA 27705");
                faltantes -= Math.min(faltantes, duracionMeses);
            }

            guardarLista(lista);
        }

        private int contarMesesConMoratoria(List<SituacionPrevisional> lista, String ley) {
            return lista.stream()
                .filter(s -> ley.equalsIgnoreCase(s.getObservaciones()))
                .mapToInt(s -> {
                    LocalDate i = toLocalDate(s.getFechaInicio());
                    LocalDate f = toLocalDate(s.getFechaFin()).plusDays(1);
                    return Period.between(i, f).getYears() * 12 + Period.between(i, f).getMonths();
                })
                .sum();
        }

        private boolean estaBloqueado(SituacionPrevisional s) {
        return s.getObservaciones() != null && s.getObservaciones().toUpperCase().contains("BLOQUEADO");
        }

        private void guardarLista(List<SituacionPrevisional> lista) {
            System.out.println("llego al guardarLista");
            System.out.println("llego al guardarLista size: "+ lista.size());
            
            for (SituacionPrevisional s : lista) {
                        this.selected = s; // 👉 importante: asignás el objeto actual a selected
                        
                        System.out.println("S: "+ this.selected.getObservaciones());
            
                    persist(PersistAction.UPDATE, "situación previsional editada");

            }
        }

}
