/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class JenisKitabFacade extends AbstractFacade<JenisKitab> {
    
    @Inject
    private EntityManager em;

    public JenisKitabFacade() {
        super(JenisKitab.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
