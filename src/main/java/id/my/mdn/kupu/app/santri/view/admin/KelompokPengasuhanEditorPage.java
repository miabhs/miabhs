/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.view.form.OrganizationEditorForm;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "kelompokPengasuhanEditorPage")
@ConversationScoped
public class KelompokPengasuhanEditorPage extends FormPage<KelompokPengasuhan> {

    @Inject
    private KelompokPengasuhanFacade dao;
    
    @Inject @Form
    private OrganizationEditorForm form;

    @Override
    public void load() {
        super.load();
        form.init(getEntity().getOrganization());
    }

    @Override
    protected KelompokPengasuhan newEntity() {
        Organization org = Organization.builder().get();
        
        return KelompokPengasuhan.builder()
                .withOrganization(org)
                .get();
    }

    @Override
    protected Result<String> save(KelompokPengasuhan entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(KelompokPengasuhan entity) {
        return dao.edit(entity);
    }

    public OrganizationEditorForm getForm() {
        return form;
    }
    
}
