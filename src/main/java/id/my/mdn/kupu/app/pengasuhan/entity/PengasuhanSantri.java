/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "MIABH_PENGASUHANSANTRI", uniqueConstraints = {})
public class PengasuhanSantri implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @ManyToOne
    private Santri santri;
    
    @ManyToOne
    private Ustadz ustadz;
    
    private LocalDate pengasuhanDate;
    
    @Enumerated(EnumType.STRING)
    private JenisPengasuhan jenis;

    private LocalDateTime created;

    @PrePersist
    private void prePersist() {
        id = UUID.randomUUID().toString();
        created = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final PengasuhanSantri other = (PengasuhanSantri) obj;
        return Objects.equals(this.id, other.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public Ustadz getUstadz() {
        return ustadz;
    }

    public void setUstadz(Ustadz ustadz) {
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
    
}
