/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

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

    @Inject @Bookmarked
    private HalaqohPengajaranList dataView;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
        dataView.setSelectionMode(() -> Selector.MULTIPLE);
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

    public HalaqohPengajaranList getDataView() {
        return dataView;
    }
    
}