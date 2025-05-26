package id.my.mdn.kupu.app.pengajaran.view;

import id.my.mdn.kupu.app.pengajaran.dao.PelaksanaanHalaqohFacade;
import id.my.mdn.kupu.app.pengajaran.entity.PelaksanaanHalaqoh;
import id.my.mdn.kupu.app.santri.dao.CatatanAbsensiHalaqohFacade;
import id.my.mdn.kupu.app.santri.dao.HalaqohPengajaranSqlFacade;
import id.my.mdn.kupu.app.santri.dao.PeriodePembelajaranFacade;
import id.my.mdn.kupu.app.santri.dao.RangkumanPengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.CatatanAbsensiHalaqoh;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.JenisPeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.KppHalaqoh;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.RangkumanPengajaran;
import id.my.mdn.kupu.app.santri.view.widget.HalaqohPengajaranList;
import id.my.mdn.kupu.app.santri.view.widget.PengajaranFilter;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.view.ReportingChildPage;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author aphasan
 */
@Named(value = "absensiHalaqohPage")
@ViewScoped
public class AbsensiHalaqohPage extends ReportingChildPage implements Serializable {

    @Inject
    private RangkumanPengajaranFacade pengajaranFacade;

    @Inject
    @Bookmarked
    private HalaqohPengajaranList masterDataView;

    @Inject
    private PengajaranFilter filterContent;

    @Inject
    private CatatanAbsensiHalaqohFacade catatanFacade;

    @Inject
    private HalaqohPengajaranSqlFacade halaqohFacade;

    @Inject
    private PelaksanaanHalaqohFacade pelaksanaanHalaqohFacade;

    @Inject
    private PeriodePembelajaranFacade periodePembelajaranFacade;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        filter.setContent(filterContent);

        masterDataView.setSelectionMode(() -> Selector.SINGLE);
        masterDataView.setName("halaqohTbl");
        masterDataView.getFilter().setName("masterFilter");
        masterDataView.getSelector().setSelectionsLabel("hp");
        masterDataView.getPager().setPageSizeLabel("mpz");
        masterDataView.getPager().setOffsetLabel("mpo");
        masterDataView.getSelector().addListener(s -> {
            filterContent.setHalaqohPengajaran(masterDataView.getSelected());
            filter.doFilter();
        });
    }

    private Map<String, Object> parametersPengajaran() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hp", filterContent.getHalaqohPengajaran());

        PeriodePembelajaran periodePembelajaran = filterContent.getPeriodePembelajaran();

        parameters.put("pp", periodePembelajaran);
        return parameters;
    }

    @Override
    protected List<ReportingJob> prepareReportingJob() {

        List<RangkumanPengajaran> listPengajaran = pengajaranFacade.findAll(parametersPengajaran());

        Map<String, Object> parameters = new HashMap<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yy");

        List<PelaksanaanHalaqoh> listDate = pelaksanaanHalaqohFacade
                .getListDate(masterDataView.getSelected(), filterContent.getPeriodePembelajaran(),
                        0, 1, 2, 3, 4, 5);

        for (int i = 1; i <= listDate.size(); i++) {
//            parameters.put("Date" + i, fmt.format(listDate.get(i - 1).getId().getPengajaranDate()));
            parameters.put("P" + i, listDate.get(i - 1));
        }

        HalaqohPengajaran halaqoh = halaqohFacade.findSingleByAttributes(
                List.of(
                        FilterData.by("id", filterContent.getHalaqohPengajaran())
                )
        );

        parameters.put("halaqohPengajaran", halaqoh);

        PeriodePembelajaran periodePembelajaran = filterContent.getPeriodePembelajaran();
        parameters.put("periodePembelajaran", periodePembelajaran);

        PeriodePembelajaran prevPeriod = periodePembelajaranFacade.getPeriodePembelajaranMin(
                periodePembelajaran.getFromDate()
                        .minus(
                                JenisPeriodePembelajaran.PEKANAN.getDuration(),
                                JenisPeriodePembelajaran.PEKANAN.getUnit()),
                JenisPeriodePembelajaran.PEKANAN
        );

        parameters.put("prevPeriodePembelajaran", prevPeriod);

        List<KppHalaqoh> listKppHalaqoh = halaqohFacade.getListKpp(periodePembelajaran, halaqoh);
        parameters.put("listKppHalaqoh", listKppHalaqoh);

        List<CatatanAbsensiHalaqoh> listCatatanAbsensiHalaqoh = catatanFacade.findAll(parameters);
        parameters.put("listCatatanAbsensiHalaqoh", listCatatanAbsensiHalaqoh);

        parameters.put("pengampuName", halaqoh != null ? halaqoh.getPengampuName() : "");
//        parameters.put("jenisKitab", halaqoh != null ? halaqoh.getName().replace("Halaqoh ", "") : "");
        parameters.put("jenisKitab", halaqoh != null ? halaqoh.getJenisKitabJudul() : "");
        parameters.put("kelompokWaktu", halaqoh != null ? halaqoh.getKelompokWaktu() : "");

        return List.of(
                new ReportingJob(
                        listPengajaran, parameters,
                        "AbsensiHalaqoh",
                        "CatatanAbsensiHalaqoh",
                        "DetailCatatanAbsensiHalaqoh",
                        "KPPAbsensiHalaqoh"
                ));
    }

    public HalaqohPengajaranList getMasterDataView() {
        return masterDataView;
    }

}
