/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.santri.entity.KakakKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PembantuPelaksanaKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.PembinaKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

        Long toRoleId = (Long) this.getFieldValue("id");

        KelompokPengasuhan kelompokPengasuhan = helper.getKelompokPengasuhanFacade().find(toRoleId);

        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("toRole", kelompokPengasuhan));

        List<PembinaKepengasuhan> listPembina = helper.getPembinaFacade().findAll(filters);
        setVariableValue("listPembina", new JRBeanCollectionDataSource(listPembina));

        List<PembantuPelaksanaKepengasuhan> listPembantu = helper.getPembantuFacade().findAll(filters);
        setVariableValue("listPembantu", new JRBeanCollectionDataSource(listPembantu));

        List<KakakKepengasuhan> listKakak = helper.getKakakFacade().findAll(filters);
        setVariableValue("listKakak", new JRBeanCollectionDataSource(listKakak));

        filters.add(new FilterData("kelompokPengasuhan", kelompokPengasuhan));

        List<Santri> listSantri = helper.getSantriFacade().findAll(0, 0, filters);
        setVariableValue("listSantri", new JRBeanCollectionDataSource(listSantri));

        filters.add(new FilterData("kelompokPengasuhan", kelompokPengasuhan));
        filters.add(new FilterData("fromDate", getParameterValue("fromDate")));
        filters.add(new FilterData("thruDate", getParameterValue("thruDate")));

        List<Aktifitas> listAktifitas = helper.getAktifitasFacade()
                .findAll(0, 0, filters,
                        List.of(
                                new SorterData("santriId"),
                                new SorterData("activityDate")
                        )
                );
        setVariableValue("listAktifitas", new JRBeanCollectionDataSource(listAktifitas));

    }
}
