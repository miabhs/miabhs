/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public enum JenisAktifitas {
    
    BDAS("BDAS"),
    BDAS_POKOK("BDAS Pokok"),
    NON_BDAS("Non BDAS");
    
    private final String label;
    
    public static JenisAktifitas convert(String string) {
        for(JenisAktifitas jns : JenisAktifitas.values()) {
            if(jns.name().equals(string)) return jns;
        }
        return null;
    }
    
    private JenisAktifitas(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
