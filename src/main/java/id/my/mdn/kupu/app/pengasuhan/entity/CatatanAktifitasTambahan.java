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
public class CatatanAktifitasTambahan {
    
    private final BentukAktifitasTambahan bentukAktifitas;
    private int count;

    public CatatanAktifitasTambahan(BentukAktifitasTambahan bentukAktifitas) {
        this.bentukAktifitas = bentukAktifitas;
        this.count++;
    }

    public void incCount() {
        count++;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.bentukAktifitas);
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
        final CatatanAktifitasTambahan other = (CatatanAktifitasTambahan) obj;
        return Objects.equals(this.bentukAktifitas, other.bentukAktifitas);
    }

    public BentukAktifitasTambahan getBentukAktifitas() {
        return bentukAktifitas;
    }
    
    public int getCount() {
        return count;
    }
    
}
