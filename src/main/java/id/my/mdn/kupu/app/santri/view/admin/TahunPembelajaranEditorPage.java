/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.TahunPembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
@Named(value = "tahunPembelajaranEditorPage")
@ConversationScoped
public class TahunPembelajaranEditorPage extends FormPage<TahunPembelajaran> {

    @Inject
    private TahunPembelajaranFacade dao;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected TahunPembelajaran newEntity() {

        TahunPembelajaran tahunPembelajaran = new TahunPembelajaran();

        return tahunPembelajaran;
    }

    @Override
    protected Result<String> save(TahunPembelajaran entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(TahunPembelajaran entity) {
        return dao.edit(entity);
    }

}
