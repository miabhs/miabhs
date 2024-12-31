/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.dao;

import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasHarian;
import id.my.mdn.kupu.app.pengasuhan.entity.Status;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import id.my.mdn.kupu.core.party.entity.Person;
import java.time.LocalDate;
import java.util.Map;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class AktifitasHarianFacade extends AbstractFacade<AktifitasHarian> {

    @Inject
    private EntityManager em;

    public AktifitasHarianFacade() {
        super(AktifitasHarian.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected From[] selectFind(CriteriaQuery cq, Map<String, Object> parameters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Root<AktifitasHarian> aktifitasHarian = cq.from(AktifitasHarian.class);
        Join<AktifitasHarian, Santri> santri = aktifitasHarian.join("santri");

        cq.select(aktifitasHarian);

        return new From[]{aktifitasHarian, santri};
    }

    @Override
    protected From[] selectCount(CriteriaQuery cq, Map<String, Object> parameters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Root<AktifitasHarian> aktifitasHarian = cq.from(AktifitasHarian.class);
        Join<AktifitasHarian, Santri> santri = aktifitasHarian.join("santri");

        cq.select(cb.count(aktifitasHarian));

        return new From[]{aktifitasHarian, santri};
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "name":
                if (filterValue.equals("")) {
                    return null;
                }

                Join<Santri, Person> person = cb.treat(from[1].join("party"), Person.class);

                return cb.or(
                        cb.like(cb.upper(person.get("firstName")),
                                ("%" + filterValue + "%").toUpperCase()),
                        cb.like(cb.upper(person.get("lastName")),
                                ("%" + filterValue + "%").toUpperCase())
                );
            case "kelompokPengasuhan":
                Join<Santri, PartyRelationship> pengasuhan = from[1].join("targetRelationships");
                return cb.and(
                        cb.equal(pengasuhan.get("fromRole").get("partyRoleType").get("id"), "KelompokPengasuhan"),
                        cb.equal(pengasuhan.get("partyRelationshipType").get("id"), "Pengasuhan"),
                        cb.equal(pengasuhan.get("fromRole"), filterValue)
                );
            case "santri":
                return cb.equal(from[0].get("santri"), filterValue);
            case "santriId":
                return cb.equal(from[0].get("santri").get("id"), filterValue);
            case "fromDate":
                LocalDate fromDate = (LocalDate) filterValue;
                return cb.greaterThanOrEqualTo(from[0].get("id").get("date"), fromDate);
            case "thruDate":
                LocalDate thruDate = (LocalDate) filterValue;
                return cb.lessThanOrEqualTo(from[0].get("id").get("date"), thruDate);
            case "date":
                LocalDate date = (LocalDate) filterValue;
                return cb.equal(from[0].get("id").get("date"), date);
            case "status":
                return cb.or(
                        cb.equal(from[0].get("status"), Status.MERAH),
                        cb.equal(from[0].get("status"), Status.KUNING)
                );
            default:
                return null;
        }
    }

    @Override
    protected Expression orderExpression(String field, From... from) {
        if (field.equals("id.date")) {
            return from[0].get("id").get("date");
        } else if (field.equals("santri.id")) {
            return from[0].get("santri").get("id");
        } else {
            return null;
        }
    }

}
