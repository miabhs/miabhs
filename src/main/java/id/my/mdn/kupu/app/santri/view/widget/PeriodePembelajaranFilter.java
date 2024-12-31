/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import jakarta.enterprise.context.Dependent;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent
public class PeriodePembelajaranFilter extends FilterContent implements Serializable {

    @Bookmark(name = "thp", nullable = true)
    private TahunPembelajaran tahunPembelajaran;

    public TahunPembelajaran getTahunPembelajaran() { 
        return tahunPembelajaran;
    }

    public void setTahunPembelajaran(TahunPembelajaran tahunPembelajaran) {
        this.tahunPembelajaran = tahunPembelajaran;
        notifyContext(tahunPembelajaran);
    }
}
