/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.view.admin.BentukAktifitasTambahanEditorPage;
import id.my.mdn.kupu.app.pengasuhan.view.widget.BentukAktifitasTambahanList;
import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.Confirmation;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.base.view.annotation.OnInit;
import id.my.mdn.kupu.core.base.view.annotation.OnReload;
import java.io.Serializable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
@Named(value = "bentukAktifitasTambahanPage")
@ViewScoped
public class BentukAktifitasTambahanPage extends ChildPage implements Serializable {

    private static final int DELETE = 1;

    @Inject
    @Bookmarked
    private BentukAktifitasTambahanList bentukAktifitasTambahanList;

    @OnInit
    public void init() {
//        bentukAktifitasTambahanList.init();
    }

    @OnReload
    public void reload() {
//        bentukAktifitasTambahanList.reload();
    }

    @Creator(of = "bentukAktifitasTambahanList")
    public void openBentukAktifitasTambahanCreator() {
        gotoChild(BentukAktifitasTambahanEditorPage.class).open();
    }

    @Editor(of = "bentukAktifitasTambahanList")
    public void openBentukAktifitasTambahanEditor() {
        gotoChild(BentukAktifitasTambahanEditorPage.class)
                .addParam("entity")
                .withValues(bentukAktifitasTambahanList.getSelector().getSelection())
                .open();
    }

    @Deleter(of = "bentukAktifitasTambahanList")
    public void openBentukAktifitasTambahanDeleter() {
        Confirmation.from(this).on(DELETE)
                .withMessage("Are you sure to delete \"{0}\" ?")
                .andMessageParams(bentukAktifitasTambahanList.getSelector().getSelection().getBentuk())
                .open();
    }

    @Override
    public Page onReturns(int what, Object returns) {
        switch (what) {
            case DELETE:
                if (((Boolean) returns) == true) {
                    bentukAktifitasTambahanList.delete(bentukAktifitasTambahanList.getSelector().getSelection());
                }
                break;
        }
        return super.onReturns(what, returns);
    }

    public BentukAktifitasTambahanList getBentukAktifitasTambahanList() {
        return bentukAktifitasTambahanList;
    }

}
