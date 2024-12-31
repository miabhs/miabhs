/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.SelectItem;
import jakarta.faces.model.SelectItemGroup;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "santriList2")
@RequestScoped
public class SantriList2 {

    @Inject
    private KelompokPengasuhanFacade kelompokPengasuhanFacade;

    @Inject
    private SantriFacade santriFacade;    
    
    private List<SelectItem> santriGroup;

    public List<SelectItem> getSantriGroup() {
        return santriGroup;
    }

    @PostConstruct
    public void init() {
        santriGroup = new ArrayList<>();

        final List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("status", StatusKesantrian.ACTIVE));

        for (KelompokPengasuhan kelompokPengasuhan : kelompokPengasuhanFacade.findAll()) {
            FilterData filterKelompokPengasuhan = new FilterData("kelompokPengasuhan", kelompokPengasuhan);
            filters.add(filterKelompokPengasuhan);

            List<SelectItem> selectItems = new ArrayList<>();

            for (Santri santri : santriFacade.findAll(0, 0, filters)) {
                selectItems.add(new SelectItem(santri, santri.getPerson().getName()));
            }

            if (!selectItems.isEmpty()) {
                SelectItemGroup santriKelompokPengasuhan = new SelectItemGroup("-- " + kelompokPengasuhan.getOrganization().getName() + " --");
                
                santriKelompokPengasuhan.setSelectItems(selectItems.toArray(new SelectItem[selectItems.size()]));

                santriGroup.add(santriKelompokPengasuhan);
            }
            filters.remove(filterKelompokPengasuhan);
        }
    }

}
