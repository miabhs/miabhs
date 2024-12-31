/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.JenisAktifitas;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
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
@Named(value = "bentukAktifitasList1")
@RequestScoped
public class BentukAktifitasList1 {

    @Inject
    private BentukAktifitasFacade dao;
    
    private List<SelectItem> fetchedItems;

    @PostConstruct
    public void init() {
        fetchedItems = new ArrayList<>();
        
        List<FilterData> filters = new ArrayList<>();
        
        for(JenisAktifitas jenis : JenisAktifitas.values()) {
            FilterData filterJenis = new FilterData("jenis", jenis);
            filters.add(filterJenis);
            
            List<SelectItem> items = new ArrayList<>();
            
            for(BentukAktifitas bentukAktifitas : dao.findAll(filters, List.of(new SorterData("kode"), new SorterData("created")))) {
                items.add(new SelectItem(bentukAktifitas, bentukAktifitas.getLabel()));
            }
            
            if(!items.isEmpty()) {
                SelectItemGroup group = new SelectItemGroup("-- " + jenis.getLabel() + " --");
                
                group.setSelectItems(items.toArray(new SelectItem[items.size()]));
                
                fetchedItems.add(group);
            }
            filters.remove(filterJenis);
        }
    }

    public List<SelectItem> getFetchedItems() {
        return fetchedItems;
    }

}
