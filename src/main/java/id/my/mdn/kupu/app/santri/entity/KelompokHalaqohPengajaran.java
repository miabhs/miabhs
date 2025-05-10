/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "MIABH_KELOMPOKHALAQOHPENGAJARAN")
public class KelompokHalaqohPengajaran implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Miabh_KelompokHalaqohPengajaran", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Miabh_KelompokHalaqohPengajaran", strategy = GenerationType.TABLE)
    private Long id;
    
    private String name;
    
    @Column(nullable = false)
    private LocalDateTime beginAt;
    
    @Column(nullable = false)
    private LocalDateTime endAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(LocalDateTime beginAt) {
        this.beginAt = beginAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public LocalTime getBeginTime() {
        return beginAt != null ? beginAt.toLocalTime() : null;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginAt = beginTime != null ? LocalDateTime.of(LocalDate.EPOCH, beginTime) : null;
    }

    public LocalTime getEndTime() {
        return endAt != null ? endAt.toLocalTime() : null;
    }

    public void setEndTime(LocalTime endTime) {
        this.endAt = endTime != null ? LocalDateTime.of(LocalDate.EPOCH, endTime) : null;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KelompokHalaqohPengajaran)) {
            return false;
        }
        KelompokHalaqohPengajaran other = (KelompokHalaqohPengajaran) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}
