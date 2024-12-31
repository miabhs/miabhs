/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.dao.RangkumanAktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanAktifitas;
import id.my.mdn.kupu.app.pengasuhan.view.widget.AktifitasHarianFilter;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterListener;
import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.OnInit;
import id.my.mdn.kupu.core.base.view.annotation.OnReload;
import id.my.mdn.kupu.core.base.view.util.ConverterUtil;
import id.my.mdn.kupu.core.base.view.widget.Filter;
import id.my.mdn.kupu.core.base.view.widget.IFilterable;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.service.ReportingJobQueue;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.convert.Converter;
import jakarta.faces.event.ActionEvent;
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
@Named("laporanAktifitasPage")
@ViewScoped
public class LaporanAktifitasPage extends ChildPage implements IFilterable, Serializable {

    @Inject
    private AktifitasHarianFilter filterContent;

    private Filter filter;

    @Inject
    private ReportingJobQueue jobQueue;

    @Inject
    private RangkumanAktifitasFacade rangkumanAktifitasFacade;

    @OnInit
    public void init() {
        filter.setContent(filterContent);
    }

    @OnReload
    public void reload() {
        prepareReport(null);
    }

    public void prepareReport(ActionEvent event) {

        List<RangkumanAktifitas> listRangkumanAktifitas = rangkumanAktifitasFacade.findAll(filter.getValues());

//        listRangkumanAktifitas.stream().forEach(ra -> {
//            ra.setKesimpulanPokok(
//                    pengasuhanService.calculateRangeStatusPokok(ra.getId(), filter.getFromDate(), filter.getThruDate()).getName()
//            );
//            ra.setKesimpulanTambahan(
//                    pengasuhanService.calculateRangeStatusTambahan(ra.getId(), filter.getFromDate(), filter.getThruDate()).getName()
//            );
//        });
        Map<String, Object> parameters = new HashMap<>();

        if (filterContent.getKelompokPengasuhan() != null) {
            parameters.put("kelompokPengasuhan", filterContent.getKelompokPengasuhan().getParty().getName());
        }

        if (filterContent.getKelompokPengasuhan() != null) {
            parameters.put("kelompok", filterContent.getKelompokPengasuhan().getParty().getName());
        }
        if (filterContent.getStatus() != null) {
            parameters.put("status", filterContent.getStatus().getName());
        }

        Converter converter = ConverterUtil.findConverter(CDI.current(), "IslamicDateConverter");

        if (converter != null) {
            if (filterContent.getFromDate() != null) {
                parameters.put("fromDate", converter.getAsString(null, null, filterContent.getFromDate()));
            }
            if (filterContent.getThruDate() != null) {
                parameters.put("thruDate", converter.getAsString(null, null, filterContent.getThruDate()));
            }
        }

        jobQueue.put(new ReportingJob(
                listRangkumanAktifitas, parameters,
                "RangkumanAktifitas",
                "AktifitasHarian",
                "Aktifitas",
                "AktifitasTambahan",
                "PembinaKepengasuhan",
                "PembantuPelaksanaKepengasuhan",
                "KakakKepengasuhan"
        ));
    }

    @Override
    public void doFilter() {

    }

    @Override
    public void clearFilter() {

    }

    @Override
    public void addFilterListener(FilterListener filterListener) {

    }

}
