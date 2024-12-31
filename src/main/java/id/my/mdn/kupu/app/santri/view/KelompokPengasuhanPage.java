/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.view.admin.KelompokPengasuhanDetailPage;
import id.my.mdn.kupu.app.santri.view.admin.KelompokPengasuhanEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.KelompokPengasuhanList;
import id.my.mdn.kupu.core.base.view.ChildPage;
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
@Named(value = "kelompokPengasuhanPage")
@ViewScoped
public class KelompokPengasuhanPage extends ChildPage implements Serializable {

    @Inject
    @Bookmarked
    private KelompokPengasuhanList dataView;
    
    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    @Creator(of = "dataView")
    public void openDataCreator() {
        gotoChild(KelompokPengasuhanEditorPage.class)
                .open();
    }

    @Editor(of = "dataView")
    public void openDataEditor() {
        gotoChild(KelompokPengasuhanDetailPage.class)
                .addParam("kp")
                .withValues(dataView.getSelections().get(0))
                .open();
    }

    @Deleter(of = "dataView")
    public void openDataDeleter() {
        dataView.deleteSelections();;
    }

    public KelompokPengasuhanList getDataView() {
        return dataView;
    }

}
