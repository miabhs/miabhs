/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.HalaqohSantriFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohSantri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "halaqohSantriList")
@Dependent
public class HalaqohSantriList extends AbstractValueList<HalaqohSantri> {

    @Inject
    private HalaqohSantriFacade dao;

    @Override
    protected List<HalaqohSantri> getFetchedItemsInternal(
            Map<String, Object> parameters, List<FilterData> filters, 
            List<SorterData> sorters, DefaultList<HalaqohSantri> defaultList, 
            DefaultChecker defaultChecker) {
        return dao.findAll(parameters, filters, sorters, defaultList.get());
    }

}
