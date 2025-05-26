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
            SELECT S6.PERSON_ID, S6.PERSON_NAME,
                   S6.SANTRI_ID, S6.NIS, S6.TAHUNMASUK_FROMDATE, S6.KELOMPOKPENGASUHAN_ID, S6.KELOMPOKPENGASUHAN_NAME, S6.KELOMPOKPENGASUHAN_KOORDINATOR,
                   A6.LABEL, A6.FROMDATE, A6.THRUDATE,
                   A6.BDAS_MERAH, A6.BDAS_KUNING, A6.BDAS,
                   A6.NON_BDAS_MERAH, A6.NON_BDAS_KUNING, A6.NON_BDAS
            FROM (
                SELECT ID AS SANTRI_ID,
                       PERSON_ID, PERSON_NAME, PERSON_GENDER, PERSON_DOB,
                       NAMABAPAK, NIS, JENISSANTRI, TAHUNMASUK_ID, TAHUNMASUK_NAME, TAHUNMASUK_FROMDATE, ANGKATAN,
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
                    JOIN (
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
            ) AS S6
            LEFT JOIN (
              SELECT SANTRI_ID, LABEL, ORDINAL, FROMDATE, THRUDATE, BDAS_MERAH, BDAS_KUNING, BDAS, NON_BDAS_MERAH, NON_BDAS_KUNING, NON_BDAS
              FROM <?>
            ) AS A6            
            ON A6.SANTRI_ID = S6.SANTRI_ID
            <?>
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
                             CASE WHEN A4.BDAS_MERAH > 2 THEN 'MERAH'
                                  WHEN (A4.BDAS_MERAH BETWEEN 1 AND 2) OR A4.BDAS_KUNING > 2 THEN 'KUNING'
                                  ELSE 'HIJAU'
                             END
                            ) AS BDAS,
                            A4.NON_BDAS_MERAH, A4.NON_BDAS_KUNING,
                            (
                             CASE WHEN A4.NON_BDAS_MERAH > 2 THEN 'MERAH'
                                  WHEN (A4.NON_BDAS_MERAH BETWEEN 1 AND 2) OR A4.NON_BDAS_KUNING > 2 THEN 'KUNING'
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
                                        CASE WHEN (BDAS_POKOK_MERAH + BDAS_POKOK_KUNING = 0) AND (BDAS_MERAH + BDAS_KUNING <= 4) THEN 'HIJAU'
                                            WHEN (BDAS_POKOK_MERAH = 0) AND (BDAS_POKOK_KUNING >= 1 AND BDAS_POKOK_KUNING <= 4) AND (BDAS_MERAH + BDAS_KUNING <= 4) THEN 'KUNING'
                                            ELSE 'MERAH'
                                        END
                                    ) AS BDAS,
                                    (
                                        CASE WHEN (NON_BDAS_MERAH > 0) THEN 'MERAH'
                                            WHEN (NON_BDAS_MERAH = 0) AND (NON_BDAS_KUNING > 0 OR NON_BDAS_ORANGE > 0) THEN 'KUNING'
                                            ELSE 'HIJAU'
                                        END
                                    ) AS NON_BDAS
                                FROM (
                                    SELECT A1.SANTRI_ID, A1.ACTIVITYDATE,
                                        SUM(CASE WHEN A1.JENIS = 'BDAS_POKOK' AND A1.NILAI = 'MERAH' THEN FREKUENSI ELSE 0 END) AS BDAS_POKOK_MERAH,
                                        SUM(CASE WHEN A1.JENIS = 'BDAS_POKOK' AND A1.NILAI = 'KUNING' THEN FREKUENSI ELSE 0 END) AS BDAS_POKOK_KUNING,
                                        SUM(CASE WHEN A1.JENIS = 'BDAS' AND A1.NILAI = 'MERAH' THEN FREKUENSI ELSE 0 END) AS BDAS_MERAH,
                                        SUM(CASE WHEN A1.JENIS = 'BDAS' AND A1.NILAI = 'KUNING' THEN FREKUENSI ELSE 0 END) AS BDAS_KUNING,
                                        SUM(CASE WHEN A1.JENIS = 'NON_BDAS' AND A1.NILAI = 'MERAH' THEN FREKUENSI ELSE 0 END) AS NON_BDAS_MERAH,
                                        SUM(CASE WHEN A1.JENIS = 'NON_BDAS' AND A1.NILAI = 'KUNING' THEN FREKUENSI ELSE 0 END) AS NON_BDAS_KUNING,
                                        SUM(CASE WHEN A1.JENIS = 'NON_BDAS' AND A1.NILAI = 'ORANGE' THEN FREKUENSI ELSE 0 END) AS NON_BDAS_ORANGE
                                    FROM (
                                        SELECT A0.SANTRI_ID, A0.ACTIVITYDATE, BA0.JENIS, BA0.NILAI, COUNT(BA0.NILAI) AS FREKUENSI
                                        FROM MIABH_AKTIFITAS AS A0
                                        JOIN MIABH_BENTUKAKTIFITAS AS BA0 ON A0.BENTUKAKTIFITAS_ID = BA0.ID
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

    public String generateQueryForPeriods(List<PeriodePembelajaran> listPeriode, KelompokPengasuhan kelompokPengasuhan) {

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

        StringBuilder filterKelompok = new StringBuilder();

        if (kelompokPengasuhan != null) {
            filterKelompok.append("WHERE S6.KELOMPOKPENGASUHAN_ID = ").append(kelompokPengasuhan.getId());
        }

        String ret = replaceParameterizedPositions(MAIN_QUERY_TEMPLATE, subQuery.toString(),
                filterKelompok.toString());
        
        return ret;
    }

    public String generateQueryForPeriods(List<PeriodePembelajaran> listPeriode, Santri santri) {

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

        if (santri != null) {
            filterSantri.append("WHERE S6.SANTRI_ID = ").append(santri.getId());
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
        q.setParameter(2, fromDate);
        q.setParameter(3, thruDate);

        return q.getResultList();
    }

    @Override
    protected String translateOrderField(String fieldName) {
        switch (fieldName) {
            case "santriId":
                return "S6.SANTRI_ID";
            case "kelompokPengasuhanId":
                return "S6.KELOMPOKPENGASUHAN_ID";
            case "koordinator":
                return "S6.KELOMPOKPENGASUHAN_KOORDINATOR";
            default:
                return super.translateOrderField(fieldName);
        }
    }

}
