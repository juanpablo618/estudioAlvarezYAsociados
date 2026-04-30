package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Agenda;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
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
    
    public List<Agenda> findAllSortedByDate() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Agenda> cq = cb.createQuery(Agenda.class);
        Root<Agenda> root = cq.from(Agenda.class);
        cq.select(root).orderBy(cb.asc(root.get("fecha"))); // Ordena por fecha ascendente

        return getEntityManager().createQuery(cq).getResultList();
    }

    public int countForLazy(Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Agenda> root = cq.from(Agenda.class);
        cq.select(cb.count(root));
        cq.where(buildPredicates(filters, cb, root).toArray(new Predicate[0]));
        return getEntityManager().createQuery(cq).getSingleResult().intValue();
    }

    public List<Agenda> findForLazy(int first, int pageSize, String sortField, boolean ascending, Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Agenda> cq = cb.createQuery(Agenda.class);
        Root<Agenda> root = cq.from(Agenda.class);
        cq.select(root);
        cq.where(buildPredicates(filters, cb, root).toArray(new Predicate[0]));

        String safeSortField = mapSortField(sortField);
        cq.orderBy(ascending ? cb.asc(root.get(safeSortField)) : cb.desc(root.get(safeSortField)));

        return getEntityManager().createQuery(cq)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private List<Predicate> buildPredicates(Map<String, Object> filters, CriteriaBuilder cb, Root<Agenda> root) {
        List<Predicate> predicates = new java.util.ArrayList<>();
        if (filters == null) {
            return predicates;
        }
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null || String.valueOf(value).trim().isEmpty()) {
                continue;
            }
            String text = String.valueOf(value).trim();
            if ("diaMesAnio".equals(key)) {
                Expression<String> fechaFormateada = cb.function("DATE_FORMAT", String.class, root.get("fecha"), cb.literal("%d/%m/%Y"));
                predicates.add(cb.like(fechaFormateada, text + "%"));
            } else if ("apellidoNombre".equals(key)) {
                Expression<String> apellidoNombre = cb.concat(cb.concat(cb.coalesce(root.<String>get("apellido"), ""), " "), cb.coalesce(root.<String>get("nombre"), ""));
                predicates.add(cb.like(cb.lower(apellidoNombre), "%" + text.toLowerCase() + "%"));
            } else {
                predicates.add(cb.equal(root.get(key), value));
            }
        }
        return predicates;
    }

    private String mapSortField(String sortField) {
        if (sortField == null || sortField.trim().isEmpty() || "diaMesAnio".equals(sortField)) {
            return "fecha";
        }
        if ("apellidoNombre".equals(sortField)) {
            return "apellido";
        }
        return sortField;
    }
    
}
