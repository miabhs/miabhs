/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractFilterableValueList;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named("kelompokPengasuhanSelectList")
@Dependent
public class KelompokPengasuhanSelectList extends AbstractFilterableValueList<KelompokPengasuhan> {
    
    @Inject
    private KelompokPengasuhanFacade dao;

    protected List<KelompokPengasuhan> getFetchedItemsInternal(
            Map<String, Object> parameters,
            List<FilterData> filters,
            List<SorterData> sorters) {
        return dao.findAll(parameters, filters, sorters);        
    }  
    
    
}
