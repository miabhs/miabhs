/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.dao;

import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.Status;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
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
public class RangkumanAktifitasFacade extends AbstractSqlFacade<RangkumanAktifitas> {

    private static final String FINDALL
            = """
            SELECT PARTYID, SANTRIID, FROMROLEID, NAMA, JUMLAHDATA, JUMLAHKUNING, JUMLAHMERAH, NAMAKELOMPOK, TAHUNMASUK, KOORDINATOR
            FROM (SELECT PARTYID, SANTRIID, FROMROLEID, NAMA, JUMLAHDATA, JUMLAHKUNING, JUMLAHMERAH, ORG.NAME AS NAMAKELOMPOK, TAHUNMASUK, KOORDINATOR
            FROM (SELECT PARTYID, SANTRIID, FROMROLEID, NAMA, JUMLAHDATA, JUMLAHKUNING, JUMLAHMERAH, ORGROLE.PARTY_ID AS ORGID, TAHUNMASUK, KOORDINATOR
            FROM (SELECT PARTYID, SANTRIID, FROMROLEID, NAMA, JUMLAHDATA, JUMLAHKUNING, JUMLAHMERAH, TAHUNMASUK, KOORDINATOR
            FROM
            (SELECT STAT1.PARTY_ID AS PARTYID, STAT1.SANTRI_ID AS SANTRIID, STAT1.FROMROLE_ID AS FROMROLEID, TRIM(CONCAT_WS(' ', P.FIRSTNAME, P.LASTNAME)) AS NAMA, STAT1.N AS JUMLAHDATA, STAT1.K AS JUMLAHKUNING, STAT1.M AS JUMLAHMERAH, STAT1.FROMDATE AS TAHUNMASUK, KOORDINATOR
            FROM
            (SELECT PROLE.PARTY_ID, STAT.SANTRI_ID, STAT.FROMROLE_ID, STAT.N, STAT.K, STAT.M, PROLE.FROMDATE, KOORDINATOR
            FROM
            (SELECT SHALF.SANTRI_ID, SHALF.FROMROLE_ID, SHALF.N, SHALF.K, SMERAH.DAT AS M, KOORDINATOR
            FROM
            (SELECT SALL.SANTRI_ID, SALL.FROMROLE_ID, SALL.DAT AS N, SKUNING.DAT AS K, KOORDINATOR
            FROM
            (SELECT PR1.SANTRI_ID, PR1.FROMROLE_ID, PR1.DAT, PR2.KOORDINATOR
            FROM
            (SELECT S2.SANTRI_ID, PREL1.FROMROLE_ID, S2.DAT
            FROM (SELECT S1.ID AS SANTRI_ID, COUNT(S1.ID) AS DAT
            FROM PARTY_PERSON P1, PARTY_PARTYROLE PR1, MIABH_SANTRI S1, MIABH_AKTIFITASHARIAN AH1
            WHERE P1.ID = PR1.PARTY_ID
            AND PR1.ID = S1.ID
            AND S1.ID = AH1.SANTRI_ID
            AND AH1.DATE >= ?
            AND AH1.DATE <= ?
            GROUP BY S1.ID
            ) AS S2
            JOIN PARTY_PARTYRELATIONSHIP PREL1 ON PREL1.TOROLE_ID = S2.SANTRI_ID
            WHERE PREL1.PARTYRELATIONSHIPTYPE_ID = 'PENGASUHAN') AS PR1
            JOIN MIABH_PENGASUHAN PR2 ON PR1.FROMROLE_ID = PR2.FROMROLE_ID AND PR1.SANTRI_ID = PR2.TOROLE_ID
            ) AS SALL
            LEFT JOIN
            (SELECT S2.SANTRI_ID, PREL1.FROMROLE_ID, S2.DAT
            FROM (SELECT S1.ID AS SANTRI_ID, COUNT(S1.ID) AS DAT
            FROM PARTY_PERSON P1, PARTY_PARTYROLE PR1, MIABH_SANTRI S1, MIABH_AKTIFITASHARIAN AH1
            WHERE P1.ID = PR1.PARTY_ID AND PR1.ID = S1.ID AND S1.ID = AH1.SANTRI_ID AND AH1.STATUS = 'KUNING'
            AND AH1.DATE >= ? AND AH1.DATE <= ?
            GROUP BY S1.ID) AS S2
            JOIN PARTY_PARTYRELATIONSHIP PREL1
            ON PREL1.TOROLE_ID = S2.SANTRI_ID
            WHERE PREL1.PARTYRELATIONSHIPTYPE_ID = 'PENGASUHAN') AS SKUNING
            ON SALL.SANTRI_ID = SKUNING.SANTRI_ID) AS SHALF
            LEFT JOIN
            (SELECT S2.SANTRI_ID, PREL1.FROMROLE_ID, S2.DAT
            FROM (SELECT S1.ID AS SANTRI_ID, COUNT(S1.ID) AS DAT
            FROM PARTY_PERSON P1, PARTY_PARTYROLE PR1, MIABH_SANTRI S1, MIABH_AKTIFITASHARIAN AH1
            WHERE P1.ID = PR1.PARTY_ID AND PR1.ID = S1.ID AND S1.ID = AH1.SANTRI_ID AND AH1.STATUS = 'MERAH'
            AND AH1.DATE >= ? AND AH1.DATE <= ?
            GROUP BY S1.ID) AS S2
            JOIN PARTYRELATIONSHIP PREL1
            ON PREL1.TOROLE_ID = S2.SANTRI_ID
            WHERE PREL1.PARTYRELATIONSHIPTYPE_ID = 'PENGASUHAN') AS SMERAH
            ON SHALF.SANTRI_ID = SMERAH.SANTRI_ID) AS STAT
            JOIN PARTY_PARTYROLE PROLE
            ON STAT.SANTRI_ID = PROLE.ID) AS STAT1
            JOIN PARTY_PERSON P
            ON STAT1.PARTY_ID = P.ID)) AS RANG1
            JOIN PARTY_PARTYROLE ORGROLE
            ON RANG1.FROMROLEID = ORGROLE.ID) AS RANG2
            JOIN PARTY_ORGANIZATION ORG
            ON RANG2.ORGID = ORG.ID)
            """;

    @Inject
    private EntityManager em;

    public RangkumanAktifitasFacade() {
        super(RangkumanAktifitas.class);
    }

    @Override
    protected String getFindAllQuery() {
        return FINDALL;
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);
        LocalDate fromDate = (LocalDate) (parameters.get("fromDate"));
        q.setParameter(1, df.format(fromDate));
        q.setParameter(3, df.format(fromDate));
        q.setParameter(5, df.format(fromDate));
        LocalDate thruDate = (LocalDate) (parameters.get("thruDate"));
        q.setParameter(2, df.format(thruDate));
        q.setParameter(4, df.format(thruDate));
        q.setParameter(6, df.format(thruDate));
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        StringBuilder condition = new StringBuilder();

        switch (filterData.name) {
            case "santriId":
                condition.append("SANTRIID = ").append(filterData.value);
                break;
            case "kelompokPengasuhan":
                KelompokPengasuhan kp = (KelompokPengasuhan) filterData.value;
                condition.append("FROMROLEID = ").append(kp.getId());
                break;
            case "status":
                String threshold = "ROUND((2 * JUMLAHDATA) / 7)";
                if (filterData.value.equals(Status.MERAH)) {
                    condition.append("JUMLAHMERAH").append(" > ").append(threshold);
                } else if (filterData.value.equals(Status.KUNING)) {
                    condition.append("(")
                            .append("JUMLAHMERAH").append(" IS NULL ").append(" AND ").append("JUMLAHKUNING").append(" IS NOT NULL ").append(" AND ").append("JUMLAHKUNING").append(" > ").append(threshold)
                            .append(" OR ")
                            .append("JUMLAHMERAH").append(" IS NOT NULL ").append(" AND ").append("JUMLAHKUNING").append(" IS NULL ").append(" AND ").append("JUMLAHMERAH").append(" <= ").append(threshold)
                            .append(" OR ")
                            .append("JUMLAHMERAH").append(" IS NOT NULL ").append(" AND ").append("JUMLAHKUNING").append(" IS NOT NULL ").append(" AND ").append("JUMLAHMERAH").append(" <= ").append(threshold)
                            .append(")");
                } else {
                    condition.append("(")
                            .append("JUMLAHMERAH").append(" IS NULL ")
                            .append(" AND ")
                            .append("(")
                            .append("JUMLAHKUNING").append(" IS NULL ")
                            .append(" OR ")
                            .append("JUMLAHKUNING").append(" IS NOT NULL ").append(" AND ").append("JUMLAHKUNING").append(" < ").append(threshold)
                            .append(")")
                            .append(")");

                }
                break;
            default:
                break;
        }

        return condition.toString();
    }

    protected String sortBy() {
        return "SANTRIID";
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
