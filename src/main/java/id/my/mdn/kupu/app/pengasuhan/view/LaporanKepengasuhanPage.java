/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.view.widget.PengasuhanSantriFilter;
import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranFilter;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.view.ReportingChildPage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.event.ValueChangeEvent;
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
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "laporanKepengasuhanPage")
@ViewScoped
public class LaporanKepengasuhanPage extends ReportingChildPage implements Serializable {

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @Inject
    private PengasuhanSantriFilter filterContent;
    
    @Inject
    private SantriFacade santriFacade;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        filter.setContent(filterContent);
        periodePembelajaranTreeInit();
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
        periodePembelajaranTree.getFilter().setName("periodeFilter");

        periodePembelajaranTree.setDefaultChecker(
                () -> periodePembelajaranTree.getFilter().<PeriodePembelajaranFilter>getContent().getTahunPembelajaran() == null);
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
        KelompokPengasuhan kelompokPengasuhan = filterContent.getKelompokPengasuhan();
        if(kelompokPengasuhan != null) {
            filters.add(FilterData.by("kelompokPengasuhan", kelompokPengasuhan));
        }
        
        Santri santri = filterContent.getSantri();
        if(santri != null) {
            filters.add(FilterData.by("santri", santri));
        }

        List<Santri> listSantri = santriFacade.findAll(filters);
        
        System.out.println("PRIPER RIPOT: " + parameters.get("periodePembelajaran"));

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

}
