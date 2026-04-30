/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Turno;
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

    public int countForLazy(Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Turno> root = cq.from(Turno.class);
        cq.select(cb.count(root));
        cq.where(buildPredicates(filters, cb, root).toArray(new Predicate[0]));
        return getEntityManager().createQuery(cq).getSingleResult().intValue();
    }

    public List<Turno> findForLazy(int first, int pageSize, String sortField, boolean ascending, Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Turno> cq = cb.createQuery(Turno.class);
        Root<Turno> root = cq.from(Turno.class);
        cq.select(root);
        cq.where(buildPredicates(filters, cb, root).toArray(new Predicate[0]));

        String safeSortField = mapSortField(sortField);
        cq.orderBy(ascending ? cb.asc(root.get(safeSortField)) : cb.desc(root.get(safeSortField)));

        return getEntityManager().createQuery(cq)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private List<Predicate> buildPredicates(Map<String, Object> filters, CriteriaBuilder cb, Root<Turno> root) {
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
                Expression<String> fechaFormateada = cb.function("DATE_FORMAT", String.class, root.get("horaYDia"), cb.literal("%d/%m/%Y"));
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
            return "horaYDia";
        }
        if ("apellidoNombre".equals(sortField)) {
            return "apellido";
        }
        return sortField;
    }
    
}
