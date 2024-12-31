/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.entity.CatatanKepengasuhan;
import id.my.mdn.kupu.app.pengasuhan.entity.CatatanKepengasuhanId;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class RangkumanAktifitasScriptlet extends JRDefaultScriptlet {

    private RangkumanAktifitasScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current().select(NamedLiteral.of("rangkumanAktifitasScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (RangkumanAktifitasScriptletHelper) beanInstance.get();
    }

    @Override
    public void beforeDetailEval() throws JRScriptletException {
        
        System.out.println("SELEK BIPOR DETIL: LaporanKepengasuhanSantri.jrxml");

        Long santriId = (Long) getParameterValue("santriId");
        PeriodePembelajaran periode = (PeriodePembelajaran) getParameterValue("periodePembelajaran");
//        LocalDate fromDate = (LocalDate) getFieldValue("fromDate");
//        LocalDate thruDate = (LocalDate) getFieldValue("thruDate");

        RangkumanAktifitasScriptletHelper helper = findHelper();
        
        CatatanKepengasuhanId id = new CatatanKepengasuhanId(santriId, periode.getId());
        CatatanKepengasuhan catatanLaporan = helper.getCatatanLaporanFacade().find(id);
        
        setVariableValue("catatanPengasuh", catatanLaporan != null ? catatanLaporan.getCatatanPengasuh() : null);
        setVariableValue("catatanMasul", catatanLaporan != null ? catatanLaporan.getCatatanMasul() : null);

//        setVariableValue("kesimpulanBDAS", helper.getAktifitasFacade().calculateKesimpulanBDAS(santriId, fromDate, thruDate).getName());

//        setVariableValue("kesimpulanNonBDAS", helper.getAktifitasFacade().calculateKesimpulanNonBDAS(santriId, fromDate, thruDate).getName());

//        setVariableValue("listCatatanBDAS", helper.getAktifitasFacade().getAktifitas(santriId, fromDate, thruDate, JenisAktifitas.BDAS_POKOK, JenisAktifitas.BDAS));

//        setVariableValue("listCatatanNonBDAS", helper.getAktifitasFacade().getAktifitas(santriId, fromDate, thruDate, JenisAktifitas.NON_BDAS));

    }
}
