/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.HalaqohPengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "halaqohPengajaranEditorPage")
@ConversationScoped
public class HalaqohPengajaranEditorPage extends FormPage<HalaqohPengajaran> {
    
    @Inject
    private HalaqohPengajaranFacade dao;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected HalaqohPengajaran newEntity() {
        return new HalaqohPengajaran();
    }

    @Override
    protected Result<String> save(HalaqohPengajaran entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(HalaqohPengajaran entity) {
        return dao.edit(entity);
    }
    
}
