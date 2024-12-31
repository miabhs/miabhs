/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.Pengasuhan;
import id.my.mdn.kupu.core.party.dao.PartyRelationshipFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PengasuhanFacade extends PartyRelationshipFacade<Pengasuhan> {
    
    @Inject
    private EntityManager em;

    public PengasuhanFacade() {
        super(Pengasuhan.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void makePengasuhanAsDefaultKorrdinator(Pengasuhan pengasuhan) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Pengasuhan> cq = cb.createQuery(entityClass);

        Root<Pengasuhan> root = cq.from(entityClass);
        cq.select(root).where(
                cb.equal(root.get("fromRole"), pengasuhan.getKelompokPengasuhan())
        );

        TypedQuery<Pengasuhan> q = getEntityManager().createQuery(cq);

        q.getResultList().stream()
                .peek(pcm -> {
                    if (pcm.equals(pengasuhan)) {
                        pcm.setKoordinator(true);
                    } else {
                        pcm.setKoordinator(false);
                    }
                }).forEach(this::edit);
    }
    
}
