/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.dao.RangkumanKepengasuhanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import static id.my.mdn.kupu.app.santri.entity.StatusKesantrian.ACTIVE;
import id.my.mdn.kupu.app.santri.view.widget.KelompokPengasuhanAltList;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.app.santri.view.widget.SantriAltList;
import id.my.mdn.kupu.app.santri.view.widget.SantriFilter;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.view.ReportingPage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "laporanSantriPage")
@ViewScoped
public class LaporanSantriPage extends ReportingPage implements Serializable {

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @Inject
    @Bookmarked
    private KelompokPengasuhanAltList kelompokPengasuhanList;

    @Inject
    @Bookmarked
    private SantriAltList santriList;

    @Inject
    private RangkumanKepengasuhanFacade rangkumanFacade;
    
    public static Object NULL() { return null; }

    @PostConstruct
    @Override
    public void init() {
        super.init();
        periodePembelajaranTreeInit();

        kelompokPengasuhanList.setSelectionMode(() -> Selector.SINGLE);
        kelompokPengasuhanList.getSelector().setSelectionsLabel("kp"); 

        santriList.setSelectionMode(() -> Selector.SINGLE);
        santriList.getSelector().setSelectionsLabel("sn");        
        santriList.getFilter().<SantriFilter>getContent()
                .setStatusKesantrian(ACTIVE);
        
    }

    private void periodePembelajaranTreeInit() {
        periodePembelajaranTree.setSelectionMode(() -> Selector.CHECKBOX);
        periodePembelajaranTree.setName("periodePembelajaranTbl");
        periodePembelajaranTree.setSelectionsLabel("ps");
    }

    public void doFilter(AjaxBehaviorEvent evt) {
        periodePembelajaranTree.doFilter();
        periodePembelajaranTree.setSelections(null);
        updateUrl();
    }

    private String generateFindAllQuery() {
        List<PeriodePembelajaran> listPeriode = periodePembelajaranTree.getSelections();
        Collections.sort(listPeriode);
        return rangkumanFacade.generateQueryForPeriods(listPeriode, kelompokPengasuhanList.getSelection(), santriList.getSelection());
    }
    
    @Override
    protected boolean isReady() {        
        List<PeriodePembelajaran> listPeriode = periodePembelajaranTree.getSelections();
        return listPeriode != null && !listPeriode.isEmpty();
    }

    @Override
    protected ReportingJob prepareReportingJob() {
        
        List<PeriodePembelajaran> listPeriode = periodePembelajaranTree.getSelections();
        
        List<RangkumanKepengasuhan> listRangkumanKepengasuhan 
                = (listPeriode != null && !listPeriode.isEmpty()) ? 
                rangkumanFacade.findAll(
                this::generateFindAllQuery,
                0, 0, null, null, null, null, null
        ) : new ArrayList<>();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("JenisBDAS", "BDAS");
        parameters.put("JenisNonBDAS", "NON_BDAS");

        return new ReportingJob(
                listRangkumanKepengasuhan, parameters, 
                "LaporanSantri",  
                "CatatanKepengasuhan",
                "HikmahKauniyah"
        );
        
    }
    
    public SantriAltList getSantriList() {
        return santriList;
    }

    public KelompokPengasuhanAltList getKelompokPengasuhanList() {
        return kelompokPengasuhanList;
    }
    
    public PeriodePembelajaranTree getPeriodePembelajaranTree() {
        return periodePembelajaranTree;
    }
}
