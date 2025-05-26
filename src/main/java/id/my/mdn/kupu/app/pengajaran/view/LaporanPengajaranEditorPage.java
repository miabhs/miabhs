/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view;

import id.my.mdn.kupu.app.pengajaran.dao.CatatanPengajaranFacade;
import id.my.mdn.kupu.app.pengajaran.dao.LaporanPengajaranFacade;
import id.my.mdn.kupu.app.pengajaran.dao.PencapaianPembelajaranFacade;
import id.my.mdn.kupu.app.pengajaran.entity.CatatanPengajaran;
import id.my.mdn.kupu.app.pengajaran.entity.CatatanPengajaranId;
import id.my.mdn.kupu.app.pengajaran.entity.LaporanPengajaran;
import id.my.mdn.kupu.app.pengajaran.entity.PencapaianBelajar;
import id.my.mdn.kupu.app.pengajaran.entity.PencapaianPembelajaran;
import id.my.mdn.kupu.app.pengajaran.view.widget.PengajaranSantriFilter;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.app.santri.view.widget.SantriFilter;
import id.my.mdn.kupu.app.santri.view.widget.SantriList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Filter;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.base.view.widget.TextEditorBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.omnifaces.cdi.ViewScoped;

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
    private SantriList santriList;

    @Inject
    private TextEditorBean editorCatatan;

    @Inject
    private CatatanPengajaranFacade catatanLaporanFacade;

    @Inject
    private LaporanPengajaranFacade laporanPengajaranFacade;

    private final Map<String, CacheContent> cache = new HashMap<>();

    private final Filter filter;

    @Inject
    private PengajaranSantriFilter filterContent;

    public LaporanPengajaranEditorPage() {
        filter = new Filter(this::onFilter);
    }

    public void onFilter(Object obj) {
        santriList.getFilter().<SantriFilter>getContent()
                .setKelompokPengasuhan(
                        filterContent.getKelompokPengasuhan()
                );
        santriList.reset();
    }

    @Override
    public Map<String, List<String>> getStates() {
        Map<String, List<String>> states = super.getStates();
        states.putAll(filter.getStates());
        return states;
    }

    public Filter getFilter() {
        return filter;
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();

        filter.setContent(filterContent);

        periodePembelajaranTreeInit();

        santriList.getFilter()
                .<SantriFilter>getContent()
                .setStatusKesantrian(StatusKesantrian.ACTIVE);
        santriList.getPager().setPageSize(1L);
        santriList.getPager().setPageSizeLabel("dPSz");
        santriList.getPager().setOffsetLabel("dOff");
        santriList.getPager().addListener(cache::clear);
        santriList.setName("detailDataTbl");

        santriList.setDefaultList(() -> new ArrayList<>());
        santriList.setDefaultCount(() -> 0L);
        santriList.setDefaultChecker(() -> periodePembelajaranTree.getSelection() == null);
        
        santriList.getFilter().setStaticFilter(() -> {
            List<FilterData> filters = new ArrayList<>();
            
            Santri santri = filterContent.getSantri();
            if(santri != null) filters.add(FilterData.by("santri", santri));
            
            KelompokPengasuhan kelompok = filterContent.getKelompokPengasuhan();
            if(kelompok != null) filters.add(FilterData.by("kelompokPengasuhan", kelompok));
            
            return filters;
        });

        editorCatatan.addLoadListener(this::loadEditorCatatan);
        editorCatatan.addSaveListener(this::saveCatatan);
    }

    private void periodePembelajaranTreeInit() {
        periodePembelajaranTree.setSelectionMode(() -> Selector.SINGLE);
        periodePembelajaranTree.setName("periodePembelajaranTbl");
        periodePembelajaranTree.setSelectionsLabel("ps");
        periodePembelajaranTree.getFilter().setName("periodeFilter");

        periodePembelajaranTree.getSelector().addListener(s -> santriList.reset());
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
        if (santri == null) {
            return List.of();
        }
        return getPencapaianBelajar(santri, periodePembelajaranTree.getSelection());
    }
    
    @Inject
    private PencapaianPembelajaranFacade pencapaianPembelajaranFacade;

    public List<PencapaianPembelajaran> getPencapaianPembelajaran(Santri santri) {
        if (santri == null) {
            return List.of();
        }
        return getPencapaianPembelajaran(santri, periodePembelajaranTree.getSelection());
    }
    
    public List<PencapaianPembelajaran> getPencapaianPembelajaran(Santri santri, PeriodePembelajaran periode) {
        return pencapaianPembelajaranFacade.getPencapaianPembelajaran(santri, periode);
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
        return pencapaianPembelajaranFacade.getCatatanPembelajaran(santri, periodePembelajaranTree.getSelection());
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

    public TextEditorBean getEditorCatatan() {
        return editorCatatan;
    }

}
