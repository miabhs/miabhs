/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view;

import id.my.mdn.kupu.app.pengajaran.entity.PengajaranSantri;
import id.my.mdn.kupu.app.pengajaran.view.widget.PengajaranSantriList;
import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.view.widget.JenisKitabList;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "pencapaianSantriPage")
@ViewScoped
public class PencapaianSantriPage extends Page implements Serializable {

    @Inject
    private SantriList santriList;
    
    @Inject
    private JenisKitabList kitabList;

    @Inject @Bookmarked
    private PengajaranSantriList dataView;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
        santriList.getFilter()
                .<SantriFilter>getContent()
                .setStatusKesantrian(StatusKesantrian.ACTIVE);
        santriList.getFilter().setFiltering(true);
    }
    
    @Creator(of = "dataView")
    public void create() {
        PengajaranSantri pengajaran = new PengajaranSantri();
        pengajaran.setId(UUID.randomUUID().toString());
        pengajaran.setPengajaranDate(LocalDate.now());
        dataView.create(pengajaran);
    }
    
    @Deleter(of = "dataView")
    public void delete() {
        dataView.delete(dataView.getSelector().getSelections());
    }

    private List<String> listUpdate;

    private int activeIndex;
    
    private Santri choosenSantri;
    
    private JenisKitab choosenKitab;

    public void initChooserContext(ActionEvent evt) {
        String idx = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idx");
        activeIndex = Integer.parseInt(idx);
        
        String updates = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("updates");
        listUpdate = List.of(updates.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
                
        String widget = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("widget");
        String trigger = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trigger");
        PrimeFaces.current().executeScript("PF('" + widget + "').show('" + trigger +"')");
    }

    public void saveEditSantri(AjaxBehaviorEvent evt) {
        PengajaranSantri pengajaranSantri = dataView.getFetchedItems().get(activeIndex);
        pengajaranSantri.setSantri(choosenSantri);
        dataView.edit(pengajaranSantri);
        PrimeFaces.current().ajax().update(listUpdate);
    }

    public void saveEditKitab(AjaxBehaviorEvent evt) {
        PengajaranSantri pengajaranSantri = dataView.getFetchedItems().get(activeIndex);
        pengajaranSantri.setJenisKitab(choosenKitab);        
        dataView.edit(pengajaranSantri);
        PrimeFaces.current().ajax().update(listUpdate);
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

    public JenisKitab getChoosenKitab() {
        return choosenKitab;
    }

    public void setChoosenKitab(JenisKitab choosenKitab) {
        this.choosenKitab = choosenKitab;
    }

    public PengajaranSantriList getDataView() {
        return dataView;
    }

    public SantriList getSantriList() {
        return santriList;
    }

    public JenisKitabList getKitabList() {
        return kitabList;
    }
    
}
