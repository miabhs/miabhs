/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class HalaqohSantriId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long halaqohPengajaran;
    
    private Long santri;
    
    public HalaqohSantriId() {}
    
    public HalaqohSantriId(Long halaqohPengajaran, Long santri) {
        this.halaqohPengajaran = halaqohPengajaran;
        this.santri = santri;
    }

    public HalaqohSantriId(String... params) {
        this(Long.valueOf(params[0]), Long.valueOf(params[1]));
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(halaqohPengajaran, santri);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.halaqohPengajaran);
        hash = 13 * hash + Objects.hashCode(this.santri);
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
        final HalaqohSantriId other = (HalaqohSantriId) obj;
        if (!Objects.equals(this.halaqohPengajaran, other.halaqohPengajaran)) {
            return false;
        }
        return Objects.equals(this.santri, other.santri);
    }
    

    public Long getHalaqohPengajaran() {
        return halaqohPengajaran;
    }

    public void setHalaqohPengajaran(Long halaqohPengajaran) {
        this.halaqohPengajaran = halaqohPengajaran;
    }

    public Long getSantri() {
        return santri;
    }

    public void setSantri(Long santri) {
        this.santri = santri;
    }
}
