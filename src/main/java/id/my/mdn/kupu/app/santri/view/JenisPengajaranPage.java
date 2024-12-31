/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.view.admin.JenisPengajaranEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.JenisPengajaranList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "jenisPengajaranPage")
@ViewScoped
public class JenisPengajaranPage extends Page implements Serializable {
    
    @Inject @Bookmarked
    private JenisPengajaranList dataView;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }
    
    @Creator(of = "dataView")
    public void creator() {
        gotoChild(JenisPengajaranEditorPage.class)
                .open();
    }
    
    @Editor(of = "dataView")
    public void editor() {
        gotoChild(JenisPengajaranEditorPage.class)
                .addParam("entity")
                .withValues(dataView.getSelection())
                .open();
    }
    
    @Deleter(of = "dataView")
    public void deleter() {
        dataView.delete(dataView.getSelections());
    }

    public JenisPengajaranList getDataView() {
        return dataView;
    }
    
}
