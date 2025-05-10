/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.KelompokHalaqohPengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokHalaqohPengajaran;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "kelompokHalaqohPembelajaranEditorPage")
@ConversationScoped
public class KelompokHalaqohPembelajaranEditorPage extends FormPage<KelompokHalaqohPengajaran> {
    
    @Inject
    private KelompokHalaqohPengajaranFacade dao;

    @Override
    protected KelompokHalaqohPengajaran newEntity() {
        return new KelompokHalaqohPengajaran();
    }

    @Override
    protected Result<String> save(KelompokHalaqohPengajaran entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(KelompokHalaqohPengajaran entity) {
        return dao.edit(entity);
    }

    
    
}
