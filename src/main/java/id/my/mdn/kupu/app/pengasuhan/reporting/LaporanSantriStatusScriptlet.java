/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.entity.JenisAktifitas;
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
public class LaporanSantriStatusScriptlet extends JRDefaultScriptlet {

    private static final Logger Log = Logger.getLogger(LaporanSantriStatusScriptlet.class.getCanonicalName());

    private AktifitasSantriScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current().select(NamedLiteral.of("aktifitasSantriScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (AktifitasSantriScriptletHelper) beanInstance.get();
    }

    @Override
    public void beforeDetailEval() throws JRScriptletException {

        Long santriId = (Long) getParameterValue("santriId");
        LocalDate fromDate = (LocalDate) getParameterValue("fromDate");
        LocalDate thruDate = (LocalDate) getParameterValue("thruDate");
        
        AktifitasSantriScriptletHelper helper = findHelper();

        setVariableValue("listCatatanBDAS", helper.getAktifitasFacade().getAktifitas(santriId, fromDate, thruDate, JenisAktifitas.BDAS_POKOK, JenisAktifitas.BDAS));

        setVariableValue("listCatatanNonBDAS", helper.getAktifitasFacade().getAktifitas(santriId, fromDate, thruDate, JenisAktifitas.NON_BDAS));


    }
}
 