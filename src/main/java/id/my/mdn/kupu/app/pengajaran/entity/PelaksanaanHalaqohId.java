/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Embeddable
public class PelaksanaanHalaqohId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long halaqohPengajaran;
    
    private LocalDate pengajaranDate;

    public Long getHalaqohPengajaran() {
        return halaqohPengajaran;
    }

    public void setHalaqohPengajaran(Long halaqohPengajaran) {
        this.halaqohPengajaran = halaqohPengajaran;
    }

    public LocalDate getPengajaranDate() {
        return pengajaranDate;
    }

    public void setPengajaranDate(LocalDate pengajaranDate) {
        this.pengajaranDate = pengajaranDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.halaqohPengajaran);
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
        final PelaksanaanHalaqohId other = (PelaksanaanHalaqohId) obj;
        if (!Objects.equals(this.halaqohPengajaran, other.halaqohPengajaran)) {
            return false;
        }
        return Objects.equals(this.pengajaranDate, other.pengajaranDate);
    }
    
}
