/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class CatatanPengajaranId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long santri;
    
    private Long periodePembelajaran;

    public CatatanPengajaranId() {
    }

    public CatatanPengajaranId(Long santri, Long periodePembelajaran) {
        this.santri = santri;
        this.periodePembelajaran = periodePembelajaran;
    }    

    public CatatanPengajaranId(String... attributes) {
        this(Long.valueOf(attributes[0]), Long.valueOf(attributes[1]));
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(santri, periodePembelajaran);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.santri);
        hash = 83 * hash + Objects.hashCode(this.periodePembelajaran);
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
        final CatatanPengajaranId other = (CatatanPengajaranId) obj;
        if (!Objects.equals(this.santri, other.santri)) {
            return false;
        }
        return Objects.equals(this.periodePembelajaran, other.periodePembelajaran);
    }

    public Long getSantri() {
        return santri;
    }

    public void setSantri(Long santri) {
        this.santri = santri;
    }

    public Long getPeriodePembelajaran() {
        return periodePembelajaran;
    }

    public void setPeriodePembelajaran(Long periodePembelajaran) {
        this.periodePembelajaran = periodePembelajaran;
    }
    
}
