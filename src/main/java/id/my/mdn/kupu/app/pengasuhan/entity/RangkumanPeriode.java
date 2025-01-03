/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class RangkumanPeriode { 

    private final PeriodePembelajaran periode;
    private NilaiAktifitas nilaiBDAS;
    private List<CatatanAktifitas> listCatatanBDAS;
    private NilaiAktifitas nilaiNonBDAS;
    private List<CatatanAktifitas> listCatatanNonBDAS;

    public RangkumanPeriode(PeriodePembelajaran periode) {
        this.periode = periode;
        this.nilaiBDAS = NilaiAktifitas.HIJAU;
        this.listCatatanBDAS = new ArrayList<>();
        this.nilaiNonBDAS = NilaiAktifitas.HIJAU;
        this.listCatatanNonBDAS = new ArrayList<>();
    }

    public PeriodePembelajaran getPeriode() {
        return periode;
    }

    public NilaiAktifitas getNilaiBDAS() {
        return nilaiBDAS;
    }

    public List<CatatanAktifitas> getListCatatanBDAS() {
        return listCatatanBDAS;
    }

    public NilaiAktifitas getNilaiNonBDAS() {
        return nilaiNonBDAS;
    }

    public List<CatatanAktifitas> getListCatatanNonBDAS() {
        return listCatatanNonBDAS;
    }

    public void setNilaiBDAS(NilaiAktifitas nilaiBDAS) {
        this.nilaiBDAS = nilaiBDAS;
    }

    public void setListCatatanBDAS(List<CatatanAktifitas> listCatatanBDAS) {
        this.listCatatanBDAS = listCatatanBDAS;
    }

    public void setNilaiNonBDAS(NilaiAktifitas nilaiNonBDAS) {
        this.nilaiNonBDAS = nilaiNonBDAS;
    }

    public void setListCatatanNonBDAS(List<CatatanAktifitas> listCatatanNonBDAS) {
        this.listCatatanNonBDAS = listCatatanNonBDAS;
    }
}