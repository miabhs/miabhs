/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class LaporanSantriScriptlet extends JRDefaultScriptlet {

    private static final Logger Log = Logger.getLogger(LaporanSantriScriptlet.class.getCanonicalName());

    private LaporanAktifitasScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current().select(NamedLiteral.of("laporanAktifitasScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (LaporanAktifitasScriptletHelper) beanInstance.get();
    }

//    @Override
//    public void beforeReportInit() throws JRScriptletException {
//
//        Locale locale = new Locale("ID");
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", locale);
//
//        setVariableValue("fromDate",
//                formatter.format(
//                        (LocalDate) getParameterValue("fromDate")
//                ).replace("Ahad", "Minggu")
//        );
//        setVariableValue("thruDate", formatter.format((LocalDate) getParameterValue("thruDate")).replace("Ahad", "Minggu"));
//        
//        LaporanAktifitasScriptletHelper helper = findHelper();
//        
//        List<FilterData> filters = new ArrayList<>();
//        
//        List<Santri> listSantri = helper.getSantriFacade().findAll(0, 0, filters);
//        setVariableValue("listSantri", listSantri);
//    
//    }

    @Override
    public void beforeGroupInit(String groupName) throws JRScriptletException {
        LaporanAktifitasScriptletHelper helper = findHelper();

        Long santriId = (Long) this.getFieldValue("id");
        LocalDate fromDate = (LocalDate) getParameterValue("fromDate");
        LocalDate thruDate = (LocalDate) getParameterValue("thruDate");

        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("santriId", santriId));
        filters.add(new FilterData("fromDate", fromDate));
        filters.add(new FilterData("thruDate", thruDate));
        
        setVariableValue("kesimpulanBDAS", helper.getAktifitasFacade().calculateKesimpulanBDAS(santriId, fromDate, thruDate).getName());
        
        setVariableValue("kesimpulanNonBDAS", helper.getAktifitasFacade().calculateKesimpulanNonBDAS(santriId, fromDate, thruDate).getName());


        List<HikmahKauniyah> listHikmah = helper.getHikmahKauniyahFacade().findAll(0, 0, filters);
        setVariableValue("listHikmah", listHikmah);

    }
}
