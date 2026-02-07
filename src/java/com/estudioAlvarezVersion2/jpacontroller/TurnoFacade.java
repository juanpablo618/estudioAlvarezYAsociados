/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Turno;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
 * @author juanpablo618@hotmail.com
 */
@Stateless
public class TurnoFacade extends AbstractFacade<Turno> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TurnoFacade() {
        super(Turno.class);
    }
    
    public List<Turno> findByResponsable(String responsable) {
        
        return em.createNamedQuery("Turno.findByResponsable", Turno.class)
                 .setParameter("responsable", responsable)
                 .getResultList();
    }

    public List<Turno> findByResponsableAndRange(String responsable, java.util.Date inicio, java.util.Date fin) {
        return em.createNamedQuery("Turno.findByResponsableAndRange", Turno.class)
                 .setParameter("responsable", responsable)
                 .setParameter("inicio", inicio)
                 .setParameter("fin", fin)
                 .getResultList();
    }
    
    
    public List<Turno> findByResponsables(Set<String> responsables) {
    
        return getEntityManager().createNamedQuery("Turno.findByResponsables", Turno.class)
                             .setParameter("responsables", responsables)
                             .getResultList();
    }

    public List<Turno> findByResponsablesAndRange(Set<String> responsables, java.util.Date inicio, java.util.Date fin) {
        return getEntityManager().createNamedQuery("Turno.findByResponsablesAndRange", Turno.class)
                             .setParameter("responsables", responsables)
                             .setParameter("inicio", inicio)
                             .setParameter("fin", fin)
                             .getResultList();
    }

    public List<Turno> findByRange(java.util.Date inicio, java.util.Date fin) {
        return getEntityManager().createNamedQuery("Turno.findByRange", Turno.class)
                             .setParameter("inicio", inicio)
                             .setParameter("fin", fin)
                             .getResultList();
    }
    

    public List<Turno> findAllSortedByDate() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Turno> cq = cb.createQuery(Turno.class);
        Root<Turno> root = cq.from(Turno.class);
        cq.select(root).orderBy(cb.asc(root.get("horaYDia"))); // Ordena por horaYDia ascendente

        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<Turno> findRange(int first, int pageSize, String sortField, boolean sortAscending, Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Turno> cq = cb.createQuery(Turno.class);
        Root<Turno> root = cq.from(Turno.class);
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
        } else if ("diaMesAnio".equals(sortField)) {
            if (sortAscending) {
                cq.orderBy(cb.asc(root.get("horaYDia")));
            } else {
                cq.orderBy(cb.desc(root.get("horaYDia")));
            }
        } else if (sortField != null && !sortField.trim().isEmpty()) {
            if (sortAscending) {
                cq.orderBy(cb.asc(root.get(sortField)));
            } else {
                cq.orderBy(cb.desc(root.get(sortField)));
            }
        } else {
            cq.orderBy(cb.asc(root.get("horaYDia")));
        }

        TypedQuery<Turno> query = getEntityManager().createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int count(Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Turno> root = cq.from(Turno.class);
        cq.select(cb.count(root));

        List<Predicate> predicates = buildPredicates(filters, cb, root);
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        return getEntityManager().createQuery(cq).getSingleResult().intValue();
    }

    private List<Predicate> buildPredicates(Map<String, Object> filters, CriteriaBuilder cb, Root<Turno> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filters == null || filters.isEmpty()) {
            return predicates;
        }

        Object diaFilter = filters.get("diaMesAnio");
        if (diaFilter != null && !diaFilter.toString().trim().isEmpty()) {
            String value = diaFilter.toString();
            if (value.length() == 10) {
                DateRange range = buildDateRange(value);
                if (range != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("horaYDia"), range.inicio()));
                    predicates.add(cb.lessThan(root.get("horaYDia"), range.fin()));
                }
            } else {
                predicates.add(cb.like(
                    cb.function("date_format", String.class, root.get("horaYDia"), cb.literal("%d/%m/%Y")),
                    "%" + value + "%"
                ));
            }
        }

        Object ordenFilter = filters.get("orden");
        if (ordenFilter != null && !ordenFilter.toString().trim().isEmpty()) {
            try {
                Integer orden = Integer.valueOf(ordenFilter.toString());
                predicates.add(cb.equal(root.get("orden"), orden));
            } catch (NumberFormatException ex) {
                // ignorar filtro inválido
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

        Object responsableFilter = filters.get("responsable");
        if (responsableFilter != null && !responsableFilter.toString().trim().isEmpty()) {
            predicates.add(cb.equal(root.get("responsable"), responsableFilter.toString()));
        }

        Object realizadoFilter = filters.get("realizado");
        if (realizadoFilter != null && !realizadoFilter.toString().trim().isEmpty()) {
            predicates.add(cb.equal(root.get("realizado"), realizadoFilter.toString()));
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

    public List<Turno> getItemsByOrder(Integer orden) {
        TypedQuery<Turno> query = em.createNamedQuery("Turno.findByOrder", Turno.class);
        query.setParameter("orden", orden);
        return query.getResultList();
    }
    
}
