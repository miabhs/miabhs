/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

/**
 *
 * @author aphasan
 */
public enum Presensi {
    HADIR("Hadir", "H"),
    TERLAMBAT("Terlambat", "T"),
    IJIN("Ijin", "I"),
    SAKIT("Sakit", "S"),
    TIDAK_HADIR("Tidak Hadir", "A");
    
    private final String label;
    
    private final String abbr;

    private Presensi(String label, String abbr) {
        this.label = label;
        this.abbr = abbr;
    }

    public String getLabel() {
        return label;
    }

    public String getAbbr() {
        return abbr;
    }
}
