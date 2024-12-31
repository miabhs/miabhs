/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasHarian;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.Kehadiran;
import static id.my.mdn.kupu.app.pengasuhan.entity.Kehadiran.HADIR;
import static id.my.mdn.kupu.app.pengasuhan.entity.Kehadiran.TERLAMBAT;
import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "aktifitasBean")
@ViewScoped
public class AktifitasBean implements Serializable {

    @Inject
    private BentukAktifitasFacade bentukAktifitasFacade;
    
    private List<BentukAktifitas> listBentukAktifitas;
    
    @PostConstruct
    public void populateColumns() {
        listBentukAktifitas = bentukAktifitasFacade.findAll();
    }
    
    public String getColor(Kehadiran kehadiran) {
        switch(kehadiran) {
            case HADIR:
                return "border-1 bg-green-600 border-green-600";
            case TERLAMBAT:
                return "border-1 bg-yellow-600 border-yellow-600";
            default:
                return "border-1 bg-red-600 border-red-600";
        }
    }
    
    public String getColor(AktifitasHarian aktifitasHarian) {
        switch(aktifitasHarian.getStatusPokok()) {
            case HIJAU:
                return "border-1 bg-green-600 border-green-600";
            case KUNING:
                return "border-1 bg-yellow-600 border-yellow-600";
            default:
                return "border-1 bg-red-600 border-red-600";
        }
    }

    public List<BentukAktifitas> getListBentukAktifitas() {
        return listBentukAktifitas;
    }
    
}
