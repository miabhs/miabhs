/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
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
@Named(value = "laporanKepengasuhanPage")
@ViewScoped
public class LaporanKepengasuhanPage extends ReportingPage implements Serializable {

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @Inject
    @Bookmarked
    private KelompokPengasuhanAltList kelompokPengasuhanList;
    
    @Bookmarked(name = "sn")
    private Santri santri;
    
    @Inject
    private SantriFacade santriFacade;

    @PostConstruct
    @Override
    public void init() {
        super.init();

        periodePembelajaranTreeInit();

        kelompokPengasuhanList.setSelectionMode(() -> Selector.SINGLE);
        kelompokPengasuhanList.getSelector().setSelectionsLabel("kp"); 
    }
    
    @Override
    protected boolean isReady() {        
        PeriodePembelajaran periode = periodePembelajaranTree.getSelection();
        return periode != null;
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
    @Override
    public ReportingJob prepareReportingJob() {

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
        KelompokPengasuhan kelompokPengasuhan = kelompokPengasuhanList.getSelection();
        if(kelompokPengasuhan != null) {
            filters.add(FilterData.by("kelompokPengasuhan", kelompokPengasuhanList.getSelection()));
        }
        
        if(santri != null) {
            filters.add(FilterData.by("santri", santri));
        }

        List<Santri> listSantri = santriFacade.findAll(filters);

        return new ReportingJob(listSantri, parameters,
                "RaporKepengasuhan",
                "LaporanKepengasuhan",
                "LaporanKepengasuhanSantri",
                "LaporanKepengasuhanSantriAktifitas"
        );
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

}
