/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.pengajaran.entity.AtributPembelajaran;
import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.app.santri.view.admin.JenisKitabEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.JenisKitabTree;
import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "jenisKitabPage")
@ViewScoped
public class JenisKitabPage extends Page implements Serializable {

    @Inject
    @Bookmarked
    private JenisKitabTree dataView;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Creator(of = "dataView")
    public void creator() {
        RequestedView requestedView = gotoChild(JenisKitabEditorPage.class);

        JenisKitab selected = dataView.getSelected();

        if (selected != null && selected.isBerjilid()) {
            requestedView.addParam("parent")
                    .withValues(selected);
        }

        requestedView.open();
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
        dataView.deleteSelected();
    }

    public void addAtribut(ActionEvent evt) {
        dataView.addAtributToSelected();
    }

    public void removeAtribut(AtributPembelajaran atr) {
        JenisKitab selected = dataView.getSelected();
        selected.getListAtribut().remove(atr);
        dataView.edit(selected);
    }

    public void onEditAtribut(CellEditEvent evt) {
        dataView.edit(dataView.getSelected());
    }

    public JenisKitabTree getDataView() {
        return dataView;
    }

}
