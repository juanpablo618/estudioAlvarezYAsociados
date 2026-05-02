package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Agenda;
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
public class AgendaFacade extends AbstractFacade<Agenda> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgendaFacade() {
        super(Agenda.class);
    }
    
    public List<Agenda> findByResponsableAndFecha(String responsable, Date fecha) {
        return getEntityManager().createNamedQuery("Agenda.findByResponsableAndFecha", Agenda.class)
                             .setParameter("responsable", responsable)
                             .setParameter("fecha", fecha)
                             .getResultList();
    }
    
    public List<Agenda> getItemsByOrder(Integer orden) {
        TypedQuery<Agenda> query = em.createNamedQuery("Agenda.findByOrder", Agenda.class);
        query.setParameter("orden", orden);
        return query.getResultList();
    }
    
    public List<Agenda> findByResponsablesAndFecha(Set<String> responsables, Date fecha) {
        return em.createNamedQuery("Agenda.findByResponsablesAndFecha", Agenda.class)
             .setParameter("responsables", responsables)
             .setParameter("fecha", fecha)
             .getResultList();
    }
    

    public List<Agenda> findRangeLazy(int first, int pageSize, String sortField, boolean ascending, Map<String, Object> filters) {
        StringBuilder jpql = new StringBuilder("SELECT a FROM Agenda a WHERE 1=1");
        appendLazyFilters(jpql, filters);
        jpql.append(" ORDER BY ");
        if ("orden".equals(sortField)) {
            jpql.append("a.orden");
        } else if ("apellidoNombre".equals(sortField)) {
            jpql.append("a.apellido, a.nombre");
        } else if ("responsable".equals(sortField)) {
            jpql.append("a.responsable");
        } else if ("realizado".equals(sortField)) {
            jpql.append("a.realizado");
        } else {
            jpql.append("a.fecha");
        }
        jpql.append(ascending ? " ASC" : " DESC");
        jpql.append(", a.idAgenda DESC");

        Query q = em.createQuery(jpql.toString());
        setLazyFilterParameters(q, filters);
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    public int countLazy(Map<String, Object> filters) {
        StringBuilder jpql = new StringBuilder("SELECT COUNT(a) FROM Agenda a WHERE 1=1");
        appendLazyFilters(jpql, filters);
        Query q = em.createQuery(jpql.toString());
        setLazyFilterParameters(q, filters);
        return ((Long) q.getSingleResult()).intValue();
    }

    private void appendLazyFilters(StringBuilder jpql, Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) {
            return;
        }
        if (hasFilter(filters, "diaMesAnio")) {
            jpql.append(" AND FUNCTION('DATE_FORMAT', a.fecha, '%d/%m/%Y') LIKE :diaMesAnio");
        }
        if (hasFilter(filters, "orden")) {
            jpql.append(" AND CAST(a.orden AS string) LIKE :orden");
        }
        if (hasFilter(filters, "apellidoNombre")) {
            jpql.append(" AND LOWER(CONCAT(COALESCE(a.apellido,''), ' ', COALESCE(a.nombre,''))) LIKE :apellidoNombre");
        }
        if (hasFilter(filters, "responsable")) {
            jpql.append(" AND LOWER(a.responsable) LIKE :responsable");
        }
        if (hasFilter(filters, "realizado")) {
            jpql.append(" AND LOWER(a.realizado) LIKE :realizado");
        }
    }

    private void setLazyFilterParameters(Query query, Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) {
            return;
        }
        if (hasFilter(filters, "diaMesAnio")) {
            query.setParameter("diaMesAnio", "%" + String.valueOf(filters.get("diaMesAnio")).trim() + "%");
        }
        if (hasFilter(filters, "orden")) {
            query.setParameter("orden", "%" + String.valueOf(filters.get("orden")).trim() + "%");
        }
        if (hasFilter(filters, "apellidoNombre")) {
            query.setParameter("apellidoNombre", "%" + String.valueOf(filters.get("apellidoNombre")).trim().toLowerCase() + "%");
        }
        if (hasFilter(filters, "responsable")) {
            query.setParameter("responsable", "%" + String.valueOf(filters.get("responsable")).trim().toLowerCase() + "%");
        }
        if (hasFilter(filters, "realizado")) {
            query.setParameter("realizado", "%" + String.valueOf(filters.get("realizado")).trim().toLowerCase() + "%");
        }
    }

    private boolean hasFilter(Map<String, Object> filters, String key) {
        Object value = filters.get(key);
        return value != null && !String.valueOf(value).trim().isEmpty();
    }

    public List<Agenda> findAllSortedByDate() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Agenda> cq = cb.createQuery(Agenda.class);
        Root<Agenda> root = cq.from(Agenda.class);
        cq.select(root).orderBy(cb.asc(root.get("fecha"))); // Ordena por fecha ascendente

        return getEntityManager().createQuery(cq).getResultList();
    }
    
}
