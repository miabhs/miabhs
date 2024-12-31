/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.dao;

import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasTambahan;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import java.time.LocalDate;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class AktifitasTambahanFacade extends AbstractFacade<AktifitasTambahan> {

    @Inject
    private EntityManager em;

    public AktifitasTambahanFacade() {
        super(AktifitasTambahan.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "aktifitasHarian":
                return cb.equal(from[0].get("aktifitasHarian"), filterValue);
            case "kehadiran":
                return cb.equal(from[0].get("kehadiran"), filterValue);
            case "santriId":
                return cb.equal(from[0].get("id").get("aktifitasHarian").get("santri"), filterValue);
            case "fromDate":
                LocalDate fromDate = (LocalDate) filterValue;
                return cb.greaterThanOrEqualTo(from[0].get("id").get("aktifitasHarian").get("date"), fromDate);
            case "thruDate":
                LocalDate thruDate = (LocalDate) filterValue;
                return cb.lessThanOrEqualTo(from[0].get("id").get("aktifitasHarian").get("date"), thruDate);
            default:
                return null;
        }
    }

    @Override
    protected Expression orderExpression(String field, From... from) {
        if (field.equals("bentukAktifitas")) {
            return from[0].get("bentukAktifitas").get("id");
        } else {
            return null;
        }
    }

}
