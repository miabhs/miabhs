/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

/**
 *
 * @author aphasan
 */
public enum PersonalSalutation {
    
    USTADZ("Ustadz"),
    KAK("Kak");
    
    private final String label;

    private PersonalSalutation(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
}
