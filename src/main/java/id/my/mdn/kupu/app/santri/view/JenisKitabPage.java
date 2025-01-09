/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.view.admin.JenisKitabEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.JenisKitabList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "jenisKitabPage")
@ViewScoped
public class JenisKitabPage extends Page implements Serializable {

    @Inject @Bookmarked
    private JenisKitabList dataView;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }
    
    @Creator(of = "dataView")
    public void creator() {
        gotoChild(JenisKitabEditorPage.class)
                .open();
    }
    
    @Editor(of = "dataView")
    public void editor() {
        gotoChild(JenisKitabEditorPage.class)
                .addParam("entity")
                .withValues(dataView.getSelected())
                .open();
    }
    
    @Deleter(of = "dataView")
    public void deleter() {
        dataView.delete(dataView.getSelections());
    }

    public JenisKitabList getDataView() {
        return dataView;
    }
    
}
