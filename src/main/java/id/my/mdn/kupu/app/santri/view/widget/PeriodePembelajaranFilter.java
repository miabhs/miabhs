/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.TahunPembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent
public class PeriodePembelajaranFilter extends FilterContent implements Serializable {

    @Bookmark(name = "thp")
    private TahunPembelajaran tahunPembelajaran;
    
    @Inject
    private TahunPembelajaranFacade tahunFacade;
    
    @PostConstruct
    private void init() {
       tahunPembelajaran = tahunFacade.findSingleByAttributes(List.of(FilterData.by("date", LocalDate.now())));
    }

    public TahunPembelajaran getTahunPembelajaran() { 
        System.err.printf("SELEKTRI GET PILTER THP: %s", tahunPembelajaran);
        return tahunPembelajaran;
    }

    public void setTahunPembelajaran(TahunPembelajaran tahunPembelajaran) {
        System.err.printf("SELEKTRI SET PILTER THP: %s", tahunPembelajaran);
        this.tahunPembelajaran = tahunPembelajaran;
    }
}
