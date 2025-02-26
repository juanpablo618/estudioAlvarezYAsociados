package com.estudioAlvarezVersion2.jpa;

import com.estudioAlvarezVersion2.jpa.Equipo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2025-02-25T22:06:54")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile SingularAttribute<Empleado, Date> fechaDeNacimiento;
    public static volatile SingularAttribute<Empleado, String> herramientas;
    public static volatile SingularAttribute<Empleado, String> password;
    public static volatile SingularAttribute<Empleado, String> domicilio;
    public static volatile SingularAttribute<Empleado, Date> fechaDeIngreso;
    public static volatile SingularAttribute<Empleado, Integer> idEmpleado;
    public static volatile SingularAttribute<Empleado, String> apellido;
    public static volatile SingularAttribute<Empleado, String> cargo;
    public static volatile SingularAttribute<Empleado, String> nombre;
    public static volatile ListAttribute<Empleado, Equipo> equipos;

}