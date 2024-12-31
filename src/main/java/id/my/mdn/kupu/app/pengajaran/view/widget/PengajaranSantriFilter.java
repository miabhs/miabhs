/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.widget;

import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import jakarta.enterprise.context.Dependent;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author aphasan
 */
@Dependent
public class PengajaranSantriFilter extends FilterContent implements Serializable {
    
    @Bookmark(name = "kp")
    private KelompokPengasuhan kelompokPengasuhan;

    @Bookmark(name = "fd")
    private LocalDate fromDate = LocalDate.now();
    
    @Bookmark(name = "td")
    private LocalDate thruDate = LocalDate.now();
    
    @Bookmark(name = "sn")
    private Santri santri;   
    
    @Bookmark(name = "kb")
    private JenisKitab jenisKitab; 

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        return kelompokPengasuhan;
    }

    public void setKelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        this.kelompokPengasuhan = kelompokPengasuhan;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public JenisKitab getJenisKitab() {
        return jenisKitab;
    }

    public void setJenisKitab(JenisKitab jenisKitab) {
        this.jenisKitab = jenisKitab;
    }
    
}
