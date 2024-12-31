/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.entity.LaporanKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import java.util.List;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class RaporKepengasuhanScriptlet extends JRDefaultScriptlet {
    
    private RaporKepengasuhanScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current().select(NamedLiteral.of("raporKepengasuhanScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (RaporKepengasuhanScriptletHelper) beanInstance.get();
    }
    

    @Override
    public void beforeGroupInit(String groupName) throws JRScriptletException { 
        
        System.out.println("SELEK BIPOR GROUP: RaporKepengasuhan.jrxml");      

        Long santriId = (Long) getFieldValue("id");
        Santri santri = findHelper().getSantriFacade().find(santriId);
        PeriodePembelajaran periode = (PeriodePembelajaran) getParameterValue("periodePembelajaran");
        
        List<LaporanKepengasuhan> listLaporanKepengasuhan = findHelper().getAktifitasFacade().getAllRangkumanPeriode(santri, periode);
        
        setVariableValue("listLaporanKepengasuhan", listLaporanKepengasuhan);
    }
}
