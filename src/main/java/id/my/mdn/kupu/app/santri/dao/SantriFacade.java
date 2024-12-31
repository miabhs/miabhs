/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.dao.PartyFacade;
import id.my.mdn.kupu.core.party.entity.Party;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.security.dao.ApplicationSecurityGroupFacade;
import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author aphasan
 */
@Stateless
public class SantriFacade extends AbstractSqlFacade<Santri> {

    private static final String FIND_ALL
            = """
              SELECT ID, PARTYID, PARTYROLETYPEID, FIRSTNAME, LASTNAME, NAMABAPAK, GENDER, DATEOFBIRTH, NIS, STATUS, TAHUNMASUKID, TAHUNMASUKNAME, TAHUNMASUKFROMDATE, KELOMPOKPENGASUHANID, KELOMPOKPENGASUHANPARTYID, KELOMPOKPENGASUHANPARTYNAME, KOORDINATOR
              FROM (SELECT SANTRI4.ID, SANTRI4.PARTY_ID AS PARTYID, SANTRI4.PARTYROLETYPE_ID AS PARTYROLETYPEID, SANTRI4.FIRSTNAME, SANTRI4.LASTNAME, SANTRI4.NAMABAPAK, SANTRI4.GENDER, SANTRI4.DATEOFBIRTH AS DATEOFBIRTH, SANTRI4.KOORDINATOR,
                     KELOMPOKPENGASUHAN2.ID AS KELOMPOKPENGASUHANID, KELOMPOKPENGASUHAN2.PARTY_ID AS KELOMPOKPENGASUHANPARTYID,
                     KELOMPOKPENGASUHAN2.NAME AS KELOMPOKPENGASUHANPARTYNAME, SANTRI4.STATUS AS STATUS, SANTRI4.NIS,
                     SANTRI4.TAHUNMASUK_ID AS TAHUNMASUKID, SANTRI4.TAHUNMASUKNAME, SANTRI4.TAHUNMASUKFROMDATE
                   FROM (SELECT SANTRI3.ID, SANTRI3.PARTY_ID, SANTRI3.PARTYROLETYPE_ID, SANTRI3.FIRSTNAME, SANTRI3.LASTNAME, SANTRI3.NAMABAPAK, SANTRI3.GENDER, SANTRI3.DATEOFBIRTH,
                               PENGASUHAN2.KOORDINATOR, PENGASUHAN2.FROMROLE_ID, SANTRI3.STATUS,
                              SANTRI3.NIS, SANTRI3.TAHUNMASUK_ID, SANTRI3.TAHUNMASUKNAME, SANTRI3.TAHUNMASUKFROMDATE
                        FROM (SELECT SANTRI2.ID, SANTRI2.PARTY_ID, SANTRI2.PARTYROLETYPE_ID, SANTRI2.FIRSTNAME, SANTRI2.LASTNAME, SANTRI2.NAMABAPAK, SANTRI2.GENDER, SANTRI2.DATEOFBIRTH,
                                     STATUSSANTRI0.STATUS, SANTRI2.NIS, SANTRI2.TAHUNMASUK_ID, SANTRI2.TAHUNMASUKNAME, SANTRI2.TAHUNMASUKFROMDATE
                                   FROM (SELECT SANTRI1.ID, SANTRI1.PARTY_ID, SANTRI1.PARTYROLETYPE_ID, PERSON1.FIRSTNAME, PERSON1.LASTNAME, SANTRI1.NAMABAPAK, PERSON1.GENDER, PERSON1.DATEOFBIRTH,
                                              SANTRI1.NIS, SANTRI1.TAHUNMASUK_ID, SANTRI1.TAHUNMASUKNAME, SANTRI1.TAHUNMASUKFROMDATE
                                         FROM (SELECT SANTRI0.ID, PARTYROLE0.PARTY_ID, PARTYROLE0.PARTYROLETYPE_ID, SANTRI0.NAMABAPAK, SANTRI0.NIS, SANTRI0.TAHUNMASUK_ID, SANTRI0.TAHUNMASUKNAME, SANTRI0.TAHUNMASUKFROMDATE
                                               FROM (SELECT S.ID, S.NAMABAPAK, S.NIS, S.TAHUNMASUK_ID, TP.NAME AS TAHUNMASUKNAME, TP.FROMDATE AS TAHUNMASUKFROMDATE
                                                     FROM MIABH_SANTRI S
                                                     JOIN MIABH_TAHUNPEMBELAJARAN TP ON S.TAHUNMASUK_ID = TP.ID
                                               ) AS SANTRI0
                                               JOIN PARTY_PARTYROLE AS PARTYROLE0
                                               ON SANTRI0.ID = PARTYROLE0.ID
                                               WHERE PARTYROLE0.FROMDATE <=  CURRENT_DATE AND (PARTYROLE0.THRUDATE IS NULL OR PARTYROLE0.THRUDATE >= CURRENT_DATE)
                                        ) AS SANTRI1
                                        JOIN PARTY_PERSON AS PERSON1
                                        ON SANTRI1.PARTY_ID = PERSON1.ID
                                   ) AS SANTRI2
                                   JOIN MIABH_STATUSSANTRI AS STATUSSANTRI0
                                   ON SANTRI2.ID = STATUSSANTRI0.SANTRI_ID
                                   WHERE STATUSSANTRI0.FROMDATE <=  CURRENT_DATE AND (STATUSSANTRI0.THRUDATE IS NULL OR STATUSSANTRI0.THRUDATE >= CURRENT_DATE)
                       ) AS SANTRI3
                       LEFT JOIN (SELECT PENGASUHAN1.FROMROLE_ID, PENGASUHAN1.TOROLE_ID, PENGASUHAN1.FROMDATE, PARTYRELATIONSHIP1.THRUDATE, PENGASUHAN1.KOORDINATOR
                              FROM MIABH_PENGASUHAN AS PENGASUHAN1
                              JOIN PARTY_PARTYRELATIONSHIP AS PARTYRELATIONSHIP1
                              ON PENGASUHAN1.PARTYRELATIONSHIPTYPE_ID = PARTYRELATIONSHIP1.PARTYRELATIONSHIPTYPE_ID
                              AND PENGASUHAN1.FROMROLE_ID = PARTYRELATIONSHIP1.FROMROLE_ID
                              AND PENGASUHAN1.TOROLE_ID = PARTYRELATIONSHIP1.TOROLE_ID
                              AND PENGASUHAN1.FROMDATE = PARTYRELATIONSHIP1.FROMDATE
                              WHERE PARTYRELATIONSHIP1.FROMDATE <=  CURRENT_DATE AND (PARTYRELATIONSHIP1.THRUDATE IS NULL OR PARTYRELATIONSHIP1.THRUDATE >= CURRENT_DATE)
                       ) AS PENGASUHAN2
                       ON SANTRI3.ID = PENGASUHAN2.TOROLE_ID
                   ) AS SANTRI4
                   LEFT JOIN (SELECT KELOMPOKPENGASUHAN1.ID, KELOMPOKPENGASUHAN1.PARTY_ID, ORGANIZATION0.NAME
                           FROM (SELECT KELOMPOKPENGASUHAN0.ID, PARTYROLE0.PARTY_ID
                                   FROM MIABH_KELOMPOKPENGASUHAN AS KELOMPOKPENGASUHAN0
                                   JOIN PARTY_PARTYROLE AS PARTYROLE0
                                   ON KELOMPOKPENGASUHAN0.ID = PARTYROLE0.ID
                           ) AS KELOMPOKPENGASUHAN1
                           JOIN PARTY_ORGANIZATION AS ORGANIZATION0
                           ON KELOMPOKPENGASUHAN1.PARTY_ID = ORGANIZATION0.ID
                   ) AS KELOMPOKPENGASUHAN2
               ON SANTRI4.FROMROLE_ID = KELOMPOKPENGASUHAN2.ID
              )
              """;

    @Inject
    private EntityManager em;

    public SantriFacade() {
        super(Santri.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        switch (filterData.name) {
            case "name":
                String nameQuery = (String) filterData.value;
                if (nameQuery.equals("")) {
                    return null;
                }
                return "(UPPER(FIRSTNAME) LIKE '%" + nameQuery.toUpperCase() + "%' "
                        + "OR UPPER(LASTNAME) LIKE '%" + nameQuery.toUpperCase() + "%')";
            case "santri":
                Santri santri = (Santri) filterData.value;
                return "ID = " + santri.getId();
            case "id":
                Long id = (Long) filterData.value;
                return "ID = " + id;
            case "statusKesantrian":
                StatusKesantrian status = (StatusKesantrian) filterData.value;
                return "STATUS = '" + status.name() + "'";
            case "kelompokPengasuhan":
                KelompokPengasuhan kelompokPengasuhan = (KelompokPengasuhan) filterData.value;
                return "KELOMPOKPENGASUHANID = " + kelompokPengasuhan.getId();
            case "party":
                Party party = (Party) filterData.value;
                return "PARTYID = " + party.getId();
            default:
                return null;
        }
    }

    @Override
    protected String getFindAllQuery() {
        return FIND_ALL;
    }

    @Inject
    private PartyFacade partyFacade;

    @Inject
    private ApplicationSecurityGroupFacade groupFacade;

    @Inject
    private ApplicationUserFacade appUserFacade;

    @Override
    public Result<String> create(Santri entity) {

        Result<String> result = null;

        entity.setFromDate(
                LocalDateTime.of(
                        entity.getTahunMasuk().getFromDate(),
                        LocalTime.of(0, 0, 0, 0)
                )
        );

        Person person = (Person) entity.getParty();
//        ApplicationSecurityGroup santriGroup = groupFacade.findByName("santri", "Santri");

        if (person.getId() == null) {
            result = partyFacade.create(person);
            getEntityManager().flush();
//            if(santriGroup != null) {
//                appUserFacade.create(person, entity.getNis(), entity.getNis(),
//                        santriGroup);
//            }
        } else {
            person = getEntityManager().find(Person.class, person.getId());
            person.getRoles().add(entity);
            result = partyFacade.edit(person);
        }

        return result;
    }

    @Override
    public Result<String> remove(Santri entity) {

        Result<String> result = null;

        entity = getEntityManager().find(Santri.class, entity.getId());
        Party party = entity.getParty();
        party.getRoles().remove(entity);
        entity.setParty(null);

        try {
            getEntityManager().remove(entity);
            getEntityManager().flush();
            removeIfUseless(party);
            result = new Result<>(true, "Data berhasil dihapus!");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "{0}.remove() method failed to remove entity ", getClass().getCanonicalName());
            result = new Result<>(false, "Penghapusan data gagal!");
        }

        return result;
    }

    private void removeIfUseless(Party party) {
        List<PartyRole> roles = getAllRoles(party);
        if (roles.isEmpty()) {
            getEntityManager().remove(party);
        }
    }

    private List<PartyRole> getAllRoles(Party party) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PartyRole> cq = cb.createQuery(PartyRole.class);
        Root<Party> root = cq.from(Party.class);
        Join<Party, PartyRole> roles = root.join("roles");

        cq.select(roles)
                .where(
                        cb.equal(root.get("id"), party.getId())
                );

        return getEntityManager().createQuery(cq).getResultList();
    }

}
