/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.view.widget.AktifitasHarianFilter;
import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Filter;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.view.ReportingPage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "laporanKelompokPage")
@ViewScoped
public class LaporanKelompokPage extends ReportingPage implements Serializable {

    @Inject
    private KelompokPengasuhanFacade kelompokPengasuhanFacade;

    @Inject
    private AktifitasHarianFilter filterContent;

    @Bookmarked
    private Filter filter;

    @PostConstruct
    @Override
    public void init() {
        super.init();

        filter = new Filter(filterContent);
        filter.addListener(this::updateUrl);
    }

    @Override
    protected ReportingJob prepareReportingJob() {

        List<KelompokPengasuhan> listKelompokPengasuhan
                = kelompokPengasuhanFacade.findAll(filter.getValues());

        Map<String, Object> parameters = new HashMap<>();

        if (filterContent.getFromDate() != null) {
            parameters.put("fromDate", filterContent.getFromDate());
        }

        if (filterContent.getThruDate() != null) {
            parameters.put("thruDate", filterContent.getThruDate());
        }

        if (filterContent.getStatus() != null) {
            parameters.put("status", filterContent.getStatus().getName());
        }

        return new ReportingJob(
                listKelompokPengasuhan, parameters,
                "RangkumanAktifitas",                 
                "AktifitasSantri",
                "Aktifitas",
                "PembinaKepengasuhan",
                "PembantuPelaksanaKepengasuhan",
                "KakakKepengasuhan"
        );
    }

    public Filter getFilter() {
        return filter;
    }
}
