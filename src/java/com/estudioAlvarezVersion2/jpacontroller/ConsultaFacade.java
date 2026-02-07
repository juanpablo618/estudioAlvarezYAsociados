/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Consulta;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author developer
 */
@Stateless
public class ConsultaFacade extends AbstractFacade<Consulta> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsultaFacade() {
        super(Consulta.class);
    }
    
    public List<Consulta> findAllExceptArchivedOrDismissed() {
        TypedQuery<Consulta> query = em.createNamedQuery("Consulta.findAllExceptArchivedOrDismissed", Consulta.class);
        return query.getResultList();
    }
    
    public boolean isCuitExisting(String cuit) {
        try {
            // Consulta JPQL para verificar si el CUIT ya existe en la base de datos
            Long count = em.createQuery("SELECT COUNT(c) FROM Consulta c WHERE c.cuit = :cuit", Long.class)
                            .setParameter("cuit", cuit)
                            .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isPhoneExisting(String telefono) {
        try {
            // Consulta JPQL para verificar si el CUIT ya existe en la base de datos
            Long count = em.createQuery("SELECT COUNT(c) FROM Consulta c WHERE c.telefono = :telefono", Long.class)
                            .setParameter("telefono", telefono)
                            .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Consulta> findRange(int first, int pageSize, String sortField, boolean sortAscending, Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Consulta> cq = cb.createQuery(Consulta.class);
        Root<Consulta> root = cq.from(Consulta.class);
        cq.select(root);

        List<Predicate> predicates = buildPredicates(filters, cb, root);
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        if ("apellidoNombre".equals(sortField)) {
            if (sortAscending) {
                cq.orderBy(cb.asc(root.get("apellido")), cb.asc(root.get("nombre")));
            } else {
                cq.orderBy(cb.desc(root.get("apellido")), cb.desc(root.get("nombre")));
            }
        } else if ("fechaDeAtencionFormateada".equals(sortField)) {
            if (sortAscending) {
                cq.orderBy(cb.asc(root.get("fechaDeAtencion")));
            } else {
                cq.orderBy(cb.desc(root.get("fechaDeAtencion")));
            }
        } else if (sortField != null && !sortField.trim().isEmpty()) {
            if (sortAscending) {
                cq.orderBy(cb.asc(root.get(sortField)));
            } else {
                cq.orderBy(cb.desc(root.get(sortField)));
            }
        } else {
            cq.orderBy(cb.desc(root.get("fechaDeAtencion")));
        }

        TypedQuery<Consulta> query = getEntityManager().createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int count(Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Consulta> root = cq.from(Consulta.class);
        cq.select(cb.count(root));

        List<Predicate> predicates = buildPredicates(filters, cb, root);
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        return getEntityManager().createQuery(cq).getSingleResult().intValue();
    }

    private List<Predicate> buildPredicates(Map<String, Object> filters, CriteriaBuilder cb, Root<Consulta> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filters == null || filters.isEmpty()) {
            return predicates;
        }

        Object fechaFilter = filters.get("fechaDeAtencionFormateada");
        if (fechaFilter != null && !fechaFilter.toString().trim().isEmpty()) {
            String value = fechaFilter.toString();
            if (value.length() == 10) {
                DateRange range = buildDateRange(value);
                if (range != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("fechaDeAtencion"), range.inicio()));
                    predicates.add(cb.lessThan(root.get("fechaDeAtencion"), range.fin()));
                }
            } else {
                predicates.add(cb.like(
                    cb.function("date_format", String.class, root.get("fechaDeAtencion"), cb.literal("%d/%m/%Y")),
                    "%" + value + "%"
                ));
            }
        }

        Object apellidoNombreFilter = filters.get("apellidoNombre");
        if (apellidoNombreFilter != null && !apellidoNombreFilter.toString().trim().isEmpty()) {
            String value = "%" + apellidoNombreFilter.toString().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("apellido")), value),
                cb.like(cb.lower(root.get("nombre")), value)
            ));
        }

        Object equipoFilter = filters.get("equipo");
        if (equipoFilter != null && !equipoFilter.toString().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("equipo")), "%" + equipoFilter.toString().toLowerCase() + "%"));
        }

        Object telefonoFilter = filters.get("telefono");
        if (telefonoFilter != null && !telefonoFilter.toString().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("telefono")), "%" + telefonoFilter.toString().toLowerCase() + "%"));
        }

        Object cuitFilter = filters.get("cuit");
        if (cuitFilter != null && !cuitFilter.toString().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("cuit")), "%" + cuitFilter.toString().toLowerCase() + "%"));
        }

        Object responsableFilter = filters.get("responsable");
        if (responsableFilter != null && !responsableFilter.toString().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("responsable")), "%" + responsableFilter.toString().toLowerCase() + "%"));
        }

        Object procedenciaFilter = filters.get("procedencia");
        if (procedenciaFilter != null && !procedenciaFilter.toString().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("procedencia")), "%" + procedenciaFilter.toString().toLowerCase() + "%"));
        }

        Object estadoFilter = filters.get("estadoConsulta");
        if (estadoFilter != null && !estadoFilter.toString().trim().isEmpty()) {
            predicates.add(cb.equal(root.get("estadoConsulta"), estadoFilter.toString()));
        }

        return predicates;
    }

    private DateRange buildDateRange(String dateStr) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.util.Date inicio = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(inicio);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            java.util.Date fin = cal.getTime();
            return new DateRange(inicio, fin);
        } catch (java.text.ParseException ex) {
            return null;
        }
    }

    private static class DateRange {
        private final java.util.Date inicio;
        private final java.util.Date fin;

        private DateRange(java.util.Date inicio, java.util.Date fin) {
            this.inicio = inicio;
            this.fin = fin;
        }

        private java.util.Date inicio() {
            return inicio;
        }

        private java.util.Date fin() {
            return fin;
        }
    }
    
}
