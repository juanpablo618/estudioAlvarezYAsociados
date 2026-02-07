package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Expediente;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.SortOrder;

/**
 *
 * @author juanpablo618@hotmail.com
 */
@Stateless
public class ExpedienteFacade extends AbstractFacade<Expediente> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExpedienteFacade() {
        super(Expediente.class);
    }
    
    public String findClaveCidiByOrden(int orden) {
        try {
            List<String> results =  em.createNamedQuery("Expediente.findClaveCidiByOrden", String.class)
                     .setParameter("orden", orden)
                     .getResultList();
            
            if (results.isEmpty()) {
                return null;
            } else {
                return results.get(0);
            }
            
            
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public String findClaveSeguridadSocialByOrden(int orden) {
        try {
            List<String> results = em.createNamedQuery("Expediente.findClaveSeguridadSocialByOrden", String.class)
                                     .setParameter("orden", orden)
                                     .getResultList();
            if (results.isEmpty()) {
                return null;
            } else {
                return results.get(0);
            }
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public String findClaveFiscalByOrden(int orden) {
        try {
              List<String> results =  em.createNamedQuery("Expediente.findClaveFiscalByOrden", String.class)
                     .setParameter("orden", orden)
                     .getResultList();
              
              if (results.isEmpty()) {
                return null;
            } else {
                return results.get(0);
            }
              
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public String findCuitByOrden(int orden) {
        try {
            List<String> results = em.createNamedQuery("Expediente.findCuitByOrden", String.class)
                     .setParameter("orden", orden)
                     .getResultList();
            
            if (results.isEmpty()) {
                return null;
            } else {
                return results.get(0);
            }
            
        } catch (NoResultException e) {
            return null;
        }
    }
    
    
      // Método para obtener solo expedientes activos
    public List<Expediente> findActiveExpedientes() {
        return em.createQuery("SELECT e FROM Expediente e WHERE e.activo = 'Si'", Expediente.class)
                 .getResultList();
    }
    
    
        public List<Expediente> findWithPaginationAll(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String queryString = "SELECT e FROM Expediente e WHERE 1=1 ";

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            queryString += " AND LOWER(e." + filter.getKey() + ") LIKE LOWER(:filter_" + filter.getKey() + ")";
        }

        if (sortField != null) {
            queryString += " ORDER BY e." + sortField + (sortOrder == SortOrder.ASCENDING ? " ASC" : " DESC");
        }

        TypedQuery<Expediente> query = em.createQuery(queryString, Expediente.class);

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            query.setParameter("filter_" + filter.getKey(), "%" + filter.getValue() + "%");
        }

        query.setFirstResult(first);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

        public List<Expediente> findWithPaginationActivos(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String queryString = "SELECT e FROM Expediente e WHERE 1=1 AND e.activo = 'Si'";

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            queryString += " AND LOWER(e." + filter.getKey() + ") LIKE LOWER(:filter_" + filter.getKey() + ")";
        }

        if (sortField != null) {
            queryString += " ORDER BY e." + sortField + (sortOrder == SortOrder.ASCENDING ? " ASC" : " DESC");
        }

        TypedQuery<Expediente> query = em.createQuery(queryString, Expediente.class);

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            query.setParameter("filter_" + filter.getKey(), "%" + filter.getValue() + "%");
        }

        query.setFirstResult(first);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
        
    public int countFilteredAll(Map<String, Object> filters) {
        String queryString = "SELECT COUNT(e) FROM Expediente e WHERE 1=1 AND e.activo = 'Si'";

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            queryString += " AND LOWER(e." + filter.getKey() + ") LIKE LOWER(:filter_" + filter.getKey() + ")";
        }

        TypedQuery<Long> query = em.createQuery(queryString, Long.class);

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            query.setParameter("filter_" + filter.getKey(), "%" + filter.getValue() + "%");
        }

        return query.getSingleResult().intValue();
    }
    
    public int countFilteredActivos(Map<String, Object> filters) {
        String queryString = "SELECT COUNT(e) FROM Expediente e WHERE 1=1 AND e.activo = 'Si'";

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            queryString += " AND LOWER(e." + filter.getKey() + ") LIKE LOWER(:filter_" + filter.getKey() + ")";
        }

        TypedQuery<Long> query = em.createQuery(queryString, Long.class);

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            query.setParameter("filter_" + filter.getKey(), "%" + filter.getValue() + "%");
        }

        return query.getSingleResult().intValue();
    }

    public List<Expediente> findRange(int first, int pageSize, String sortField, boolean sortAscending, Map<String, Object> filters, boolean onlyActivos) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Expediente> cq = cb.createQuery(Expediente.class);
        Root<Expediente> root = cq.from(Expediente.class);
        cq.select(root);

        List<Predicate> predicates = buildPredicates(filters, cb, root, onlyActivos);
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        if ("apellidoYNombre".equals(sortField)) {
            if (sortAscending) {
                cq.orderBy(cb.asc(root.get("apellido")), cb.asc(root.get("nombre")));
            } else {
                cq.orderBy(cb.desc(root.get("apellido")), cb.desc(root.get("nombre")));
            }
        } else if (sortField != null && !sortField.isBlank()) {
            if (sortAscending) {
                cq.orderBy(cb.asc(root.get(sortField)));
            } else {
                cq.orderBy(cb.desc(root.get(sortField)));
            }
        } else {
            cq.orderBy(cb.asc(root.get("idExpediente")));
        }

        TypedQuery<Expediente> query = getEntityManager().createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int count(Map<String, Object> filters, boolean onlyActivos) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Expediente> root = cq.from(Expediente.class);
        cq.select(cb.count(root));

        List<Predicate> predicates = buildPredicates(filters, cb, root, onlyActivos);
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        return getEntityManager().createQuery(cq).getSingleResult().intValue();
    }

    private List<Predicate> buildPredicates(Map<String, Object> filters, CriteriaBuilder cb, Root<Expediente> root, boolean onlyActivos) {
        List<Predicate> predicates = new ArrayList<>();
        if (onlyActivos) {
            predicates.add(cb.equal(root.get("activo"), "Si"));
        }
        if (filters == null || filters.isEmpty()) {
            return predicates;
        }

        Object ordenFilter = filters.get("orden");
        if (ordenFilter != null && !ordenFilter.toString().isBlank()) {
            try {
                Integer orden = Integer.valueOf(ordenFilter.toString());
                predicates.add(cb.equal(root.get("orden"), orden));
            } catch (NumberFormatException ex) {
                // ignorar filtro inválido
            }
        }

        Object apellidoNombreFilter = filters.get("apellidoYNombre");
        if (apellidoNombreFilter != null && !apellidoNombreFilter.toString().isBlank()) {
            String value = "%" + apellidoNombreFilter.toString().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("apellido")), value),
                cb.like(cb.lower(root.get("nombre")), value)
            ));
        }

        Object cuitFilter = filters.get("cuit");
        if (cuitFilter != null && !cuitFilter.toString().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("cuit")), "%" + cuitFilter.toString().toLowerCase() + "%"));
        }

        return predicates;
    }
    
}
