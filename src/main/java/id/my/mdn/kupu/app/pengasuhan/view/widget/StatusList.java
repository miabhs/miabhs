/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.entity.Status;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "statusList")
@RequestScoped
public class StatusList extends AbstractValueList<Status> {

    public StatusList() {
        super(Status.class);
    }

    @Override
    protected List<Status> getFetchedItemsInternal(Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<Status> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return Arrays.asList(Status.values());
    }
    
}
