/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.view.admin.KelompokHalaqohPembelajaranEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.KelompokHalaqohPengajaranList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import jakarta.annotation.PostConstruct;
import org.omnifaces.cdi.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Named(value = "kelompokHalaqohPembelajaranPage")
@ViewScoped
public class KelompokHalaqohPembelajaranPage extends Page implements Serializable {

    @Inject @Bookmarked
    private KelompokHalaqohPengajaranList dataView;

    @PostConstruct @Override
    protected void init() {
        super.init();
    }
    
    @Creator(of = "dataView")
    public void openCreator() {
        gotoChild(KelompokHalaqohPembelajaranEditorPage.class).open();
    }
    
    @Editor(of = "dataView")
    public void openEditor() {
        gotoChild(KelompokHalaqohPembelajaranEditorPage.class)
                .addParam("entity")
                .withValues(dataView.getSelected())
                .open();
    }
    
    @Deleter(of = "dataView")
    public void onDelete() {
        dataView.deleteSelected();
    }
 
    public KelompokHalaqohPengajaranList getDataView() {
        return dataView;
    }
    
}
