/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import java.time.LocalDate;
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
public class AktifitasSantriScriptlet extends JRDefaultScriptlet {

    private static final Logger Log = Logger.getLogger(AktifitasSantriScriptlet.class.getCanonicalName());

    private AktifitasSantriScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current().select(NamedLiteral.of("aktifitasSantriScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (AktifitasSantriScriptletHelper) beanInstance.get();
    }

    @Override
    public void beforeDetailEval() throws JRScriptletException {
        AktifitasSantriScriptletHelper helper = findHelper();

        Long santriId = (Long) getFieldValue("id");
        LocalDate fromDate = (LocalDate) getParameterValue("fromDate");
        LocalDate thruDate = (LocalDate) getParameterValue("thruDate");
        
        setVariableValue("kesimpulanPokok", helper.getAktifitasFacade().calculateKesimpulanBDAS(santriId, fromDate, thruDate).getName());
        setVariableValue("kesimpulanTambahan", helper.getAktifitasFacade().calculateKesimpulanNonBDAS(santriId, fromDate, thruDate).getName());

    }
}
