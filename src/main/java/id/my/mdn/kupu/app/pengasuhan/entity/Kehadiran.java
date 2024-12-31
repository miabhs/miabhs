/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
 public enum Kehadiran {
    
    HADIR("Hadir"),
    TERLAMBAT("Terlambat"),
    TIDAK_HADIR("Tidak hadir");
    
    final String label;

    private Kehadiran(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
