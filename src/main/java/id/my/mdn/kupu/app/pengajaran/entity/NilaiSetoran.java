/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

/**
 *
 * @author aphasan
 */
public enum NilaiSetoran {
    A("Sangat Baik", 4),
    B("Baik", 3),
    C("Cukup", 2),
    D("Kurang", 1),
    E("Tidak Setoran", 0);
    
    private final String label;
    
    private final int nilai;

    private NilaiSetoran(String label, int nilai) {
        this.label = label;
        this.nilai = nilai;
    }

    public String getLabel() {
        return label;
    }

    public int getNilai() {
        return nilai;
    }
}
