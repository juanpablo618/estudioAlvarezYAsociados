/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpa;

import java.util.Date;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author juanp
 */
public class EventoParaTurno extends DefaultScheduleEvent{
    private String responsable;

    public EventoParaTurno(String titulo, Date empieza, Date termina, String responsable) {
            super(titulo, empieza, termina);
            this.responsable = responsable;

    }
    
    public EventoParaTurno(String responsable) {
        super();
        this.responsable = responsable;
    }

    public EventoParaTurno(String string, Date date, Date date0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public EventoParaTurno() {
        super();
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    

    public EventoParaTurno(String responsable, String title, Date start, Date end) {
        super(title, start, end);
        this.responsable = responsable;
    }

    public EventoParaTurno(String responsable, String title, Date start, Date end, boolean allDay) {
        super(title, start, end, allDay);
        this.responsable = responsable;
    }

    public EventoParaTurno(String responsable, String title, Date start, Date end, String styleClass) {
        super(title, start, end, styleClass);
        this.responsable = responsable;
    }

    public EventoParaTurno(String responsable, String title, Date start, Date end, Object data) {
        super(title, start, end, data);
        this.responsable = responsable;
    }
    
    
    
    
}
