/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class AktifitasTambahanId implements Serializable {

    private AktifitasHarianId aktifitasHarian;

    private String bentukAktifitas;

    public AktifitasTambahanId() {
    }

    public AktifitasTambahanId(AktifitasHarianId aktifitasHarian, String bentukAktifitas) {
        this.aktifitasHarian = aktifitasHarian;
        this.bentukAktifitas = bentukAktifitas;
    }

    public AktifitasTambahanId(String... params) {
        this(new AktifitasHarianId(params[0], params[1]), params[2]);
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(aktifitasHarian, bentukAktifitas);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.aktifitasHarian);
        hash = 59 * hash + Objects.hashCode(this.bentukAktifitas);
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
        final AktifitasTambahanId other = (AktifitasTambahanId) obj;
        if (!Objects.equals(this.bentukAktifitas, other.bentukAktifitas)) {
            return false;
        }
        return Objects.equals(this.aktifitasHarian, other.aktifitasHarian);
    }

    public AktifitasHarianId getAktifitasHarian() {
        return aktifitasHarian;
    }

    public void setAktifitasHarian(AktifitasHarianId aktifitasHarian) {
        this.aktifitasHarian = aktifitasHarian;
    }

    public String getBentukAktifitas() {
        return bentukAktifitas;
    }

    public void setBentukAktifitas(String bentukAktifitas) {
        this.bentukAktifitas = bentukAktifitas;
    }

}
