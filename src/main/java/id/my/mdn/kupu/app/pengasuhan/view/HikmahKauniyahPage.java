/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.app.pengasuhan.view.admin.HikmahKauniyahEditorPage;
import id.my.mdn.kupu.app.pengasuhan.view.widget.HikmahKauniyahList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
@Named(value = "hikmahKauniyahPage")
@ViewScoped
public class HikmahKauniyahPage extends Page implements Serializable {
    
    @Inject
    @Bookmarked
    private HikmahKauniyahList dataView;

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Creator(of = "dataView")
    public void addCatatanAktifitas() {
        dataView.addBlank();
    }
    
    public void onEditContent(HikmahKauniyah hikmahKauniyah) {
        gotoChild(HikmahKauniyahEditorPage.class)
                .addParam("entity")
                .withValues(hikmahKauniyah)
                .open();
    }

    @Deleter(of = "dataView")
    public void delCatatanAktifitas() {
        dataView.deleteSelected();
    }

    public HikmahKauniyahList getDataView() {
        return dataView;
    }

}
