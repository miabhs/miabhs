/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.dao;

import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class HikmahKauniyahFacade extends AbstractSqlFacade<HikmahKauniyah> {

    private static final String FINDALL
            = """
            SELECT HK0.ID, HK0.CONTENT, HK0.EVENT_DATE,
            HK0.SANTRI_ID, SANTRI5.PARTY_ID, SANTRI5.FIRSTNAME, SANTRI5.LASTNAME,
            SANTRI5.KELOMPOKPENGASUHANID, SANTRI5.KELOMPOKPENGASUHANORGANIZATIONID,
            SANTRI5.KELOMPOKPENGASUHANORGANIZATIONNAME, HK0.CREATED
            FROM MIABH_HIKMAHKAUNIYAH AS HK0
            LEFT JOIN (SELECT SANTRI4.ID, SANTRI4.PARTY_ID, SANTRI4.FIRSTNAME, SANTRI4.LASTNAME,
            SANTRI4.KOORDINATOR, KELOMPOKPENGASUHAN2.ID AS KELOMPOKPENGASUHANID,
            KELOMPOKPENGASUHAN2.PARTY_ID AS KELOMPOKPENGASUHANORGANIZATIONID,
            KELOMPOKPENGASUHAN2.NAME AS KELOMPOKPENGASUHANORGANIZATIONNAME
            FROM (SELECT SANTRI3.ID, SANTRI3.PARTY_ID, SANTRI3.FIRSTNAME, SANTRI3.LASTNAME,
            PENGASUHAN2.KOORDINATOR, PENGASUHAN2.FROMROLE_ID
                 FROM (SELECT SANTRI2.ID, SANTRI2.PARTY_ID, SANTRI2.FIRSTNAME,
                       SANTRI2.LASTNAME
                       FROM (SELECT SANTRI1.ID, SANTRI1.PARTY_ID,
                             PERSON1.FIRSTNAME, PERSON1.LASTNAME
                             FROM (SELECT SANTRI0.ID, PARTYROLE0.PARTY_ID
                                   FROM MIABH_SANTRI AS SANTRI0
                                   JOIN PARTY_PARTYROLE AS PARTYROLE0
                                   ON SANTRI0.ID = PARTYROLE0.ID
                                   WHERE PARTYROLE0.FROMDATE <=  CURRENT_DATE
                                   AND (PARTYROLE0.THRUDATE IS NULL OR PARTYROLE0.THRUDATE >= CURRENT_DATE)
                            ) AS SANTRI1
                            JOIN PARTY_PERSON AS PERSON1
                            ON SANTRI1.PARTY_ID = PERSON1.ID
                       ) AS SANTRI2
                       JOIN MIABH_STATUSSANTRI AS STATUSSANTRI0
                       ON SANTRI2.ID = STATUSSANTRI0.SANTRI_ID
                       WHERE STATUSSANTRI0.FROMDATE <=  CURRENT_DATE
                       AND (STATUSSANTRI0.THRUDATE IS NULL OR STATUSSANTRI0.THRUDATE >= CURRENT_DATE)
                       AND STATUSSANTRI0.STATUS = 'ACTIVE'
                ) AS SANTRI3
                    JOIN (SELECT PENGASUHAN1.FROMROLE_ID, PENGASUHAN1.TOROLE_ID,
                          PENGASUHAN1.FROMDATE, PARTYRELATIONSHIP1.THRUDATE, PENGASUHAN1.KOORDINATOR
                          FROM MIABH_PENGASUHAN AS PENGASUHAN1
                          JOIN PARTY_PARTYRELATIONSHIP AS PARTYRELATIONSHIP1
                          ON PENGASUHAN1.PARTYRELATIONSHIPTYPE_ID = PARTYRELATIONSHIP1.PARTYRELATIONSHIPTYPE_ID
                          AND PENGASUHAN1.FROMROLE_ID = PARTYRELATIONSHIP1.FROMROLE_ID
                          AND PENGASUHAN1.TOROLE_ID = PARTYRELATIONSHIP1.TOROLE_ID
                          AND PENGASUHAN1.FROMDATE = PARTYRELATIONSHIP1.FROMDATE
                    ) AS PENGASUHAN2
                    ON SANTRI3.ID = PENGASUHAN2.TOROLE_ID
                    WHERE PENGASUHAN2.FROMDATE <=  CURRENT_DATE
                    AND (PENGASUHAN2.THRUDATE IS NULL OR PENGASUHAN2.THRUDATE >= CURRENT_DATE)
            ) AS SANTRI4
            JOIN (SELECT KELOMPOKPENGASUHAN1.ID, KELOMPOKPENGASUHAN1.PARTY_ID, ORGANIZATION0.NAME
                    FROM (SELECT KELOMPOKPENGASUHAN0.ID, PARTYROLE0.PARTY_ID
                            FROM MIABH_KELOMPOKPENGASUHAN AS KELOMPOKPENGASUHAN0
                            JOIN PARTY_PARTYROLE AS PARTYROLE0
                            ON KELOMPOKPENGASUHAN0.ID = PARTYROLE0.ID
                    ) AS KELOMPOKPENGASUHAN1
                    JOIN PARTY_ORGANIZATION AS ORGANIZATION0
                    ON KELOMPOKPENGASUHAN1.PARTY_ID = ORGANIZATION0.ID
            ) AS KELOMPOKPENGASUHAN2
            ON SANTRI4.FROMROLE_ID = KELOMPOKPENGASUHAN2.ID
            ) AS SANTRI5 ON HK0.SANTRI_ID = SANTRI5.ID
            """;

    @Inject
    private EntityManager em;

    public HikmahKauniyahFacade() {
        super(HikmahKauniyah.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String getFindAllQuery() {
        return FINDALL;
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterData.name) {
            case "id":
                return "HK0.ID = '" + filterData.value + "'";
            case "santri":
                Santri santri = (Santri) filterData.value;
                return "HK0.SANTRI_ID = " + santri.getId();
            case "santriId":
                Long santriId = (Long) filterData.value;
                return "HK0.SANTRI_ID = " + santriId;
            case "kelompokPengasuhan":
                KelompokPengasuhan kelompokPengasuhan = (KelompokPengasuhan) filterData.value;
                return "SANTRI5.KELOMPOKPENGASUHANID = " + kelompokPengasuhan.getId();
            case "fromDate":
                LocalDate fromDate = (LocalDate) filterData.value;
                return "HK0.EVENT_DATE >= '"
                        + DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT)
                                .format(fromDate) + "'";
            case "thruDate":
                LocalDate thruDate = (LocalDate) filterData.value;
                return "HK0.EVENT_DATE <= '"
                        + DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT)
                                .format(thruDate) + "'";
            default:
                return null;
        }
    }

    @Override
    protected String translateOrderField(String fieldName) {
        switch (fieldName) {
            case "date":
                return "HK0.EVENT_DATE";
            case "name":
                return "SANTRI5.FIRSTNAME";
            case "created":
                return "HK0.CREATED";
            default:
                return super.translateOrderField(fieldName);
        }
    }

//    @Override
//    public void edit(HikmahKauniyah entity) {
//        HikmahKauniyah existingEntity = getEntityManager().find(HikmahKauniyah.class, entity.getId());
//        existingEntity.setDate(entity.getDate());
//        if (entity.getSantri() != null) {
//            existingEntity.setSantri(
//                    getEntityManager().find(Santri.class, entity.getSantri().getId())
//            );
//
//        }
//        existingEntity.setContent(entity.getContent());
//        getEntityManager().merge(existingEntity);
//    }
}
