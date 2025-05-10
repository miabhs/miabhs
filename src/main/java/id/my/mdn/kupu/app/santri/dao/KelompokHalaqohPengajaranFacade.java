/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.KelompokHalaqohPengajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class KelompokHalaqohPengajaranFacade extends AbstractFacade<KelompokHalaqohPengajaran> {
    
    @Inject
    private EntityManager em;

    public KelompokHalaqohPengajaranFacade() {
        super(KelompokHalaqohPengajaran.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
