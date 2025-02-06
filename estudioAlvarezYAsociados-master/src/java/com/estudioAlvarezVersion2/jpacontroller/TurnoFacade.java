/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Turno;
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
 * @author developer
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
    
}
