package com.estudioAlvarezVersion2.jpacontroller.util;

import com.estudioAlvarezVersion2.jpa.Expediente;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
public class ExpedienteUtils {

    public static Predicate<Expediente> filtroTipoDeTramite(String tipoDeTramiteSelected) {
        
        if (tipoDeTramiteSelected != null && !"".equals(tipoDeTramiteSelected)) {
            return (Expediente l) -> {
                if (l.getTipoDeTramite() != null) {

        
        if(l.getTipoDeTramite().equalsIgnoreCase("Jubilación Ordinaria")) {
            l.setTipoDeTramite("JUBILACION ORDINARIA") ; 
        }
 
                    return l.getTipoDeTramite().equalsIgnoreCase(tipoDeTramiteSelected) ;
                } else {
                    return false;
                }

            };
        } else {
            return (Expediente l) -> {
               return true;

            };
        }

    }

    public static Predicate<Expediente> filtroFechaDeCumple(String fechaDeCumpleSelected) {
        System.out.println("ACA!!!: "+fechaDeCumpleSelected);
        if (fechaDeCumpleSelected != null && !"".equals(fechaDeCumpleSelected)) {
            return (Expediente l) -> {
                if (l.getFechaDeNacimiento() != null) {
                    System.out.println("entro al ultimo if de filtroFechaDeCumple");

                    Date date = l.getFechaDeNacimiento();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(date);

                    String sSubCadena = strDate.substring(0, 5);
                    System.out.println("sSubCadena: " + sSubCadena);

                    return sSubCadena.equals(fechaDeCumpleSelected);
                } else {
                    return false;
                }
            };
        } else {
            return (Expediente l) -> {
                return true;
            };
        }
    }

    public static Predicate<Expediente> filtroEstadoDelTramite(String estadoDelTramiteSelected) {
        if (estadoDelTramiteSelected != null && !"".equals(estadoDelTramiteSelected)) {
            return (Expediente l) -> {
                if (l.getEstadoDelTramite() != null) {
                    System.out.println("entro al ultimo if de filtroEstadoDelTramite");

                    return l.getEstadoDelTramite().equals(estadoDelTramiteSelected);

                } else {
                    return false;
                }
            };
        } else {
            return (Expediente l) -> {
                return true;
            };
        }
    }

    public static Predicate<Expediente> filtroResponsable(String responsableSelected) {
       System.out.println("entro al predicate filtroResponsable");
       System.out.println("ACA responsableSelected: "+responsableSelected);

        if (responsableSelected != null && !"".equals(responsableSelected)) {
            return (Expediente l) -> {
                if (l.getResponsable()!= null) {
                    System.out.println("entro al ultimo if de filtroApoderado");

                    return l.getResponsable().equals(responsableSelected);

                } else {
                    return false;
                }
            };
        }
        return (Expediente l) -> {
            return true;
        };
    }

    public static Predicate<Expediente> filtroTipoDeExpediente(String tipoDeExpedienteSelected) {
        if (tipoDeExpedienteSelected != null && !"".equals(tipoDeExpedienteSelected)) {
            return (Expediente l) -> {
                if (l.getTipoDeExpediente() != null) {

                    System.out.println("entro al ultimo if de filtroTipoDeExpediente");

                    return l.getTipoDeExpediente().equals(tipoDeExpedienteSelected);
                } else {
                    return false;
                }
            };
        }
        return (Expediente l) -> {
            return true;
        };
    }

    public static Predicate<Expediente> filtroSexo(String sexoSelected) {
        if (sexoSelected != null && !"".equals(sexoSelected)) {
            return (Expediente l) -> {
                if (l.getSexo() != null) {
                    System.out.println("entro al ultimo if de filtroSexo");

                    return l.getSexo().equals(sexoSelected);

                } else {
                    return false;
                }
            };
        }
        return (Expediente l) -> {
            return true;
        };
    }

}
