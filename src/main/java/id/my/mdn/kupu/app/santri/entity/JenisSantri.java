/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

/**
 *
 * @author aphasan
 */
public enum JenisSantri {
    ASRAMA("A", "Asrama"),
    KHUSUS("K", "Khusus"),
    REGULER("R", "Reguler");

    final String kode;
    final String label;

    private JenisSantri(String kode, String label) {
        this.kode = kode;
        this.label = label;
    }

    public String getKode() {
        return kode;
    }

    public String getLabel() {
        return label;
    }
}
