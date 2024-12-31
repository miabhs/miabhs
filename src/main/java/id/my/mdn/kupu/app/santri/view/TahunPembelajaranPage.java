/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.app.santri.view.admin.PeriodePembelajaranEditorPage;
import id.my.mdn.kupu.app.santri.view.admin.TahunPembelajaranEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranFilter;
import id.my.mdn.kupu.app.santri.view.widget.PeriodePembelajaranTree;
import id.my.mdn.kupu.app.santri.view.widget.TahunPembelajaranList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import static id.my.mdn.kupu.core.base.view.widget.Selector.CHECKBOX;
import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "tahunPembelajaranPage")
@ViewScoped
public class TahunPembelajaranPage extends Page implements Serializable {

    @Inject
    @Bookmarked
    private TahunPembelajaranList tahunPembelajaranList;

    @Inject
    @Bookmarked
    private PeriodePembelajaranTree periodePembelajaranTree;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        tahunPembelajaranListInit();
        periodePembelajaranTreeInit();
    }

    private void tahunPembelajaranListInit() {
        tahunPembelajaranList.setSelectionMode(() -> SINGLE);
        tahunPembelajaranList.setName("tahunPembelajaranTbl");
        tahunPembelajaranList.getSelector().setSelectionsLabel("thp");
        tahunPembelajaranList.getPager().setPageSizeLabel("tp");
        tahunPembelajaranList.getPager().setOffsetLabel("to");
        
        tahunPembelajaranList.getSelector().addListenerInternal(s -> onSelectTahunPembelajaran((TahunPembelajaran) s));

        tahunPembelajaranList.getSelector().addListener((s) -> onSelectTahunPembelajaran((TahunPembelajaran) s));
    }

    private void periodePembelajaranTreeInit() {
        periodePembelajaranTree.setSelectionMode(() -> CHECKBOX);
        periodePembelajaranTree.setName("periodePembelajaranTbl");
        periodePembelajaranTree.setSelectionsLabel("ps");
    }

    private void onSelectTahunPembelajaran(TahunPembelajaran tahunPembelajaran) {
            periodePembelajaranTree.reset();
            periodePembelajaranTree.getFilter()
                    .<PeriodePembelajaranFilter>getContent()
                    .setTahunPembelajaran(tahunPembelajaran);
    }

    @Creator(of = "tahunPembelajaranList")
    public void openTahunPembelajaranCreator() {
        gotoChild(TahunPembelajaranEditorPage.class).open();
    }

    @Editor(of = "tahunPembelajaranList")
    public void openTahunPembelajaranEditor() {
        gotoChild(TahunPembelajaranEditorPage.class)
                .addParam("entity")
                .withValues(tahunPembelajaranList.getSelector().getSelection())
                .open();
    }

    @Deleter(of = "tahunPembelajaranList")
    public void openTahunPembelajaranDeleter() {
        tahunPembelajaranList.deleteSelected();
    }

    @Creator(of = "periodePembelajaranTree")
    public void openPeriodePembelajaranCreator() {
        gotoChild(PeriodePembelajaranEditorPage.class)
                .addParam("tahunPembelajaran")
                .withValues(tahunPembelajaranList.getSelector().getSelection())
                .addParam("parent")
                .withValues(periodePembelajaranTree.getSelector().getSelection())
                .open();
    }

    @Editor(of = "periodePembelajaranTree")
    public void openPeriodePembelajaranEditor() {
        gotoChild(PeriodePembelajaranEditorPage.class)
                .addParam("entity")
                .withValues(periodePembelajaranTree.getSelector().getSelection())
                .open();
    }

    @Deleter(of = "periodePembelajaranTree")
    public void openPeriodePembelajaranDeleter() {
        periodePembelajaranTree.delete(
                periodePembelajaranTree.getSelector().getSelection());
    }

    public TahunPembelajaranList getTahunPembelajaranList() {
        return tahunPembelajaranList;
    }

    public PeriodePembelajaranTree getPeriodePembelajaranTree() {
        return periodePembelajaranTree;
    }
}
