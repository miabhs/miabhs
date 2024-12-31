/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.KategoriKitabFacade;
import id.my.mdn.kupu.app.santri.entity.KategoriKitab;
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
@Named(value = "kategoriKitabEditorPage")
@ConversationScoped
public class KategoriKitabEditorPage extends FormPage<KategoriKitab> {
    
    @Inject
    private KategoriKitabFacade dao;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected KategoriKitab newEntity() {
        return new KategoriKitab();
    }

    @Override
    protected Result<String> save(KategoriKitab entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(KategoriKitab entity) {
        return dao.edit(entity);
    }
    
}
