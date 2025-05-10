/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.dao;

import id.my.mdn.kupu.app.santri.entity.Pengajaran;
import id.my.mdn.kupu.core.party.dao.PartyRelationshipFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class PengajaranFacade extends PartyRelationshipFacade<Pengajaran> {
    
    @Inject
    private EntityManager em;

    public PengajaranFacade() {
        super(Pengajaran.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
