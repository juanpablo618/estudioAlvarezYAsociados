package com.estudioAlvarezVersion2.downloadPDf;

import com.estudioAlvarezVersion2.jpa.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
@Stateless
public class ConfiguracionesGeneralesFacade extends AbstractFacade<ConfiguracionesGenerales> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfiguracionesGeneralesFacade() {
        super(ConfiguracionesGenerales.class);
    }
    
}
