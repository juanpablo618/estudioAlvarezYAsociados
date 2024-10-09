/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Expediente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author developer
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
    
    
}
