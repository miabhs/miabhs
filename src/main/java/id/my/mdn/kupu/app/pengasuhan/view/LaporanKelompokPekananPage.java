/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.dao.RangkumanKepengasuhanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanKepengasuhan;
import id.my.mdn.kupu.app.pengasuhan.view.widget.LaporanKelompokFilter;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranFilter;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import static id.my.mdn.kupu.core.base.view.widget.Selector.CHECKBOX;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.view.ReportingChildPage;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "laporanKelompokPekananPage")
@ViewScoped
public class LaporanKelompokPekananPage extends ReportingChildPage implements Serializable {

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @Inject
    private LaporanKelompokFilter filterContent;

    @Inject
    private RangkumanKepengasuhanFacade rangkumanFacade;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        filter.setContent(filterContent);
        periodePembelajaranTreeInit(); 
    }

    private void periodePembelajaranTreeInit() {
        periodePembelajaranTree.setSelectionMode(() -> CHECKBOX);
        periodePembelajaranTree.setName("periodePembelajaranTbl");
        periodePembelajaranTree.setSelectionsLabel("ps");
        periodePembelajaranTree.getFilter().setName("periodeFilter");

        periodePembelajaranTree.setDefaultChecker(
                () -> periodePembelajaranTree.getFilter().<PeriodePembelajaranFilter>getContent().getTahunPembelajaran() == null);

        periodePembelajaranTree.getSelector().addListenerInternal((obj) -> {

            List<PeriodePembelajaran> selections = (List<PeriodePembelajaran>) obj;

            Optional<LocalDate> min = selections.stream().map(o -> o.getFromDate())
                    .sorted((a, b) -> a.compareTo(b)).findFirst();

            Optional<LocalDate> max = selections.stream().map(o -> o.getThruDate())
                    .sorted((a, b) -> (a.compareTo(b) * (-1))).findFirst();

            if (min.isPresent() && max.isPresent()) {
                filterContent.setFromDate(min.get());
                filterContent.setThruDate(max.get());
            } else {
                filterContent.setFromDate(null);
                filterContent.setThruDate(null);
            }
        });
    }

    private String generateFindAllQuery() {
        List<PeriodePembelajaran> listPeriode = periodePembelajaranTree.getSelections();
        Collections.sort(listPeriode);
        return rangkumanFacade.generateQueryForPeriods(
                listPeriode,
                filterContent.getKelompokPengasuhan()
        );
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
                = (listPeriode != null && !listPeriode.isEmpty())
                ? rangkumanFacade.findAll(
                        this::generateFindAllQuery,
                        0, 0, null, null, List.of(SorterData.by("kelompokPengasuhanId")), null, null
                ) : new ArrayList<>();

        Map<String, Object> parameters = new HashMap<>();

        if (filterContent.getFromDate() != null) {
            parameters.put("fromDate", filterContent.getFromDate());
        }

        if (filterContent.getThruDate() != null) {
            parameters.put("thruDate", filterContent.getThruDate());
        }

        return new ReportingJob(
                listRangkumanKepengasuhan, parameters,
                "RangkumanAktifitas",
                "PembinaKepengasuhan",
                "PembantuPelaksanaKepengasuhan",
                "KakakKepengasuhan",
                "AktifitasSantri",
                "Aktifitas"
        );
    }

    public PeriodePembelajaranTree getPeriodePembelajaranTree() {
        return periodePembelajaranTree;
    }
}
