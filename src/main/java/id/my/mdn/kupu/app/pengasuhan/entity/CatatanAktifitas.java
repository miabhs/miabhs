/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class CatatanAktifitas {
    
    private final BentukAktifitas bentukAktifitas;
    private int count;

    public CatatanAktifitas(BentukAktifitas bentukAktifitas) {
        this.bentukAktifitas = bentukAktifitas;
        this.count++;
    }

    public void incCount() {
        count++;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.bentukAktifitas);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CatatanAktifitas other = (CatatanAktifitas) obj;
        return Objects.equals(this.bentukAktifitas, other.bentukAktifitas);
    }

    public BentukAktifitas getBentukAktifitas() {
        return bentukAktifitas;
    }

    public int getCount() {
        return count;
    }
    
}
