/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.admin;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
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
@Named(value = "bentukAktifitasEditorPage")
@ConversationScoped
public class BentukAktifitasEditorPage extends FormPage<BentukAktifitas> {

    @Inject
    private BentukAktifitasFacade dao;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected BentukAktifitas newEntity() {

        BentukAktifitas bentukAktifitas = new BentukAktifitas();

        return bentukAktifitas;
    }

    @Override
    protected Result<String> save(BentukAktifitas entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(BentukAktifitas entity) {
        return dao.edit(entity);
    }

}
