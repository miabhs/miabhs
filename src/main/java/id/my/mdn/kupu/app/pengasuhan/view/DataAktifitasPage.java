/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.app.pengasuhan.view.widget.AltAktifitasList;
import id.my.mdn.kupu.app.pengasuhan.view.widget.BentukAktifitasList;
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
import jakarta.faces.event.ValueChangeEvent;
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
@Named(value = "dataAktifitasPage")
@ViewScoped
public class DataAktifitasPage extends Page implements Serializable {

    @Inject
    private SantriList santriList;

    @Inject
    private BentukAktifitasList bentukAktifitasList;

    @Inject
    @Bookmarked
    private AltAktifitasList aktifitasList;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        santriList.getFilter()
                .<SantriFilter>getContent()
                .setStatusKesantrian(StatusKesantrian.ACTIVE);
        santriList.getFilter().setFiltering(true);
    }

    @Creator(of = "aktifitasList")
    public void addCatatanAktifitas() {
        Aktifitas aktifitas = new Aktifitas();
        aktifitas.setId(UUID.randomUUID().toString());
        aktifitas.setActivityDate(LocalDate.now());
        aktifitasList.create(aktifitas);
    }

    @Deleter(of = "aktifitasList")
    public void delCatatanAktifitas() {
        aktifitasList.delete(aktifitasList.getSelector().getSelections());
    }

    public void onEditKeterangan(ValueChangeEvent evt) {
        aktifitasList.getSelector().getSelection().setNotes((String) evt.getNewValue());
        aktifitasList.edit(aktifitasList.getSelector().getSelection());
    }

    public void confirm(Aktifitas aktifitas) {
        aktifitas.setConfirmed(!aktifitas.isConfirmed());
        aktifitasList.edit(aktifitas);
    }

    public AltAktifitasList getAktifitasList() {
        return aktifitasList;
    }

    private List<String> listUpdate;

    private int activeIndex;

    private Santri choosenSantri;

    private BentukAktifitas choosenBentukAktifitas;

    public void initChooserContext(ActionEvent evt) {
        String idx = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idx");
        activeIndex = Integer.parseInt(idx);

        String updates = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("updates");
        listUpdate = List.of(updates.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());

        String widget = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("widget");
        String trigger = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("trigger");
        PrimeFaces.current().executeScript("PF('" + widget + "').show('" + trigger + "')");
    }

    public void saveEditSantri(AjaxBehaviorEvent evt) {
        Aktifitas aktifitas = aktifitasList.getFetchedItems().get(activeIndex);
        aktifitas.setSantri(choosenSantri);
        aktifitasList.edit(aktifitas);
        PrimeFaces.current().ajax().update(listUpdate);
    }

    public void saveEditBentukAktifitas(AjaxBehaviorEvent evt) {
        Aktifitas aktifitas = aktifitasList.getFetchedItems().get(activeIndex);
        aktifitas.setBentukAktifitas(choosenBentukAktifitas);
        aktifitas.setConfirmed(true);
        aktifitasList.edit(aktifitas);
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

    public BentukAktifitas getChoosenBentukAktifitas() {
        return choosenBentukAktifitas;
    }

    public void setChoosenBentukAktifitas(BentukAktifitas choosenBentukAktifitas) {
        this.choosenBentukAktifitas = choosenBentukAktifitas;
    }

    public SantriList getSantriList() {
        return santriList;
    }

    public BentukAktifitasList getBentukAktifitasList() {
        return bentukAktifitasList;
    }
}
