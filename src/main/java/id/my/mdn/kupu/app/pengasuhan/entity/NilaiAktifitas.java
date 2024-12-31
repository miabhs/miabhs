/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.core.base.util.PageUtil;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */ 
public enum NilaiAktifitas {
    HIJAU("Hijau"),
    KUNING("Kuning"),
    ORANGE("Oranye"),
    MERAH("Merah");
    
    private final String label;

    private NilaiAktifitas(String label) {
        this.label = label;
    }

    public String getColor() {
        return label;
    }
    
    public String getLabel() {
        switch(this) {
            case HIJAU:
                return PageUtil.compileMessage("NilaiAktifitas.HIJAU");
            case KUNING:
                return PageUtil.compileMessage("NilaiAktifitas.KUNING");
            case ORANGE:
                return PageUtil.compileMessage("NilaiAktifitas.ORANGE");
            case MERAH:
                return PageUtil.compileMessage("NilaiAktifitas.MERAH");
            default:
                return null;
        }
    }
}
