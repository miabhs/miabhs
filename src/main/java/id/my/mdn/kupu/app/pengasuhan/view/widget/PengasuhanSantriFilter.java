/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.widget.SantriLazyChooser;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Dependent
public class PengasuhanSantriFilter extends FilterContent implements Serializable {
    
    @Bookmark(name = "kp")
    private KelompokPengasuhan kelompokPengasuhan;
    
    @Bookmark(name = "sn")
    private Santri santri; 

    @Inject
    private SantriLazyChooser santriChooser;

    @PostConstruct
    public void init() {
        santriChooser.setListener(this::onSelectSantri);
        santriChooser.getList().getFilter().setStaticFilter(                
               santriChooser.getList()
                       .getFilter().staticFilter.plus(this::santriFilter)
        );
    }

    public void onSelectSantri(Santri santri) {
        this.santri = santri;
    }

    private List<FilterData> santriFilter() {
        if(kelompokPengasuhan == null) return null;
        return List.of(FilterData.by("kelompokPengasuhan", kelompokPengasuhan));
    }
    
    public void onChangeKelompokPengasuhan(AjaxBehaviorEvent evt) {
        santri = null;
    }

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

    public SantriLazyChooser getSantriChooser() {
        return santriChooser;
    }
    
}
