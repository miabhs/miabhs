/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_STATUSSANTRI")
public class StatusSantri implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private StatusSantriId id;
    
    @MapsId("santri")
    @ManyToOne
    private Santri santri;
    
    @Enumerated(EnumType.STRING)
    private StatusKesantrian status;
    
    private LocalDate thruDate;
    
    @Lob
    private String remarks;

    public StatusSantriId getId() {
        return id;
    }

    public void setId(StatusSantriId id) {
        this.id = id;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public StatusKesantrian getStatus() {
        return status;
    }

    public void setStatus(StatusKesantrian status) {
        this.status = status;
    }

    public LocalDate getFromDate() {
        return id.getFromDate();
    }

    public void setFromDate(LocalDate fromDate) {
        id.setFromDate(fromDate);
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        if (!(object instanceof StatusSantri)) {
            return false;
        }
        StatusSantri other = (StatusSantri) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
