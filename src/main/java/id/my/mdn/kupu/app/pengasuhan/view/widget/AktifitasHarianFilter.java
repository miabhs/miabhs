/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.JenisAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.NilaiAktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.Status;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import java.io.Serializable;
import java.time.LocalDate;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "aktifitasHarianFilter")
@Dependent
public class AktifitasHarianFilter extends FilterContent implements Serializable {
    
    @Bookmark(name = "nm")
    private String name;
    
    @Bookmark(name = "kp")
    private KelompokPengasuhan kelompokPengasuhan;

    @Bookmark(name = "fd")
    private LocalDate fromDate = LocalDate.now();
    
    @Bookmark(name = "td")
    private LocalDate thruDate = LocalDate.now();;
    
    @Bookmark(name = "st")
    private Status status;   
    
    @Bookmark(name = "sn")
    private Santri santri;   
    
    @Bookmark(name = "bs")
    private BentukAktifitas bentukAktifitas;   
    
    @Bookmark(name = "js")
    private JenisAktifitas jenisAktifitas; 
    
    @Bookmark(name = "na")
    private NilaiAktifitas nilaiAktifitas;
    
    @Bookmark(name = "sk")
    private StatusKesantrian statusKesantrian;

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        return kelompokPengasuhan;
    }

    public void setKelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        this.kelompokPengasuhan = kelompokPengasuhan;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public JenisAktifitas getJenisAktifitas() {
        return jenisAktifitas;
    }

    public void setJenisAktifitas(JenisAktifitas jenisAktifitas) {
        this.jenisAktifitas = jenisAktifitas;
    }

    public NilaiAktifitas getNilaiAktifitas() {
        return nilaiAktifitas;
    }

    public void setNilaiAktifitas(NilaiAktifitas nilaiAktifitas) {
        this.nilaiAktifitas = nilaiAktifitas;
    }

    public BentukAktifitas getBentukAktifitas() {
        return bentukAktifitas;
    }

    public void setBentukAktifitas(BentukAktifitas bentukAktifitas) {
        this.bentukAktifitas = bentukAktifitas;
    }

    public StatusKesantrian getStatusKesantrian() {
        return statusKesantrian;
    }

    public void setStatusKesantrian(StatusKesantrian statusKesantrian) {
        this.statusKesantrian = statusKesantrian;
    }
    
}
