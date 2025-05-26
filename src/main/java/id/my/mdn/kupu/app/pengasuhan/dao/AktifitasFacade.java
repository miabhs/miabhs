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
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.GenderType;
import id.my.mdn.kupu.core.security.service.SecurityService;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class AktifitasFacade extends AbstractSqlFacade<Aktifitas> {

    private static final String FINDALL
            = """
              SELECT ID, ACTIVITYDATE, CONFIRMED, CREATED, CREATOR_ID, LASTMODIFIED, LASTMODIFIER_ID, NOTES,   
                     SANTRI_ID, SANTRI_PERSON_ID, SANTRI_NAME, SANTRI_GENDER,
                     KELOMPOKPENGASUHAN_ID, KELOMPOKPENGASUHAN_ORG_ID, KELOMPOKPENGASUHAN_NAME, KELOMPOKPENGASUHAN_FROMDATE, 
                     BENTUKAKTIFITAS_ID, BENTUKAKTIFITAS_BENTUK, BENTUKAKTIFITAS_JENIS, BENTUKAKTIFITAS_NILAI
              FROM (
                  SELECT AKT.ID, AKT.ACTIVITYDATE, AKT.CONFIRMED, AKT.CREATED, AKT.CREATOR_ID, AKT.LASTMODIFIED, AKT.LASTMODIFIER_ID, AKT.NOTES,   
                         SN.ID AS SANTRI_ID, SN.PERSON_ID AS SANTRI_PERSON_ID, SN.PERSON_NAME AS SANTRI_NAME, SN.PERSON_GENDER AS SANTRI_GENDER,
                         PS.FROMROLE_ID AS KELOMPOKPENGASUHAN_ID, PYX.ID AS KELOMPOKPENGASUHAN_ORG_ID, PYX.FIRSTNAME AS KELOMPOKPENGASUHAN_NAME, PS.FROMDATE AS KELOMPOKPENGASUHAN_FROMDATE, 
                         AKT.BENTUKAKTIFITAS_ID, AKT.BENTUK AS BENTUKAKTIFITAS_BENTUK, AKT.JENIS AS BENTUKAKTIFITAS_JENIS, AKT.NILAI AS BENTUKAKTIFITAS_NILAI
                  FROM (
                      SELECT AKT0.ID, AKT0.ACTIVITYDATE, AKT0.SANTRI_ID, AKT0.BENTUKAKTIFITAS_ID, AKT0.NOTES, AKT0.CONFIRMED,
                             BAKT0.BENTUK, BAKT0.JENIS, BAKT0.NILAI, 
                             AKT0.CREATED, AKT0.CREATOR_ID, AKT0.LASTMODIFIED, AKT0.LASTMODIFIER_ID
                      FROM MIABH_AKTIFITAS AS AKT0
                      LEFT JOIN MIABH_BENTUKAKTIFITAS AS BAKT0 ON AKT0.BENTUKAKTIFITAS_ID = BAKT0.ID
                      WHERE AKT0.ACTIVITYDATE >= ? AND AKT0.ACTIVITYDATE <= ?
                  ) AS AKT
                  LEFT JOIN (
                      SELECT ID,
                             PERSON_ID, PERSON_NAME, PERSON_GENDER, PERSON_DOB,
                             NAMABAPAK, NIS, JENISSANTRI, TAHUNMASUK_ID, TAHUNMASUK_NAME, TAHUNMASUK_FROMDATE, ANGKATAN
                          FROM (
                          SELECT SN.ID,
                                 PR.PARTY_ID AS PERSON_ID, CONCAT_WS(' ', PY.FIRSTNAME, PY.LASTNAME) AS PERSON_NAME, PN.GENDER AS PERSON_GENDER, PN.DATEOFBIRTH AS PERSON_DOB,
                                 SN.NAMABAPAK, SN.NIS, SN.JENISSANTRI, SN.TAHUNMASUK_ID, TP.NAME AS TAHUNMASUK_NAME, TP.FROMDATE AS TAHUNMASUK_FROMDATE, SN.ANGKATAN
                          FROM MIABH_SANTRI AS SN
                          JOIN PARTY_PARTYROLE AS PR ON SN.ID = PR.ID
                          JOIN PARTY_PARTY AS PY ON PR.PARTY_ID = PY.ID
                          JOIN PARTY_PERSON AS PN ON PY.ID = PN.ID
                          JOIN MIABH_TAHUNPEMBELAJARAN AS TP ON SN.TAHUNMASUK_ID = TP.ID
                      )
                  ) AS SN ON AKT.SANTRI_ID = SN.ID
                  LEFT JOIN (
                      SELECT PS1.FROMROLE_ID, PS1.TOROLE_ID, PS1. FROMDATE, PREL1.THRUDATE
                      FROM MIABH_PENGASUHAN AS PS1
                      JOIN PARTY_PARTYRELATIONSHIP AS PREL1
                      ON PREL1.PARTYRELATIONSHIPTYPE_ID = 'Pengasuhan' AND PS1.FROMROLE_ID = PREL1.FROMROLE_ID AND PS1.TOROLE_ID = PREL1.TOROLE_ID AND PS1.FROMDATE = PREL1.FROMDATE
                      WHERE PS1.FROMDATE <= ?
                  ) AS PS ON AKT.SANTRI_ID = PS.TOROLE_ID AND ((PS.THRUDATE IS NOT NULL AND AKT.ACTIVITYDATE <= PS.THRUDATE) OR (PS.THRUDATE IS NULL AND AKT.ACTIVITYDATE >= PS.FROMDATE))        
                  LEFT JOIN PARTY_PARTYROLE AS PRX ON PS.FROMROLE_ID = PRX.ID
                  LEFT JOIN PARTY_PARTY AS PYX ON PRX.PARTY_ID = PYX.ID
              )
              """;

    private static final String FIND
            = """
              SELECT AK.ID, AK.ACTIVITYDATE,
                     AK.CONFIRMED, AK.CREATED, AK.CREATOR_ID, AK.LASTMODIFIED, AK.LASTMODIFIER_ID
              FROM MIABH_AKTIFITAS AS AK
              WHERE AK.ID = ?
              """;

    @Inject
    private EntityManager em;

    @Inject
    private PeriodePembelajaranFacade periodeFacade;

    @Inject
    private CatatanKepengasuhanFacade catatanLaporanFacade;

    @Inject
    private SecurityService ss;

    public AktifitasFacade() {
        super(Aktifitas.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        q.setParameter(1, parameters.get("fromDate"));
        q.setParameter(2, parameters.get("thruDate"));
        q.setParameter(3, parameters.get("thruDate"));
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        switch (filterData.name) {
            case "id":
                return "ID = '" + filterData.value + "'";
            case "santriName":
                String nameQuery = filterData.value != null ? (String) filterData.value : "";
                if (nameQuery.equals("")) {
                    return null;
                }
                return "(UPPER(SANTRI_NAME) LIKE '%" + nameQuery.toUpperCase() + "%')";
            case "gender":
                GenderType gender = (GenderType) filterData.value;
                return "(SANTRI_GENDER = '" + gender.name() + "' OR SANTRI_GENDER IS NULL)";
            case "santri":
                Santri santri = (Santri) filterData.value;
                return "SANTRI_ID = " + santri.getId();
            case "santriId":
                Long santriId = (Long) filterData.value;
                return "SANTRI_ID = " + santriId;
            case "kelompokPengasuhan":
                KelompokPengasuhan kelompokPengasuhan = (KelompokPengasuhan) filterData.value;
                return "KELOMPOKPENGASUHAN_ID = " + kelompokPengasuhan.getId() + "";
            case "bentukAktifitas":
                BentukAktifitas bentukAktifitas = (BentukAktifitas) filterData.value;
                return "BENTUKAKTIFITAS_ID = '" + bentukAktifitas.getId() + "'";
            case "jenisAktifitas":
                if (filterData.value instanceof List) {
                    List<JenisAktifitas> listJenisAktifitas = (List<JenisAktifitas>) filterData.value;
                    return listJenisAktifitas.stream()
                            .map(jenisAktifitas -> "BENTUKAKTIFITAS_JENIS = '" + jenisAktifitas.name() + "'")
                            .collect(Collectors.joining(" OR ", "( ", " )"));
                } else {
                    JenisAktifitas jenisAktifitas = (JenisAktifitas) filterData.value;
                    return "BENTUKAKTIFITAS_JENIS = '" + jenisAktifitas.name() + "'";
                }
            case "nilaiAktifitas":
                NilaiAktifitas nilaiAktifitas = (NilaiAktifitas) filterData.value;
                return "BENTUKAKTIFITAS_NILAI = '" + nilaiAktifitas.name() + "'";
            default:
                return null;
        }
    }

    @Override
    protected String translateOrderField(String fieldName) {
        switch (fieldName) {
            case "activityDate":
                return "ACTIVITYDATE";
            case "created":
                return "CREATED";
            case "santriId":
                return "SANTRI_ID";
            case "santriName":
                return "SANTRI_NAME";
            default:
                return super.translateOrderField(fieldName);
        }
    }

    @Override
    protected String getFindAllQuery() {
        return FINDALL;
    }

    @Override
    protected String getFindQuery() {
        return FIND;
    }

    private long countCatatanAktifitas(
            Long santriId, JenisAktifitas jenisAktifitas,
            NilaiAktifitas nilaiAktifitas, LocalDate fromDate,
            LocalDate thruDate) {
        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("santriId", santriId));
        filters.add(new FilterData("jenisAktifitas", jenisAktifitas));
        filters.add(new FilterData("nilaiAktifitas", nilaiAktifitas));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fromDate", fromDate);
        parameters.put("thruDate", thruDate);

        return countAll(parameters, filters);
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

    public CatatanKepengasuhan findCatatanLaporan(Santri santri, PeriodePembelajaran periode) {
        CatatanKepengasuhanId id = new CatatanKepengasuhanId(santri.getId(), periode.getId());
        return catatanLaporanFacade.find(id);
    }

    public List<LaporanKepengasuhan> getAllRangkumanPeriode(Santri santri, PeriodePembelajaran parentPeriode) {
        List<LaporanKepengasuhan> collect = periodeFacade.getChildren(parentPeriode).stream()
                .map(periode
                        -> new LaporanKepengasuhan(
                        santri,
                        periode,
                        getRangkumanPeriode(santri.getId(), periode),
                        findCatatanLaporan(santri, periode))
                ).collect(Collectors.toList());
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

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Result<String> create(Aktifitas entity) {
        entity.setCreator(ss.getUser());
        return super.create(entity);
    }

    @Override
    public Result<String> edit(Aktifitas entity) {
        entity.setLastModified(LocalDateTime.now());
        entity.setLastModifier(ss.getUser());
        return super.edit(entity);
    }

    public void createBlank(LocalDate date) {
        Aktifitas entity = new Aktifitas();
        entity.setActivityDate(date);
        create(entity);
    }

}
