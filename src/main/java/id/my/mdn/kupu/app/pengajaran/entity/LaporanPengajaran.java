/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class LaporanPengajaran {
    private Santri santri;
    private PeriodePembelajaran periode;
    private List<PencapaianBelajar> listPencapaianBelajar;
    private CatatanPengajaran catatanLaporan;

    public LaporanPengajaran(Santri santri, PeriodePembelajaran periode, List<PencapaianBelajar> listRangkumanPeriode, CatatanPengajaran catatanLaporan) {
        this.santri = santri;
        this.periode = periode;
        this.listPencapaianBelajar = listRangkumanPeriode;
        this.catatanLaporan = catatanLaporan;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public PeriodePembelajaran getPeriode() {
        return periode;
    }

    public void setPeriode(PeriodePembelajaran periode) {
        this.periode = periode;
    }

    public List<PencapaianBelajar> getListPencapaianBelajar() {
        return listPencapaianBelajar;
    }

    public CatatanPengajaran getCatatanLaporan() {
        return catatanLaporan;
    }

    public void setListPencapaianBelajar(List<PencapaianBelajar> listPencapaianBelajar) {
        this.listPencapaianBelajar = listPencapaianBelajar;
    }

    public void setCatatanLaporan(CatatanPengajaran catatanLaporan) {
        this.catatanLaporan = catatanLaporan;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.santri);
        hash = 11 * hash + Objects.hashCode(this.periode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LaporanPengajaran other = (LaporanPengajaran) obj;
        if (!Objects.equals(this.santri, other.santri)) {
            return false;
        }
        return Objects.equals(this.periode, other.periode);
    }
}
