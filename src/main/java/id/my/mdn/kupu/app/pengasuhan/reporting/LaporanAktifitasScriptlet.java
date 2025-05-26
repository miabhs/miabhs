/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.santri.entity.FungsionalKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PelaksanaKepengasuhan;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class LaporanAktifitasScriptlet extends JRDefaultScriptlet {

    private static final Logger Log = Logger.getLogger(LaporanAktifitasScriptlet.class.getCanonicalName());

    private LaporanAktifitasScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current().select(NamedLiteral.of("laporanAktifitasScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (LaporanAktifitasScriptletHelper) beanInstance.get();
    }

    @Override
    public void beforeReportInit() throws JRScriptletException {

        Locale locale = new Locale("ID");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", locale);

        setVariableValue("fromDate",
                formatter.format(
                        (LocalDate) getParameterValue("fromDate")
                ).replace("Ahad", "Minggu")
        );
        setVariableValue("thruDate",
                formatter.format((LocalDate) getParameterValue("thruDate")
                ).replace("Ahad", "Minggu"));
    }

    @Override
    public void beforeGroupInit(String groupName) throws JRScriptletException {

        LaporanAktifitasScriptletHelper helper = findHelper();

        Long toRoleId = (Long) this.getFieldValue("kelompokPengasuhanId");

        KelompokPengasuhan kelompokPengasuhan = helper.getKelompokPengasuhanFacade().find(toRoleId);

        FilterData toRoleFilter = FilterData.by("toRole", kelompokPengasuhan);

        List<PelaksanaKepengasuhan> listPembina = helper.getPelaksanaFacade().
                findAll(
                        toRoleFilter,
                        FilterData.by("fungsi", List.of(FungsionalKepengasuhan.PEMBINA_KEPENGASUHAN))
                );
        setVariableValue("listPembina", new JRBeanCollectionDataSource(listPembina));

        List<PelaksanaKepengasuhan> listPembantu = helper.getPelaksanaFacade()
                .findAll(
                        toRoleFilter,
                        FilterData.by("fungsi", List.of(
                                FungsionalKepengasuhan.MASUL_KEPENGASUHAN,
                                FungsionalKepengasuhan.PELAKSANA_KEPENGASUHAN,
                                FungsionalKepengasuhan.PEMBANTU_PELAKSANA_KEPENGASUHAN
                        ))
                );
        listPembantu.sort((e1, e2) -> Integer.compare(e1.getFungsional().getLevel(), e2.getFungsional().getLevel()));
        
        setVariableValue("listPembantu", new JRBeanCollectionDataSource(listPembantu));

        List<PelaksanaKepengasuhan> listKakak = helper.getPelaksanaFacade().
                findAll(
                        toRoleFilter,
                        FilterData.by("fungsi", List.of(FungsionalKepengasuhan.KAKAK_KEPENGASUHAN))
                );
        setVariableValue("listKakak", new JRBeanCollectionDataSource(listKakak));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fromDate", getParameterValue("fromDate"));
        parameters.put("thruDate", getParameterValue("thruDate"));

        List<FilterData> filters = new ArrayList<>();
        
        filters.add(new FilterData("kelompokPengasuhan", kelompokPengasuhan));

        List<Aktifitas> listAktifitas = helper.getAktifitasFacade()
                .findAll(parameters, filters, List.of(
                        new SorterData("santriId"),
                        new SorterData("activityDate")
                ));
        
        setVariableValue("listAktifitas", new JRBeanCollectionDataSource(listAktifitas));

    }
}
