/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.app.pengasuhan.view.admin.HikmahKauniyahEditorPage;
import id.my.mdn.kupu.app.pengasuhan.view.widget.HikmahKauniyahList;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.view.widget.SantriFilter;
import id.my.mdn.kupu.app.santri.view.widget.SantriList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
@Named(value = "hikmahKauniyahPage")
@ViewScoped
public class HikmahKauniyahPage extends Page implements Serializable {

    @Inject
    private SantriList santriList;
    
    @Inject
    @Bookmarked
    private HikmahKauniyahList dataView;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        santriList.getFilter()
                .<SantriFilter>getContent()
                .setStatusKesantrian(StatusKesantrian.ACTIVE);
        santriList.getFilter().setFiltering(true);
    }

    @Creator(of = "dataView")
    public void addCatatanAktifitas() {
        HikmahKauniyah hikmahKauniyah = new HikmahKauniyah();
        dataView.create(hikmahKauniyah);
    }
    
    public void onEditContent(HikmahKauniyah hikmahKauniyah) {
        gotoChild(HikmahKauniyahEditorPage.class)
                .addParam("entity")
                .withValues(hikmahKauniyah)
                .open();
    }

    @Deleter(of = "dataView")
    public void delCatatanAktifitas() {
        dataView.deleteSelections();
    }

    public HikmahKauniyahList getDataView() {
        return dataView;
    }

    private List<String> listUpdate;

    private int activeIndex;
    
    private Santri choosenSantri;

    public void saveEditSantri(AjaxBehaviorEvent evt) {
        HikmahKauniyah entity = dataView.getFetchedItems().get(activeIndex);
        dataView.getFetchedItems().get(activeIndex).setSantri(choosenSantri);
        dataView.edit(entity);
        
        PrimeFaces.current().ajax().update(listUpdate);
    }

    public void initChooserContext(ActionEvent evt) {
        String idx = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idx");
        activeIndex = Integer.parseInt(idx);
        
        String updates = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("updates");
        listUpdate = List.of(updates.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
                
        String widget = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("widget");
        String trigger = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trigger");
        PrimeFaces.current().executeScript("PF('" + widget + "').show('" + trigger +"')");
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public Santri getChoosenSantri() {
        return choosenSantri;
    }

    public void setChoosenSantri(Santri choosenSantri) {
        this.choosenSantri = choosenSantri;
    }

    public SantriList getSantriList() {
        return santriList;
    }

}
