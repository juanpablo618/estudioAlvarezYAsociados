/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.EventoDeCausasJudiciales;
import java.util.List;
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
public class EventoDeCausasJudicialesFacade extends AbstractFacade<EventoDeCausasJudiciales> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoDeCausasJudicialesFacade() {
        super(EventoDeCausasJudiciales.class);
    }

    public List<EventoDeCausasJudiciales> findByNombre(String nombre) {
        return em.createNamedQuery("EventoDeCausasJudiciales.findByNombre", EventoDeCausasJudiciales.class)
                 .setParameter("nombre", nombre)
                 .getResultList();
    }

    public List<EventoDeCausasJudiciales> findByApellido(String apellido) {
        return em.createNamedQuery("EventoDeCausasJudiciales.findByApellido", EventoDeCausasJudiciales.class)
                 .setParameter("apellido", apellido)
                 .getResultList();
    }

    public List<EventoDeCausasJudiciales> findAllSortedByFecha() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<EventoDeCausasJudiciales> cq = cb.createQuery(EventoDeCausasJudiciales.class);
        Root<EventoDeCausasJudiciales> root = cq.from(EventoDeCausasJudiciales.class);
        cq.select(root).orderBy(cb.asc(root.get("fecha"))); // Ordena por fecha ascendente

        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<EventoDeCausasJudiciales> getItemsByOrder(Integer orden) {
        TypedQuery<EventoDeCausasJudiciales> query = em.createNamedQuery("EventoDeCausasJudiciales.findByOrden", EventoDeCausasJudiciales.class);
        query.setParameter("orden", orden);
        return query.getResultList();
    }
}
