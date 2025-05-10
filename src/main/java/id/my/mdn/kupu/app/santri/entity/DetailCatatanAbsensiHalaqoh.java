/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import java.io.Serializable;

/**
 *
 * @author aphasan
 */
public class DetailCatatanAbsensiHalaqoh implements Serializable {
    
    private final String bentukAktifitas;
    
    private final Integer quantity;
    
    private final boolean confirmed;

    public DetailCatatanAbsensiHalaqoh(String bentukAktifitas, Integer quantity, boolean confirmed) {
        this.bentukAktifitas = bentukAktifitas;
        this.quantity = quantity;
        this.confirmed = confirmed;
    }

    public String getBentukAktifitas() {
        return bentukAktifitas;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(bentukAktifitas).append(" ")
                .append("(").append(quantity).append(")");
        return sb.toString();
    }
    
}
