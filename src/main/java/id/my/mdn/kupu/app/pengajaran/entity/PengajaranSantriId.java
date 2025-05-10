/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Embeddable
public class PengajaranSantriId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private PartyRelationshipId pengajaran;
    
    private LocalDate pengajaranDate;

    public PengajaranSantriId() {
    }

    public PengajaranSantriId(PartyRelationshipId pengajaran, LocalDate pengajaranDate) {
        this.pengajaran = pengajaran;
        this.pengajaranDate = pengajaranDate;
    }

    public PengajaranSantriId(String... params) {
    }

    public PartyRelationshipId getPengajaran() {
        return pengajaran;
    }

    public void setPengajaran(PartyRelationshipId pengajaran) {
        this.pengajaran = pengajaran;
    }

    public LocalDate getPengajaranDate() {
        return pengajaranDate;
    }

    public void setPengajaranDate(LocalDate pengajaranDate) {
        this.pengajaranDate = pengajaranDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.pengajaran);
        hash = 79 * hash + Objects.hashCode(this.pengajaranDate);
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
        final PengajaranSantriId other = (PengajaranSantriId) obj;
        if (!Objects.equals(this.pengajaran, other.pengajaran)) {
            return false;
        }
        return Objects.equals(this.pengajaranDate, other.pengajaranDate);
    }

    @Override
    public String toString() {
        return "PengajaranSantriId{" + "pengajaran=" + pengajaran + ", pengajaranDate=" + pengajaranDate + '}';
    }
}
