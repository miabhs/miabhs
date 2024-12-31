/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class SantriAltFacade extends AbstractSqlFacade<Santri> {

    private static final String FINDALL
            = """
            SELECT ID, PARTYID, FIRSTNAME, LASTNAME, GENDER, DATEOFBIRTH, NIS, STATUS, TAHUNMASUKID, TAHUNMASUKNAME,
                    KELOMPOKPENGASUHANID, KELOMPOKPENGASUHANPARTYID, KELOMPOKPENGASUHANPARTYNAME, KOORDINATOR
            FROM (SELECT S5.ID, S5.PARTYID, PRS1.FIRSTNAME AS FIRSTNAME, PRS1.LASTNAME AS LASTNAME, PRS1.GENDER, PRS1.DATEOFBIRTH AS DATEOFBIRTH, S5.NIS, S5.TAHUNMASUKID, S5.TAHUNMASUKNAME, S5.STATUS, S5.KELOMPOKPENGASUHANID, S5.KELOMPOKPENGASUHANPARTYID, S5.KELOMPOKPENGASUHANPARTYNAME, S5.KOORDINATOR
                FROM (SELECT S4.ID, PROLE1.PARTY_ID AS PARTYID, S4.NIS, S4.TAHUNMASUKID, S4.TAHUNMASUKNAME, S4.STATUS, S4.KELOMPOKPENGASUHANID, S4.KELOMPOKPENGASUHANPARTYID, S4.KELOMPOKPENGASUHANPARTYNAME, S4.KOORDINATOR
                    FROM (SELECT S3.ID, S3.NIS, S3.TAHUNMASUKID, S3.TAHUNMASUKNAME, S3.STATUS, PGSH3.KELOMPOKPENGASUHANID, PGSH3.KELOMPOKPENGASUHANPARTYID, PGSH3.KELOMPOKPENGASUHANPARTYNAME, PGSH3.KOORDINATOR
                        FROM (SELECT S2.ID, S2.NIS, S2.TAHUNMASUKID, S2.TAHUNMASUKNAME, SS1.STATUS
                                FROM (SELECT S1.ID, S1.NIS, S1.TAHUNMASUK_ID AS TAHUNMASUKID, T1.NAME AS TAHUNMASUKNAME
                                      FROM (SELECT S0.ID, S0.TAHUNMASUK_ID, S0.NIS FROM MIABH_SANTRI S0) AS S1
                                      JOIN (SELECT T0.ID, T0.NAME FROM MIABH_TAHUNPEMBELAJARAN AS T0) AS T1
                                      ON S1.TAHUNMASUK_ID = T1.ID
                                ) AS S2
                                JOIN (SELECT SS0.SANTRI_ID, SS0.STATUS
                                      FROM MIABH_STATUSSANTRI SS0
                                      WHERE SS0.FROMDATE <= ?
                                      AND ( SS0.THRUDATE IS NULL OR SS0.THRUDATE >= ? )
                                ) AS SS1
                                ON S2.ID = SS1.SANTRI_ID
                        ) AS S3
                        LEFT OUTER JOIN (SELECT PGSH2.SANTRIID, PGSH2.KELOMPOKPENGASUHANID, PGSH2.KELOMPOKPENGASUHANPARTYID, ORG0.NAME AS KELOMPOKPENGASUHANPARTYNAME, PGSH2.KOORDINATOR
                                        FROM (SELECT PGSH1.TOROLE_ID AS SANTRIID, PROLE0.ID AS KELOMPOKPENGASUHANID, PROLE0.PARTY_ID AS KELOMPOKPENGASUHANPARTYID, PGSH1.KOORDINATOR
                                                FROM (SELECT PGSH0.FROMROLE_ID, PGSH0.TOROLE_ID, PGSH0.FROMDATE, PREL0.THRUDATE, PGSH0.KOORDINATOR
                                                      FROM MIABH_PENGASUHAN AS PGSH0
                                                      JOIN PARTY_PARTYRELATIONSHIP AS PREL0
                                                      ON PGSH0.PARTYRELATIONSHIPTYPE_ID = PREL0.PARTYRELATIONSHIPTYPE_ID
                                                      AND PGSH0.FROMROLE_ID = PREL0.FROMROLE_ID AND PGSH0.TOROLE_ID = PREL0.TOROLE_ID
                                                      AND PGSH0.FROMDATE = PREL0.FROMDATE
                                                      WHERE PGSH0.FROMDATE <= ?
                                                      AND ( PREL0.THRUDATE IS NULL OR PREL0.THRUDATE >= ? )) AS PGSH1
                                                JOIN PARTY_PARTYROLE AS PROLE0
                                                ON PGSH1.FROMROLE_ID = PROLE0.ID
                                        ) AS PGSH2
                                        JOIN PARTY_ORGANIZATION AS ORG0
                                        ON PGSH2.KELOMPOKPENGASUHANPARTYID = ORG0.ID
                        ) AS PGSH3
                        ON S3.ID = PGSH3.SANTRIID
                    ) AS S4
                    JOIN PARTY_PARTYROLE AS PROLE1
                    ON S4.ID = PROLE1.ID
                ) AS S5
                JOIN PARTY_PERSON AS PRS1
                ON S5.PARTYID = PRS1.ID
            ) AS S6
            """;

    @Inject
    private EntityManager em;

    public SantriAltFacade() {
        super(Santri.class);
    }

    @Override
    protected String getFindAllQuery() {
        return FINDALL;
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);
//        LocalDate now = LocalDate.now();
        String strNow = df.format((LocalDate)parameters.get("date"));
        q.setParameter(1, strNow);
        q.setParameter(2, strNow);
        q.setParameter(3, strNow);
        q.setParameter(4, strNow);
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        StringBuilder condition = new StringBuilder();

        switch (filterData.name) {
            case "santriId":
//                condition.append("santriId = ").append(val);
                break;
            case "kelompokPengasuhan":
//                KelompokPengasuhan kp = (KelompokPengasuhan) val;
//                condition.append("fromroleId = ").append(kp.getId());
                break;
            case "status":
                StatusKesantrian status = (StatusKesantrian) filterData.value;
                condition.append("STATUS = '").append(status.name()).append("'");
                break;
            default:
                break;
        }

        return condition.toString();
    }

    protected String sortBy() {
        return "id";
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
