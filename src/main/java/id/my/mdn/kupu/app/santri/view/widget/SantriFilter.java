/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import java.io.Serializable;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "santriFilter")
@Dependent
public class SantriFilter extends FilterContent implements Serializable {
    
    @Bookmark(name = "nm")
    private String name;  
    
    @Bookmark(name = "sn")
    private Santri santri;  
    
    @Bookmark(name = "sk")
    private StatusKesantrian statusKesantrian;
    
    @Bookmark(name = "kp")
    private KelompokPengasuhan kelompokPengasuhan;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
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
    
}
