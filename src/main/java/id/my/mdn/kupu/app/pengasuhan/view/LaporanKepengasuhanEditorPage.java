/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.dao.CatatanKepengasuhanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.CatatanKepengasuhan;
import id.my.mdn.kupu.app.pengasuhan.entity.CatatanKepengasuhanId;
import id.my.mdn.kupu.app.pengasuhan.entity.LaporanKepengasuhan;
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
@Named(value = "laporanKepengasuhanEditorPage")
@ViewScoped
public class LaporanKepengasuhanEditorPage extends Page implements Serializable {

    private static class CacheContent {

        private List<LaporanKepengasuhan> listLaporan;

        public CacheContent(List<LaporanKepengasuhan> listLaporan) {
            this.listLaporan = listLaporan;
        }
    }

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @Inject
    @Bookmarked
    private SantriList santriList;

    @Inject
    private TextEditorBean editorCatatanPengasuh;

    @Inject
    private TextEditorBean editorCatatanMasul;

    @Inject
    private CatatanKepengasuhanFacade catatanLaporanFacade;

    @Inject
    private AktifitasFacade aktifitasFacade;

    private final Map<String, CacheContent> cache = new HashMap<>();

    @Inject
    @Bookmarked
    private KelompokPengasuhanAltList kelompokPengasuhanList;
    
    @Bookmarked(name = "sn")
    private Santri santri;    

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

        editorCatatanPengasuh.addLoadListener(this::loadEditorCatatanPengasuh);
        editorCatatanPengasuh.addSaveListener(this::saveCatatanPengasuh);
        editorCatatanMasul.addLoadListener(this::loadEditorCatatanMasul);
        editorCatatanMasul.addSaveListener(this::saveCatatanMasul);

        kelompokPengasuhanList.setSelectionMode(() -> Selector.SINGLE);
        kelompokPengasuhanList.getSelector().setSelectionsLabel("kp"); 
    }

    private void periodePembelajaranTreeInit() {
        periodePembelajaranTree.setSelectionMode(() -> Selector.SINGLE);
        periodePembelajaranTree.setName("periodePembelajaranTbl");
        periodePembelajaranTree.setSelectionsLabel("ps");
    }

    public void doFilter(AjaxBehaviorEvent evt) {
        periodePembelajaranTree.doFilter();
        periodePembelajaranTree.setSelections(null);
        updateUrl();
    }

    public void onChangeSantri(ValueChangeEvent evt) {

    }

    private String loadEditorCatatanPengasuh(Object id) {
        LaporanKepengasuhan laporan = (LaporanKepengasuhan) id;
        CatatanKepengasuhan catatanLaporan = laporan.getCatatanLaporan();
        String initialContent = null;
        if (catatanLaporan != null) {
            initialContent = catatanLaporan.getCatatanPengasuh();
        }

        return initialContent != null ? initialContent : "";
    }

    private String loadEditorCatatanMasul(Object id) {
        LaporanKepengasuhan laporan = (LaporanKepengasuhan) id;
        CatatanKepengasuhan catatanLaporan = laporan.getCatatanLaporan();
        String initialContent = null;
        if (catatanLaporan != null) {
            initialContent = catatanLaporan.getCatatanMasul();
        }

        return initialContent != null ? initialContent : "";
    }

    private void saveCatatanPengasuh(Object id, String content) {
        LaporanKepengasuhan laporan = (LaporanKepengasuhan) id;
        CatatanKepengasuhan existingCatatanLaporan = laporan.getCatatanLaporan();
        if (existingCatatanLaporan == null) {
            CatatanKepengasuhanId idCatatan = new CatatanKepengasuhanId(laporan.getSantri().getId(), laporan.getPeriode().getId());
            existingCatatanLaporan = new CatatanKepengasuhan();
            existingCatatanLaporan.setId(idCatatan);

            existingCatatanLaporan.setSantri(laporan.getSantri());
            existingCatatanLaporan.setPeriodePembelajaran(laporan.getPeriode());

            existingCatatanLaporan.setCatatanPengasuh(content);

            catatanLaporanFacade.create(existingCatatanLaporan);

        } else {

            existingCatatanLaporan.setCatatanPengasuh(content);

            catatanLaporanFacade.edit(existingCatatanLaporan);

        }

        int idxLaporan = cache.get(laporan.getSantri().getId().toString()).listLaporan.indexOf(laporan);
        cache.get(laporan.getSantri().getId().toString())
                .listLaporan.get(idxLaporan)
                .setCatatanLaporan(existingCatatanLaporan);
    }

    private void saveCatatanMasul(Object id, String content) {
        LaporanKepengasuhan laporan = (LaporanKepengasuhan) id;
        CatatanKepengasuhan existingCatatanLaporan = laporan.getCatatanLaporan();
        if (existingCatatanLaporan == null) {
            CatatanKepengasuhanId idCatatan = new CatatanKepengasuhanId(laporan.getSantri().getId(), laporan.getPeriode().getId());
            existingCatatanLaporan = new CatatanKepengasuhan();
            existingCatatanLaporan.setId(idCatatan);

            existingCatatanLaporan.setSantri(laporan.getSantri());
            existingCatatanLaporan.setPeriodePembelajaran(laporan.getPeriode());

            existingCatatanLaporan.setCatatanMasul(content);

            catatanLaporanFacade.create(existingCatatanLaporan);

        } else {

            existingCatatanLaporan.setCatatanMasul(content);

            catatanLaporanFacade.edit(existingCatatanLaporan);

        }

        int idxLaporan = cache.get(laporan.getSantri().getId().toString()).listLaporan.indexOf(laporan);
        cache.get(laporan.getSantri().getId().toString())
                .listLaporan.get(idxLaporan)
                .setCatatanLaporan(existingCatatanLaporan);
    }

    public List<LaporanKepengasuhan> getRangkumanPeriode(Santri santri) {
        if(santri == null) return List.of();
        return getRangkumanPeriode(santri, periodePembelajaranTree.getSelection());
    }

    public List<LaporanKepengasuhan> getRangkumanPeriode(Santri santri, PeriodePembelajaran periode) {
         if (!cache.containsKey(santri.getId().toString())) {

            cache.put(santri.getId().toString(),
                    new CacheContent(aktifitasFacade.getAllRangkumanPeriode(santri, periode)));

        }
        
        return cache.get(santri.getId().toString()).listLaporan;
    }

    public String getCatatanPengasuh(LaporanKepengasuhan laporan) {
        if (laporan != null && laporan.getCatatanLaporan() != null && laporan.getCatatanLaporan().getCatatanPengasuh() != null) {
            return laporan.getCatatanLaporan().getCatatanPengasuh();
        } else {
            return null;
        }
    }

    public String getCatatanMasul(LaporanKepengasuhan laporan) {
        if (laporan != null && laporan.getCatatanLaporan() != null && laporan.getCatatanLaporan().getCatatanMasul() != null) {
            return laporan.getCatatanLaporan().getCatatanMasul();
        } else {
            return null;
        }
    }

    public PeriodePembelajaranTree getPeriodePembelajaranTree() {
        return periodePembelajaranTree;
    }

    public KelompokPengasuhanAltList getKelompokPengasuhanList() {
        return kelompokPengasuhanList;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public SantriList getSantriList() {
        return santriList;
    }

    public TextEditorBean getEditorCatatanPengasuh() {
        return editorCatatanPengasuh;
    }

    public TextEditorBean getEditorCatatanMasul() {
        return editorCatatanMasul;
    }

}
