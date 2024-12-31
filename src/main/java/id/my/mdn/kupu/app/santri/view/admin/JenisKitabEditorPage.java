/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.JenisKitabFacade;
import id.my.mdn.kupu.app.santri.entity.JenisKitab;
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
@Named(value = "jenisKitabEditorPage")
@ConversationScoped
public class JenisKitabEditorPage extends FormPage<JenisKitab> {
    
    @Inject
    private JenisKitabFacade dao;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected JenisKitab newEntity() {
        return new JenisKitab();
    }

    @Override
    protected Result<String> save(JenisKitab entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String>  edit(JenisKitab entity) {
        return dao.edit(entity);
    }

    
    
}
