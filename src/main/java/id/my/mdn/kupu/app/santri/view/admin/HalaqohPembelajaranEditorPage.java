/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.HalaqohPengajaranSqlFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.view.widget.JenisKitabList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.view.form.OrganizationEditorForm;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "halaqohPembelajaranEditorPage")
@ConversationScoped
public class HalaqohPembelajaranEditorPage extends FormPage<HalaqohPengajaran> {
    
    @Inject
    private HalaqohPengajaranSqlFacade dao;
    
    @Inject @Form
    private OrganizationEditorForm organizationForm;
    
    @Inject
    private JenisKitabList jenisKitabList;

    @Override
    public void load() {
        super.load();
        organizationForm.init(getEntity().getOrganization());
        jenisKitabList.getFilter().setStaticFilter(this::getJenisKitabFilter);
    }
    
    private List<FilterData> getJenisKitabFilter() {
        List<FilterData> filters = new ArrayList<>();
        filters.add(FilterData.by("parent", null));
        filters.add(FilterData.by("kategori", entity.getKategoriKitab()));
        return filters;
    }

    @Override
    protected HalaqohPengajaran newEntity() {
        
        Organization org = Organization.builder().get();
        
        return HalaqohPengajaran.builder()
                .withOrganization(org)
                .get();
    }

    @Override
    protected void beforeSave() {
        entity.setNama("Halaqoh " + entity.getKategoriKitab().getName());
    }

    @Override
    protected Result<String> save(HalaqohPengajaran entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(HalaqohPengajaran entity) {
        return dao.edit(entity);
    }

    public OrganizationEditorForm getOrganizationForm() {
        return organizationForm;
    }

    public JenisKitabList getJenisKitabList() {
        return jenisKitabList;
    }
    
}
