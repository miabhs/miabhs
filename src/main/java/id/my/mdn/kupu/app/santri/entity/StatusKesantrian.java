/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public enum StatusKesantrian {
    
    ACTIVE("StatusKesantrian.ACTIVE"),
    INACTIVE("StatusKesantrian.INACTIVE"),
    LEAVE("StatusKesantrian.LEAVE"),
    GRADUATED("StatusKesantrian.GRADUATED"),
    DROPOUT("StatusKesantrian.DROPOUT");
    
    private final String label;
    
    private StatusKesantrian(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
}
