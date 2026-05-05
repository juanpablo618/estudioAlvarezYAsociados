/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Turno;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    
    public List<Turno> findByHoraYDia(java.util.Date horaYDia) {
        return em.createNamedQuery("Turno.findByHoraYDia", Turno.class)
                 .setParameter("horaYDia", horaYDia)
                 .getResultList();
    }
    
    public List<Turno> findByResponsable(String responsable) {
        
        return em.createNamedQuery("Turno.findByResponsable", Turno.class)
                 .setParameter("responsable", responsable)
                 .getResultList();
    }
    
    
    public List<Turno> findByResponsables(Set<String> responsables) {
    
        return getEntityManager().createNamedQuery("Turno.findByResponsables", Turno.class)
                             .setParameter("responsables", responsables)
                             .getResultList();
    }
    


    public List<Turno> findRangeLazy(int first, int pageSize, String sortField, boolean ascending, Map<String, Object> filters) {
        StringBuilder jpql = new StringBuilder("SELECT t FROM Turno t WHERE 1=1");
        appendLazyFilters(jpql, filters);
        jpql.append(" ORDER BY ");
        if ("orden".equals(sortField)) {
            jpql.append("t.orden");
        } else if ("apellidoNombre".equals(sortField)) {
            jpql.append("t.apellido, t.nombre");
        } else if ("responsable".equals(sortField)) {
            jpql.append("t.responsable");
        } else if ("realizado".equals(sortField)) {
            jpql.append("t.realizado");
        } else {
            jpql.append("t.horaYDia");
        }
        jpql.append(ascending ? " ASC" : " DESC");
        jpql.append(", t.idTurno DESC");

        Query q = em.createQuery(jpql.toString());
        setLazyFilterParameters(q, filters);
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    public int countLazy(Map<String, Object> filters) {
        StringBuilder jpql = new StringBuilder("SELECT COUNT(t) FROM Turno t WHERE 1=1");
        appendLazyFilters(jpql, filters);
        Query q = em.createQuery(jpql.toString());
        setLazyFilterParameters(q, filters);
        return ((Long) q.getSingleResult()).intValue();
    }

    private void appendLazyFilters(StringBuilder jpql, Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) return;
        if (hasFilter(filters, "diaMesAnio")) jpql.append(" AND t.horaYDia >= :fechaDesde AND t.horaYDia < :fechaHasta");
        if (hasFilter(filters, "orden")) jpql.append(" AND t.orden = :orden");
        if (hasFilter(filters, "apellidoNombre")) jpql.append(" AND LOWER(CONCAT(COALESCE(t.apellido,''), ' ', COALESCE(t.nombre,''))) LIKE :apellidoNombre");
        if (hasFilter(filters, "responsable")) jpql.append(" AND LOWER(t.responsable) LIKE :responsable");
        if (hasFilter(filters, "realizado")) jpql.append(" AND LOWER(t.realizado) LIKE :realizado");
    }

    private void setLazyFilterParameters(Query query, Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) return;
        if (hasFilter(filters, "diaMesAnio")) {
            Date[] rangoFecha = parseDateFilter(String.valueOf(filters.get("diaMesAnio")));
            if (rangoFecha != null) {
                query.setParameter("fechaDesde", rangoFecha[0]);
                query.setParameter("fechaHasta", rangoFecha[1]);
            }
        }
        if (hasFilter(filters, "orden")) {
            Integer orden = parseIntegerFilter(filters.get("orden"));
            if (orden != null) {
                query.setParameter("orden", orden);
            }
        }
        if (hasFilter(filters, "apellidoNombre")) query.setParameter("apellidoNombre", "%" + String.valueOf(filters.get("apellidoNombre")).trim().toLowerCase() + "%");
        if (hasFilter(filters, "responsable")) query.setParameter("responsable", "%" + String.valueOf(filters.get("responsable")).trim().toLowerCase() + "%");
        if (hasFilter(filters, "realizado")) query.setParameter("realizado", "%" + String.valueOf(filters.get("realizado")).trim().toLowerCase() + "%");
    }

    private boolean hasFilter(Map<String, Object> filters, String key) {
        Object value = filters.get(key);
        if (value == null || String.valueOf(value).trim().isEmpty()) {
            return false;
        }
        if ("orden".equals(key)) {
            return parseIntegerFilter(value) != null;
        }
        if ("diaMesAnio".equals(key)) {
            return parseDateFilter(String.valueOf(value)) != null;
        }
        return true;
    }

    private Integer parseIntegerFilter(Object value) {
        try {
            return Integer.valueOf(String.valueOf(value).trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Date[] parseDateFilter(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty() || trimmed.length() != 10) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(trimmed);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date from = cal.getTime();
            cal.add(Calendar.DATE, 1);
            Date to = cal.getTime();
            return new Date[]{from, to};
        } catch (ParseException ex) {
            return null;
        }
    }

    public List<Turno> findAllSortedByDate() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Turno> cq = cb.createQuery(Turno.class);
        Root<Turno> root = cq.from(Turno.class);
        cq.select(root).orderBy(cb.asc(root.get("horaYDia"))); // Ordena por horaYDia ascendente

        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<Turno> getItemsByOrder(Integer orden) {
        TypedQuery<Turno> query = em.createNamedQuery("Turno.findByOrder", Turno.class);
        query.setParameter("orden", orden);
        return query.getResultList();
    }
    
}
