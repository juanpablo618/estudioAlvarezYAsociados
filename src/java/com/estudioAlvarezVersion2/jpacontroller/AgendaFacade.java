package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Agenda;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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

    public List<Agenda> findRange(int first, int pageSize, String sortField, boolean sortAscending, Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Agenda> cq = cb.createQuery(Agenda.class);
        Root<Agenda> root = cq.from(Agenda.class);
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
                cq.orderBy(cb.asc(root.get("fecha")));
            } else {
                cq.orderBy(cb.desc(root.get("fecha")));
            }
        } else if (sortField != null && !sortField.isBlank()) {
            if (sortAscending) {
                cq.orderBy(cb.asc(root.get(sortField)));
            } else {
                cq.orderBy(cb.desc(root.get(sortField)));
            }
        } else {
            cq.orderBy(cb.asc(root.get("fecha")));
        }

        TypedQuery<Agenda> query = getEntityManager().createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int count(Map<String, Object> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Agenda> root = cq.from(Agenda.class);
        cq.select(cb.count(root));

        List<Predicate> predicates = buildPredicates(filters, cb, root);
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        return getEntityManager().createQuery(cq).getSingleResult().intValue();
    }

    private List<Predicate> buildPredicates(Map<String, Object> filters, CriteriaBuilder cb, Root<Agenda> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filters == null || filters.isEmpty()) {
            return predicates;
        }

        Object fechaFilter = filters.get("fecha");
        if (fechaFilter != null) {
            Date fecha = null;
            if (fechaFilter instanceof Date) {
                fecha = (Date) fechaFilter;
            } else if (fechaFilter instanceof String && !((String) fechaFilter).isBlank()) {
                try {
                    fecha = new java.text.SimpleDateFormat("dd/MM/yyyy").parse((String) fechaFilter);
                } catch (java.text.ParseException ex) {
                    fecha = null;
                }
            }
            if (fecha != null) {
                predicates.add(cb.equal(root.get("fecha"), fecha));
            }
        }

        Object diaFilter = filters.get("diaMesAnio");
        if (diaFilter != null && !diaFilter.toString().isBlank()) {
            String value = diaFilter.toString();
            if (value.length() == 10) {
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                    Date inicio = sdf.parse(value);
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(inicio);
                    cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
                    Date fin = cal.getTime();
                    predicates.add(cb.greaterThanOrEqualTo(root.get("fecha"), inicio));
                    predicates.add(cb.lessThan(root.get("fecha"), fin));
                } catch (java.text.ParseException ex) {
                    // ignorar filtro inválido
                }
            } else {
                predicates.add(cb.like(
                    cb.function("date_format", String.class, root.get("fecha"), cb.literal("%d/%m/%Y")),
                    "%" + value + "%"
                ));
            }
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

        Object apellidoNombreFilter = filters.get("apellidoNombre");
        if (apellidoNombreFilter != null && !apellidoNombreFilter.toString().isBlank()) {
            String value = "%" + apellidoNombreFilter.toString().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("apellido")), value),
                cb.like(cb.lower(root.get("nombre")), value)
            ));
        }

        Object responsableFilter = filters.get("responsable");
        if (responsableFilter != null && !responsableFilter.toString().isBlank()) {
            predicates.add(cb.equal(root.get("responsable"), responsableFilter.toString()));
        }

        Object descripcionFilter = filters.get("descripcion");
        if (descripcionFilter != null && !descripcionFilter.toString().isBlank()) {
            String value = "%" + descripcionFilter.toString().toLowerCase() + "%";
            predicates.add(cb.like(cb.lower(root.get("descripcion")), value));
        }

        Object realizadoFilter = filters.get("realizado");
        if (realizadoFilter != null && !realizadoFilter.toString().isBlank()) {
            predicates.add(cb.equal(root.get("realizado"), realizadoFilter.toString()));
        }

        return predicates;
    }
    
}
