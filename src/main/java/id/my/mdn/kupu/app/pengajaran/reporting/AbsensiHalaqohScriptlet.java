/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.reporting;

import id.my.mdn.kupu.app.santri.entity.DetailCatatanAbsensiHalaqoh;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class AbsensiHalaqohScriptlet extends JRDefaultScriptlet {

    private AbsensiHalaqohScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current()
                .select(NamedLiteral.of("absensiHalaqohScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (AbsensiHalaqohScriptletHelper) beanInstance.get();
    }       

    @Override
    public void beforeDetailEval() throws JRScriptletException {
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("santriId", getFieldValue("santriId"));
        PeriodePembelajaran periodePembelajaran = (PeriodePembelajaran) getParameterValue("prevPeriodePembelajaran");
        if(periodePembelajaran == null) {
            PeriodePembelajaran currentPeriod = (PeriodePembelajaran) getParameterValue("periodePembelajaran");
            periodePembelajaran = new PeriodePembelajaran();
            periodePembelajaran.setFromDate(currentPeriod.getFromDate().minus(currentPeriod.getJenisPeriode().getDuration(), currentPeriod.getJenisPeriode().getUnit()));
            periodePembelajaran.setThruDate(currentPeriod.getThruDate().minus(currentPeriod.getJenisPeriode().getDuration(), currentPeriod.getJenisPeriode().getUnit()));
        }
        parameters.put("periodePembelajaran", periodePembelajaran);
        
        List<DetailCatatanAbsensiHalaqoh> listDetailCatatanAbsensiHalaqoh 
                = findHelper().getDetailCatatanFacade().findAll(parameters);
        
        String catatan = listDetailCatatanAbsensiHalaqoh.stream()
                .map(e -> e.toString()).collect(Collectors.joining("; "));
        
        setVariableValue("catatan", catatan);
        
        setVariableValue("listDetailCatatanAbsensiHalaqoh", listDetailCatatanAbsensiHalaqoh);
    }

}
