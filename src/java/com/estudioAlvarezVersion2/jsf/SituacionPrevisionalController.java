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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
    private String totalAniosDeServicio; 
    private String resultadoMoratoriaV4;
    private String resultadoMoratoriaV5;
    private String tiempoInactivoHasta1993;
    private String tiempoInactivoDesde1993;
    private int totalDiasConMoratoria24476 = 0; // valor acumulado (se puede mostrar luego)
    private static final int TOTAL_MESES_OBJETIVO = 360; // 30 años * 12

    public void clearReporteFinal() {
        this.totalTiempoConAportes = null; // o "" si preferís
    } 
    
    public String getTotalAniosDeServicio() {
        return totalAniosDeServicio;
    }

    public void setTotalAniosDeServicio(String totalAniosDeServicio) {
        this.totalAniosDeServicio = totalAniosDeServicio;
    }

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
    
    public String calcularResultadoPrevisional(int orden) {
    List<SituacionPrevisional> lista = verSituacionPrevisionalesPorNroDeOrden(orden);

    Set<YearMonth> mesesAportados = new HashSet<>();
    Set<YearMonth> meses24476     = new HashSet<>();
    Set<YearMonth> meses27705     = new HashSet<>();

    long mesesAportadosBrutos = 0L; // 👈 contador bruto (sin dedup)

    for (SituacionPrevisional s : lista) {
        LocalDate inicio = (s.getFechaInicio() == null ? null : 
                s.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        LocalDate fin    = (s.getFechaFin() == null ? null : 
                s.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (inicio == null || fin == null || fin.isBefore(inicio)) continue;

        // contador bruto (sin deduplicar) si tiene empleador
        if (s.getEmpleador() != null && !s.getEmpleador().trim().isEmpty()) {
            YearMonth a = YearMonth.from(inicio);
            YearMonth b = YearMonth.from(fin);
            mesesAportadosBrutos += ChronoUnit.MONTHS.between(a, b) + 1;
        }

        // recorrer mes a mes
        YearMonth ym    = YearMonth.from(inicio);
        YearMonth ymFin = YearMonth.from(fin);
        while (!ym.isAfter(ymFin)) {
            if (s.getEmpleador() != null && !s.getEmpleador().trim().isEmpty()) {
                mesesAportados.add(ym); // únicos (dedup)
            } else {
                LocalDate primerDiaMes = ym.atDay(1);
                if (LIMITE_24476 != null && !primerDiaMes.isAfter(LIMITE_24476)) {
                    meses24476.add(ym);
                } else if (INICIO_27705 != null && FIN_27705 != null
                        && !primerDiaMes.isBefore(INICIO_27705)
                        && !primerDiaMes.isAfter(FIN_27705)) {
                    meses27705.add(ym);
                }
            }
            ym = ym.plusMonths(1);
        }
    }

    // quitar aportes laborales de las moratorias
    meses24476.removeAll(mesesAportados);
    meses27705.removeAll(mesesAportados);

    // prioridad: dar preferencia a 27.705 si hay intersección
    Set<YearMonth> inter = new HashSet<>(meses24476);
    inter.retainAll(meses27705);
    meses24476.removeAll(inter);

    int yaAportados = mesesAportados.size();
    int faltantes   = Math.max(0, 360 - yaAportados);

    List<YearMonth> sel27705 = meses27705.stream().sorted().limit(faltantes).toList();
    faltantes -= sel27705.size();

    List<YearMonth> sel24476 = meses24476.stream().sorted().limit(faltantes).toList();

    int meses27705Usados = sel27705.size();
    int meses24476Usados = sel24476.size();

    int totalFinalMeses = yaAportados + meses24476Usados + meses27705Usados;

    StringBuilder resumen = new StringBuilder();
    resumen.append("════════════════════════════════════════════\n");
    resumen.append("RESUMEN GENERAL\n");
    resumen.append("════════════════════════════════════════════\n");
    resumen.append("Meses con aportes registrados (brutos): ").append(mesesAportadosBrutos).append("\n");
    resumen.append("Meses con aportes computables (únicos): ").append(yaAportados).append("\n");
    resumen.append("Meses agregados por Ley 24.476: ").append(meses24476Usados).append("\n");
    resumen.append("Meses agregados por Ley 27.705: ").append(meses27705Usados).append("\n");
    resumen.append("Total meses computados para jubilación: ").append(totalFinalMeses).append("\n");

    if (totalFinalMeses >= 360) {
        resumen.append("✅ Se alcanzó el máximo de 360 meses permitidos.\n");
    } else {
        resumen.append("⚠️  Faltan ").append(360 - totalFinalMeses).append(" meses para llegar a los 360.\n");
    }

    return resumen.toString();
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

    public void setTotalTiempoConAportes(String totalTiempoConAportes) {
        this.totalTiempoConAportes = totalTiempoConAportes;
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

    
        
        
       // ================= MÉTODO FINAL UNIFICADO (retorna el reporte) =================
        public void generarReporteIntegralFinal(
                int orden,
                Date fechaNacimiento,
                String sexo,
                int hijosBiologicos,
                int hijosAdoptados,
                int hijosConDiscapacidad,
                int hijosConAUH
        ) {
            // ----- Constantes legales -----
            final int EDAD_MUJER = 60, EDAD_VARON = 65;

            // ----- Setup -----
            ZoneId zona = ZoneId.systemDefault();
            LocalDate fnac = fechaNacimiento.toInstant().atZone(zona).toLocalDate();
            int edadLegal = "Femenino".equalsIgnoreCase(sexo) ? EDAD_MUJER : EDAD_VARON;
            LocalDate fechaEdadLegal = fnac.plusYears(edadLegal);
            LocalDate inicio18 = fnac.plusYears(18);
            LocalDate primerMesComputable = (inicio18.getDayOfMonth() == 1) ? inicio18 : inicio18.plusMonths(1).withDayOfMonth(1);
            DateTimeFormatter fDMY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter fMY  = DateTimeFormatter.ofPattern("MM/yyyy");
            LocalDate hoy = LocalDate.now();

            // ----- Traer períodos (robusto, no cortar si no hay) -----
            List<SituacionPrevisional> crudos = verSituacionPrevisionalesPorNroDeOrden(orden);
            if (crudos == null) crudos = Collections.emptyList();

            List<Intervalo> intervalos = crudos.stream()
                    .filter(s -> s.getEmpleador() != null && !s.getEmpleador().trim().isEmpty()
                              && s.getFechaInicio() != null && s.getFechaFin() != null)
                    .map(s -> new Intervalo(
                            s.getFechaInicio().toInstant().atZone(zona).toLocalDate(),
                            s.getFechaFin().toInstant().atZone(zona).toLocalDate()))
                    .filter(it -> !it.fin.isBefore(it.inicio))
                    .sorted(Comparator.comparing(i -> i.inicio))
                    .collect(Collectors.toList());

            // ⚠️ NO retornar si está vacío: el reporte debe poder generarse sin aportes reales.
            List<Intervalo> fusionados = intervalos.isEmpty() ? Collections.emptyList() : fusionar(intervalos);


            // ----- Meses completos + días residuales (Regla 7, 30d=1m) -----
            int mesesCompletosAportes = 0, diasResidualesAportes = 0;
            for (Intervalo it : fusionados) {
                MesesDias md = contarMesesCompletosYDias(it.inicio, it.fin);
                mesesCompletosAportes += md.meses;
                diasResidualesAportes += md.dias;
            }
            mesesCompletosAportes += diasResidualesAportes / 30;
            diasResidualesAportes  = diasResidualesAportes % 30;

            // YM aportados (marca el mes si tiene ≥1 día aportado)
            Set<YearMonth> mesesAportados = mesesDesdeIntervalos(fusionados);

            // ----- Servicio exacto (A/M/D) informativo -----
            Period pServicio = periodDesdeDias(diasEntreIntervalos(fusionados));

            // ====== Armado de encabezado común ======
            StringBuilder sb = new StringBuilder();
            sb.append("════════════════════════════════════════════\n");
            sb.append("📄 Reporte Integral de Situación Previsional\n");
            sb.append("════════════════════════════════════════════\n");
            sb.append("👤 Sexo: ").append(sexo).append("\n");
            sb.append("🎂 Fecha de nacimiento: ").append(fnac.format(fDMY)).append("\n");
            sb.append("🎯 Edad jubilatoria legal: ").append(edadLegal).append(" años (cumple el ")
              .append(fechaEdadLegal.format(fDMY)).append(")\n");
            sb.append("🗓️ Inicio de cómputo (18 años): ").append(inicio18.format(fDMY))
              .append(" → primer mes computable: ").append(primerMesComputable.format(fDMY)).append("\n");
            sb.append("📅 Fecha de cálculo: ").append(hoy.format(fDMY)).append("\n\n");

            sb.append("🧮 CÓMPUTO DE APORTES (intervalos fusionados)\n");
            sb.append("──────────────────────────────────────────────\n");
            sb.append("• Tiempo con aportes (exacto): ").append(formatear(pServicio)).append("\n");
            sb.append("• Meses con aportes registrados (solo completos): ").append(mesesCompletosAportes)
              .append(" (+").append(diasResidualesAportes).append(" días residuales)\n\n");

            // 🚩 REGLA 15: si aportes efectivos ≥ 360, no topear ni aplicar extras
            if (mesesCompletosAportes >= TOTAL_MESES_OBJETIVO) {
                sb.append("⏩ APORTES ADICIONALES\n");
                sb.append("──────────────────────\n");
                sb.append("• No aplican: supera los 360 exclusivamente con aportes efectivos.\n\n");
                sb.append("📈 TOTALES\n");
                sb.append("────────────\n");
                sb.append("• Total meses acreditados por aportes efectivos (sin tope): ")
                  .append(mesesCompletosAportes).append("\n");
                sb.append("• Total computable para el derecho: ").append(mesesCompletosAportes).append("\n\n");
                sb.append("✅ Cumple la meta de 30 años (y la excede con aportes efectivos).\n");
                totalTiempoConAportes = sb.toString();
                return;
            }

            // ----- Art. 22 bis (modelo acordado) -----
            int mesesReconHijos = mesesReconocimientoHijos(sexo, hijosBiologicos, hijosAdoptados, hijosConDiscapacidad, hijosConAUH);

            // ----- Proyección hasta edad legal DESDE hoy -----
            int mesesPorAportarHastaEdad = hoy.isBefore(fechaEdadLegal)
                    ? mesesProyectablesDesdeHoyHastaEdad(mesesAportados, fechaEdadLegal, hoy)
                    : 0;

            // ===== ORDEN OPTIMIZADO (minimiza moratoria y llega lo antes posible) =====
            // Base sin moratorias
            int totalBase = mesesCompletosAportes + mesesReconHijos + mesesPorAportarHastaEdad;
            int faltanTrasBase = Math.max(0, 360 - totalBase);

            // Compensación por exceso (si ya alcanzó edad legal) ANTES que moratorias
            int compMesesAplicados = 0;
            YMD exHoyYMD = new YMD(0,0,0), bonHoyYMD = new YMD(0,0,0);
            if (!hoy.isBefore(fechaEdadLegal) && faltanTrasBase > 0) {
                exHoyYMD = excesoEdadYMD(fechaEdadLegal, hoy);
                bonHoyYMD = bonificarExceso2a1(exHoyYMD);
                compMesesAplicados = Math.min(faltanTrasBase, aMeses(bonHoyYMD));
            }
            int subtotalConComp = totalBase + compMesesAplicados;
            int faltanTrasComp = Math.max(0, 360 - subtotalConComp);

            // Moratorias (universo − meses aportados)
            YearMonth ymInicio = YearMonth.from(primerMesComputable);
            List<YearMonth> libres24476 = rangoYM(ymInicio, YearMonth.from(LIMITE_24476)).stream()
                    .filter(ym -> !mesesAportados.contains(ym)).collect(Collectors.toList());
            List<YearMonth> libres27705 = hoy.isBefore(fechaEdadLegal)
                    ? rangoYM(maxYM(ymInicio, YearMonth.from(INICIO_27705)), YearMonth.from(FIN_27705)).stream()
                        .filter(ym -> !mesesAportados.contains(ym)).collect(Collectors.toList())
                    : Collections.emptyList();

            int necesarios = faltanTrasComp;
            List<YearMonth> sel24476 = new ArrayList<>();
            for (YearMonth ym : libres24476) { if (necesarios == 0) break; sel24476.add(ym); necesarios--; }

            List<YearMonth> sel27705 = new ArrayList<>();
            if (necesarios > 0 && !libres27705.isEmpty()) {
                for (List<YearMonth> bloque : agruparConsecutivos(libres27705)) {
                    if (necesarios == 0) break;
                    if (bloque.size() <= necesarios) { sel27705.addAll(bloque); necesarios -= bloque.size(); }
                    else { sel27705.addAll(bloque.subList(0, necesarios)); necesarios = 0; }
                }
            }
            int agregados24476 = sel24476.size();
            int agregados27705 = sel27705.size();

            int totalHastaEdad = subtotalConComp + agregados24476 + agregados27705;
            // En rama normal, extras nunca deben pasar de 360 (cap del derecho):
            if (totalHastaEdad > TOTAL_MESES_OBJETIVO) totalHastaEdad = TOTAL_MESES_OBJETIVO;
            int faltanTrasMoratorias = Math.max(0, TOTAL_MESES_OBJETIVO - totalHastaEdad);

            // Post-edad: combinación óptima aporte + compensación (primer mes posible)
            PlanPostEdad plan = new PlanPostEdad();
            if (faltanTrasMoratorias > 0) plan = planOptimoPostEdad(faltanTrasMoratorias, fechaEdadLegal, hoy);

            int totalAcreditadoSinTope = totalHastaEdad + (faltanTrasMoratorias > 0 ? plan.mesesTotalesAcreditados() : 0);
            int totalComputableDerecho = Math.min(totalAcreditadoSinTope, TOTAL_MESES_OBJETIVO);

            sb.append("👶 RECONOCIMIENTO DE TAREAS DE CUIDADO — Art. 22 bis\n");
            sb.append("───────────────────────────────────────────────────\n");
            sb.append("• Bonificación por hijos/as: ").append(mesesReconHijos).append(" meses\n\n");

            sb.append("⏩ APORTES FUTUROS HASTA LA EDAD LEGAL (desde la fecha de cálculo)\n");
            sb.append("──────────────────────────────────────────────────────────────────\n");
            sb.append("• Meses completos posibles hasta edad legal: ").append(mesesPorAportarHastaEdad).append("\n\n");

            sb.append("🧾 COMPENSACIÓN POR EXCESO DE EDAD — Art. 19 (2:1 en A/M/D)\n");
            sb.append("──────────────────────────────────────────────────────────\n");
            if (!hoy.isBefore(fechaEdadLegal)) {
                sb.append("• Exceso de edad a la fecha: ").append(exHoyYMD.y).append(" años, ").append(exHoyYMD.m)
                  .append(" meses, ").append(exHoyYMD.d).append(" días\n");
                sb.append("• Bonificación (2:1, días hacia abajo): ").append(bonHoyYMD.y).append(" años, ")
                  .append(bonHoyYMD.m).append(" meses, ").append(bonHoyYMD.d).append(" días")
                  .append(" → aplicados ahora: ").append(compMesesAplicados).append(" meses\n\n");
            } else {
                sb.append("• Aún no cumplió la edad legal → sin bonificación por ahora\n\n");
            }

            sb.append("📘 LEY 24.476 (hasta 30/09/1993)\n");
            sb.append("────────────────────────────────\n");
            sb.append("🔢 Meses posibles (huecos): ").append(libres24476.size()).append("\n");
            if (!sel24476.isEmpty()) {
                sb.append("✅ Seleccionados: ").append(agregados24476).append("\n");
                sb.append(listarRangosYM(sel24476));
            } else sb.append("— Sin selección (no necesarios o ya se llegó a 360)\n");
            sb.append("\n");

            sb.append("📙 LEY 27.705 (01/10/1993 a 31/03/2012)\n");
            sb.append("───────────────────────────────────────\n");
            if (hoy.isBefore(fechaEdadLegal)) {
                sb.append("🔢 Meses posibles (huecos): ").append(libres27705.size()).append("\n");
                if (!sel27705.isEmpty()) {
                    sb.append("✅ Seleccionados (bloques consecutivos): ").append(agregados27705).append("\n");
                    sb.append(listarRangosYM(sel27705));
                } else sb.append("— Sin selección (no necesarios o ya se llegó a 360)\n");
            } else sb.append("— No aplicable: ya cumplió la edad jubilatoria (uso exclusivo en etapa prejubilatoria)\n");
            sb.append("\n");

            sb.append("📈 TOTAL COMPUTADO (hasta edad legal)\n");
            sb.append("─────────────────────────────────────\n");
            sb.append("• Meses con aportes:            ").append(mesesCompletosAportes).append("\n");
            sb.append("• + Art. 22 bis (cuidado):      ").append(mesesReconHijos).append("\n");
            sb.append("• + Por aportar (hoy→edad):     ").append(mesesPorAportarHastaEdad).append("\n");
            sb.append("• + Art. 19 (compensación):     ").append(compMesesAplicados).append("\n");
            sb.append("• + Ley 24.476:                 ").append(agregados24476).append("\n");
            sb.append("• + Ley 27.705:                 ").append(agregados27705).append("\n");
            sb.append("= Subtotal:                     ").append(totalHastaEdad).append(" / 360\n\n");

            if (faltanTrasMoratorias > 0) {
                sb.append("🧭 PROYECCIÓN POST-EDAD (aportes + compensación 2:1, primer mes posible)\n");
                sb.append("──────────────────────────────────────────────────────────────────\n");
                sb.append("• Necesario restante al terminar moratorias: ").append(faltanTrasMoratorias).append(" meses\n");
                sb.append("• Plan mínimo post-edad:\n");
                sb.append("   – Aporte post-edad: ").append(plan.mesesAportadosPost).append(" meses\n");
                sb.append("   – Compensación 2:1 usada: ").append(plan.mesesCompensacion).append(" meses\n");
                if (plan.fechaCumplimiento != null)
                    sb.append("= Fecha estimada de cumplimiento de 360: ").append(plan.fechaCumplimiento.format(fMY)).append("\n\n");
                else sb.append("= Fecha estimada de cumplimiento de 360: no determinada\n\n");
            }

            sb.append("🏁 TOTALES\n");
            sb.append("────────────\n");
            sb.append("• Total meses acreditados (sumatoria aplicada en este plan): ")
              .append(totalAcreditadoSinTope).append("\n");
            sb.append("• Total computable para el derecho: ")
              .append(totalComputableDerecho).append("\n\n");
            sb.append(totalComputableDerecho >= TOTAL_MESES_OBJETIVO
                    ? "✅ Cumple la meta de 30 años."
                    : "⚠️ Faltan " + (TOTAL_MESES_OBJETIVO - totalComputableDerecho) + " meses.");

            totalTiempoConAportes = sb.toString();
            
 //           return sb.toString();
            
            
        }
        
    // ========================== HELPERS (pegar en la misma clase) ==========================
    private static class Intervalo { LocalDate inicio, fin; Intervalo(LocalDate i, LocalDate f){inicio=i;fin=f;} }
    private static class MesesDias { int meses, dias; MesesDias(int m,int d){meses=m;dias=d;} }
    private static class YMD { int y,m,d; YMD(int y,int m,int d){this.y=y;this.m=m;this.d=d;} }
    private static class PlanPostEdad { int mesesAportadosPost; int mesesCompensacion; LocalDate fechaCumplimiento; int mesesTotalesAcreditados(){ return mesesAportadosPost + mesesCompensacion; } }
    private static List<Intervalo> fusionar(List<Intervalo> ordenadosPorInicio) {
        List<Intervalo> out = new ArrayList<>();
        if (ordenadosPorInicio == null || ordenadosPorInicio.isEmpty()) return out;

        Intervalo act = ordenadosPorInicio.get(0);
        for (int k = 1; k < ordenadosPorInicio.size(); k++) {
            Intervalo s = ordenadosPorInicio.get(k);
            if (!act.fin.plusDays(1).isBefore(s.inicio)) { // solapa o contiguo
                if (s.fin.isAfter(act.fin)) act.fin = s.fin;
            } else {
                out.add(act);
                act = s;
            }
        }
        out.add(act);
        return out;
    }
    private static MesesDias contarMesesCompletosYDias(LocalDate inicio, LocalDate fin){
    if (inicio == null || fin == null || fin.isBefore(inicio)) return new MesesDias(0,0);
    int m=0,d=0; YearMonth ys=YearMonth.from(inicio), ye=YearMonth.from(fin), ym=ys;
    while(!ym.isAfter(ye)){
        LocalDate mIni=ym.atDay(1), mFin=ym.atEndOfMonth();
        boolean full = !inicio.isAfter(mIni) && !fin.isBefore(mFin);
        if(full) m++; else{
            LocalDate a = inicio.isAfter(mIni)?inicio:mIni;
            LocalDate b = fin.isBefore(mFin)?fin:mFin;
            if(!b.isBefore(a)) d += (int) ChronoUnit.DAYS.between(a, b.plusDays(1));
        }
        ym=ym.plusMonths(1);
    }
    return new MesesDias(m,d);
}
    private static Set<YearMonth> mesesDesdeIntervalos(List<Intervalo> fusionados){
        Set<YearMonth> res = new HashSet<>();
        if (fusionados == null || fusionados.isEmpty()) return res;
        for (Intervalo it : fusionados) {
            YearMonth a = YearMonth.from(it.inicio), b = YearMonth.from(it.fin), ym = a;
            while (!ym.isAfter(b)) { res.add(ym); ym = ym.plusMonths(1); }
        }
        return res;
    }    
    private static int mesesProyectablesDesdeHoyHastaEdad(Set<YearMonth> mesesAportados, LocalDate fechaEdadLegal, LocalDate hoy){
    YearMonth cursor=YearMonth.from(hoy).plusMonths(1);
    YearMonth limite=YearMonth.from(fechaEdadLegal.minusMonths(1));
    if(cursor.isAfter(limite)) return 0; int c=0; while(!cursor.isAfter(limite)){ c++; cursor=cursor.plusMonths(1);} return c;
}
    private static List<YearMonth> rangoYM(YearMonth a, YearMonth b){
    List<YearMonth> out=new ArrayList<>(); if(a==null||b==null||a.isAfter(b)) return out;
    YearMonth ym=a; while(!ym.isAfter(b)){ out.add(ym); ym=ym.plusMonths(1);} return out;
}
    private static YearMonth maxYM(YearMonth a, YearMonth b){ return (a.atDay(1).isAfter(b.atDay(1)))?a:b; }
    private static List<List<YearMonth>> agruparConsecutivos(List<YearMonth> meses){
    List<List<YearMonth>> bloques=new ArrayList<>(); if(meses.isEmpty()) return bloques;
    List<YearMonth> orden=meses.stream().sorted().collect(Collectors.toList());
    List<YearMonth> curr=new ArrayList<>(); curr.add(orden.get(0));
    for(int i=1;i<orden.size();i++){ YearMonth p=orden.get(i-1), n=orden.get(i);
        if(p.plusMonths(1).equals(n)) curr.add(n); else { bloques.add(curr); curr=new ArrayList<>(); curr.add(n); } }
    bloques.add(curr); return bloques;
}
    private static String listarRangosYM(List<YearMonth> meses){
        if(meses.isEmpty()) return "";
        List<String> rangos=new ArrayList<>(); List<YearMonth> ord=meses.stream().sorted().collect(Collectors.toList());
        YearMonth start=ord.get(0), prev=start;
        for(int i=1;i<ord.size();i++){ YearMonth now=ord.get(i);
            if(!prev.plusMonths(1).equals(now)){ rangos.add(rangoYMString(start,prev)); start=now; } prev=now; }
        rangos.add(rangoYMString(start,prev));
        StringBuilder sb=new StringBuilder(); for(String r:rangos) sb.append("   ➡️ ").append(r).append("\n"); return sb.toString();
    }
    private static String rangoYMString(YearMonth a, YearMonth b){ DateTimeFormatter fm=DateTimeFormatter.ofPattern("MM/yyyy"); return a.format(fm)+" - "+b.format(fm); }
    private static long diasEntre(LocalDate a, LocalDate b){ return ChronoUnit.DAYS.between(a, b.plusDays(1)); } // inclusivo
    private static long diasEntreIntervalos(List<Intervalo> ints){ long d=0; for(Intervalo it:ints) d+=diasEntre(it.inicio,it.fin); return d; }
    private static Period periodDesdeDias(long totalDias){ LocalDate base=LocalDate.of(2000,1,1); return Period.between(base, base.plusDays(totalDias)); }
    private static String formatear(Period p){ return p.getYears()+" años, "+p.getMonths()+" meses, "+p.getDays()+" días"; }

    private static YMD excesoEdadYMD(LocalDate fechaEdadLegal, LocalDate hasta){
        if(!hasta.isAfter(fechaEdadLegal)) return new YMD(0,0,0);
        Period p=Period.between(fechaEdadLegal, hasta); return new YMD(p.getYears(), p.getMonths(), p.getDays());
    }
    private static YMD bonificarExceso2a1(YMD ex){ return new YMD(ex.y/2, ex.m/2, ex.d/2); } // días hacia abajo
    private static int aMeses(YMD ymd){ return ymd.y*12 + ymd.m + (ymd.d/30); }

    private static PlanPostEdad planOptimoPostEdad(int faltan, LocalDate fechaEdadLegal, LocalDate hoy){
    PlanPostEdad plan=new PlanPostEdad();
    LocalDate inicioExceso=fechaEdadLegal;
    YearMonth cursor=YearMonth.from(hoy.isBefore(fechaEdadLegal)?fechaEdadLegal:hoy).plusMonths(1); // primer mes completo
    int aportados=0;
    while(true){
        LocalDate finMesPrevio=cursor.atDay(1).minusDays(1);
        YMD ex = finMesPrevio.isBefore(inicioExceso) ? new YMD(0,0,0) : excesoEdadYMD(fechaEdadLegal, finMesPrevio);
        int comp = aMeses(bonificarExceso2a1(ex));
        if (aportados + comp >= faltan){
            plan.mesesAportadosPost = aportados;
            plan.mesesCompensacion  = Math.min(comp, faltan - aportados + comp);
            plan.fechaCumplimiento  = cursor.atDay(1);
            return plan;
        }
        aportados++; cursor = cursor.plusMonths(1);
        if(aportados>1200){ plan.mesesAportadosPost=aportados; plan.mesesCompensacion=comp; plan.fechaCumplimiento=cursor.atDay(1); return plan; }
    }
}

    private static int mesesReconocimientoHijos(String sexo,int hijosBio,int hijosAdop,int hijosDisc,int hijosAUH){
        if(!"Femenino".equalsIgnoreCase(sexo)) return 0;
        return hijosBio*12 + hijosAdop*24 + hijosDisc*12 + hijosAUH*12;
    }
    
}
