/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.service;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasTambahanFacade;
import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasTambahanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasHarian;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasHarianId;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasId;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasTambahan;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitasTambahan;
import id.my.mdn.kupu.app.pengasuhan.entity.CatatanAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.CatatanAktifitasTambahan;
import id.my.mdn.kupu.app.pengasuhan.entity.Status;
import id.my.mdn.kupu.app.santri.dao.FungsiPelaksanaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.PembantuPelaksanaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.PengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Musyrif;
import id.my.mdn.kupu.app.santri.entity.PembantuPelaksanaKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.Pengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.QueryHelper;
import id.my.mdn.kupu.core.base.util.QueryParameter;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PengasuhanService {

    @Inject
    private EntityManager em;

    @Inject
    private PengasuhanFacade pengasuhanFacade;

    @Inject
    private AktifitasFacade aktifitasFacade;

    @Inject
    private AktifitasTambahanFacade aktifitasTambahanFacade;

    @Inject
    private BentukAktifitasTambahanFacade bentukAktifitasTambahanFacade;

    @Inject
    private KelompokPengasuhanFacade kelompokPengasuhanFacade;

    @Inject
    private QueryHelper helper;

    public void addSantri(KelompokPengasuhan kelompokPengasuhan, List<Santri> listSantri) {
        listSantri.stream().forEach(santri -> {
            List<FilterData> filters = new ArrayList<>();
            filters.add(new FilterData("fromRole", kelompokPengasuhan));
            filters.add(new FilterData("toRole", santri));
            filters.add(new FilterData("ongoing", null));

            Pengasuhan pengasuhan = pengasuhanFacade.findSingleByAttributes(filters);

            if (pengasuhan == null) {
                pengasuhan = new Pengasuhan();
                pengasuhan.setKelompokPengasuhan(kelompokPengasuhan);
                pengasuhan.setSantri(santri);
                pengasuhanFacade.create(pengasuhan);
            }
        });
    }

    public Ustadz getUstadzPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        Ustadz ustadz = helper.queryOne(em, "Pengasuhan.getUstadz", Ustadz.class,
                new QueryParameter("kelompokPengasuhan", kelompokPengasuhan));

        return ustadz;
    }

    public Musyrif getMusyrifPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        Musyrif musyrif = helper.queryOne(em, "Pengasuhan.getMusyrif", Musyrif.class,
                new QueryParameter("kelompokPengasuhan", kelompokPengasuhan));

        return musyrif;
    }

    private List<Santri> getSantri(KelompokPengasuhan kelompokPengasuhan) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Santri> cq = cb.createQuery(Santri.class);
        Root<Santri> santri = cq.from(Santri.class);
        Join<Santri, Pengasuhan> pengasuhan = cb.treat(santri.join("targetRelationships"), Pengasuhan.class);

        cq.where(cb.equal(pengasuhan.get("fromRole"), kelompokPengasuhan));

        TypedQuery<Santri> q = em.createQuery(cq);

        return q.getResultList();
    }

//    @Schedule(minute = "0", hour = "*/12", persistent = false)
    public void generateAktifitasSantri() {
        LocalDate fromDate = LocalDate.now().withDayOfMonth(1);
        LocalDate thruDate = LocalDate.now();

        generateAktifitasSantri(fromDate, thruDate);
    }

    public void generateAktifitasSantri(LocalDate fromDate, LocalDate thruDate) {
        kelompokPengasuhanFacade.findAll().stream()
                .forEach(kelompok -> {
                    generateAktifitasSantri(kelompok, fromDate, thruDate);
                });
    }

    public void generateAktifitasSantri(KelompokPengasuhan kelompokPengasuhan, LocalDate fromDate, LocalDate thruDate) {

        List<Santri> listSantri = getSantri(kelompokPengasuhan);

        listSantri.stream().forEach(santri -> generateAktifitasSantri(santri, fromDate, thruDate));
    }

//    private final ExecutorService executor = Executors.newCachedThreadPool();
    public void generateAktifitasSantri(final Santri santri, final LocalDate fromDate, final LocalDate thruDate) {
        if ((fromDate == null || thruDate == null) || (thruDate.isBefore(fromDate))) {
            return;
        }

//        EntityManager em1 = emf.createEntityManager();
//        executor.execute(() -> {
        for (LocalDate date = fromDate; date.isBefore(thruDate) || date.isEqual(thruDate);) {

            generateAktifitasSantri(santri, date);
            date = date.plusDays(1);
        }
//        });
    }

//    @Asynchronous
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void generateAktifitasSantri(Santri santri, LocalDate date) {

        AktifitasHarianId aktifitasHarianId = new AktifitasHarianId(santri.getId(), date);

        AktifitasHarian existingAktifitasHarian = em.find(AktifitasHarian.class, aktifitasHarianId);

        if (existingAktifitasHarian != null) {
            return;
        }

        AktifitasHarian aktifitasHarian = new AktifitasHarian();
        aktifitasHarian.setId(aktifitasHarianId);
        aktifitasHarian.setSantri(santri);
        aktifitasHarian.setDate(date);
//        aktifitasHarian.setListAktifitas(new ArrayList<>());
        aktifitasHarian.setStatus(Status.HIJAU);
        aktifitasHarian.setStatusPokok(Status.HIJAU);
        aktifitasHarian.setStatusTambahan(Status.HIJAU);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BentukAktifitas> cq = cb.createQuery(BentukAktifitas.class);
        Root<BentukAktifitas> root = cq.from(BentukAktifitas.class);
        cq.select(root);

        List<BentukAktifitas> listBentukAktifitas = em.createQuery(cq).getResultList();

        listBentukAktifitas.stream().forEach(bentukAktifitas -> {
            AktifitasId id = new AktifitasId(aktifitasHarianId, bentukAktifitas.getId());
            Aktifitas aktifitas = new Aktifitas();

//            aktifitas.setId(id);
//            aktifitas.setAktifitasHarian(aktifitasHarian);
            aktifitas.setBentukAktifitas(bentukAktifitas);
//            aktifitas.setKehadiran(Kehadiran.HADIR);

//            aktifitasHarian.getListAktifitas().add(aktifitas);
        });

        em.persist(aktifitasHarian);
        em.flush();
        em.clear();
    }

    public List<CatatanAktifitas> getAktifitas(Long santriId, LocalDate fromDate, LocalDate thruDate, Boolean proper) {

        List<CatatanAktifitas> listCatatanAktifitas = new ArrayList<>();

        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("santriId", santriId));
        filters.add(new FilterData("fromDate", fromDate));
        filters.add(new FilterData("thruDate", thruDate));
        filters.add(new FilterData("proper", proper));
        List<Aktifitas> listAktifitas = aktifitasFacade.findAll(filters, Arrays.asList(new SorterData("bentukAktifitas")));

//        listAktifitas.stream()
//                .map(aktifitas -> {
//                    CatatanAktifitas catatanAktifitas = new CatatanAktifitas(aktifitas.getBentukAktifitas(), aktifitas.getKehadiran());
//
//                    return catatanAktifitas;
//                })
//                .forEach(catatanAktifitas -> {
//                    if (!listCatatanAktifitas.contains(catatanAktifitas)) {
//                        listCatatanAktifitas.add(catatanAktifitas);
//                    } else {
//                        int existedIndex = listCatatanAktifitas.indexOf(catatanAktifitas);
//                        CatatanAktifitas existedEntry = listCatatanAktifitas.get(existedIndex);
//                        existedEntry.incCount();
//                    }
//                });
        return listCatatanAktifitas;
    }

    public List<CatatanAktifitasTambahan> getAktifitasTambahan(Long santriId, LocalDate fromDate, LocalDate thruDate) {

        List<CatatanAktifitasTambahan> listCatatanAktifitas = new ArrayList<>();

        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("santriId", santriId));
        filters.add(new FilterData("fromDate", fromDate));
        filters.add(new FilterData("thruDate", thruDate));
        List<AktifitasTambahan> listAktifitas = aktifitasTambahanFacade.findAll(filters, Arrays.asList(new SorterData("bentukAktifitas")));

        listAktifitas.stream()
                .map(aktifitas -> {
                    CatatanAktifitasTambahan catatanAktifitas = new CatatanAktifitasTambahan(aktifitas.getBentukAktifitas());

                    return catatanAktifitas;
                })
                .forEach(catatanAktifitas -> {
                    if (!listCatatanAktifitas.contains(catatanAktifitas)) {
                        listCatatanAktifitas.add(catatanAktifitas);
                    } else {
                        int existedIndex = listCatatanAktifitas.indexOf(catatanAktifitas);
                        CatatanAktifitasTambahan existedEntry = listCatatanAktifitas.get(existedIndex);
                        existedEntry.incCount();
                    }
                });

        return listCatatanAktifitas;
    }

    public List<AktifitasTambahan> getAktifitasTambahanList(AktifitasHarian aktifitasHarian) {

        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("aktifitasHarian", aktifitasHarian));

        return aktifitasTambahanFacade.findAll(filters, Arrays.asList(new SorterData("bentukAktifitas")));
    }

    public void addAktifitasTambahan(AktifitasHarian aktifitasHarian, String bentukAktifitasTambahanId) {
        BentukAktifitasTambahan bentukAktifitasTambahan = bentukAktifitasTambahanFacade.find(bentukAktifitasTambahanId);

        AktifitasTambahan aktifitasTambahan = new AktifitasTambahan();
        aktifitasTambahan.setAktifitasHarian(aktifitasHarian);
        aktifitasTambahan.setBentukAktifitas(bentukAktifitasTambahan);
        aktifitasTambahan.setConfirmed(false);

        aktifitasTambahanFacade.create(aktifitasTambahan);
    }

    public void deleteAktifitasTambahan(AktifitasTambahan aktifitasTambahan) {
        aktifitasTambahanFacade.remove(aktifitasTambahan);
    }

    public KelompokPengasuhan getKelompokPengasuhan(Long santriId, LocalDate date) {
        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("toRoleId", santriId));
        filters.add(new FilterData("ondate", date));

        List<Pengasuhan> listPengasuhan = pengasuhanFacade.findAll(filters);

        if (listPengasuhan.isEmpty() || listPengasuhan.size() > 1) {
            return null;
        } else {
            return listPengasuhan.get(0).getKelompokPengasuhan();
        }
    }

    @Inject
    private PembantuPelaksanaKepengasuhanFacade pembantuPelaksanaKepengasuhanFacade;

    @Inject
    private FungsiPelaksanaKepengasuhanFacade fungsiPelaksanaKepengasuhanFacade;

    public Ustadz getMasulKepengasuhan(KelompokPengasuhan kelompokPengasuhan, LocalDate date) {

        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("toRole", kelompokPengasuhan));
        filters.add(new FilterData("ondate", date));
        filters.add(new FilterData("fungsi", 
                fungsiPelaksanaKepengasuhanFacade.findSingleByAttribute("name", "Mas'ul Kepengasuhan")));

        List<PembantuPelaksanaKepengasuhan> listPembantuPelaksanaKepengasuhan = pembantuPelaksanaKepengasuhanFacade.findAll(filters);

        if (listPembantuPelaksanaKepengasuhan.isEmpty() || listPembantuPelaksanaKepengasuhan.size() > 1) {
            return null;
        } else {
            return listPembantuPelaksanaKepengasuhan.get(0).getUstadz();
        }

    }
}
