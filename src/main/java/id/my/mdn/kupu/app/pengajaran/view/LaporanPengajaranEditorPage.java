/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view;

import id.my.mdn.kupu.app.pengajaran.dao.CatatanPengajaranFacade;
import id.my.mdn.kupu.app.pengajaran.dao.LaporanPengajaranFacade;
import id.my.mdn.kupu.app.pengajaran.entity.CatatanPengajaran;
import id.my.mdn.kupu.app.pengajaran.entity.CatatanPengajaranId;
import id.my.mdn.kupu.app.pengajaran.entity.LaporanPengajaran;
import id.my.mdn.kupu.app.pengajaran.entity.PencapaianBelajar;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.view.widget.KelompokPengasuhanAltList;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.app.santri.view.widget.SantriFilter;
import id.my.mdn.kupu.app.santri.view.widget.SantriList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.base.view.widget.TextEditorBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "laporanPengajaranEditorPage")
@ViewScoped
public class LaporanPengajaranEditorPage extends Page implements Serializable {

    private static class CacheContent {

        public final LaporanPengajaran laporanPengajaran;

        public CacheContent(LaporanPengajaran laporanPengajaran) {
            this.laporanPengajaran = laporanPengajaran;
        }
    }

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @Inject
    @Bookmarked
    private KelompokPengasuhanAltList kelompokPengasuhanList;
    
    @Bookmarked(name = "sn")
    private Santri santri;

    @Inject
    @Bookmarked
    private SantriList santriList;

    @Inject
    private TextEditorBean editorCatatan;

    @Inject
    private CatatanPengajaranFacade catatanLaporanFacade;

    @Inject
    private LaporanPengajaranFacade laporanPengajaranFacade;

    private final Map<String, CacheContent> cache = new HashMap<>();


    @PostConstruct
    @Override
    public void init() {
        super.init();

        periodePembelajaranTreeInit();        
        
        santriList.getFilter()
                .<SantriFilter>getContent()
                .setStatusKesantrian(StatusKesantrian.ACTIVE);
        santriList.getFilter().setFiltering(true);
        santriList.getPager().setPageSize(1L);
        santriList.getPager().setPageSizeLabel("dPSz");
        santriList.getPager().setOffsetLabel("dOff");
        santriList.getPager().addListener(cache::clear);
        santriList.setName("detailDataTbl");

        santriList.setDefaultList(() -> new ArrayList<>());
        santriList.setDefaultCount(() -> 0L);
        santriList.setDefaultChecker(() -> periodePembelajaranTree.getSelection() == null);

        editorCatatan.addLoadListener(this::loadEditorCatatan);
        editorCatatan.addSaveListener(this::saveCatatan);

        kelompokPengasuhanList.setSelectionMode(() -> Selector.SINGLE);
        kelompokPengasuhanList.getSelector().setSelectionsLabel("kp");
    }

    private void periodePembelajaranTreeInit() {
        periodePembelajaranTree.setSelectionMode(() -> Selector.SINGLE);
        periodePembelajaranTree.setName("periodePembelajaranTbl");
        periodePembelajaranTree.setSelectionsLabel("ps");
    }

    public void onChangePeriodePembelajaran(ValueChangeEvent evt) {
        PeriodePembelajaran periodePembelajaran = (PeriodePembelajaran) evt.getNewValue();
        periodePembelajaranTree.setSelection(periodePembelajaran);
    }

    public void onChangeSantri(ValueChangeEvent evt) {

    }

    private String loadEditorCatatan(Object id) {
        Santri santri = (Santri) id;
        CatatanPengajaran catatanLaporan = getCatatan(santri);
        String initialContent = null;
        if (catatanLaporan != null) {
            initialContent = catatanLaporan.getCatatan();
        }

        return initialContent != null ? initialContent : "";
    }

    private void saveCatatan(Object id, String content) {
        Santri santri = (Santri) id;
        CatatanPengajaran existingCatatanLaporan = getCatatan(santri);
        if (existingCatatanLaporan == null) {
            PeriodePembelajaran periode = periodePembelajaranTree.getSelection();
            CatatanPengajaranId idCatatan = new CatatanPengajaranId(santri.getId(), periode.getId());
            existingCatatanLaporan = new CatatanPengajaran();
            existingCatatanLaporan.setId(idCatatan);

            existingCatatanLaporan.setSantri(santri);
            existingCatatanLaporan.setPeriodePembelajaran(periode);

            existingCatatanLaporan.setCatatan(content);

            catatanLaporanFacade.create(existingCatatanLaporan);

        } else {

            existingCatatanLaporan.setCatatan(content);

            catatanLaporanFacade.edit(existingCatatanLaporan);

        }

        cache.get(santri.getId().toString()).laporanPengajaran
                .setCatatanLaporan(existingCatatanLaporan);
    }

    public List<PencapaianBelajar> getPencapaianBelajar(Santri santri) {
        if(santri == null) return List.of();
        return getPencapaianBelajar(santri, periodePembelajaranTree.getSelection());
    }

    public List<PencapaianBelajar> getPencapaianBelajar(Santri santri, PeriodePembelajaran periode) {
        LaporanPengajaran laporanPengajaran = laporanPengajaranFacade.getLaporan(santri, periode);
        if (!cache.containsKey(santri.getId().toString())) {

            cache.put(santri.getId().toString(),
                    new CacheContent(laporanPengajaran));

        }

        return cache.get(santri.getId().toString()).laporanPengajaran.getListPencapaianBelajar();
    }

    public CatatanPengajaran getCatatan(Santri santri) {
        return getCatatan(santri, periodePembelajaranTree.getSelection());
    }

    public CatatanPengajaran getCatatan(Santri santri, PeriodePembelajaran periode) {
        LaporanPengajaran laporanPengajaran = laporanPengajaranFacade.getLaporan(santri, periode);
        if (!cache.containsKey(santri.getId().toString())) {

            cache.put(santri.getId().toString(),
                    new CacheContent(laporanPengajaran));

        }

        return cache.get(santri.getId().toString()).laporanPengajaran.getCatatanLaporan();
    }

    public void doFilter(AjaxBehaviorEvent evt) {
        periodePembelajaranTree.doFilter();
        periodePembelajaranTree.setSelections(null);
        updateUrl();
    }

    public PeriodePembelajaranTree getPeriodePembelajaranTree() {
        return periodePembelajaranTree;
    }

    public SantriList getSantriList() {
        return santriList;
    }

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

    public TextEditorBean getEditorCatatan() {
        return editorCatatan;
    }

}
