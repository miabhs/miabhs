/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.view.admin.PeriodePembelajaranEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
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
@Named(value = "periodePembelajaranPage")
@ViewScoped
public class PeriodePembelajaranPage extends ChildPage implements Serializable {
    
    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;
    
    @OnInit
    public void init() {
//        periodePembelajaranTree.init();
    }
    
    @OnReload
    public void reload() {
//        periodePembelajaranTree.reload();
    }
    
    @Creator(of = "periodePembelajaranTree")
    public void createPeriodePembelajaran() {
        RequestedView childPageUrl = gotoChild(PeriodePembelajaranEditorPage.class);

        if (periodePembelajaranTree.getSelector().getSelection() != null) {
            childPageUrl.addParam("parent");
            childPageUrl.withValues(periodePembelajaranTree.getSelector().getSelection());
        }

        childPageUrl.open();
    }    

    public PeriodePembelajaranTree getPeriodePembelajaranTree() {
        return periodePembelajaranTree;
    }
    
}
