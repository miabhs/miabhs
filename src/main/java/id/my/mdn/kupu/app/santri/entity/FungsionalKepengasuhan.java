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
    
    PEMBINA_KEPENGASUHAN("Pembina Kepengasuhan", 1),
    MASUL_KEPENGASUHAN("Mas'ul Kepengasuhan", 2),
    PELAKSANA_KEPENGASUHAN("Pelaksana Kepengasuhan", 3),
    PEMBANTU_PELAKSANA_KEPENGASUHAN("Pembantu Pelaksana Kepengasuhan", 4),
    KAKAK_KEPENGASUHAN("Kakak Kepengasuhan", 5);
    
    private final String label;
    private final int level;

    private FungsionalKepengasuhan(String label, int level) {
        this.label = label;
        this.level = level;
    }

    public String getLabel() {
        return label;
    }

    public int getLevel() {
        return level;
    }
}
