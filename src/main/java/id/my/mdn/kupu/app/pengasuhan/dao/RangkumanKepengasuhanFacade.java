/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.dao;

import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanKepengasuhan;
import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanKepengasuhanDetail;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class RangkumanKepengasuhanFacade extends AbstractSqlFacade<RangkumanKepengasuhan> {

    @Inject
    private EntityManager em;

    public RangkumanKepengasuhanFacade() {
        super(RangkumanKepengasuhan.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private static final String MAIN_QUERY_TEMPLATE
            = """
            SELECT S6.PARTYID, S6.FIRSTNAME, S6.LASTNAME,
                   A6.SANTRI_ID, S6.NIS, S6.KELOMPOKPENGASUHANPARTYNAME,
                   A6.LABEL, A6.FROMDATE, A6.THRUDATE,
                   A6.BDAS_MERAH, A6.BDAS_KUNING, A6.BDAS,
                   A6.NON_BDAS_MERAH, A6.NON_BDAS_KUNING, A6.NON_BDAS
            FROM (
              SELECT SANTRI_ID, LABEL, ORDINAL, FROMDATE, THRUDATE, BDAS_MERAH, BDAS_KUNING, BDAS, NON_BDAS_MERAH, NON_BDAS_KUNING, NON_BDAS
              FROM <?>
            ) AS A6
            INNER JOIN (
                SELECT ID, PARTYID, PARTYROLETYPEID, FIRSTNAME, LASTNAME, NAMABAPAK, GENDER, DATEOFBIRTH,
                       NIS, STATUS, TAHUNMASUKID, TAHUNMASUKNAME, TAHUNMASUKFROMDATE,
                       KELOMPOKPENGASUHANID, KELOMPOKPENGASUHANPARTYID, KELOMPOKPENGASUHANPARTYNAME, KOORDINATOR
                FROM (
                    SELECT S4.ID, S4.PARTY_ID AS PARTYID, S4.PARTYROLETYPE_ID AS PARTYROLETYPEID,
                           S4.FIRSTNAME, S4.LASTNAME, S4.NAMABAPAK, S4.GENDER, S4.DATEOFBIRTH AS DATEOFBIRTH,
                           S4.KOORDINATOR, KKS2.ID AS KELOMPOKPENGASUHANID, KKS2.PARTY_ID AS KELOMPOKPENGASUHANPARTYID,
                           KKS2.NAME AS KELOMPOKPENGASUHANPARTYNAME, S4.STATUS AS STATUS, S4.NIS,
                           S4.TAHUNMASUK_ID AS TAHUNMASUKID, S4.TAHUNMASUKNAME, S4.TAHUNMASUKFROMDATE
                    FROM (
                        SELECT S3.ID, S3.PARTY_ID, S3.PARTYROLETYPE_ID, S3.FIRSTNAME, S3.LASTNAME,
                               S3.NAMABAPAK, S3.GENDER, S3.DATEOFBIRTH, PS2.KOORDINATOR, PS2.FROMROLE_ID,
                               S3.STATUS, S3.NIS, S3.TAHUNMASUK_ID, S3.TAHUNMASUKNAME, S3.TAHUNMASUKFROMDATE
                        FROM (
                            SELECT S2.ID, S2.PARTY_ID, S2.PARTYROLETYPE_ID, S2.FIRSTNAME, S2.LASTNAME,
                                   S2.NAMABAPAK, S2.GENDER, S2.DATEOFBIRTH, SS0.STATUS, S2.NIS,
                                   S2.TAHUNMASUK_ID, S2.TAHUNMASUKNAME, S2.TAHUNMASUKFROMDATE
                            FROM (
                                SELECT S1.ID, S1.PARTY_ID, S1.PARTYROLETYPE_ID, PSON1.FIRSTNAME, PSON1.LASTNAME,
                                       S1.NAMABAPAK, PSON1.GENDER, PSON1.DATEOFBIRTH, S1.NIS,
                                       S1.TAHUNMASUK_ID, S1.TAHUNMASUKNAME, S1.TAHUNMASUKFROMDATE
                                FROM (
                                    SELECT S0.ID, PROLE0.PARTY_ID, PROLE0.PARTYROLETYPE_ID, S0.NAMABAPAK,
                                           S0.NIS, S0.TAHUNMASUK_ID, S0.TAHUNMASUKNAME, S0.TAHUNMASUKFROMDATE
                                    FROM (
                                        SELECT S.ID, S.NAMABAPAK, S.NIS,
                                               S.TAHUNMASUK_ID, TP.NAME AS TAHUNMASUKNAME, TP.FROMDATE AS TAHUNMASUKFROMDATE
                                        FROM MIABH_SANTRI S
                                        JOIN MIABH_PENGASUHAN PS ON PS.TOROLE_ID = S.ID
                                        JOIN MIABH_TAHUNPEMBELAJARAN TP ON S.TAHUNMASUK_ID = TP.ID
                                        <?>
                                    ) AS S0
                                    JOIN PARTY_PARTYROLE AS PROLE0
                                    ON S0.ID = PROLE0.ID
                                    WHERE PROLE0.FROMDATE <=  CURRENT_DATE
                                    AND (PROLE0.THRUDATE IS NULL OR PROLE0.THRUDATE >= CURRENT_DATE)
                                ) AS S1
                                 JOIN PARTY_PERSON AS PSON1
                                 ON S1.PARTY_ID = PSON1.ID
                            ) AS S2
                            JOIN MIABH_STATUSSANTRI AS SS0
                            ON S2.ID = SS0.SANTRI_ID
                            WHERE SS0.FROMDATE <=  CURRENT_DATE
                            AND (SS0.THRUDATE IS NULL OR SS0.THRUDATE >= CURRENT_DATE)
                        ) AS S3
                        LEFT JOIN (
                            SELECT PS1.FROMROLE_ID, PS1.TOROLE_ID, PS1.FROMDATE, PREL1.THRUDATE, PS1.KOORDINATOR
                            FROM MIABH_PENGASUHAN AS PS1
                            JOIN PARTY_PARTYRELATIONSHIP AS PREL1
                            ON PS1.PARTYRELATIONSHIPTYPE_ID = PREL1.PARTYRELATIONSHIPTYPE_ID
                            AND PS1.FROMROLE_ID = PREL1.FROMROLE_ID
                            AND PS1.TOROLE_ID = PREL1.TOROLE_ID
                            AND PS1.FROMDATE = PREL1.FROMDATE
                            WHERE PREL1.FROMDATE <=  CURRENT_DATE
                            AND (PREL1.THRUDATE IS NULL OR PREL1.THRUDATE >= CURRENT_DATE)
                        ) AS PS2
                        ON S3.ID = PS2.TOROLE_ID
                    ) AS S4
                    LEFT JOIN (
                        SELECT KKS1.ID, KKS1.PARTY_ID, ORG0.NAME
                        FROM (
                            SELECT KKS0.ID, PROLE0.PARTY_ID
                            FROM MIABH_KELOMPOKPENGASUHAN AS KKS0
                            JOIN PARTY_PARTYROLE AS PROLE0
                            ON KKS0.ID = PROLE0.ID
                        ) AS KKS1
                        JOIN PARTY_ORGANIZATION AS ORG0
                        ON KKS1.PARTY_ID = ORG0.ID
                    ) AS KKS2
                    ON S4.FROMROLE_ID = KKS2.ID
                )
            ) AS S6
            ON A6.SANTRI_ID = S6.ID
            ORDER BY A6.SANTRI_ID, A6.ORDINAL
            """;

    private static final String SUB_QUERY_TEMPLATE
            = """   
                (
                  SELECT S0.ID AS SANTRI_ID, '<?>' AS LABEL, <?> AS ORDINAL, (DATE '<?>') AS FROMDATE, (DATE '<?>') AS THRUDATE,
                         (CASE WHEN A5.BDAS_MERAH IS NULL THEN 0 ELSE A5.BDAS_MERAH END) AS BDAS_MERAH,
                         (CASE WHEN A5.BDAS_KUNING IS NULL THEN 0 ELSE A5.BDAS_KUNING END) AS BDAS_KUNING ,
                         (CASE WHEN A5.BDAS IS NULL THEN 'HIJAU' ELSE A5.BDAS END) AS BDAS ,
                         (CASE WHEN A5.NON_BDAS_MERAH IS NULL THEN 0 ELSE A5.NON_BDAS_MERAH END) AS NON_BDAS_MERAH,
                         (CASE WHEN A5.NON_BDAS_KUNING IS NULL THEN 0 ELSE A5.NON_BDAS_KUNING END) AS NON_BDAS_KUNING,
                         (CASE WHEN A5.NON_BDAS IS NULL THEN 'HIJAU' ELSE A5.NON_BDAS END) AS NON_BDAS
                  FROM MIABH_SANTRI AS S0
                  LEFT OUTER JOIN (
                          SELECT A4.SANTRI_ID,
                                 A4.BDAS_MERAH, A4.BDAS_KUNING,
                                 (
                                  CASE WHEN A4.BDAS_MERAH > 2
                                       THEN 'MERAH'
                                       WHEN (A4.BDAS_MERAH BETWEEN 1 AND 2) OR A4.BDAS_KUNING > 2
                                       THEN 'KUNING'
                                       ELSE 'HIJAU'
                                  END
                                 ) AS BDAS,
                                 A4.NON_BDAS_MERAH, A4.NON_BDAS_KUNING,
                                 (
                                  CASE WHEN A4.NON_BDAS_MERAH > 2
                                       THEN 'MERAH'
                                       WHEN (A4.NON_BDAS_MERAH BETWEEN 1 AND 2) OR A4.NON_BDAS_KUNING > 2
                                       THEN 'KUNING'
                                       ELSE 'HIJAU'
                                  END
                                 ) AS NON_BDAS
                          FROM (
                              SELECT A3.SANTRI_ID,
                                     SUM(CASE WHEN BDAS = 'MERAH' THEN 1 ELSE 0 END) AS BDAS_MERAH,
                                     SUM(CASE WHEN BDAS = 'KUNING' THEN 1 ELSE 0 END) AS BDAS_KUNING,
                                     SUM(CASE WHEN NON_BDAS = 'MERAH' THEN 1 ELSE 0 END) AS NON_BDAS_MERAH,
                                     SUM(CASE WHEN NON_BDAS = 'KUNING' THEN 1 ELSE 0 END) AS NON_BDAS_KUNING
                              FROM (
                                  SELECT A2.SANTRI_ID, A2.ACTIVITYDATE,
                                          (
                                           CASE WHEN (BDAS_POKOK_MERAH + BDAS_POKOK_KUNING = 0) AND (BDAS_MERAH + BDAS_KUNING <= 4)
                                                THEN 'HIJAU'
                                                WHEN (BDAS_POKOK_MERAH = 0) AND (BDAS_POKOK_KUNING >= 1 AND BDAS_POKOK_KUNING <= 4) AND (BDAS_MERAH + BDAS_KUNING <= 4)
                                                THEN 'KUNING'
                                                ELSE 'MERAH'
                                           END
                                          ) AS BDAS,
                                          (
                                           CASE WHEN (NON_BDAS_MERAH > 0)
                                                THEN 'MERAH'
                                                WHEN (NON_BDAS_MERAH = 0) AND (NON_BDAS_KUNING > 0 OR NON_BDAS_ORANGE > 0)
                                                THEN 'KUNING'
                                                ELSE 'HIJAU'
                                           END
                                          ) AS NON_BDAS
                                  FROM (
                                      SELECT A1.SANTRI_ID, A1.ACTIVITYDATE,
                                             SUM(CASE WHEN A1.JENIS = 'BDAS_POKOK_MERAH' THEN FREKUENSI ELSE 0 END) AS BDAS_POKOK_MERAH,
                                             SUM(CASE WHEN A1.JENIS = 'BDAS_POKOK_KUNING' THEN FREKUENSI ELSE 0 END) AS BDAS_POKOK_KUNING,
                                             SUM(CASE WHEN A1.JENIS = 'BDAS_MERAH' THEN FREKUENSI ELSE 0 END) AS BDAS_MERAH,
                                             SUM(CASE WHEN A1.JENIS = 'BDAS_KUNING' THEN FREKUENSI ELSE 0 END) AS BDAS_KUNING,
                                             SUM(CASE WHEN A1.JENIS = 'NON_BDAS_MERAH' THEN FREKUENSI ELSE 0 END) AS NON_BDAS_MERAH,
                                             SUM(CASE WHEN A1.JENIS = 'NON_BDAS_KUNING' THEN FREKUENSI ELSE 0 END) AS NON_BDAS_KUNING,
                                             SUM(CASE WHEN A1.JENIS = 'NON_BDAS_ORANGE' THEN FREKUENSI ELSE 0 END) AS NON_BDAS_ORANGE
                                      FROM (
                                          SELECT A0.SANTRI_ID, A0.ACTIVITYDATE, (BA0.JENIS || '_' || BA0.NILAI) AS JENIS, COUNT(BA0.NILAI) AS FREKUENSI
                                          FROM MIABH_AKTIFITAS AS A0
                                          JOIN MIABH_BENTUKAKTIFITAS AS BA0
                                          ON A0.BENTUKAKTIFITAS_ID = BA0.ID
                                          WHERE A0.ACTIVITYDATE BETWEEN '<?>' AND '<?>'
                                          GROUP BY A0.SANTRI_ID, A0.ACTIVITYDATE, BA0.JENIS, BA0.NILAI
                                      ) AS A1
                                      GROUP BY A1.SANTRI_ID, A1.ACTIVITYDATE
                              ) AS A2
                              ) AS A3
                              GROUP BY A3.SANTRI_ID
                          ) AS A4
                      ) AS A5
                  ON S0.ID = A5.SANTRI_ID
                )           
            """;

    private static final String CATATAN_QUERY
            = """
              SELECT AKT2.CATATAN, AKT2.JENIS
               FROM (
                   SELECT (BAKT0.BENTUK || ' (' || AKT1.FREKUENSI || ')') AS CATATAN,
                          BAKT0.JENIS
              
                   FROM (
                       SELECT AKT0.BENTUKAKTIFITAS_ID, COUNT(AKT0.BENTUKAKTIFITAS_ID) AS FREKUENSI
                       FROM MIABH_AKTIFITAS AS AKT0
                       WHERE AKT0.SANTRI_ID = ?
                       AND (AKT0.ACTIVITYDATE >= ? AND AKT0.ACTIVITYDATE <= ?)
                       GROUP BY AKT0.BENTUKAKTIFITAS_ID
                   ) AS AKT1
                   JOIN MIABH_BENTUKAKTIFITAS BAKT0
                   ON AKT1.BENTUKAKTIFITAS_ID = BAKT0.ID
               ) AS AKT2
              """;

    @Override
    protected String getFindAllQuery() {
        return null;
    }

    public String generateQueryForPeriods(List<PeriodePembelajaran> listPeriode, KelompokPengasuhan kelompokPengasuhan, Santri santri) {

        StringBuilder subQuery = new StringBuilder();

        for (int i = 0; i < listPeriode.size(); i++) {
            PeriodePembelajaran periode = listPeriode.get(i);
            if (i > 0) {
                subQuery.append("UNION ALL");
            }
            PeriodePembelajaran parentPeriode = periode.getParent();
            String parentLabel = parentPeriode != null ? (" (" + parentPeriode.getName() + ")") : "";
            subQuery.append(replaceParameterizedPositions(SUB_QUERY_TEMPLATE,
                    periode.getName() + parentLabel,
                    String.valueOf(i),
                    periode.getFromDate().toString(),
                    periode.getThruDate().toString(),
                    periode.getFromDate().toString(),
                    periode.getThruDate().toString()
            ));
        }

        StringBuilder filterSantri = new StringBuilder();

        if (kelompokPengasuhan != null && santri != null) {
            filterSantri.append("WHERE PS.FROMROLE_ID = ").append(kelompokPengasuhan.getId())
                    .append(" AND ")
                    .append("S.ID = ").append(santri.getId());
        } else if (kelompokPengasuhan != null && santri == null) {
            filterSantri.append("WHERE PS.FROMROLE_ID = ").append(kelompokPengasuhan.getId());
        } else if (kelompokPengasuhan == null && santri != null) {
            filterSantri.append("WHERE S.ID = ").append(santri.getId());
        }

        String ret = replaceParameterizedPositions(MAIN_QUERY_TEMPLATE, subQuery.toString(),
                filterSantri.toString());
        
        return ret;
    }

    public List<RangkumanKepengasuhanDetail> getDetailKepengasuhan(Long santriId, LocalDate fromDate, LocalDate thruDate) {
        Query q = getEntityManager().createNativeQuery(
                CATATAN_QUERY,
                RangkumanKepengasuhanDetail.class.getSimpleName()
        );
        q.setParameter(1, santriId);
        q.setParameter(2, fromDate.toString());
        q.setParameter(3, thruDate.toString());

        return q.getResultList();
    }

}
