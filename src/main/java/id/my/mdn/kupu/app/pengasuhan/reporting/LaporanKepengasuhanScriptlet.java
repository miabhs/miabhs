/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanKepengasuhanDetail;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class LaporanKepengasuhanScriptlet extends JRDefaultScriptlet {

    private LaporanKepengasuhanScriptletHelper findHelper() {
        Instance<Object> beanInstance = CDI.current().select(NamedLiteral.of("laporanKepengasuhanScriptletHelper"));

        if (beanInstance.isUnsatisfied() || !beanInstance.isResolvable() || beanInstance.isAmbiguous()) {
            return null;
        }

        return (LaporanKepengasuhanScriptletHelper) beanInstance.get();
    }

    @Override
    public void beforeDetailEval() throws JRScriptletException {
        
        Long santriId = (Long) getFieldValue("santriId");
        LocalDate fromDate = (LocalDate) getFieldValue("fromDate");
        LocalDate thruDate = (LocalDate) getFieldValue("thruDate");

        List<RangkumanKepengasuhanDetail> catatanKepengasuhan = findHelper().getRangkumanKepengasuhanFacade().getDetailKepengasuhan(
                santriId,
                LocalDate.parse(fromDate.toString()),
                LocalDate.parse(thruDate.toString()));
        
        List<RangkumanKepengasuhanDetail> bdas= new ArrayList<>();
        List<RangkumanKepengasuhanDetail> nonBdas= new ArrayList<>();
        
        for(RangkumanKepengasuhanDetail c : catatanKepengasuhan) {
            if(c.getJenis().equals("NON_BDAS")) {
                nonBdas.add(c);
            } else {
                bdas.add(c);
            }
        }
        
        setVariableValue("catatanBDAS", new JRBeanCollectionDataSource(bdas));
        setVariableValue("catatanNonBDAS", new JRBeanCollectionDataSource(nonBdas));
        
        LaporanKepengasuhanScriptletHelper helper = findHelper();
        
        List<HikmahKauniyah> listHikmah = helper.getHikmahKauniyahFacade().findAll(
                FilterData.by("santriId", santriId),
                FilterData.by("fromDate", fromDate),
                FilterData.by("thruDate", thruDate)
        );
        
        setVariableValue("listHikmah", listHikmah);
    }
}
