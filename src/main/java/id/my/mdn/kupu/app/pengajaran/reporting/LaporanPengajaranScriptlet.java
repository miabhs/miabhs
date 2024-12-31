/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.reporting;

import id.my.mdn.kupu.app.pengajaran.entity.CatatanPengajaran;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class LaporanPengajaranScriptlet extends JRDefaultScriptlet {

    private LaporanPengajaranScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current().select(NamedLiteral.of("laporanPengajaranScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (LaporanPengajaranScriptletHelper) beanInstance.get();
    }

    @Override
    public void beforeGroupInit(String groupName) throws JRScriptletException {

        LaporanPengajaranScriptletHelper helper = findHelper();

        Long santriId = (Long) getFieldValue("id");
        Santri santri = helper.getSantriFacade().find(santriId);

        TahunPembelajaran tahunPembelajaran = (TahunPembelajaran) getParameterValue("tahunPembelajaran");

        setVariableValue("tahunBelajar", santri.calculateTahunBelajar(tahunPembelajaran));

        PeriodePembelajaran periodePembelajaran = (PeriodePembelajaran) getParameterValue("periodePembelajaran");

        setVariableValue("listPencapaianBelajar",
                helper.getLaporanPengajaranFacade().getPencapaianBelajar(santri, periodePembelajaran)
        );

        CatatanPengajaran catatanLaporan = findHelper().getLaporanPengajaranFacade().getCatatanPengajaran(santri, periodePembelajaran);
        
        if (catatanLaporan != null) {
            setVariableValue("catatanLaporan", catatanLaporan);
        } else {
            setVariableValue("catatanLaporan", null);
        }
        
    }

}
