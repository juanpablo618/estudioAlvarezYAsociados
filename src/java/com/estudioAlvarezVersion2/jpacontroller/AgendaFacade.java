package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Agenda;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
    public List<Agenda> findAllSortedByDate() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Agenda> cq = cb.createQuery(Agenda.class);
        Root<Agenda> root = cq.from(Agenda.class);
        cq.select(root).orderBy(cb.asc(root.get("fecha"))); // Ordena por fecha ascendente

        return getEntityManager().createQuery(cq).getResultList();
    }
    
}
