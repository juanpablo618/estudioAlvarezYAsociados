package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.Agenda;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author juanpablo618@hotmail.com
 */
@Named
@ViewScoped
public class CounterView implements Serializable {
     
    private Date number ;
    private int totalAgendas;
    private int totalAgendasRealizadas;
    private int totalAgendasNoRealizadas;
    private Map<String, Integer> cantidadAgendasPorEmpleado;
    private Map<String, Integer> cantidadAgendasPorDia;
    
    private StringBuilder cantAgendasPorEmpleado;
    
    private PieChartModel pieModel1;
    private BarChartModel barModel;
    private StreamedContent chart;

    private String responsable;
    private Date fechaDesde;
    private Date fechaHasta;
    private int cantAgendasPorEmpleadoPorFechas;
    
    private String totalAgendasParaElDia;

    public CounterView() {
        number = new Date();

        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);

        List<Agenda> agendas = agendaController.getItems();
        cantidadAgendasPorEmpleado = new HashMap<>();

        for (Agenda agenda : agendas) {
            String empleado = agenda.getResponsable();
            
            // Actualiza contador general por empleado
            cantidadAgendasPorEmpleado.put(empleado, cantidadAgendasPorEmpleado.getOrDefault(empleado, 0) + 1);

            // Verifica si la agenda no está realizada
            if (!agenda.getRealizado().equalsIgnoreCase("Si")) {
                totalAgendasNoRealizadas++;

                // Incrementa el contador específico para agendas no realizadas por empleado
                cantidadAgendasPorEmpleado.put(empleado + "_no_realizadas", cantidadAgendasPorEmpleado.getOrDefault(empleado + "_no_realizadas", 0) + 1);

            }
        
        }

    // Construye la cadena resultante
    StringBuilder result = new StringBuilder();

    for (Map.Entry<String, Integer> entry : cantidadAgendasPorEmpleado.entrySet()) {
        String empleado = entry.getKey();
        
        // Verifica si la entrada representa un empleado (no "_no_realizadas")
        if (!empleado.endsWith("_no_realizadas")) {
            Integer totalAgendas = entry.getValue();
            int realizadas = totalAgendas - cantidadAgendasPorEmpleado.getOrDefault(empleado + "_no_realizadas", 0);
            int noRealizadas = cantidadAgendasPorEmpleado.getOrDefault(empleado + "_no_realizadas", 0);

            result.append(" Empleado: ").append(empleado)
                  .append(", Cantidad de Agendas: ").append(totalAgendas)
                  .append(", realizadas: ").append(realizadas)
                  .append(", no realizadas: ").append(noRealizadas).append('\n').append('\n');
        }
    }

    cantAgendasPorEmpleado = result;
    totalAgendas = agendaController.getItems().size();
    totalAgendasRealizadas = totalAgendas - totalAgendasNoRealizadas;
    
    createPieModel1(totalAgendasRealizadas, totalAgendasNoRealizadas);
  
    createBarModel();
    }
    
    private void createBarModel() {
        barModel = initBarModel();
 
        barModel.setLegendPosition("ne");
 
        // Establecer el ancho de las barras al 50% del espacio disponible
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Empleados");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
    
     private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries realizadas = new ChartSeries();
        ChartSeries noRealizadas = new ChartSeries();
        
        realizadas.setLabel("Realizadas");
        noRealizadas.setLabel("No Realizadas");
            
        for (Map.Entry<String, Integer> entry : cantidadAgendasPorEmpleado.entrySet()) {
            String empleado = entry.getKey();
        
            if (!empleado.endsWith("_no_realizadas")) {
                Integer totalAgendas = entry.getValue();
                int realizadas2 = totalAgendas - cantidadAgendasPorEmpleado.getOrDefault(empleado + "_no_realizadas", 0);
                int noRealizadas2 = cantidadAgendasPorEmpleado.getOrDefault(empleado + "_no_realizadas", 0);

                realizadas.set(empleado, realizadas2);
                noRealizadas.set(empleado, noRealizadas2);
               }
        }
        model.addSeries(realizadas);
        model.addSeries(noRealizadas);

      return model;
    
     }
       
     public BarChartModel getBarModel() {
        return barModel;
    }
 
    public PieChartModel getPieModel1() {
        return pieModel1;
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

    public int getCantAgendasPorEmpleadoPorFechas() {
        return cantAgendasPorEmpleadoPorFechas;
    }

    public void setCantAgendasPorEmpleadoPorFechas(int cantAgendasPorEmpleadoPorFechas) {
        this.cantAgendasPorEmpleadoPorFechas = cantAgendasPorEmpleadoPorFechas;
    }

    public StringBuilder getCantAgendasPorEmpleado() {
        return cantAgendasPorEmpleado;
    }
    
     private void createPieModel1(int totalAgendasRealizadas1, int totalAgendasNoRealizadas1) {
        pieModel1 = new PieChartModel();
 
        pieModel1.set("Total agendas realizadas", totalAgendasRealizadas1);
        pieModel1.set("Total agendas no realizadas", totalAgendasNoRealizadas1);
 
        pieModel1.setShadow(false);
        
        pieModel1.setLegendPosition("e");
        pieModel1.setFill(false);
        pieModel1.setShowDataLabels(true);
        pieModel1.setDiameter(150);
        
    }
    
    public String getNumber() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        String dateString = dateFormat.format(number);
        return dateString;
    }

    public int getTotalAgendas() {
        return totalAgendas;
    }

    public int getTotalAgendasRealizadas() {
        return totalAgendasRealizadas;
    }

    public int getTotalAgendasNoRealizadas() {
        return totalAgendasNoRealizadas;
    }

    public Map<String, Integer> getCantidadAgendasPorEmpleado() {
        return cantidadAgendasPorEmpleado;
    }
    
    public void incrementDate(String userNombreCompleto, String dateSession) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

        Date date = dateFormat.parse(dateSession);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.setTime(number);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        number = calendar.getTime();
        
        String dateString = dateFormat.format(number);
        
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        TurnoController turnoController = context.getApplication().evaluateExpressionGet(context, "#{turnoController}", TurnoController.class);

        turnoController.setFilteredTurnosConSesion(turnoController.getItemsBySessionUser(userNombreCompleto, dateString));
        
        agendaController.setFilteredAgendasConSesion(agendaController.getItemsBySessionUser(userNombreCompleto, dateString));
        
        
    }
    
    public void incrementDateByLeader(String leaderNombreCompleto, String dateSession) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

        Date date = dateFormat.parse(dateSession);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.setTime(number);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        number = calendar.getTime();
        
        String dateString = dateFormat.format(number);
        
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        TurnoController turnoController = context.getApplication().evaluateExpressionGet(context, "#{turnoController}", TurnoController.class);

        turnoController.setFilteredTurnosConSesion(turnoController.getItemsByLeader(leaderNombreCompleto, dateString));
        
        agendaController.setFilteredAgendasConSesion(agendaController.getItemsByLeader(leaderNombreCompleto, dateString));
        
    }
    
    public void decrementDateByLeader(String leaderNombreCompleto, String dateSession) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

        Date date = dateFormat.parse(dateSession);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.setTime(number);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        number = calendar.getTime();
        
        String dateString = dateFormat.format(number);
        
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        TurnoController turnoController = context.getApplication().evaluateExpressionGet(context, "#{turnoController}", TurnoController.class);

        turnoController.setFilteredTurnosConSesion(turnoController.getItemsByLeader(leaderNombreCompleto, dateString));
        
        agendaController.setFilteredAgendasConSesion(agendaController.getItemsByLeader(leaderNombreCompleto, dateString));
        
    }
    
    public void decrementDate(String userNombreCompleto, String dateSession) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

        Date date = dateFormat.parse(dateSession);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.setTime(number);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        number = calendar.getTime();
        
        String dateString = dateFormat.format(number);
        
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        TurnoController turnoController = context.getApplication().evaluateExpressionGet(context, "#{turnoController}", TurnoController.class);

        turnoController.setFilteredTurnosConSesion(turnoController.getItemsBySessionUser(userNombreCompleto, dateString));
        
        agendaController.setFilteredAgendasConSesion(agendaController.getItemsBySessionUser(userNombreCompleto, dateString));
        
    }
    
    public void actualDate(String userNombreCompleto, String dateSession) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

        Date date = dateFormat.parse(dateSession);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        number = calendar.getTime();
        
        String dateString = dateFormat.format(number);
        
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        TurnoController turnoController = context.getApplication().evaluateExpressionGet(context, "#{turnoController}", TurnoController.class);

        turnoController.setFilteredTurnosConSesion(turnoController.getItemsBySessionUser(userNombreCompleto, dateString));
        
        agendaController.setFilteredAgendasConSesion(agendaController.getItemsBySessionUser(userNombreCompleto, dateString));
        
    }
    
    public void actualDateByLeader(String leaderNombreCompleto, String dateSession) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

        Date date = dateFormat.parse(dateSession);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        number = calendar.getTime();
        
        String dateString = dateFormat.format(number);
        
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        TurnoController turnoController = context.getApplication().evaluateExpressionGet(context, "#{turnoController}", TurnoController.class);

        turnoController.setFilteredTurnosConSesion(turnoController.getItemsByLeader(leaderNombreCompleto, dateString));
        
        agendaController.setFilteredAgendasConSesion(agendaController.getItemsByLeader(leaderNombreCompleto, dateString));
        
    }
    
    public void filtrarAgendasPorFiltros() {
        // Utiliza los valores de los filtros (responsable, fechaDesde, fechaHasta)
        // para filtrar las agendas y actualizar la lista de agendas filtradas
        // Luego, actualiza la propiedad cantAgendasPorEmpleadoFiltradas
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        
        
        int cantidadAgendasFiltradas = 0;

    // Obtén la lista completa de agendas
    List<Agenda> agendas = agendaController.getItems();

    // Obtén los valores de los filtros
    String responsableFiltro = agendaController.getResponsable();
    Date fechaDesdeFiltro = agendaController.getFechaDesde();
    Date fechaHastaFiltro = agendaController.getFechaHasta();
    String realizadoFiltro = agendaController.getRealizado();
    
    // Itera sobre la lista completa y actualiza el contador si se cumplen los filtros
    for (Agenda agenda : agendas) {
        boolean cumpleFiltros = true;

        // Verifica el filtro por responsable
        if (responsableFiltro != null && !responsableFiltro.isEmpty()) {
            cumpleFiltros = cumpleFiltros && agenda.getResponsable().equalsIgnoreCase(responsableFiltro);
        }

        // Verifica el filtro por fecha desde
        if (fechaDesdeFiltro != null) {
            cumpleFiltros = cumpleFiltros && (agenda.getFecha().equals(fechaDesdeFiltro) || agenda.getFecha().after(fechaDesdeFiltro));
        }

        // Verifica el filtro por fecha hasta
        if (fechaHastaFiltro != null) {
            cumpleFiltros = cumpleFiltros && (agenda.getFecha().equals(fechaHastaFiltro) || agenda.getFecha().before(fechaHastaFiltro));
        }
        
        // Verifica el filtro por realizado
        if (realizadoFiltro != null && !realizadoFiltro.isEmpty()) {
            cumpleFiltros = cumpleFiltros && agenda.getRealizado().equalsIgnoreCase(realizadoFiltro);
        }

        // Incrementa el contador si la agenda cumple con todos los filtros
        if (cumpleFiltros) {
            cantidadAgendasFiltradas++;
        }
    }

    // Actualiza la propiedad en tu bean con la cantidad de agendas filtradas
    setCantAgendasPorEmpleadoFiltradas(cantidadAgendasFiltradas);
        
    }

    public void filtrarAgendasPorFiltrosConPorcentaje() {
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        
        int totalAgendas = 0;
        int totalRealizadasSi = 0;
        int totalRealizadasNo = 0;
        int totalReagendadas = 0;

        // Obtén la lista completa de agendas
        List<Agenda> agendas = agendaController.getItems();

        // Obtén los valores de los filtros
        String responsableFiltro = agendaController.getResponsable();
        Date fechaDesdeFiltro = agendaController.getFechaDesde();
        Date fechaHastaFiltro = agendaController.getFechaHasta();
        
        // Itera sobre la lista completa y actualiza los contadores si se cumplen los filtros
        for (Agenda agenda : agendas) {
            boolean cumpleFiltros = true;

            // Verifica el filtro por responsable
            if (responsableFiltro != null && !responsableFiltro.isEmpty()) {
                cumpleFiltros = cumpleFiltros && agenda.getResponsable().equalsIgnoreCase(responsableFiltro);
            }

            // Verifica el filtro por fecha desde
            if (fechaDesdeFiltro != null) {
                cumpleFiltros = cumpleFiltros && (agenda.getFecha().equals(fechaDesdeFiltro) || agenda.getFecha().after(fechaDesdeFiltro));
            }

            // Verifica el filtro por fecha hasta
            if (fechaHastaFiltro != null) {
                cumpleFiltros = cumpleFiltros && (agenda.getFecha().equals(fechaHastaFiltro) || agenda.getFecha().before(fechaHastaFiltro));
            }

            // Incrementa los contadores si la agenda cumple con todos los filtros
            if (cumpleFiltros) {
                totalAgendas++;
                if ("No".equalsIgnoreCase(agenda.getRealizado())) {
                    totalRealizadasNo++;
                } else if ("Si".equalsIgnoreCase(agenda.getRealizado())) {
                    totalRealizadasSi++;
                } else if ("Reagendado".equalsIgnoreCase(agenda.getRealizado())) {
                    totalReagendadas++;
                }
            }
        }

        // Calcula los porcentajes
        double porcentajeRealizadasSi = (totalAgendas > 0) ? (totalRealizadasSi * 100.0 / totalAgendas) : 0;
        double porcentajeRealizadasNo = (totalAgendas > 0) ? (totalRealizadasNo * 100.0 / totalAgendas) : 0;
        double porcentajeReagendadas = (totalAgendas > 0) ? (totalReagendadas * 100.0 / totalAgendas) : 0;

        // Actualiza la propiedad en tu bean con la cantidad de agendas filtradas
        //setCantAgendasPorEmpleadoFiltradas(totalAgendas);
        setTotalAgendasParaElDia("Total de agendas: " + totalAgendas +
                "/  Total realizadas Si: " + totalRealizadasSi + " = " + porcentajeRealizadasSi + "%" + 
                "/  Total realizadas No: " + totalRealizadasNo + " = " + porcentajeRealizadasNo + "%" +
                "/  Total Reagendadas: " + totalReagendadas + " = " + porcentajeReagendadas + "%");
        
    }
    
    public int getCantAgendasPorEmpleadoFiltradas() {
        // Devuelve la lista de agendas filtradas o la información que desees mostrar
        return cantAgendasPorEmpleadoPorFechas;
    }

    private void setCantAgendasPorEmpleadoFiltradas(int cantidadAgendasFiltradas) {
        cantAgendasPorEmpleadoPorFechas = cantidadAgendasFiltradas;
    }

    public String getTotalAgendasParaElDia() {
        return totalAgendasParaElDia;
    }

    public void setTotalAgendasParaElDia(String totalAgendasParaElDia) {
        this.totalAgendasParaElDia = totalAgendasParaElDia;
    }

   }