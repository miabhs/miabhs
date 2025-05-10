/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.entity.JenisSantri;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import id.my.mdn.kupu.core.party.entity.GenderType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Named(value = "santriFilter")
@Dependent
public class SantriFilter extends FilterContent implements Serializable {
    
    @Bookmark(name = "nm")
    private String name;  
    
    @Bookmark(name = "tm")
    private TahunPembelajaran tahunMasuk;
    
    @Bookmark(name = "kp")
    private KelompokPengasuhan kelompokPengasuhan;
    
    @Bookmark(name = "gd")
    private GenderType gender;
    
    @Bookmark(name = "sk")
    private StatusKesantrian statusKesantrian;
    
    @Bookmark(name = "js")
    private JenisSantri jenisSantri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TahunPembelajaran getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(TahunPembelajaran tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public StatusKesantrian getStatusKesantrian() {        
        return statusKesantrian;
    }

    public void setStatusKesantrian(StatusKesantrian statusKesantrian) {        
        this.statusKesantrian = statusKesantrian;
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        return kelompokPengasuhan;
    }

    public void setKelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        this.kelompokPengasuhan = kelompokPengasuhan;
    }

    public JenisSantri getJenisSantri() {
        return jenisSantri;
    }

    public void setJenisSantri(JenisSantri jenisSantri) {
        this.jenisSantri = jenisSantri;
    }
    
}
