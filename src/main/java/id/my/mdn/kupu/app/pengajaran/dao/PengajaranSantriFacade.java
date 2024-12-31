/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.dao;

import id.my.mdn.kupu.app.pengajaran.entity.PengajaranSantri;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PengajaranSantriFacade extends AbstractSqlFacade<PengajaranSantri> {

    private static final String FINDALL = 
            """
            SELECT PENGAJARAN1.ID AS ID,
                   PENGAJARAN1.PENGAJARANDATE,
                   PENGAJARAN1.CREATED,
                   PENGAJARAN1.NOTES,
                   PENGAJARAN1.SANTRI_ID AS SANTRIID, SANTRI5.PARTY_ID AS PERSONID, SANTRI5.FIRSTNAME, SANTRI5.LASTNAME,
                   SANTRI5.KELOMPOKPENGASUHANID, SANTRI5.KELOMPOKPENGASUHANORGANIZATIONID, SANTRI5.KELOMPOKPENGASUHANORGANIZATIONNAME,
                   PENGAJARAN1.JENISKITAB_ID AS JENISKITABID, PENGAJARAN1.JUDUL
            FROM (SELECT PENGAJARAN0.ID, PENGAJARAN0.CREATED, PENGAJARAN0.PENGAJARANDATE, PENGAJARAN0.SANTRI_ID, PENGAJARAN0.JENISKITAB_ID, PENGAJARAN0.NOTES,
                         JENISKITAB0.JUDUL
                    FROM MIABH_PENGAJARANSANTRI AS PENGAJARAN0
                    LEFT JOIN MIABH_JENISKITAB AS JENISKITAB0
                    ON PENGAJARAN0.JENISKITAB_ID = JENISKITAB0.ID
            ) AS PENGAJARAN1
            LEFT JOIN (SELECT SANTRI4.ID, SANTRI4.PARTY_ID, SANTRI4.FIRSTNAME, SANTRI4.LASTNAME, SANTRI4.KOORDINATOR,
                              KELOMPOKPENGASUHAN2.ID AS KELOMPOKPENGASUHANID, KELOMPOKPENGASUHAN2.PARTY_ID AS KELOMPOKPENGASUHANORGANIZATIONID,
                              KELOMPOKPENGASUHAN2.NAME AS KELOMPOKPENGASUHANORGANIZATIONNAME
                       FROM (SELECT SANTRI3.ID, SANTRI3.PARTY_ID, SANTRI3.FIRSTNAME, SANTRI3.LASTNAME, PENGASUHAN2.KOORDINATOR, PENGASUHAN2.FROMROLE_ID
                             FROM (SELECT SANTRI2.ID, SANTRI2.PARTY_ID, SANTRI2.FIRSTNAME, SANTRI2.LASTNAME
                                        FROM (SELECT SANTRI1.ID, SANTRI1.PARTY_ID, PERSON1.FIRSTNAME, PERSON1.LASTNAME
                                              FROM (SELECT SANTRI0.ID, PARTYROLE0.PARTY_ID
                                                    FROM MIABH_SANTRI AS SANTRI0
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
                                        AND STATUSSANTRI0.STATUS = 'ACTIVE'
                            ) AS SANTRI3
                                JOIN (SELECT PENGASUHAN1.FROMROLE_ID, PENGASUHAN1.TOROLE_ID, PENGASUHAN1.FROMDATE, PARTYRELATIONSHIP1.THRUDATE, PENGASUHAN1.KOORDINATOR
                                        FROM MIABH_PENGASUHAN AS PENGASUHAN1
                                        JOIN PARTY_PARTYRELATIONSHIP AS PARTYRELATIONSHIP1
                                        ON PENGASUHAN1.PARTYRELATIONSHIPTYPE_ID = PARTYRELATIONSHIP1.PARTYRELATIONSHIPTYPE_ID
                                        AND PENGASUHAN1.FROMROLE_ID = PARTYRELATIONSHIP1.FROMROLE_ID
                                        AND PENGASUHAN1.TOROLE_ID = PARTYRELATIONSHIP1.TOROLE_ID
                                        AND PENGASUHAN1.FROMDATE = PARTYRELATIONSHIP1.FROMDATE
                                ) AS PENGASUHAN2
                                ON SANTRI3.ID = PENGASUHAN2.TOROLE_ID
                                WHERE PENGASUHAN2.FROMDATE <=  CURRENT_DATE AND (PENGASUHAN2.THRUDATE IS NULL OR PENGASUHAN2.THRUDATE >= CURRENT_DATE)
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
            ) AS SANTRI5
            ON PENGAJARAN1.SANTRI_ID = SANTRI5.ID
            """;

    @Inject
    private EntityManager em;

    public PengajaranSantriFacade() {
        super(PengajaranSantri.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        switch (filterData.name) {
            case "id":
                String id = (String) filterData.value;
                return "pengajaran1.id = '" + id + "'";
            case "santri":
                Santri santri = (Santri) filterData.value;
                return "pengajaran1.santri_id = " + santri.getId();
            case "santriId":
                Long santriId = (Long) filterData.value;
                return "pengajaran1.santri_id = " + santriId;
            case "kelompokPengasuhan":
                KelompokPengasuhan kelompokPengasuhan = (KelompokPengasuhan) filterData.value;
                return "santri5.kelompokPengasuhanId = " + kelompokPengasuhan.getId();
            case "fromDate":
                LocalDate fromDate = (LocalDate) filterData.value;
                return "pengajaran1.pengajaranDate >= '" + DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT).format(fromDate) + "'";
            case "thruDate":
                LocalDate thruDate = (LocalDate) filterData.value;
                return "pengajaran1.pengajaranDate <= '" + DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT).format(thruDate) + "'";
            default:
                return null;
        }
    }

    @Override
    protected String translateOrderField(String fieldName) {
        switch (fieldName) {
            case "pengajaranDate":
                return "pengajaran1.pengajaranDate";
            case "created":
                return "pengajaran1.created";
            case "santriId":
                return "pengajaran1.santri_id";
            default:
                return super.translateOrderField(fieldName);
        }
    }

    @Override
    protected String getFindAllQuery() {
        return FINDALL;
    }
}
