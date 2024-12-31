/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class LaporanKepengasuhan {
    private Santri santri;
    private PeriodePembelajaran periode;
    private List<RangkumanPeriode> listRangkumanPeriode;
    private CatatanKepengasuhan catatanLaporan;

    public LaporanKepengasuhan(Santri santri, PeriodePembelajaran periode, List<RangkumanPeriode> listRangkumanPeriode, CatatanKepengasuhan catatanLaporan) {
        this.santri = santri;
        this.periode = periode;
        this.listRangkumanPeriode = listRangkumanPeriode;
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

    public List<RangkumanPeriode> getListRangkumanPeriode() {
        return listRangkumanPeriode;
    }

    public CatatanKepengasuhan getCatatanLaporan() {
        return catatanLaporan;
    }

    public void setListRangkumanPeriode(List<RangkumanPeriode> listRangkumanPeriode) {
        this.listRangkumanPeriode = listRangkumanPeriode;
    }

    public void setCatatanLaporan(CatatanKepengasuhan catatanLaporan) {
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
        final LaporanKepengasuhan other = (LaporanKepengasuhan) obj;
        if (!Objects.equals(this.santri, other.santri)) {
            return false;
        }
        return Objects.equals(this.periode, other.periode);
    }
}
