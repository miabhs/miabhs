/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.JenisPengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.JenisPengajaran;
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
@Named(value = "jenisPengajaranEditorPage")
@ConversationScoped
public class JenisPengajaranEditorPage extends FormPage<JenisPengajaran> {
    
    @Inject
    private JenisPengajaranFacade dao;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected JenisPengajaran newEntity() {
        return new JenisPengajaran();
    }

    @Override
    protected Result<String> save(JenisPengajaran entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(JenisPengajaran entity) {
        return dao.edit(entity);
    }
    
}
