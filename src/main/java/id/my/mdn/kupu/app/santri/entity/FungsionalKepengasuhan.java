/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

/**
 *
 * @author aphasan
 */
public enum FungsionalKepengasuhan {
    
    PEMBINA_KEPENGASUHAN("Pembina Kepengasuhan"),
    MASUL_KEPENGASUHAN("Mas'ul Kepengasuhan"),
    PELAKSANA_KEPENGASUHAN("Pelaksana Kepengasuhan"),
    PEMBANTU_PELAKSANA_KEPENGASUHAN("Pembantu Pelaksana Kepengasuhan"),
    KAKAK_KEPENGASUHAN("Kakak Kepengasuhan");
    
    private final String label;

    private FungsionalKepengasuhan(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
