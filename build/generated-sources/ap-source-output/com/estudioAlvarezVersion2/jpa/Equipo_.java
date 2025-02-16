package com.estudioAlvarezVersion2.jpa;

import com.estudioAlvarezVersion2.jpa.Empleado;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2025-02-15T12:41:16")
@StaticMetamodel(Equipo.class)
public class Equipo_ { 

    public static volatile SingularAttribute<Equipo, Integer> idEquipo;
    public static volatile ListAttribute<Equipo, Empleado> empleados;
    public static volatile SingularAttribute<Equipo, String> nombre;

}