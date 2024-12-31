package id.my.mdn.kupu.app.pengasuhan.entity;

import java.time.LocalDate;

public class CatatanHarian {

    public static final String QUERY = """
                                       SELECT AKTIFITAS0.ACTIVITYDATE AS ACTIVITYDATE,
                                       BENTUKAKTIFITAS0.ID AS ID, BENTUKAKTIFITAS0.KODE AS KODE, BENTUKAKTIFITAS0.JENIS AS JENIS,
                                       BENTUKAKTIFITAS0.BENTUK AS BENTUK, BENTUKAKTIFITAS0.NILAI AS NILAI, AKTIFITAS0.NOTES AS NOTES
                                       FROM MIABH_AKTIFITAS AS AKTIFITAS0
                                       LEFT JOIN MIABH_BENTUKAKTIFITAS AS BENTUKAKTIFITAS0
                                       ON AKTIFITAS0.BENTUKAKTIFITAS_ID = BENTUKAKTIFITAS0.ID
                                       WHERE AKTIFITAS0.SANTRI_ID = ? AND AKTIFITAS0.ACTIVITYDATE >= ? AND AKTIFITAS0.ACTIVITYDATE <= ?
                                       """;

    public final LocalDate activityDate;
    public final BentukAktifitas bentukAktifitas;
    public final String notes;

    public CatatanHarian(LocalDate activitydate, String id, String kode, String jenis, String bentuk, String nilai, String notes) {
        this.activityDate = activitydate;
        this.bentukAktifitas = new BentukAktifitas(id, kode, bentuk, JenisAktifitas.valueOf(jenis), NilaiAktifitas.valueOf(nilai));
        this.notes = notes;
    }
}
