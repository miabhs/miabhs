/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.pengasuhan.view.widget.AltAktifitasList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "dataAktifitasPage")
@ViewScoped
public class DataAktifitasPage extends Page implements Serializable {

    @Inject
    @Bookmarked
    private AltAktifitasList aktifitasList;

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Creator(of = "aktifitasList")
    public void addCatatanAktifitas() {
        aktifitasList.addBlank();
    }

    @Deleter(of = "aktifitasList")
    public void delCatatanAktifitas() {
        aktifitasList.deleteSelected();
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
}
