/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class PencapaianBelajarId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long santri;
    
    private Long halaqoh;
    
    private Long jenisKitab; 
    
    private LocalDate pengajaranDate;

    public PencapaianBelajarId() {
    }

    public PencapaianBelajarId(Long santri, Long halaqoh, Long jenisKitab, LocalDate pengajaranDate) {
        this.santri = santri;
        this.halaqoh = halaqoh;
        this.jenisKitab = jenisKitab;
        this.pengajaranDate = pengajaranDate;
    }

    public PencapaianBelajarId(String... attributes) {
        this(Long.valueOf(attributes[0]),
                Long.valueOf(attributes[1]),
                Long.valueOf(attributes[2]),
                EntityUtil.stringKeyToLocalDate(attributes[3], Constants.KEYFORMAT_LOCALDATE)
        );
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(
                santri, halaqoh, jenisKitab, 
                EntityUtil.stringKeyFromLocalDate(pengajaranDate, Constants.KEYFORMAT_LOCALDATE)
        );
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.santri);
        hash = 67 * hash + Objects.hashCode(this.halaqoh);
        hash = 67 * hash + Objects.hashCode(this.jenisKitab);
        hash = 67 * hash + Objects.hashCode(this.pengajaranDate);
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
        final PencapaianBelajarId other = (PencapaianBelajarId) obj;
        if (!Objects.equals(this.santri, other.santri)) {
            return false;
        }
        if (!Objects.equals(this.halaqoh, other.halaqoh)) {
            return false;
        }
        if (!Objects.equals(this.jenisKitab, other.jenisKitab)) {
            return false;
        }
        return Objects.equals(this.pengajaranDate, other.pengajaranDate);
    }

    public Long getSantri() {
        return santri;
    }

    public void setSantri(Long santri) {
        this.santri = santri;
    }

    public Long getHalaqoh() {
        return halaqoh;
    }

    public void setHalaqoh(Long halaqoh) {
        this.halaqoh = halaqoh;
    }

    public Long getJenisKitab() {
        return jenisKitab;
    }

    public void setJenisKitab(Long jenisKitab) {
        this.jenisKitab = jenisKitab;
    }

    public LocalDate getPengajaranDate() {
        return pengajaranDate;
    }

    public void setPengajaranDate(LocalDate pengajaranDate) {
        this.pengajaranDate = pengajaranDate;
    }
    
}
