/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.dao;

import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.CatatanAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.CatatanHarian;
import id.my.mdn.kupu.app.pengasuhan.entity.CatatanKepengasuhan;
import id.my.mdn.kupu.app.pengasuhan.entity.CatatanKepengasuhanId;
import id.my.mdn.kupu.app.pengasuhan.entity.JenisAktifitas;
import static id.my.mdn.kupu.app.pengasuhan.entity.JenisAktifitas.BDAS;
import static id.my.mdn.kupu.app.pengasuhan.entity.JenisAktifitas.BDAS_POKOK;
import static id.my.mdn.kupu.app.pengasuhan.entity.JenisAktifitas.NON_BDAS;
import id.my.mdn.kupu.app.pengasuhan.entity.LaporanKepengasuhan;
import id.my.mdn.kupu.app.pengasuhan.entity.NilaiAktifitas;
import static id.my.mdn.kupu.app.pengasuhan.entity.NilaiAktifitas.KUNING;
import static id.my.mdn.kupu.app.pengasuhan.entity.NilaiAktifitas.MERAH;
import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanHarian;
import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanPeriode;
import id.my.mdn.kupu.app.pengasuhan.entity.Status;
import id.my.mdn.kupu.app.santri.dao.PeriodePembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class AktifitasFacade extends AbstractSqlFacade<Aktifitas> {

    private static final String FINDALL
            = """
              SELECT AKTIFITAS1.ID AS ID,
                     AKTIFITAS1.ACTIVITYDATE,
                     AKTIFITAS1.CREATED,
                     AKTIFITAS1.NOTES,
                     AKTIFITAS1.CONFIRMED,
                     AKTIFITAS1.SANTRI_ID AS SANTRIID, SANTRI5.PARTY_ID AS PERSONID, SANTRI5.FIRSTNAME, SANTRI5.LASTNAME,
                     SANTRI5.KELOMPOKPENGASUHANID, SANTRI5.KELOMPOKPENGASUHANORGANIZATIONID, SANTRI5.KELOMPOKPENGASUHANORGANIZATIONNAME,
                     AKTIFITAS1.BENTUKAKTIFITAS_ID AS BENTUKAKTIFITASID, AKTIFITAS1.BENTUK, AKTIFITAS1.JENIS, AKTIFITAS1.NILAI
              FROM (SELECT AKTIFITAS0.ID, AKTIFITAS0.CREATED, AKTIFITAS0.ACTIVITYDATE, AKTIFITAS0.SANTRI_ID, AKTIFITAS0.BENTUKAKTIFITAS_ID, AKTIFITAS0.NOTES, AKTIFITAS0.CONFIRMED,
                           BENTUKAKTIFITAS0.BENTUK, BENTUKAKTIFITAS0.JENIS, BENTUKAKTIFITAS0.NILAI
                      FROM MIABH_AKTIFITAS AS AKTIFITAS0
                      LEFT JOIN MIABH_BENTUKAKTIFITAS AS BENTUKAKTIFITAS0
                      ON AKTIFITAS0.BENTUKAKTIFITAS_ID = BENTUKAKTIFITAS0.ID
              ) AS AKTIFITAS1
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
              ON AKTIFITAS1.SANTRI_ID = SANTRI5.ID
              """;

    @Inject
    private EntityManager em;

    public AktifitasFacade() {
        super(Aktifitas.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        switch (filterData.name) {
            case "id":
                String id = (String) filterData.value;
                return "AKTIFITAS1.ID = '" + id + "'";
            case "santri":
                Santri santri = (Santri) filterData.value;
                return "AKTIFITAS1.SANTRI_ID = " + santri.getId();
            case "santriId":
                Long santriId = (Long) filterData.value;
                return "AKTIFITAS1.SANTRI_ID = " + santriId;
            case "kelompokPengasuhan":
                KelompokPengasuhan kelompokPengasuhan = (KelompokPengasuhan) filterData.value;
                return "SANTRI5.KELOMPOKPENGASUHANID = " + kelompokPengasuhan.getId();
            case "bentukAktifitas":
                BentukAktifitas bentukAktifitas = (BentukAktifitas) filterData.value;
                return "AKTIFITAS1.BENTUKAKTIFITAS_ID = '" + bentukAktifitas.getId() + "'";
            case "jenisAktifitas":
                if (filterData.value instanceof List) {
                    List<JenisAktifitas> listJenisAktifitas = (List<JenisAktifitas>) filterData.value;
                    return listJenisAktifitas.stream()
                            .map(jenisAktifitas -> "AKTIFITAS1.JENIS = '" + jenisAktifitas.name() + "'")
                            .collect(Collectors.joining(" OR ", "( ", " )"));
                } else {
                    JenisAktifitas jenisAktifitas = (JenisAktifitas) filterData.value;
                    return "AKTIFITAS1.JENIS = '" + jenisAktifitas.name() + "'";
                }
            case "nilaiAktifitas":
                NilaiAktifitas nilaiAktifitas = (NilaiAktifitas) filterData.value;
                return "AKTIFITAS1.NILAI = '" + nilaiAktifitas.name() + "'";
            case "fromDate":
                LocalDate fromDate = (LocalDate) filterData.value;
                return "AKTIFITAS1.ACTIVITYDATE >= '" + DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT).format(fromDate) + "'";
            case "thruDate":
                LocalDate thruDate = (LocalDate) filterData.value;
                return "AKTIFITAS1.ACTIVITYDATE <= '" + DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT).format(thruDate) + "'";
            default:
                return null;
        }
    }

    @Override
    protected String translateOrderField(String fieldName) {
        switch (fieldName) {
            case "activityDate":
                return "AKTIFITAS1.ACTIVITYDATE";
            case "created":
                return "AKTIFITAS1.CREATED";
            case "santriId":
                return "AKTIFITAS1.SANTRI_ID";
            default:
                return super.translateOrderField(fieldName);
        }
    }

    @Override
    protected String getFindAllQuery() {
        return FINDALL;
    }

    private long countCatatanAktifitas(
            Long santriId, JenisAktifitas jenisAktifitas,
            NilaiAktifitas nilaiAktifitas, LocalDate fromDate,
            LocalDate thruDate) {
        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("santriId", santriId));
        filters.add(new FilterData("jenisAktifitas", jenisAktifitas));
        filters.add(new FilterData("nilaiAktifitas", nilaiAktifitas));
        filters.add(new FilterData("fromDate", fromDate));
        filters.add(new FilterData("thruDate", thruDate));

        return countAll(filters);
    }

    public List<CatatanHarian> getCatatanHarian(Santri santri, PeriodePembelajaran periode) {
        return getCatatanHarian(santri.getId(), periode.getFromDate(), periode.getThruDate());
    }

    public List<CatatanHarian> getCatatanHarian(Long santriId, LocalDate fromDate, LocalDate thruDate) {
        Query q = em.createNativeQuery(CatatanHarian.QUERY, "CatatanHarian");
        q.setParameter(1, santriId);
        q.setParameter(2, fromDate);
        q.setParameter(3, thruDate);

        return (List<CatatanHarian>) q.getResultList();
    }

    private RangkumanHarian rangkumanHarian(LocalDate activityDate, List<CatatanHarian> listCatatan) {
        long jumlahMerahBDASPokok = 0;
        long jumlahKuningBDASPokok = 0;
        long jumlahMerahBDAS = 0;
        long jumlahKuningBDAS = 0;
        List<CatatanAktifitas> listCatatanBDAS = new ArrayList<>();

        long jumlahMerahNonBDAS = 0;
        long jumlahKuningNonBDAS = 0;
        List<CatatanAktifitas> listCatatanNonBDAS = new ArrayList<>();

        for (CatatanHarian catatan : listCatatan) {
            if (catatan.activityDate.equals(activityDate)) {

                CatatanAktifitas catatanBDAS = null;
                CatatanAktifitas catatanNonBDAS = null;

                switch (catatan.bentukAktifitas.getJenis()) {
                    case BDAS_POKOK:
                        if (catatan.notes != null && !catatan.notes.isBlank()) {
                            jumlahKuningBDASPokok++;
                        } else {
                            jumlahMerahBDASPokok++;
                        }
                        catatanBDAS = new CatatanAktifitas(catatan.bentukAktifitas);
                        break;
                    case BDAS:
                        if (catatan.notes != null && !catatan.notes.isBlank()) {
                            jumlahKuningBDAS++;
                        } else {
                            jumlahMerahBDAS++;
                        }
                        catatanBDAS = new CatatanAktifitas(catatan.bentukAktifitas);
                        break;
                    case NON_BDAS:
                        if (catatan.bentukAktifitas.getNilai().equals(NilaiAktifitas.KUNING)) {
                            jumlahKuningNonBDAS++;
                        } else if (catatan.bentukAktifitas.getNilai().equals(NilaiAktifitas.MERAH)) {
                            jumlahMerahNonBDAS++;
                        }
                        catatanNonBDAS = new CatatanAktifitas(catatan.bentukAktifitas);
                        break;
                    default:
                        break;
                }

                if (catatanBDAS != null) {
                    if (!listCatatanBDAS.contains(catatanBDAS)) {
                        listCatatanBDAS.add(catatanBDAS);
                    } else {
                        int existedIndex = listCatatanBDAS.indexOf(catatanBDAS);
                        CatatanAktifitas existedEntry = listCatatanBDAS.get(existedIndex);
                        existedEntry.incCount();
                    }
                }

                if (catatanNonBDAS != null) {
                    if (!listCatatanNonBDAS.contains(catatanNonBDAS)) {
                        listCatatanNonBDAS.add(catatanNonBDAS);
                    } else {
                        int existedIndex = listCatatanNonBDAS.indexOf(catatanNonBDAS);
                        CatatanAktifitas existedEntry = listCatatanNonBDAS.get(existedIndex);
                        existedEntry.incCount();
                    }
                }
            }
        }

        RangkumanHarian rangkumanHarian = new RangkumanHarian(activityDate);

        if ((jumlahMerahBDASPokok + jumlahKuningBDASPokok == 0) && (jumlahMerahBDAS + jumlahKuningBDAS <= 4)) {
            rangkumanHarian.setNilaiBDAS(NilaiAktifitas.HIJAU);
        } else if ((jumlahMerahBDASPokok == 0) && (jumlahMerahBDAS + jumlahKuningBDAS <= 4)
                && (jumlahKuningBDASPokok >= 1 && jumlahKuningBDASPokok <= 4)) {
            rangkumanHarian.setNilaiBDAS(NilaiAktifitas.KUNING);
        } else {
            rangkumanHarian.setNilaiBDAS(NilaiAktifitas.MERAH);
        }

        if (jumlahMerahNonBDAS > 0) {
            rangkumanHarian.setNilaiNonBDAS(NilaiAktifitas.MERAH);
        } else if (jumlahMerahNonBDAS == 0 && jumlahKuningNonBDAS > 0) {
            rangkumanHarian.setNilaiNonBDAS(NilaiAktifitas.KUNING);
        }

        rangkumanHarian.getListCatatanBDAS().addAll(listCatatanBDAS);
        rangkumanHarian.getListCatatanNonBDAS().addAll(listCatatanNonBDAS);

        return rangkumanHarian;
    }

    private RangkumanPeriode rangkumanPeriode(PeriodePembelajaran periode, List<RangkumanHarian> listRangkumanHarian) {
        RangkumanPeriode rangkumanPeriode = new RangkumanPeriode(periode);

        int jumlahMerahBDAS = 0;
        int jumlahKuningBDAS = 0;
        int jumlahMerahNonBDAS = 0;
        int jumlahKuningNonBDAS = 0;

        List<CatatanAktifitas> listCatatanBDAS = new ArrayList<>();
        List<CatatanAktifitas> listCatatanNonBDAS = new ArrayList<>();

        for (RangkumanHarian rangkumanHarian : listRangkumanHarian) {
            switch (rangkumanHarian.getNilaiBDAS()) {
                case KUNING:
                    jumlahKuningBDAS++;
                    break;
                case MERAH:
                    jumlahMerahBDAS++;
                    break;
                default:
                    break;
            }
            listCatatanBDAS.addAll(rangkumanHarian.getListCatatanBDAS());
            switch (rangkumanHarian.getNilaiNonBDAS()) {
                case KUNING:
                    jumlahKuningNonBDAS++;
                    break;
                case MERAH:
                    jumlahMerahNonBDAS++;
                    break;
                default:
                    break;
            }
            listCatatanNonBDAS.addAll(rangkumanHarian.getListCatatanNonBDAS());
        }

        long period = periode.getFromDate().until(periode.getThruDate().plusDays(1), DAYS);
        int threshold = Math.round((2 * period) / 7);

        if (jumlahMerahBDAS > threshold) {
            rangkumanPeriode.setNilaiBDAS(NilaiAktifitas.MERAH);
        } else if ((jumlahMerahBDAS > 0 && jumlahMerahBDAS <= threshold) || jumlahKuningBDAS > threshold) {
            rangkumanPeriode.setNilaiBDAS(NilaiAktifitas.KUNING);
        } else {
            rangkumanPeriode.setNilaiBDAS(NilaiAktifitas.HIJAU);
        }

        if (jumlahMerahNonBDAS > 0) {
            rangkumanPeriode.setNilaiNonBDAS(NilaiAktifitas.MERAH);
        } else if (jumlahMerahNonBDAS == 0 && jumlahKuningNonBDAS > 0) {
            rangkumanPeriode.setNilaiNonBDAS(NilaiAktifitas.KUNING);
        } else {
            rangkumanPeriode.setNilaiNonBDAS(NilaiAktifitas.HIJAU);
        }

        rangkumanPeriode.setListCatatanBDAS(listCatatanBDAS);
        rangkumanPeriode.setListCatatanNonBDAS(listCatatanNonBDAS);

        return rangkumanPeriode;
    }

    @Inject
    private PeriodePembelajaranFacade periodeFacade;

    @Inject
    private CatatanKepengasuhanFacade catatanLaporanFacade;

    public CatatanKepengasuhan findCatatanLaporan(Santri santri, PeriodePembelajaran periode) {
        CatatanKepengasuhanId id = new CatatanKepengasuhanId(santri.getId(), periode.getId());
        return catatanLaporanFacade.find(id);
    }

    public List<LaporanKepengasuhan> getAllRangkumanPeriode(Santri santri, PeriodePembelajaran parentPeriode) {
        System.out.println("SELEK BEGIN GETOL RANK PRIOD");
        List<LaporanKepengasuhan> collect = periodeFacade.getChildren(parentPeriode).stream()
                .map(periode
                        -> new LaporanKepengasuhan(
                                santri,
                                periode,
                                getRangkumanPeriode(santri.getId(), periode),
                                findCatatanLaporan(santri, periode))
                ).collect(Collectors.toList());
        System.out.println("SELEK END GETOL RANK PRIOD");
        return collect;
    }

    public List<RangkumanPeriode> getRangkumanPeriode(Santri santri, PeriodePembelajaran periode) {
        return getRangkumanPeriode(santri.getId(), periode);
    }

    public List<RangkumanPeriode> getRangkumanPeriode(Long santriId, PeriodePembelajaran periode) {
        List<CatatanHarian> listCatatanHarian = getCatatanHarian(santriId, periode.getFromDate(), periode.getThruDate());

        List<RangkumanPeriode> listRangkumanPeriode = new ArrayList<>();
        List<PeriodePembelajaran> childrenPeriode = periodeFacade.getChildren(periode);
        for (PeriodePembelajaran childPeriode : childrenPeriode) {
            List<RangkumanHarian> childListRangkumanHarian = new ArrayList<>();
            for (LocalDate date = childPeriode.getFromDate(); !date.isAfter(childPeriode.getThruDate()); date = date.plusDays(1)) {
                RangkumanHarian childRangkumanHarian = rangkumanHarian(date, listCatatanHarian);
                childListRangkumanHarian.add(childRangkumanHarian);
            }
            RangkumanPeriode childRangkumanPeriode = rangkumanPeriode(childPeriode, childListRangkumanHarian);
            listRangkumanPeriode.add(childRangkumanPeriode);
        }

        return listRangkumanPeriode;
    }

    public void calculateKesimpulan(Santri santri, PeriodePembelajaran periode) {
        for (LocalDate date = periode.getFromDate(); !date.isAfter(periode.getThruDate()); date = date.plusDays(1)) {

        }
    }

    public Status calculateKesimpulanBDAS(Long santriId, LocalDate fromDate, LocalDate thruDate) {

        if (santriId == null || fromDate == null || thruDate == null) {
            return null;
        }

        int nMerah = 0;
        int nKuning = 0;

        for (LocalDate i = LocalDate.from(fromDate); !i.isAfter(thruDate); i = i.plusDays(1)) {
            Status kesimpulan = calculateKesimpulanBDAS(santriId, i);
            if (kesimpulan.equals(Status.MERAH)) {
                nMerah++;
            }
            if (kesimpulan.equals(Status.KUNING)) {
                nKuning++;
            }
        }

        long period = fromDate.until(thruDate.plusDays(1), DAYS);

        return calculateKesimpulanBDAS(period, nKuning, nMerah);
    }

    public Status calculateKesimpulanNonBDAS(Long santriId, LocalDate fromDate, LocalDate thruDate) {

        if (santriId == null || fromDate == null || thruDate == null) {
            return null;
        }

        int nMerah = 0;
        int nKuning = 0;

        for (LocalDate i = LocalDate.from(fromDate); !i.isAfter(thruDate); i = i.plusDays(1)) {
            Status kesimpulan = calculateKesimpulanNonBDAS(santriId, i);
            if (kesimpulan.equals(Status.MERAH)) {
                nMerah++;
            }
            if (kesimpulan.equals(Status.KUNING)) {
                nKuning++;
            }
        }

        long period = fromDate.until(thruDate.plusDays(1), DAYS);

        return calculateKesimpulanNonBDAS(period, nKuning, nMerah);
    }

    public Status calculateKesimpulanBDAS(Long santriId, LocalDate date) {

        long jumlahCatatanMerahBDASPokok = countCatatanAktifitas(
                santriId, JenisAktifitas.BDAS_POKOK,
                NilaiAktifitas.MERAH, date, date);
        long jumlahCatatanKuningBDASPokok = countCatatanAktifitas(
                santriId, JenisAktifitas.BDAS_POKOK,
                NilaiAktifitas.KUNING, date, date);
        long jumlahCatatanMerahBDAS = countCatatanAktifitas(
                santriId, JenisAktifitas.BDAS,
                NilaiAktifitas.MERAH, date, date);
        long jumlahCatatanKuningBDAS = countCatatanAktifitas(
                santriId, JenisAktifitas.BDAS,
                NilaiAktifitas.KUNING, date, date);

        boolean hadir_semua_tanpa_terlambat_atau_hadir_tanpa_terlambat_minimal_10_termasuk_semua_wajib
                = (jumlahCatatanMerahBDASPokok + jumlahCatatanKuningBDASPokok + jumlahCatatanMerahBDAS + jumlahCatatanKuningBDAS == 0)
                || ((jumlahCatatanMerahBDAS + jumlahCatatanKuningBDAS <= 4) && (jumlahCatatanMerahBDASPokok + jumlahCatatanKuningBDASPokok == 0));

        boolean hadir_minimal_10_termasuk_semua_wajib_dan_ada_terlambat_wajib_1_sd_4
                = (jumlahCatatanMerahBDASPokok == 0)
                && (jumlahCatatanKuningBDASPokok >= 1 && jumlahCatatanKuningBDASPokok <= 4)
                && (jumlahCatatanMerahBDAS <= 4);

        if (hadir_semua_tanpa_terlambat_atau_hadir_tanpa_terlambat_minimal_10_termasuk_semua_wajib) {
            return Status.HIJAU;
        } else if (hadir_minimal_10_termasuk_semua_wajib_dan_ada_terlambat_wajib_1_sd_4) {
            return Status.KUNING;
        } else {
            return Status.MERAH;
        }
    }

    public Status calculateKesimpulanBDAS(Santri santri, PeriodePembelajaran periode) {

        if (santri.getId() == null || periode.getFromDate() == null || periode.getThruDate() == null) {
            return null;
        }

        int nMerah = 0;
        int nKuning = 0;

        for (LocalDate date = periode.getFromDate(); !date.isAfter(periode.getThruDate()); date = date.plusDays(1)) {
            Status kesimpulan = calculateKesimpulanBDAS(santri.getId(), date);
            if (kesimpulan.equals(Status.MERAH)) {
                nMerah++;
            }
            if (kesimpulan.equals(Status.KUNING)) {
                nKuning++;
            }
        }

        long period = periode.getFromDate().until(periode.getThruDate().plusDays(1), DAYS);

        return calculateKesimpulanBDAS(period, nKuning, nMerah);
    }

    private Status calculateKesimpulanBDAS(long period, int nKuning, int nMerah) {
        int threshold = Math.round((2 * period) / 7);

        if (nMerah > threshold) {
            return Status.MERAH;
        } else if ((nMerah > 0 && nMerah <= threshold) || nKuning > threshold) {
            return Status.KUNING;
        } else {
            return Status.HIJAU;
        }
    }

    public Status calculateKesimpulanNonBDAS(Long santriId, LocalDate date) {

        long jumlahCatatanMerahNonBDAS = countCatatanAktifitas(santriId, JenisAktifitas.NON_BDAS, NilaiAktifitas.MERAH, date, date);
        long jumlahCatatanKuningNonBDAS = countCatatanAktifitas(santriId, JenisAktifitas.NON_BDAS, NilaiAktifitas.KUNING, date, date);

        if (jumlahCatatanMerahNonBDAS > 0) {
            return Status.MERAH;
        } else if (jumlahCatatanMerahNonBDAS == 0 && jumlahCatatanKuningNonBDAS > 0) {
            return Status.KUNING;
        } else {
            return Status.HIJAU;
        }

    }

    public Status calculateKesimpulanNonBDAS(Santri santri, PeriodePembelajaran periode) {

        if (santri.getId() == null || periode.getFromDate() == null || periode.getThruDate() == null) {
            return null;
        }

        int nMerah = 0;
        int nKuning = 0;

        for (LocalDate i = periode.getFromDate(); !i.isAfter(periode.getThruDate()); i = i.plusDays(1)) {
            Status kesimpulan = calculateKesimpulanNonBDAS(santri.getId(), i);
            if (kesimpulan.equals(Status.MERAH)) {
                nMerah++;
            }
            if (kesimpulan.equals(Status.KUNING)) {
                nKuning++;
            }
        }

        long period = periode.getFromDate().until(periode.getThruDate().plusDays(1), DAYS);

        return calculateKesimpulanNonBDAS(period, nKuning, nMerah);
    }

    private Status calculateKesimpulanNonBDAS(long period, int nKuning, int nMerah) {
        int threshold = Math.round((1 * period) / 7);

        if (nMerah >= threshold) {
            return Status.MERAH;
        } else if ((nMerah == 0) && nKuning >= threshold) {
            return Status.KUNING;
        } else {
            return Status.HIJAU;
        }
    }

    public List<CatatanAktifitas> getAktifitas(Long santriId, LocalDate fromDate, LocalDate thruDate, JenisAktifitas... listJenisAktifitas) {

        List<CatatanAktifitas> listCatatanAktifitas = new ArrayList<>();

        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("santriId", santriId));
        filters.add(new FilterData("fromDate", fromDate));
        filters.add(new FilterData("thruDate", thruDate));
        filters.add(new FilterData("jenisAktifitas", List.of(listJenisAktifitas)));
        List<Aktifitas> listAktifitas = findAll(0, 0, filters);

        listAktifitas.stream()
                .map(aktifitas -> {
                    CatatanAktifitas catatanAktifitas = new CatatanAktifitas(aktifitas.getBentukAktifitas());

                    return catatanAktifitas;
                })
                .forEach(catatanAktifitas -> {
                    if (!listCatatanAktifitas.contains(catatanAktifitas)) {
                        listCatatanAktifitas.add(catatanAktifitas);
                    } else {
                        int existedIndex = listCatatanAktifitas.indexOf(catatanAktifitas);
                        CatatanAktifitas existedEntry = listCatatanAktifitas.get(existedIndex);
                        existedEntry.incCount();
                    }
                });
        return listCatatanAktifitas;
    }

}
