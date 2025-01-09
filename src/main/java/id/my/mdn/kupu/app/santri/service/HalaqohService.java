/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

/**
 *
 * @author aphasan
 */
@Stateless
@LocalBean
public class HalaqohService {

    private static final String ABSENSI_QUERY
            = """
            SELECT HQS5.SANTRI_ID, HQS5.FIRSTNAME, HQS5.LASTNAME, HQS5.NIS, HQS5.TAHUNMASUK_NAME, HQS5.TAHUNMASUK_FROMDATE, 
                   A4.BDAS_MERAH, A4.BDAS_KUNING, A4.NON_BDAS_MERAH, A4.NON_BDAS_KUNING
            FROM (
                SELECT HQS4.SANTRI_ID, PSON0.FIRSTNAME, PSON0.LASTNAME, HQS4.NIS, HQS4.TAHUNMASUK_NAME, HQS4.TAHUNMASUK_FROMDATE
                FROM (
                    SELECT ROL0.PARTY_ID, HQS3.SANTRI_ID, HQS3.NIS, HQS3.TAHUNMASUK_NAME, HQS3.TAHUNMASUK_FROMDATE
                    FROM (
                        SELECT HQS2.SANTRI_ID, HQS2.NIS, TP0.NAME AS TAHUNMASUK_NAME, TP0.FROMDATE AS TAHUNMASUK_FROMDATE
                        FROM (
                            SELECT S0.ID AS SANTRI_ID, S0.NIS AS NIS, S0.TAHUNMASUK_ID
                            FROM (
                                SELECT HQS0.SANTRI_ID
                                FROM MIABH_HALAQOHSANTRI AS HQS0
                                WHERE HQS0.HALAQOHPENGAJARAN_ID = 1
                            ) AS HQS1
                            JOIN MIABH_SANTRI AS S0
                            ON HQS1.SANTRI_ID = S0.ID
                        ) AS HQS2
                        JOIN MIABH_TAHUNPEMBELAJARAN AS TP0
                        ON HQS2.TAHUNMASUK_ID = TP0.ID
                    ) AS HQS3
                    JOIN PARTY_PARTYROLE AS ROL0
                    ON HQS3.SANTRI_ID = ROL0.ID
                ) AS HQS4
                JOIN PARTY_PERSON AS PSON0
                ON HQS4.PARTY_ID = PSON0.ID
            ) AS HQS5
            LEFT JOIN (
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
                            WHERE (A0.ACTIVITYDATE BETWEEN '2024-12-30' AND '2025-01-05')
                            AND A0.SANTRI_ID IN (
                              SELECT HQS0.SANTRI_ID
                              FROM MIABH_HALAQOHSANTRI AS HQS0
                              WHERE HQS0.HALAQOHPENGAJARAN_ID = 1
                            )
                            GROUP BY A0.SANTRI_ID, A0.ACTIVITYDATE, BA0.JENIS, BA0.NILAI
                        ) AS A1
                        GROUP BY A1.SANTRI_ID, A1.ACTIVITYDATE
                    ) AS A2
                ) AS A3
                GROUP BY A3.SANTRI_ID
            ) AS A4
            ON HQS5.SANTRI_ID = A4.SANTRI_ID
              """;
}
