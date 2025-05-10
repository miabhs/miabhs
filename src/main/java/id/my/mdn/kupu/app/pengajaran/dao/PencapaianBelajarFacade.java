/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.dao;

import id.my.mdn.kupu.app.pengajaran.entity.PencapaianBelajar;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PencapaianBelajarFacade extends AbstractFacade<PencapaianBelajar> {

    @Inject
    private EntityManager em;

    public PencapaianBelajarFacade() {
        super(PencapaianBelajar.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
