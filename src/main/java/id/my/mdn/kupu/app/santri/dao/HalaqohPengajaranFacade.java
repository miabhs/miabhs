/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.PengampuHalaqoh;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.dao.AbstractPartyRoleFacade;
import id.my.mdn.kupu.core.party.dao.PartyRoleTypeFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class HalaqohPengajaranFacade extends AbstractPartyRoleFacade<HalaqohPengajaran> {

    @Inject
    private EntityManager em;

    @Inject
    private PartyRoleTypeFacade roleTypeFacade;
    
    @Inject
    private UstadzFacade ustadzFacade;

    public HalaqohPengajaranFacade() {
        super(HalaqohPengajaran.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected PartyRoleTypeFacade getRoleTypeFacade() {
        return roleTypeFacade;
    }

    @Override
    public Result<String> create(HalaqohPengajaran entity) {
        Ustadz pengampu = ustadzFacade.find(entity.getPengampuId());
        if (pengampu != null) {
            PengampuHalaqoh.builder()
                    .from(pengampu)
                    .to(entity);
        }
        Result<String> result = super.create(entity);
        return result;
    }

    @Override
    public Result<String> edit(HalaqohPengajaran entity) {
        return super.edit(entity);
    }

    @Override
    public Result<String> remove(HalaqohPengajaran entity) {
        return super.remove(entity);
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue,
            CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "name":
                return cb.equal(from[1].get("firstName"), filterValue);
            case "halaqoh":
                return cb.equal(from[0], filterValue);
            default:
                return null;
        }
    }

}
