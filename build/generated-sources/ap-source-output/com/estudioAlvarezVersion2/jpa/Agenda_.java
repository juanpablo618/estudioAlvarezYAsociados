package com.estudioAlvarezVersion2.jpa;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2025-02-25T22:06:54")
@StaticMetamodel(Agenda.class)
public class Agenda_ { 

    public static volatile SingularAttribute<Agenda, String> descripcion;
    public static volatile SingularAttribute<Agenda, Date> fecha;
    public static volatile SingularAttribute<Agenda, String> responsable;
    public static volatile SingularAttribute<Agenda, String> realizado;
    public static volatile SingularAttribute<Agenda, Integer> idAgenda;
    public static volatile SingularAttribute<Agenda, String> apellido;
    public static volatile SingularAttribute<Agenda, Integer> orden;
    public static volatile SingularAttribute<Agenda, String> nombre;
    public static volatile SingularAttribute<Agenda, String> prioridad;

}