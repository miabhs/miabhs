/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Embeddable
public class PengasuhanSantriId implements Serializable{
    
    private Long santri;
    
    private Long ustadz;
    
    private LocalDate pengasuhanDate;
    
    @Enumerated(EnumType.STRING)
    private JenisPengasuhan jenis;

    public PengasuhanSantriId() {
    }

    public PengasuhanSantriId(Long santri, Long ustadz, LocalDate pengasuhanDate, JenisPengasuhan jenis) {
        this.santri = santri;
        this.ustadz = ustadz;
        this.pengasuhanDate = pengasuhanDate;
        this.jenis = jenis;
    }

    public Long getSantri() {
        return santri;
    }

    public void setSantri(Long santri) {
        this.santri = santri;
    }

    public Long getUstadz() {
        return ustadz;
    }

    public void setUstadz(Long ustadz) {
        this.ustadz = ustadz;
    }

    public LocalDate getPengasuhanDate() {
        return pengasuhanDate;
    }

    public void setPengasuhanDate(LocalDate pengasuhanDate) {
        this.pengasuhanDate = pengasuhanDate;
    }

    public JenisPengasuhan getJenis() {
        return jenis;
    }

    public void setJenis(JenisPengasuhan jenis) {
        this.jenis = jenis;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.santri);
        hash = 67 * hash + Objects.hashCode(this.ustadz);
        hash = 67 * hash + Objects.hashCode(this.pengasuhanDate);
        hash = 67 * hash + Objects.hashCode(this.jenis);
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
        final PengasuhanSantriId other = (PengasuhanSantriId) obj;
        if (!Objects.equals(this.santri, other.santri)) {
            return false;
        }
        if (!Objects.equals(this.ustadz, other.ustadz)) {
            return false;
        }
        if (!Objects.equals(this.pengasuhanDate, other.pengasuhanDate)) {
            return false;
        }
        return this.jenis == other.jenis;
    }
    
}
