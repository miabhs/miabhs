/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.admin;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasTambahanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitasTambahan;
import id.my.mdn.kupu.app.pengasuhan.entity.Status;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
@Named(value = "bentukAktifitasTambahanEditorPage")
@ConversationScoped
public class BentukAktifitasTambahanEditorPage extends FormPage<BentukAktifitasTambahan> {

    @Inject
    private BentukAktifitasTambahanFacade dao;

    @Override
    protected BentukAktifitasTambahan newEntity() {

        BentukAktifitasTambahan bentukAktifitasTambahan = new BentukAktifitasTambahan();
        bentukAktifitasTambahan.setStatus(Status.KUNING);
        return bentukAktifitasTambahan;
    }

    @Override
    protected Result<String> save(BentukAktifitasTambahan entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(BentukAktifitasTambahan entity) {
        return dao.edit(entity);
    }

}
