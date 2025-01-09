/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.dao.HalaqohSantriFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.admin.HalaqohPengajaranEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.HalaqohPengajaranList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "halaqohPengajaranPage")
@ViewScoped
public class HalaqohPengajaranPage extends Page implements Serializable {

    private static final int CHOOSE_SANTRI = 1;

    @Inject @Bookmarked
    private HalaqohPengajaranList dataView;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
        dataView.setSelectionMode(() -> Selector.SINGLE);
    }
    
    @Creator(of = "dataView")
    public void creator() {
        gotoChild(HalaqohPengajaranEditorPage.class)
                .open();
    }
    
    @Editor(of = "dataView")
    public void editor() {
        gotoChild(HalaqohPengajaranEditorPage.class)
                .addParam("entity")
                .withValues(dataView.getSelection())
                .open();
    }
    
    @Deleter(of = "dataView")
    public void deleter() {
        dataView.delete(dataView.getSelections());
    }
    
    public void addSantri(HalaqohPengajaran halaqoh) {
        dataView.setSelection(halaqoh);
        gotoChild(SantriSingleChooserPage.class)
                .addParam("what")
                .withValues(CHOOSE_SANTRI)
                .open();
    }
    
    @Inject
    private HalaqohSantriFacade halaqohSantriFacade;

    @Override
    public Page onReturns(int what, Object returns) {
        if (returns != null) {
            switch (what) {
                case CHOOSE_SANTRI: 
                    Santri santri = (Santri) returns;
                    dataView.getSelected().getListSantri().add(santri);
                    dataView.edit(dataView.getSelected());
                    break;
                default:
                    break;
            }
        }
        return super.onReturns(what, returns);
    }

    public HalaqohPengajaranList getDataView() {
        return dataView;
    }
    
}
