/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.UstadzFacade;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Named(value = "ustadzSelectList")
@Dependent
public class UstadzSelectList extends AbstractValueList<Ustadz> {
    
    @Inject
    private UstadzFacade dao;

    @Override
    protected List<Ustadz> getFetchedItemsInternal(Map<String, Object> parameters, List<FilterTypes.FilterData> filters, List<SorterData> sorters, DefaultList<Ustadz> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(parameters, filters, sorters);
    }
        
}
