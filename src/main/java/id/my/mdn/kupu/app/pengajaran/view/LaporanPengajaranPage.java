/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view;

import id.my.mdn.kupu.app.pengajaran.dao.LaporanPengajaranFacade;
import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.widget.KelompokPengasuhanAltList;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranFilter;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.view.ReportingPage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.chrono.HijrahChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "laporanPengajaranPage")
@ViewScoped
public class LaporanPengajaranPage extends ReportingPage implements Serializable {

//    private static class CacheContent {
//
//        public final LaporanPengajaran laporanPengajaran;
//
//        public CacheContent(LaporanPengajaran laporanPengajaran) {
//            this.laporanPengajaran = laporanPengajaran;
//        }
//    }

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @Inject
    @Bookmarked
    private KelompokPengasuhanAltList kelompokPengasuhanList;
    
    @Bookmarked(name = "sn")
    private Santri santri;

//    @Inject
//    @Bookmarked
//    private SantriList detailDataView;

//    @Inject
//    private SantriList santriList;

//    public SantriList getSantriList() {
//        return santriList;
//    }

//    @Inject
//    private TextEditorBean editorCatatan;

//    @Inject
//    private CatatanPengajaranFacade catatanLaporanFacade;

    @Inject
    private SantriFacade santriFacade;

    @Inject
    private LaporanPengajaranFacade laporanPengajaranFacade;

//    private final Map<String, CacheContent> cache = new HashMap<>();

//    @Bookmarked(name = "e")
//    private Boolean edit = true;

    @PostConstruct
    @Override
    public void init() {
        super.init();

        periodePembelajaranTreeInit();

//        periodePembelajaranTree.getSelector().setSelectionsLabel("ps");
//
//        periodePembelajaranTree.getFilter().setStaticFilter(() -> Arrays.asList(new FilterData("parent", null)));
//
//        periodePembelajaranTree.setDefaultList(() -> new ArrayList<>());
//
//        periodePembelajaranTree.setDefaultChecker(()
//                -> periodePembelajaranTree.getFilter()
//                        .<PeriodePembelajaranFilter>getContent()
//                        .getTahunPembelajaran() == null
//        );

//        periodePembelajaranTree.addSelectListener(new SelectorListener() {
//
//            @Override
//            public void onSelect(Object selected) {
//                cache.clear();
//                detailDataView.invalidate();
//            }
//
//        });
        
//        detailDataView.getFilter()
//                .<SantriFilter>getContent()
//                .setStatusKesantrian(StatusKesantrian.ACTIVE);
//        detailDataView.getFilter().setFiltering(true);
//        detailDataView.getPager().setPageSize(1L);
//        detailDataView.getPager().setPageSizeLabel("dPSz");
//        detailDataView.getPager().setOffsetLabel("dOff");
//        detailDataView.getPager().addListener(cache::clear);
//        detailDataView.setName("detailDataTbl");

//        detailDataView.setDefaultList(() -> {
//            if (periodePembelajaranTree.getSelection() == null) {
//                return new ArrayList<>();
//            } else {
//                return null;
//            }
//        });
//        detailDataView.setDefaultCount(() -> {
//            if (periodePembelajaranTree.getSelection() == null) {
//                return 0L;
//            } else {
//                return null;
//            }
//        });
//        detailDataView.setDefaultChecker(() -> {
//            return periodePembelajaranTree.getSelection() == null;
//        });

//        santriList.getFilter()
//                .<SantriFilter>getContent()
//                .setStatusKesantrian(StatusKesantrian.ACTIVE);

//        editorCatatan.addLoadListener(this::loadEditorCatatan);
//        editorCatatan.addSaveListener(this::saveCatatan);
    }

    private void periodePembelajaranTreeInit() {
        periodePembelajaranTree.setSelectionMode(() -> Selector.SINGLE);
        periodePembelajaranTree.setName("periodePembelajaranTbl");
        periodePembelajaranTree.setSelectionsLabel("ps");
    }

//    public void toggleEdit(AjaxBehaviorEvent evt) {
//        edit = !edit;
//        if (!edit) {
//
//            PrimeFaces.current().executeScript("PF('detailBlocker').show()");
//            prepareReport(null);
//            PrimeFaces.current().ajax().update(":detail-frm :pager-frm");
//        }
//    }

//    public Boolean getEdit() {
//        return edit;
//    }

//    public void setEdit(Boolean edit) {
//        this.edit = edit;
//    }

    public void onChangePeriodePembelajaran(ValueChangeEvent evt) {
        PeriodePembelajaran periodePembelajaran = (PeriodePembelajaran) evt.getNewValue();
        periodePembelajaranTree.setSelection(periodePembelajaran);
    }

    public void onChangeSantri(ValueChangeEvent evt) {

    }

//    private String loadEditorCatatan(Object id) {
//        Santri santri = (Santri) id;
//        CatatanPengajaran catatanLaporan = getCatatan(santri);
//        String initialContent = null;
//        if (catatanLaporan != null) {
//            initialContent = catatanLaporan.getCatatan();
//        }
//
//        return initialContent != null ? initialContent : "";
//    }

//    private void saveCatatan(Object id, String content) {
//        Santri santri = (Santri) id;
//        CatatanPengajaran existingCatatanLaporan = getCatatan(santri);
//        if (existingCatatanLaporan == null) {
//            PeriodePembelajaran periode = periodePembelajaranTree.getSelection();
//            CatatanPengajaranId idCatatan = new CatatanPengajaranId(santri.getId(), periode.getId());
//            existingCatatanLaporan = new CatatanPengajaran();
//            existingCatatanLaporan.setId(idCatatan);
//
//            existingCatatanLaporan.setSantri(santri);
//            existingCatatanLaporan.setPeriodePembelajaran(periode);
//
//            existingCatatanLaporan.setCatatan(content);
//
//            catatanLaporanFacade.create(existingCatatanLaporan);
//
//        } else {
//
//            existingCatatanLaporan.setCatatan(content);
//
//            catatanLaporanFacade.edit(existingCatatanLaporan);
//
//        }
//
//        cache.get(santri.getId().toString()).laporanPengajaran
//                .setCatatanLaporan(existingCatatanLaporan);
//    }

//    public List<PencapaianBelajar> getPencapaianBelajar(Santri santri) {
//        return getPencapaianBelajar(santri, periodePembelajaranTree.getSelection());
//    }

//    public List<PencapaianBelajar> getPencapaianBelajar(Santri santri, PeriodePembelajaran periode) {
//        LaporanPengajaran laporanPengajaran = laporanPengajaranFacade.getLaporan(santri, periode);
//        if (!cache.containsKey(santri.getId().toString())) {
//
//            cache.put(santri.getId().toString(),
//                    new CacheContent(laporanPengajaran));
//
//        }
//
//        return cache.get(santri.getId().toString()).laporanPengajaran.getListPencapaianBelajar();
//    }

//    public CatatanPengajaran getCatatan(Santri santri) {
//        return getCatatan(santri, periodePembelajaranTree.getSelection());
//    }

//    public CatatanPengajaran getCatatan(Santri santri, PeriodePembelajaran periode) {
//        LaporanPengajaran laporanPengajaran = laporanPengajaranFacade.getLaporan(santri, periode);
//        if (!cache.containsKey(santri.getId().toString())) {
//
//            cache.put(santri.getId().toString(),
//                    new CacheContent(laporanPengajaran));
//
//        }
//
//        return cache.get(santri.getId().toString()).laporanPengajaran.getCatatanLaporan();
//    }

    public void doFilter(AjaxBehaviorEvent evt) {
        periodePembelajaranTree.doFilter();
        periodePembelajaranTree.setSelections(null);
        updateUrl();
    }
    
    @Override
    protected boolean isReady() {        
        PeriodePembelajaran periode = periodePembelajaranTree.getSelection();
        return periode != null;
    }

    @Override
    protected ReportingJob prepareReportingJob() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tahunPembelajaran", periodePembelajaranTree.getFilter()
                .<PeriodePembelajaranFilter>getContent().getTahunPembelajaran());
        parameters.put("periodePembelajaran", periodePembelajaranTree.getSelection());

        LocalDate issued = LocalDate.now();

        parameters.put("issued", issued.format(DateTimeFormatter.ofPattern("d/M/yyyy")));

        String hijrahDateString = HijrahChronology.INSTANCE.date(issued)
                .format(DateTimeFormatter.ofPattern("d/M/yyyy", new Locale("ar")).withDecimalStyle(DecimalStyle.of(new Locale("ar"))));
        parameters.put("issuedHijri", hijrahDateString);

        List<FilterData> filters = new ArrayList<>();
        
//        List<Santri> listSantri = santriFacade.findAll(
//                detailDataView.getParameters(),
//                detailDataView.getFilter().getValues()
//        );

        List<Santri> listSantri = santriFacade.findAll(filters);

        return new ReportingJob(listSantri, parameters,
                "LaporanPengajaran", "LaporanPencapaianBelajar"
        );

    }

    public PeriodePembelajaranTree getPeriodePembelajaranTree() {
        return periodePembelajaranTree;
    }

//    public SantriList getDetailDataView() {
//        return detailDataView;
//    }

//    public TextEditorBean getEditorCatatan() {
//        return editorCatatan;
//    }

    public KelompokPengasuhanAltList getKelompokPengasuhanList() {
        return kelompokPengasuhanList;
    }

    public void setKelompokPengasuhanList(KelompokPengasuhanAltList kelompokPengasuhanList) {
        this.kelompokPengasuhanList = kelompokPengasuhanList;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

}
