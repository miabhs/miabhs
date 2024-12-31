/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.entity.Status;
import id.my.mdn.kupu.app.pengasuhan.view.widget.AktifitasHarianFilter;
import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.view.widget.KelompokPengasuhanAltList;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.view.ReportingPage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
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
@Named(value = "laporanKelompokPekananPage")
@ViewScoped
public class LaporanKelompokPekananPage extends ReportingPage implements Serializable {

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @Inject
    @Bookmarked
    private KelompokPengasuhanAltList kelompokPengasuhanList;

    @Inject
    private KelompokPengasuhanFacade kelompokPengasuhanFacade;

    @Inject
    private AktifitasHarianFilter filterContent;

    @Bookmarked(name = "st")
    private Status status;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        periodePembelajaranTreeInit();

        kelompokPengasuhanList.setSelectionMode(() -> SINGLE);
        kelompokPengasuhanList.getSelector().setSelectionsLabel("kp");
    }

    private void periodePembelajaranTreeInit() {
        periodePembelajaranTree.setSelectionMode(() -> SINGLE);
        periodePembelajaranTree.setName("periodePembelajaranTbl");
        periodePembelajaranTree.setSelectionsLabel("ps");
    }

    public void doFilter(AjaxBehaviorEvent evt) {
        periodePembelajaranTree.doFilter();
        periodePembelajaranTree.setSelections(null);
        updateUrl();
    }

    @Override
    protected boolean isReady() {
        PeriodePembelajaran listPeriode = periodePembelajaranTree.getSelection();
        return listPeriode != null;
    }

    @Override
    protected ReportingJob prepareReportingJob() {

        List<FilterData> filters = new ArrayList<>();
        
        KelompokPengasuhan kelompokPengasuhan = kelompokPengasuhanList.getSelection();
        if (kelompokPengasuhan != null) {
            filters.add(FilterData.by("kelompokPengasuhan", kelompokPengasuhan));
        }

        List<KelompokPengasuhan> listKelompokPengasuhan
                = kelompokPengasuhanFacade.findAll(filters);

        Map<String, Object> parameters = new HashMap<>();

        if (filterContent.getFromDate() != null) {
            parameters.put("fromDate", periodePembelajaranTree.getSelection().getFromDate());
        }

        if (filterContent.getThruDate() != null) {
            parameters.put("thruDate", periodePembelajaranTree.getSelection().getThruDate());
        }

        if (filterContent.getStatus() != null) {
            parameters.put("status", status);
        }

        return new ReportingJob(
                listKelompokPengasuhan, parameters, 
                "RangkumanAktifitas",
                "PembinaKepengasuhan",
                "PembantuPelaksanaKepengasuhan",
                "KakakKepengasuhan",
                "AktifitasSantri",
                "Aktifitas"
        );
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PeriodePembelajaranTree getPeriodePembelajaranTree() {
        return periodePembelajaranTree;
    }

    public KelompokPengasuhanAltList getKelompokPengasuhanList() {
        return kelompokPengasuhanList;
    }
}
