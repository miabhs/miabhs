/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.santri.entity.JenisSantri;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.dao.PartyFacade;
import id.my.mdn.kupu.core.party.entity.GenderType;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 */
@Stateless
public class SantriFacade extends AbstractSqlFacade<Santri> {

    private static final String FIND_ALL
            = """
              SELECT ID,
                     PERSON_ID, PERSON_NAME, PERSON_GENDER, PERSON_DOB,
                     NAMABAPAK, NIS, JENISSANTRI, ANGKATAN, TAHUNMASUK_ID, TAHUNMASUK_NAME, TAHUNMASUK_FROMDATE,
                     KELOMPOKPENGASUHAN_ID, KELOMPOKPENGASUHAN_ORG_ID, KELOMPOKPENGASUHAN_NAME, KELOMPOKPENGASUHAN_KOORDINATOR, KELOMPOKPENGASUHAN_PENGASUHAN_FROMDATE,
                     STATUS, STATUS_FROMDATE
              FROM (
                  SELECT SN.ID,
                         PR.PARTY_ID AS PERSON_ID, CONCAT_WS(' ', PY.FIRSTNAME, PY.LASTNAME) AS PERSON_NAME, PN.GENDER AS PERSON_GENDER, PN.DATEOFBIRTH AS PERSON_DOB,
                         SN.NAMABAPAK, SN.NIS, SN.JENISSANTRI, SN.TAHUNMASUK_ID, TP.NAME AS TAHUNMASUK_NAME, TP.FROMDATE AS TAHUNMASUK_FROMDATE, SN.ANGKATAN,
                         PS.FROMROLE_ID AS KELOMPOKPENGASUHAN_ID, PYX.ID AS KELOMPOKPENGASUHAN_ORG_ID, PYX.FIRSTNAME AS KELOMPOKPENGASUHAN_NAME, PS.KOORDINATOR AS KELOMPOKPENGASUHAN_KOORDINATOR, PS.FROMDATE AS KELOMPOKPENGASUHAN_PENGASUHAN_FROMDATE,
                         SS.STATUS, SS.STATUS_FROMDATE
                  FROM MIABH_SANTRI AS SN
                  JOIN PARTY_PARTYROLE AS PR ON SN.ID = PR.ID
                  JOIN PARTY_PARTY AS PY ON PR.PARTY_ID = PY.ID
                  JOIN PARTY_PERSON AS PN ON PY.ID = PN.ID
                  JOIN MIABH_TAHUNPEMBELAJARAN AS TP ON SN.TAHUNMASUK_ID = TP.ID
                  LEFT JOIN (
                      SELECT PSZ.FROMROLE_ID, PSZ.TOROLE_ID, PSZ.FROMDATE, PSZ.KOORDINATOR
                      FROM (
                          SELECT PSX.FROMROLE_ID, PSX.TOROLE_ID, PSX.FROMDATE, PSX.KOORDINATOR
                          FROM MIABH_PENGASUHAN AS PSX
                          JOIN (    
                              SELECT PS.TOROLE_ID, MAX(PS.FROMDATE) AS FROMDATE
                              FROM MIABH_PENGASUHAN AS PS
                              WHERE PS.FROMDATE <= CURRENT_DATE
                              GROUP BY PS.TOROLE_ID
                          ) AS PSY
                          ON PSX.TOROLE_ID = PSY.TOROLE_ID AND PSX.FROMDATE = PSY.FROMDATE
                      ) AS PSZ
                      JOIN PARTY_PARTYRELATIONSHIP AS PREL
                      ON PSZ.FROMROLE_ID = PREL.FROMROLE_ID AND PSZ.TOROLE_ID = PREL.TOROLE_ID AND PSZ.FROMDATE = PREL.FROMDATE AND PREL.PARTYRELATIONSHIPTYPE_ID = 'Pengasuhan'
                  ) AS PS ON PS.TOROLE_ID = SN.ID        
                  LEFT JOIN PARTY_PARTYROLE AS PRX ON PS.FROMROLE_ID = PRX.ID
                  LEFT JOIN PARTY_PARTY AS PYX ON PRX.PARTY_ID = PYX.ID
                  LEFT JOIN (
                      SELECT SSY.SANTRI_ID, SSY.FROMDATE AS STATUS_FROMDATE, SSX.STATUS
                      FROM (
                              SELECT SANTRI_ID, MAX(FROMDATE) AS FROMDATE
                              FROM MIABH_STATUSSANTRI
                              WHERE FROMDATE <= CURRENT_DATE
                              GROUP BY SANTRI_ID
                      ) AS SSY
                      JOIN MIABH_STATUSSANTRI AS SSX
                      ON SSY.SANTRI_ID = SSX.SANTRI_ID AND SSY.FROMDATE = SSX.FROMDATE
                  ) AS SS ON SN.ID = SS.SANTRI_ID
              ) 
              """;

    private static final String FIND
            = """
              SELECT ID, PERSON_ID, PERSON_NAME, PERSON_GENDER, PERSON_DOB
              FROM (
                  SELECT SN.ID,
                         PR.PARTY_ID AS PERSON_ID, CONCAT_WS(' ', PY.FIRSTNAME, PY.LASTNAME) AS PERSON_NAME, PN.GENDER AS PERSON_GENDER, PN.DATEOFBIRTH AS PERSON_DOB
                  FROM MIABH_SANTRI AS SN
                  JOIN PARTY_PARTYROLE AS PR ON SN.ID = PR.ID
                  JOIN PARTY_PARTY AS PY ON PR.PARTY_ID = PY.ID
                  JOIN PARTY_PERSON AS PN ON PY.ID = PN.ID
              )
              WHERE ID = ?
              """;

    private static final String FIND_ALT
            = """
              SELECT ID, PERSON_ID, PERSON_NAME, PERSON_GENDER, PERSON_DOB,
                     NAMABAPAK, NIS, JENISSANTRI, ANGKATAN, TAHUNMASUK_ID, TAHUNMASUK_NAME, TAHUNMASUK_FROMDATE,
                     KELOMPOKPENGASUHAN_ID, KELOMPOKPENGASUHAN_ORG_ID, KELOMPOKPENGASUHAN_NAME, KELOMPOKPENGASUHAN_KOORDINATOR, KELOMPOKPENGASUHAN_PENGASUHAN_FROMDATE
              FROM (
                  SELECT SN.ID,
                         PR.PARTY_ID AS PERSON_ID, CONCAT_WS(' ', PY.FIRSTNAME, PY.LASTNAME) AS PERSON_NAME, PN.GENDER AS PERSON_GENDER, PN.DATEOFBIRTH AS PERSON_DOB,
                         SN.NAMABAPAK, SN.NIS, SN.JENISSANTRI, SN.ANGKATAN, SN.TAHUNMASUK_ID, TP.NAME AS TAHUNMASUK_NAME, TP.FROMDATE AS TAHUNMASUK_FROMDATE,
                         PS.FROMROLE_ID AS KELOMPOKPENGASUHAN_ID, PYX.ID AS KELOMPOKPENGASUHAN_ORG_ID, PYX.FIRSTNAME AS KELOMPOKPENGASUHAN_NAME, PS.KOORDINATOR AS KELOMPOKPENGASUHAN_KOORDINATOR, PS.FROMDATE AS KELOMPOKPENGASUHAN_PENGASUHAN_FROMDATE
                  FROM MIABH_SANTRI AS SN
                  JOIN PARTY_PARTYROLE AS PR ON SN.ID = PR.ID
                  JOIN PARTY_PARTY AS PY ON PR.PARTY_ID = PY.ID
                  JOIN PARTY_PERSON AS PN ON PY.ID = PN.ID
                  JOIN MIABH_TAHUNPEMBELAJARAN AS TP ON SN.TAHUNMASUK_ID = TP.ID
                  LEFT JOIN (
                      SELECT PSZ.FROMROLE_ID, PSZ.TOROLE_ID, PSZ.FROMDATE, PSZ.KOORDINATOR
                      FROM (
                          SELECT PSX.FROMROLE_ID, PSX.TOROLE_ID, PSX.FROMDATE, PSX.KOORDINATOR
                          FROM MIABH_PENGASUHAN AS PSX
                          JOIN (    
                              SELECT PS.TOROLE_ID, MAX(PS.FROMDATE) AS FROMDATE
                              FROM MIABH_PENGASUHAN AS PS
                              WHERE PS.FROMDATE <= CURRENT_DATE
                              GROUP BY PS.TOROLE_ID
                          ) AS PSY
                          ON PSX.TOROLE_ID = PSY.TOROLE_ID AND PSX.FROMDATE = PSY.FROMDATE
                      ) AS PSZ
                      JOIN PARTY_PARTYRELATIONSHIP AS PREL
                      ON PSZ.FROMROLE_ID = PREL.FROMROLE_ID AND PSZ.TOROLE_ID = PREL.TOROLE_ID AND PSZ.FROMDATE = PREL.FROMDATE AND PREL.PARTYRELATIONSHIPTYPE_ID = 'Pengasuhan'
                  ) AS PS ON PS.TOROLE_ID = SN.ID        
                  LEFT JOIN PARTY_PARTYROLE AS PRX ON PS.FROMROLE_ID = PRX.ID
                  LEFT JOIN PARTY_PARTY AS PYX ON PRX.PARTY_ID = PYX.ID
              )
              WHERE ID = ?
              """;

    @Inject
    private EntityManager em;

    @Inject
    private PartyFacade partyFacade;

    @Inject
    private ApplicationSecurityGroupFacade groupFacade;

    @Inject
    private ApplicationUserFacade appUserFacade;

    @Inject
    private NikSeedFacade nisFacade;

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
                String nameQuery = filterData.value != null ? (String) filterData.value : "";
                if (nameQuery.equals("")) {
                    return null;
                }
                return "(UPPER(PERSON_NAME) LIKE '%" + nameQuery.toUpperCase() + "%')";
            case "santri":
                Santri santri = (Santri) filterData.value;
                return "ID = " + santri.getId();
            case "id":
                Long id = (Long) filterData.value;
                return "ID = " + id;
            case "tahunMasuk":
                return "TAHUNMASUK_ID = " + filterData.value;
            case "gender":
                GenderType gender = (GenderType) filterData.value;
                return "PERSON_GENDER = '" + gender.name() + "'";
            case "jenisSantri":
                JenisSantri jenisSantri = (JenisSantri) filterData.value;
                return "JENISSANTRI = '" + jenisSantri.name() + "'";
            case "statusKesantrian":
                StatusKesantrian status = (StatusKesantrian) filterData.value;
                return "STATUS = '" + status.name() + "'";
            case "kelompokPengasuhan":
                KelompokPengasuhan kelompokPengasuhan = (KelompokPengasuhan) filterData.value;
                return "KELOMPOKPENGASUHAN_ID = " + kelompokPengasuhan.getId();
            case "party":
                Party party = (Party) filterData.value;
                return "PERSON_ID = " + party.getId();
            default:
                return null;
        }
    }

    @Override
    protected String translateOrderField(String fieldName) {
        switch (fieldName) {
            case "nis":
                return "NIS";
            case "name":
                return "PERSON_NAME";
            case "dateOfBirth":
                return "PERSON_DOB";
            default:
                return null;
        }
    }

    @Override
    protected String getFindAllQuery() {
        return FIND_ALL;
    }

    @Override
    protected String getFindQuery() {
        return FIND;
    }

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
//            getEntityManager().flush();
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

    public Result<String> createAndGenerateNis(Santri entity) {
        Result<String> result = create(entity);
        nisFacade.generateNis(entity);
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

    public void generateNis(List<Santri> listSantri) {

        nisFacade.generateNis(listSantri.stream().filter(s -> s.getNis().isEmpty()).collect(Collectors.toList()));
    }

    public Santri findAlt(Long id) {
        try {
            return (Santri) em.createNativeQuery(FIND_ALT, "Santri")
                    .setParameter(1, id)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Inject
    private AktifitasFacade aktifitasFacade;

    @Inject
    private BentukAktifitasFacade bentukAktifitasFacade;

    public void generateAktifitas(int n) {
        RandomGenerator rnd = RandomGenerator.getDefault();

        LocalDate now = LocalDate.now();
        List<String> idBentukList = bentukAktifitasFacade.findAll().stream().map(b -> b.getId()).collect(Collectors.toList());

        for (int i = 0; i < n; i++) {
            Aktifitas a = new Aktifitas();
            int nextDateInc = rnd.nextInt(0, 150);
            a.setActivityDate(now.plusDays(nextDateInc));

            Santri santri = null;
            while (santri == null) {
                long santriId = rnd.nextLong(12, 611);
                santri = em.find(Santri.class, santriId);
            }
            a.setSantri(santri);

            int nextBentuk = rnd.nextInt(0, idBentukList.size());
            a.setBentukAktifitas(bentukAktifitasFacade.find(idBentukList.get(nextBentuk)));

            a.setConfirmed(true);

            aktifitasFacade.create(a);
        }
    }

}
