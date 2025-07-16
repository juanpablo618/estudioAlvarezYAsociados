package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.SituacionPrevisional;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.SituacionPrevisionalFacade;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
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

    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MM/yyyy");
    private static final DateTimeFormatter RANGO_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate LIMITE_24476 = LocalDate.of(1993, Month.SEPTEMBER, 30);
    private static final LocalDate INICIO_27705 = LocalDate.of(1993, Month.OCTOBER, 1);
    private static final LocalDate FIN_27705 = LocalDate.of(2012, Month.MARCH, 31);

 
    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.SituacionPrevisionalFacade ejbFacade;
    private List<SituacionPrevisional> items = null;
    private SituacionPrevisional selected;
    private SituacionPrevisional selectedNuevo;
    private UploadedFile archivoCsv;
    private String totalTiempoConAportes;
    private String resultadoMoratoriaV4;
    private String resultadoMoratoriaV5;
    
    private String tiempoInactivoHasta1993;
    private String tiempoInactivoDesde1993;

    private int totalDiasConMoratoria24476 = 0; // valor acumulado (se puede mostrar luego)

    

public String getTotalTiempoConAportes() {
    return totalTiempoConAportes;
}

    public String getResultadoMoratoriaV4() {
        return resultadoMoratoriaV4;
    }

    public void setResultadoMoratoriaV4(String resultadoMoratoriaV4) {
        this.resultadoMoratoriaV4 = resultadoMoratoriaV4;
    }

    public String getResultadoMoratoriaV5() {
        return resultadoMoratoriaV5;
    }

    public void setResultadoMoratoriaV5(String resultadoMoratoriaV5) {
        this.resultadoMoratoriaV5 = resultadoMoratoriaV5;
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

public void calcularResultadoPrevisional(int orden) {
    List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);

    int totalDias = 0;
    int totalMeses = 0;
    int totalAnios = 0;
    int yaAportados = 0;

    List<String> periodos24476 = new ArrayList<>();
    List<String> periodos27705 = new ArrayList<>();

    for (SituacionPrevisional s : lista) {
        if (s.getEmpleador() != null && !s.getEmpleador().trim().isEmpty()) {
            totalDias += s.getDias();
            totalMeses += s.getMeses();
            totalAnios += s.getAnios();
        } else if (s.getFechaInicio() != null && s.getFechaFin() != null) {
            try {
                LocalDate inicio = LocalDate.parse((CharSequence) s.getFechaInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate fin = LocalDate.parse((CharSequence) s.getFechaFin(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                List<String> meses = generarPeriodos(inicio, fin);

                for (String mes : meses) {
                    LocalDate fecha = parsePeriodo(mes);
                    if (!fecha.isAfter(LIMITE_24476)) {
                        periodos24476.add(mes);
                    } else if (!fecha.isBefore(INICIO_27705) && !fecha.isAfter(FIN_27705)) {
                        periodos27705.add(mes);
                    }
                }
            } catch (Exception e) {
                // Manejo opcional si alguna fecha no está bien formada
                e.printStackTrace();
            }
        }
    }

    // Consolidar tiempo aportado
    totalMeses += totalDias / 30;
    totalDias = totalDias % 30;

    totalAnios += totalMeses / 12;
    totalMeses = totalMeses % 12;

    yaAportados = totalAnios * 12 + totalMeses;

    // Obtener períodos de moratoria (con manejo seguro)
    List<String[]> periodosSeleccionados = limitarA360Meses(yaAportados, periodos24476, periodos27705);
    int meses24476 = (periodosSeleccionados.size() > 0) ? periodosSeleccionados.get(0).length : 0;
    int meses27705 = (periodosSeleccionados.size() > 1) ? periodosSeleccionados.get(1).length : 0;

    int totalFinalMeses = yaAportados + meses24476 + meses27705;

    // Crear resumen
    StringBuilder resumen = new StringBuilder();
    resumen.append("════════════════════════════════════════════\n");
    resumen.append("RESUMEN GENERAL\n");
    resumen.append("════════════════════════════════════════════\n");
    resumen.append("Meses con aportes registrados: ").append(yaAportados).append("\n");
    resumen.append("Meses agregados por Ley 24.476: ").append(meses24476).append("\n");
    resumen.append("Meses agregados por Ley 27.705: ").append(meses27705).append("\n");
    resumen.append("Total meses computados para jubilación: ").append(totalFinalMeses).append("\n");

    if (totalFinalMeses >= 360) {
        resumen.append("✅ Se alcanzó el máximo de 360 meses permitidos.\n");
    } else {
        int faltan = 360 - totalFinalMeses;
        resumen.append("⚠️  Faltan ").append(faltan).append(" meses para llegar a los 360.\n");
    }

    resultadoMoratoriaV4 = resumen.toString();
}


private static List<String[]> limitarA360Meses(int yaAportados, List<String> moratoria24476, List<String> moratoria27705) {
    int faltan = 360 - yaAportados;
    if (faltan <= 0) {
        return Arrays.asList(new String[0], new String[0]);
    }

    List<String> seleccion24476 = moratoria24476.stream()
        .sorted()
        .limit(Math.min(faltan, moratoria24476.size()))
        .collect(Collectors.toList());

    faltan -= seleccion24476.size();

    List<String> seleccion27705 = moratoria27705.stream()
        .sorted()
        .limit(Math.min(faltan, moratoria27705.size()))
        .collect(Collectors.toList());

    return Arrays.asList(
        seleccion24476.toArray(new String[0]),
        seleccion27705.toArray(new String[0])
    );
}


    private static List<String> generarPeriodos(LocalDate inicio, LocalDate fin) {
        
        List<String> periodos = new ArrayList<>();
        while (!inicio.isAfter(fin)) {
            periodos.add(inicio.format(OUTPUT_FORMAT));
            inicio = inicio.plusMonths(1);
        }
        return periodos;
    }

    private static LocalDate parsePeriodo(String periodo) {
        return LocalDate.parse("01/" + periodo, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
        LocalDate inicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Agregamos 1 día para incluir el último día
        fin = fin.plusDays(1);

        // Obtenemos el período exacto
        Period periodo = Period.between(inicio, fin);

        selectedNuevo.setAnios(periodo.getYears());
        selectedNuevo.setMeses(periodo.getMonths());
        selectedNuevo.setDias(periodo.getDays());
    } else {
        selectedNuevo.setAnios(0);
        selectedNuevo.setMeses(0);
        selectedNuevo.setDias(0);
    }

    persistParaSituacionPrevisionalNuevo(PersistAction.CREATE, "Situación previsional creada exitosamente para el nro de orden: " + orden);
    
    if (!JsfUtil.isValidationFailed()) {
        items = null;
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
            
            for (SituacionPrevisional s : lista) {
                        this.selected = s; // 👉 importante: asignás el objeto actual a selected
                        
                    persist(PersistAction.UPDATE, "situación previsional editada");

            }
        }

    
    private static List<String[]> limitarA360Meses(int yaAportados, List<String> periodosSinAportes) {
    final int MAX_MESES = 360;

    List<String[]> seleccionados = new ArrayList<>();

    // Primero, los períodos que aplican a la ley 24.476 (hasta 30/09/1993)
    List<String> ley24476 = periodosSinAportes.stream()
            .filter(p -> !parsePeriodo(p).isAfter(LIMITE_24476))
            .sorted()
            .collect(Collectors.toList());

    for (String p : ley24476) {
        if (yaAportados + seleccionados.size() >= MAX_MESES) break;
        seleccionados.add(new String[]{p, "24.476"});
    }

    // Luego, los períodos que aplican a la ley 27.705 (entre 01/10/1993 y 31/03/2012)
    List<String> ley27705 = periodosSinAportes.stream()
            .filter(p -> {
                LocalDate fecha = parsePeriodo(p);
                return (!fecha.isBefore(INICIO_27705) && !fecha.isAfter(FIN_27705));
            })
            .sorted()
            .collect(Collectors.toList());

    for (String p : ley27705) {
        if (yaAportados + seleccionados.size() >= MAX_MESES) break;
        seleccionados.add(new String[]{p, "27.705"});
    }

    return seleccionados;
}
       
    private static List<String[]> calcularTodoConLey27705(List<String> periodosSinAportes, int yaAportados) {
    final int MAX_MESES = 360;
    List<String[]> seleccionados = new ArrayList<>();

    List<String> ley27705 = periodosSinAportes.stream()
            .filter(p -> {
                LocalDate fecha = parsePeriodo(p);
                return (!fecha.isBefore(INICIO_27705) && !fecha.isAfter(FIN_27705));
            })
            .sorted()
            .collect(Collectors.toList());

    for (String p : ley27705) {
        if (yaAportados + seleccionados.size() >= MAX_MESES) break;
        seleccionados.add(new String[]{p, "27.705"});
    }

    return seleccionados;
}
        
        
        
    public void periodosPosiblesSegunCadaLey(int orden, Date fechaDeNacimiento) {
    List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);
    ZoneId zona = ZoneId.systemDefault();
    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    LocalDate fechaNacimiento = fechaDeNacimiento.toInstant().atZone(zona).toLocalDate();
    LocalDate fechaInicioGeneral = fechaNacimiento.plusYears(18);
    LocalDate fechaFinGeneral = LocalDate.of(2012, 3, 31); // Límite ley 27.705

    StringBuilder sb = new StringBuilder();

    // Paso 2: Verificación edad
    if (LocalDate.now().isBefore(fechaInicioGeneral)) {
        sb.append("⚠️ El titular aún no ha cumplido 18 años. No se pueden computar períodos.\n\n");
        resultadoMoratoriaV5 = sb.toString();
        return;
    }

    sb.append("🧾 Fecha de nacimiento: ").append(fechaNacimiento.format(formatoFecha)).append("\n");
    sb.append("🎂 Inicio de cómputo (desde 18 años): ").append(fechaInicioGeneral.format(formatoFecha)).append("\n\n");

    // Paso 4: Periodos activos
    List<LocalDate[]> periodosActivos = lista.stream()
        .filter(s -> s.getFechaInicio() != null && s.getFechaFin() != null)
        .map(s -> new LocalDate[]{
            s.getFechaInicio().toInstant().atZone(zona).toLocalDate(),
            s.getFechaFin().toInstant().atZone(zona).toLocalDate()
        })
        .sorted(Comparator.comparing(a -> a[0]))
        .collect(Collectors.toList());

    // Paso 5: Calcular huecos reales
    List<LocalDate[]> periodosInactivosRaw = new ArrayList<>();
    LocalDate cursor = fechaInicioGeneral;

    for (LocalDate[] periodo : periodosActivos) {
        LocalDate inicio = periodo[0];
        LocalDate fin = periodo[1];

        if (cursor.isBefore(inicio)) {
            periodosInactivosRaw.add(new LocalDate[]{cursor, inicio.minusDays(1)});
        }
        cursor = fin.plusDays(1);
    }

    if (cursor.isBefore(fechaFinGeneral)) {
        periodosInactivosRaw.add(new LocalDate[]{cursor, fechaFinGeneral});
    }

    // Paso 6: Filtrar por leyes
    List<LocalDate[]> periodosLey24476 = periodosInactivosRaw.stream()
        .filter(p -> !p[0].isAfter(LIMITE_24476))
        .map(p -> new LocalDate[]{
            p[0],
            p[1].isAfter(LIMITE_24476) ? LIMITE_24476 : p[1]
        })
        .collect(Collectors.toList());

    List<LocalDate[]> periodosLey27705 = periodosInactivosRaw.stream()
        .filter(p -> !(p[1].isBefore(INICIO_27705) || p[0].isAfter(FIN_27705)))
        .map(p -> new LocalDate[]{
            p[0].isBefore(INICIO_27705) ? INICIO_27705 : p[0],
            p[1].isAfter(FIN_27705) ? FIN_27705 : p[1]
        })
        .collect(Collectors.toList());

    // Paso 7: Mostrar resultados con duración real
    sb.append("📘 Ley 24.476 (Hasta 30/09/1993):\n");
    int totalMeses24476 = 0;
    for (LocalDate[] p : periodosLey24476) {
        String desde = p[0].format(DateTimeFormatter.ofPattern("MM/yyyy"));
        String hasta = p[1].format(DateTimeFormatter.ofPattern("MM/yyyy"));
        String duracion = calcularDuracion(p[0], p[1]);
        sb.append("➡️ ").append(desde).append(" - ").append(hasta).append("  📅 ").append(duracion).append("\n");

        Period periodo = Period.between(p[0], p[1].plusDays(1));
        totalMeses24476 += periodo.getYears() * 12 + periodo.getMonths();
    }
    sb.append("\n🔢 Total de meses: ").append(totalMeses24476).append(" meses\n\n");

    sb.append("📙 Ley 27.705 (01/10/1993 hasta 31/03/2012):\n");
    int totalMeses27705 = 0;
    for (LocalDate[] p : periodosLey27705) {
        String desde = p[0].format(DateTimeFormatter.ofPattern("MM/yyyy"));
        String hasta = p[1].format(DateTimeFormatter.ofPattern("MM/yyyy"));
        String duracion = calcularDuracion(p[0], p[1]);
        sb.append("➡️ ").append(desde).append(" - ").append(hasta).append("  📅 ").append(duracion).append("\n");

        Period periodo = Period.between(p[0], p[1].plusDays(1));
        totalMeses27705 += periodo.getYears() * 12 + periodo.getMonths();
    }
    sb.append("\n🔢 Total de meses: ").append(totalMeses27705).append(" meses\n");

    resultadoMoratoriaV5 = sb.toString();
}

    private String calcularDuracionDePeriodo(String periodo, LocalDate fechaInicioPersonalizado) {
        try {
            String[] partes = periodo.split(" - ");
            if (partes.length != 2) return "";

            DateTimeFormatter mesAnio = DateTimeFormatter.ofPattern("MM/yyyy");
            LocalDate inicio = LocalDate.parse("01/" + partes[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate fin = LocalDate.parse("01/" + partes[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .withDayOfMonth(finFecha(partes[1]));

            if (fechaInicioPersonalizado != null && partes[0].equals(formatMMYYYY(fechaInicioPersonalizado))) {
                inicio = fechaInicioPersonalizado;
            }

            return calcularDuracion(inicio, fin);
        } catch (Exception e) {
            return "";
        }
    }

    private int finFecha(String mesAnio) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM/yyyy");
        LocalDate fecha = LocalDate.parse(mesAnio, formato);
        return fecha.lengthOfMonth();
    }

    private String formatMMYYYY(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        return formatter.format(fecha);
    }

    private String calcularDuracion(LocalDate inicio, LocalDate fin) {
        Period periodo = Period.between(inicio, fin.plusDays(1)); // incluir último día
        int meses = periodo.getYears() * 12 + periodo.getMonths();
        int dias = periodo.getDays();

        StringBuilder sb = new StringBuilder();
        if (meses > 0) sb.append(meses).append(" meses");
        if (dias > 0) sb.append(" y ").append(dias).append(" días");
        return sb.toString();
    }

    private static class ResultadoAgrupado {
    List<String> grupos;
    long totalMeses;

    ResultadoAgrupado(List<String> grupos, long totalMeses) {
        this.grupos = grupos;
        this.totalMeses = totalMeses;
    }
}

    private ResultadoAgrupado agruparPeriodosConInfo(List<String> periodosStr) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
    List<LocalDate> fechas = periodosStr.stream()
        .map(p -> YearMonth.parse(p, formatter).atDay(1))
        .sorted()
        .collect(Collectors.toList());


    List<String> grupos = new ArrayList<>();
    if (fechas.isEmpty()) return new ResultadoAgrupado(grupos, 0);

    LocalDate inicio = fechas.get(0);
    LocalDate anterior = inicio;
    long totalMeses = 0;

    for (int i = 1; i < fechas.size(); i++) {
        LocalDate actual = fechas.get(i);
        if (!actual.equals(anterior.plusMonths(1))) {
            long cantidadMeses = YearMonth.from(inicio).until(YearMonth.from(anterior), ChronoUnit.MONTHS) + 1;
            totalMeses += cantidadMeses;
            grupos.add(formatter.format(inicio) + " - " + formatter.format(anterior) + "  📅 " + cantidadMeses + " meses");

            if (actual.isAfter(anterior.plusMonths(3))) {
                grupos.add("");
            }
            inicio = actual;
        }
        anterior = actual;
    }

    // último grupo
    long cantidadMeses = YearMonth.from(inicio).until(YearMonth.from(anterior), ChronoUnit.MONTHS) + 1;
    totalMeses += cantidadMeses;
    grupos.add(formatter.format(inicio) + " - " + formatter.format(anterior) + "  📅 " + cantidadMeses + " meses");

    return new ResultadoAgrupado(grupos, totalMeses);
}

    public void calcularCombinaciónOptima(int orden, Date fechaDeNacimiento) {
    List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);
    ZoneId zona = ZoneId.systemDefault();
    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatoMes = DateTimeFormatter.ofPattern("MM/yyyy");

    LocalDate fechaNacimiento = fechaDeNacimiento.toInstant().atZone(zona).toLocalDate();
    LocalDate fechaInicioGeneral = fechaNacimiento.plusYears(18);
    LocalDate fechaFinGeneral = LocalDate.of(2012, 3, 31);

    StringBuilder sb = new StringBuilder();

    LocalDate hoy = LocalDate.now();
    if (hoy.isBefore(fechaInicioGeneral)) {
        sb.append("⚠️ El titular aún no ha cumplido 18 años. No se pueden computar períodos.\n\n");
        resultadoMoratoriaV5 = sb.toString();
        return;
    }

    sb.append("🧾 Fecha de nacimiento: ").append(fechaNacimiento.format(formatoFecha)).append("\n");
    sb.append("🎂 Inicio de cómputo (desde 18 años): ").append(fechaInicioGeneral.format(formatoFecha)).append("\n\n");

    List<LocalDate[]> periodosActivos = lista.stream()
            .filter(s -> s.getFechaInicio() != null && s.getFechaFin() != null)
            .map(s -> new LocalDate[]{
                    s.getFechaInicio().toInstant().atZone(zona).toLocalDate(),
                    s.getFechaFin().toInstant().atZone(zona).toLocalDate()
            })
            .sorted(Comparator.comparing(a -> a[0]))
            .collect(Collectors.toList());

    int yaAportados = calcularMesesConAportes(lista);

    List<String[]> seleccionFinal = new ArrayList<>();
    int mesesRestantes = 360 - yaAportados;
    if (mesesRestantes <= 0) {
        sb.append("✅ Ya tiene los 360 meses con aportes reales.\n");
    } else {
        List<String[]> periodosInactivos = new ArrayList<>();
        LocalDate cursor = fechaInicioGeneral;

        for (LocalDate[] periodo : periodosActivos) {
            LocalDate inicio = periodo[0];
            LocalDate fin = periodo[1];
            if (cursor.isBefore(inicio)) {
                LocalDate tmp = cursor.withDayOfMonth(1);
                while (!tmp.isAfter(inicio.minusMonths(1))) {
                    String ley = tmp.isBefore(LocalDate.of(1993, 10, 1)) ? "24.476" : "27.705";
                    periodosInactivos.add(new String[]{tmp.toString(), ley});
                    tmp = tmp.plusMonths(1);
                }
            }
            cursor = fin.plusMonths(1);
        }

        if (cursor.isBefore(fechaFinGeneral)) {
            LocalDate tmp = cursor.withDayOfMonth(1);
            while (!tmp.isAfter(fechaFinGeneral)) {
                String ley = tmp.isBefore(LocalDate.of(1993, 10, 1)) ? "24.476" : "27.705";
                periodosInactivos.add(new String[]{tmp.toString(), ley});
                tmp = tmp.plusMonths(1);
            }
        }

        // Primero ley 24.476 (sin importar continuidad)
        for (String[] p : periodosInactivos) {
            if (mesesRestantes == 0) break;
            if (p[1].equals("24.476")) {
                seleccionFinal.add(p);
                mesesRestantes--;
            }
        }

        // Luego ley 27.705 (en bloque continuo desde el primer mes posible)
        if (mesesRestantes > 0) {
            List<String[]> ley27705 = periodosInactivos.stream()
                    .filter(p -> p[1].equals("27.705"))
                    .collect(Collectors.toList());

            for (int i = 0; i <= ley27705.size() - mesesRestantes; i++) {
                boolean esBloqueContinuo = true;
                for (int j = 1; j < mesesRestantes; j++) {
                    LocalDate prev = LocalDate.parse(ley27705.get(i + j - 1)[0]);
                    LocalDate curr = LocalDate.parse(ley27705.get(i + j)[0]);
                    if (!curr.equals(prev.plusMonths(1))) {
                        esBloqueContinuo = false;
                        break;
                    }
                }
                if (esBloqueContinuo) {
                    for (int j = 0; j < mesesRestantes; j++) {
                        seleccionFinal.add(ley27705.get(i + j));
                    }
                    break;
                }
            }
        }
    }

    // 🔹 Agrupar para mostrar
    sb.append("✅ PERÍODOS SELECCIONADOS:\n");
    Map<String, List<LocalDate>> porLey = new LinkedHashMap<>();
    for (String[] p : seleccionFinal) {
        LocalDate fecha = LocalDate.parse(p[0]);
        String ley = p[1];
        porLey.computeIfAbsent(ley, k -> new ArrayList<>()).add(fecha);
    }

    Map<String, Long> totalPorLey = new HashMap<>();
    for (Map.Entry<String, List<LocalDate>> entry : porLey.entrySet()) {
        String ley = entry.getKey();
        List<LocalDate> fechas = entry.getValue().stream().sorted().collect(Collectors.toList());

        List<String> rangos = agruparRangos(fechas, formatoMes);
        rangos.forEach(r -> sb.append("➡️ ").append(r).append("  ← Ley ").append(ley).append("\n"));
        totalPorLey.put(ley, (long) fechas.size());
    }

    // 🔹 Mostrar resumen
    sb.append("\n📊 RESUMEN GENERAL\n");
    sb.append("Meses con aportes registrados: ").append(yaAportados).append("\n");
    totalPorLey.forEach((ley, cant) -> sb.append("Meses agregados por Ley ").append(ley).append(": ").append(cant).append("\n"));

    long total = yaAportados + totalPorLey.values().stream().mapToLong(Long::longValue).sum();
    sb.append("Total meses computados para jubilación: ").append(total).append("\n");

    if (total >= 360) {
        sb.append("✅ Se alcanzó el máximo de 360 meses permitidos.\n");
    } else {
        sb.append("⚠️ Faltan ").append(360 - total).append(" meses para completar 30 años.\n");
    }

    resultadoMoratoriaV5 = sb.toString();
}

// Agrupar rangos consecutivos de meses
private List<String> agruparRangos(List<LocalDate> fechas, DateTimeFormatter formato) {
    List<String> rangos = new ArrayList<>();
    if (fechas.isEmpty()) return rangos;

    LocalDate inicio = fechas.get(0);
    LocalDate fin = inicio;

    for (int i = 1; i < fechas.size(); i++) {
        LocalDate actual = fechas.get(i);
        if (actual.equals(fin.plusMonths(1))) {
            fin = actual;
        } else {
            rangos.add(formato.format(inicio) + " - " + formato.format(fin));
            inicio = fin = actual;
        }
    }
    rangos.add(formato.format(inicio) + " - " + formato.format(fin));
    return rangos;
}

    
    
    private List<LocalDate[]> limitarA360MesesContinuos(int aportados, List<LocalDate[]> inactivos) {
    int necesarios = 360 - aportados;
    List<LocalDate[]> seleccion = new ArrayList<>();
    int acumulado = 0;

    for (LocalDate[] rango : inactivos) {
        LocalDate inicio = rango[0];
        LocalDate fin = rango[1];

        while (!inicio.isAfter(fin) && acumulado < necesarios) {
            LocalDate finMes = inicio.withDayOfMonth(1).plusMonths(1).minusDays(1);
            if (finMes.isAfter(fin)) finMes = fin;

            seleccion.add(new LocalDate[]{inicio, finMes});
            acumulado++;
            inicio = inicio.plusMonths(1);
        }

        if (acumulado >= necesarios) break;
    }

    return seleccion;
}

    public static class ResultadoAgrupadoPorLey {
    public List<String> rangos;
    public Map<String, Long> totalMesesPorLey;

    public ResultadoAgrupadoPorLey(List<String> rangos, Map<String, Long> totalMesesPorLey) {
        this.rangos = rangos;
        this.totalMesesPorLey = totalMesesPorLey;
    }
}

    private ResultadoAgrupadoPorLey agruparYSumarPeriodosPorLey(List<String[]> seleccionFinal) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

    class PeriodoLey {
        LocalDate fecha;
        String ley;
        PeriodoLey(String periodo, String ley) {
            this.fecha = YearMonth.parse(periodo, formatter).atDay(1);
            this.ley = ley;
        }
    }

    List<PeriodoLey> lista = seleccionFinal.stream()
        .map(p -> new PeriodoLey(p[0], p[1]))
        .sorted(Comparator.comparing((PeriodoLey pl) -> pl.ley).thenComparing(pl -> pl.fecha))
        .collect(Collectors.toList());

    List<String> resultados = new ArrayList<>();
    Map<String, Long> totales = new HashMap<>();
    if (lista.isEmpty()) return new ResultadoAgrupadoPorLey(resultados, totales);

    String leyActual = lista.get(0).ley;
    LocalDate inicio = lista.get(0).fecha;
    LocalDate anterior = inicio;

    for (int i = 1; i < lista.size(); i++) {
        PeriodoLey actual = lista.get(i);
        boolean cambioLey = !actual.ley.equals(leyActual);
        boolean noConsecutivo = !actual.fecha.equals(anterior.plusMonths(1));

        if (cambioLey || noConsecutivo) {
            long meses = YearMonth.from(inicio).until(YearMonth.from(anterior), ChronoUnit.MONTHS) + 1;
            resultados.add(formatter.format(inicio) + " - " + formatter.format(anterior) + "  ← Ley " + leyActual);
            totales.put(leyActual, totales.getOrDefault(leyActual, 0L) + meses);

            leyActual = actual.ley;
            inicio = actual.fecha;
        }
        anterior = actual.fecha;
    }

    // último rango
    long meses = YearMonth.from(inicio).until(YearMonth.from(anterior), ChronoUnit.MONTHS) + 1;
    resultados.add(formatter.format(inicio) + " - " + formatter.format(anterior) + "  ← Ley " + leyActual);
    totales.put(leyActual, totales.getOrDefault(leyActual, 0L) + meses);

    return new ResultadoAgrupadoPorLey(resultados, totales);
}

    /*
     Objetivo:
        Modificar el método calcularTodoCon27705 para que:
        Solo analice períodos sin aportes dentro del rango de la Ley 27.705 (01/01/2012 a 31/03/2012).
        Encuentre el mejor bloque continuo de meses faltantes dentro de ese rango, hasta completar 360 meses (máximo).
    
    */
    public void calcularTodoCon27705(int orden, Date fechaDeNacimiento) {
    List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);

    if (lista == null || lista.isEmpty()) {
        resultadoMoratoriaV5 = "❌ No se encontró información para el orden " + orden;
        return;
    }

    ZoneId zona = ZoneId.systemDefault();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    LocalDate nacimiento = fechaDeNacimiento.toInstant().atZone(zona).toLocalDate();
    LocalDate desde = nacimiento.plusYears(18);
    LocalDate hasta = FIN_27705;
    final int MAX_MESES = 360;

    StringBuilder sb = new StringBuilder();

    if (LocalDate.now().isBefore(desde)) {
        resultadoMoratoriaV5 = "⚠️ El titular aún no ha cumplido 18 años.";
        return;
    }

    // 1. Periodos con aportes reales
    Set<String> aportados = new HashSet<>();
    for (SituacionPrevisional s : lista) {
        if (s.getEmpleador() != null && !s.getEmpleador().trim().isEmpty()
                && s.getFechaInicio() != null && s.getFechaFin() != null) {
            LocalDate inicio = s.getFechaInicio().toInstant().atZone(zona).toLocalDate();
            LocalDate fin = s.getFechaFin().toInstant().atZone(zona).toLocalDate();
            aportados.addAll(generarPeriodos(inicio, fin));
        }
    }

    int cantidadAportados = aportados.size();

    // 2. GENERAR PERIODOS POSIBLES DENTRO DEL RANGO 27.705
    List<String> sinAportes27705 = new ArrayList<>();
    for (String p : generarPeriodos(INICIO_27705, FIN_27705)) {
        if (!aportados.contains(p)) {
            sinAportes27705.add(p);
        }
    }

    // 3. SELECCIONAR HASTA COMPLETAR LOS 360 MESES
    int faltantes = MAX_MESES - cantidadAportados;
    List<String[]> seleccionFinal = new ArrayList<>();

    for (String periodo : sinAportes27705) {
        if (faltantes == 0) break;
        seleccionFinal.add(new String[]{periodo, "27.705"});
        faltantes--;
    }

    // 4. MOSTRAR RESULTADO
    sb.append("📋 Evaluación solo con Ley 27.705\n\n");
    sb.append("📅 Fecha nacimiento: ").append(nacimiento.format(formato)).append("\n");
    sb.append("🎂 Inicio cómputo (18 años): ").append(desde.format(formato)).append("\n");
    sb.append("📆 Fin período legal: ").append(hasta.format(formato)).append("\n\n");

    sb.append("✅ Períodos sin aportes seleccionados (Ley 27.705):\n");
    ResultadoAgrupadoPorLey agrupado = agruparYSumarPeriodosPorLey(seleccionFinal);
    agrupado.rangos.forEach(r -> sb.append("➡️ ").append(r).append("\n"));

    sb.append("\n📊 Resumen:\n");
    sb.append("🧾 Meses con aportes reales: ").append(cantidadAportados).append("\n");
    sb.append("📙 Meses por Ley 27.705: ").append(agrupado.totalMesesPorLey.getOrDefault("27.705", 0L)).append("\n");

    long total = cantidadAportados + agrupado.totalMesesPorLey.getOrDefault("27.705", 0L);
    sb.append("📈 Total meses computados: ").append(total).append("\n");

    if (total >= MAX_MESES) {
        sb.append("✅ Se alcanzó el máximo de 360 meses permitidos.\n");
    } else {
        sb.append("⚠️ Faltan ").append(MAX_MESES - total).append(" meses para completar los 30 años.\n");
    }

    resultadoMoratoriaV5 = sb.toString();
}


}
