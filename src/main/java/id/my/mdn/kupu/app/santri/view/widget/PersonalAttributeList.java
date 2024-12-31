/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.PersonalAttributeFacade;
import id.my.mdn.kupu.app.santri.entity.PersonalAttribute;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "personalAttributeList")
@RequestScoped
public class PersonalAttributeList extends AbstractValueList<PersonalAttribute> {

    @Inject
    private PersonalAttributeFacade dao;

    @Override
    protected List<PersonalAttribute> getFetchedItemsInternal(Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<PersonalAttribute> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll();
    }

}
