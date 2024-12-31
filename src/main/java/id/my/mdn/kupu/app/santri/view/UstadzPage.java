/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.view.admin.UstadzDetailPage;
import id.my.mdn.kupu.app.santri.view.admin.UstadzEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.UstadzList;
import id.my.mdn.kupu.core.base.view.ChildPage;
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
@Named(value = "ustadzPage")
@ViewScoped
public class UstadzPage extends ChildPage implements Serializable {

    @Inject
    @Bookmarked
    private UstadzList dataView;
    
    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    @Creator(of = "dataView")
    public void openDataCreator() {
        gotoChild(UstadzEditorPage.class)
                .addParam("partyClass")
                .withValues("person")
                .open();
    }

    @Editor(of = "dataView")
    public void openDataEditor() {
        gotoChild(UstadzDetailPage.class)
                .addParam("ustadz")
                .withValues(dataView.getSelector().getSelection())
                .open();
    }

    @Deleter(of = "dataView")
    public void openDataDeleter() {
        dataView.deleteSelections();
    }

    public UstadzList getDataView() {
        return dataView;
    }
    
}
