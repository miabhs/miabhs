/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.PembantuPelaksanaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.PembantuPelaksanaKepengasuhan;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "pembantuPelaksanaKepengasuhanList")
@Dependent
public class PembantuPelaksanaKepengasuhanList extends AbstractMutablePagedValueList<PembantuPelaksanaKepengasuhan> {
    
    @Inject
    private PembantuPelaksanaKepengasuhanFacade dao;

    @Override
    protected List<PembantuPelaksanaKepengasuhan> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<PembantuPelaksanaKepengasuhan> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(PembantuPelaksanaKepengasuhan entity) {
        dao.create(entity);
    }

    @Override
    public void edit(PembantuPelaksanaKepengasuhan entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(PembantuPelaksanaKepengasuhan entity) {
        dao.remove(entity);
    } 

    @Override
    public String[] getCreatePermission() {
        return new String[]{};
    }

    @Override
    public String[] getUpdatePermission() {
        return new String[]{};
    }

    @Override
    public String[] getDeletePermission() {
        return new String[]{};
    }   
    
}
