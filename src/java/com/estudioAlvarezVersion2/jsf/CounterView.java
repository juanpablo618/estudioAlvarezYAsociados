/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jsf;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author juanp
 */


@Named
@ViewScoped
public class CounterView implements Serializable {
     
    private Date number ;
    
    
    public CounterView() {
        // Inicializar la fecha con la fecha actual (hoy)
        number = new Date();
    }
 
    public String getNumber() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

        String dateString = dateFormat.format(number);
        
        
        return dateString;
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
    
    
}