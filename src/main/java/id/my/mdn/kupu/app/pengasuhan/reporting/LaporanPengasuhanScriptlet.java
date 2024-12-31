/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import java.time.LocalDate;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class LaporanPengasuhanScriptlet extends JRDefaultScriptlet {

    private LaporanPengasuhanScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current().select(NamedLiteral.of("laporanPengasuhanScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (LaporanPengasuhanScriptletHelper) beanInstance.get();
    }

    @Override
    public void beforeGroupInit(String groupName) throws JRScriptletException {
        
        Object parameterValue = getParameterValue("LaporanKepengasuhanSantri");
        System.out.println("SELEK BIPOR GRUP: LaporanKepengasuhan.jrxml => "
                + ((net.sf.jasperreports.engine.JasperReport)parameterValue).getName());
        
        Santri santri = (Santri) getFieldValue("santri");

        TahunPembelajaran tahunPembelajaran = (TahunPembelajaran) getParameterValue("tahunPembelajaran");

        setVariableValue("tahunBelajar", santri.calculateTahunBelajar(tahunPembelajaran));

        setVariableValue("santriId", santri.getId());

        PeriodePembelajaran periodePembelajaran = (PeriodePembelajaran) getParameterValue("periodePembelajaran");

        LaporanPengasuhanScriptletHelper helper = findHelper();
        
        LocalDate date = periodePembelajaran.getFromDate();
        KelompokPengasuhan kelompokPengasuhan = helper.getPengasuhanService().getKelompokPengasuhan(santri.getId(), date);

        if (kelompokPengasuhan != null) {
            Ustadz masul = helper.getPengasuhanService().getMasulKepengasuhan(kelompokPengasuhan, date);

            if (masul != null) {
                setVariableValue("masul", masul.getPerson().getName());
            }
        }
    }

}
