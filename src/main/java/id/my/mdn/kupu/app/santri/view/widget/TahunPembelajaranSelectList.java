/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.TahunPembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.base.view.widget.CommonFilter;
import static id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData.DESC;
import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "tahunPembelajaranSelectList")
@Dependent
public class TahunPembelajaranSelectList extends AbstractValueList<TahunPembelajaran> {
    
    @Inject
    private TahunPembelajaranFacade dao;
    
    @Inject
    private CommonFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
        setSelectionMode(() -> SINGLE);
    }

    @Override
    protected List<TahunPembelajaran> getFetchedItemsInternal(Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<TahunPembelajaran> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(parameters, filters, sorters);        
    }

    @Override
    public List<SorterData> getSorters() {
        return Arrays.asList(new SorterData("fromDate", DESC));
    }
    
}
