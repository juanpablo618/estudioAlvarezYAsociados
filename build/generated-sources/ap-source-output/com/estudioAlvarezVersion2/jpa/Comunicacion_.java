package com.estudioAlvarezVersion2.jpa;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2025-02-15T12:41:16")
@StaticMetamodel(Comunicacion.class)
public class Comunicacion_ { 

    public static volatile SingularAttribute<Comunicacion, String> descripcion;
    public static volatile SingularAttribute<Comunicacion, Date> fecha;
    public static volatile SingularAttribute<Comunicacion, Integer> idComunicacion;
    public static volatile SingularAttribute<Comunicacion, String> responsable;
    public static volatile SingularAttribute<Comunicacion, Integer> orden;

}