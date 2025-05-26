/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.widget.KelompokPengasuhanSelectList;
import id.my.mdn.kupu.app.santri.view.widget.SantriLazyChooser;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import id.my.mdn.kupu.core.party.entity.GenderType;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "pengasuhanSantriFilter")
@Dependent
public class PengasuhanSantriFilter extends FilterContent implements Serializable {

    @Bookmark(name = "gd")
    private GenderType gender;
    
    @Bookmark(name = "kp")
    private KelompokPengasuhan kelompokPengasuhan;
    
    @Bookmark(name = "sn")
    private Santri santri; 

    @Inject
    private KelompokPengasuhanSelectList kelompokPengasuhanChooser;

    @Inject
    private SantriLazyChooser santriChooser;

    @PostConstruct
    public void init() {

        kelompokPengasuhanChooser.setFilters(() -> {
            if (gender == null) {
                return List.of();
            }
            return List.of(FilterData.by("gender", gender));
        });
        
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
        List<FilterData> filterSantri = new ArrayList<>();

        if (kelompokPengasuhan != null) {
            filterSantri.add(FilterData.by("kelompokPengasuhan", kelompokPengasuhan));
        }

        if (gender != null) {
            filterSantri.add(FilterData.by("gender", gender));
        }

        return filterSantri;
    }

    public void updateGenderFilter(AjaxBehaviorEvent evt) {
        kelompokPengasuhan = null;
        santri = null;
    }

    public void updateKelompokFilter(AjaxBehaviorEvent evt) {
        santri = null;
    }
    
    public void onChangeKelompokPengasuhan(AjaxBehaviorEvent evt) {
        santri = null;
    }

    public KelompokPengasuhanSelectList getKelompokPengasuhanChooser() {
        return kelompokPengasuhanChooser;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
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
