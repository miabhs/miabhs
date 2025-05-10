/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view;

import id.my.mdn.kupu.app.pengajaran.view.widget.PengajaranSantriFilter;
import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.widget.KelompokPengasuhanSelectList;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranFilter;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.view.ReportingChildPage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ValueChangeEvent;
import org.omnifaces.cdi.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.chrono.HijrahChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
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
public class LaporanPengajaranPage extends ReportingChildPage implements Serializable {

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @Inject
    @Bookmarked
    private KelompokPengasuhanSelectList kelompokPengasuhanList;

    @Inject
    private PengajaranSantriFilter filterContent;
    
    @Inject
    private SantriFacade santriFacade;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        filter.setContent(filterContent);

        periodePembelajaranTreeInit();
    }

    private void periodePembelajaranTreeInit() {
        periodePembelajaranTree.setSelectionMode(() -> Selector.SINGLE);
        periodePembelajaranTree.setName("periodePembelajaranTbl");
        periodePembelajaranTree.setSelectionsLabel("ps");
    }

    public void onChangePeriodePembelajaran(ValueChangeEvent evt) {
        PeriodePembelajaran periodePembelajaran = (PeriodePembelajaran) evt.getNewValue();
        periodePembelajaranTree.setSelection(periodePembelajaran);
        
        periodePembelajaranTree.getSelector().addListener(s -> filter.doFilter());
    }
    
    @Override
    protected boolean isReady() {        
        PeriodePembelajaran periode = periodePembelajaranTree.getSelection();
        return periode != null;
    }

    @Override
    protected ReportingJob prepareReportingJob() {
        
        System.out.println("RIPOT: prepareReportingJob()");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tahunPembelajaran", periodePembelajaranTree.getFilter()
                .<PeriodePembelajaranFilter>getContent().getTahunPembelajaran());
        parameters.put("periodePembelajaran", periodePembelajaranTree.getSelection());

        LocalDate issued = LocalDate.now();

        parameters.put("issued", issued.format(DateTimeFormatter.ofPattern("d/M/yyyy")));

        String hijrahDateString = HijrahChronology.INSTANCE.date(issued)
                .format(DateTimeFormatter.ofPattern(
                        "d/M/yyyy", new Locale("ar")).withDecimalStyle(DecimalStyle.of(new Locale("ar"))));
        parameters.put("issuedHijri", hijrahDateString);

        List<Santri> listSantri = santriFacade.findAll(filter.getValues());

        return new ReportingJob(listSantri, parameters,
                "LaporanPengajaran", "LaporanPencapaianBelajar"
        );

    }

    public PeriodePembelajaranTree getPeriodePembelajaranTree() {
        return periodePembelajaranTree;
    }

    public KelompokPengasuhanSelectList getKelompokPengasuhanList() {
        return kelompokPengasuhanList;
    }

    public void setKelompokPengasuhanList(KelompokPengasuhanSelectList kelompokPengasuhanList) {
        this.kelompokPengasuhanList = kelompokPengasuhanList;
    }

}
