/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Embeddable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class StatusSantriId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long santri;
    
    private LocalDate fromDate;

    public StatusSantriId() {
    }

    public StatusSantriId(Long santri, LocalDate fromDate) {
        this.santri = santri;
        this.fromDate = fromDate;
    }

    public StatusSantriId(String[] params) {
        this(Long.valueOf(params[0]), EntityUtil.stringKeyToLocalDate(params[1], Constants.KEYFORMAT_LOCALDATE));
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(santri, EntityUtil.stringKeyFromLocalDate(fromDate, Constants.KEYFORMAT_LOCALDATE));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.santri);
        hash = 97 * hash + Objects.hashCode(this.fromDate);
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
        final StatusSantriId other = (StatusSantriId) obj;
        if (!Objects.equals(this.santri, other.santri)) {
            return false;
        }
        return Objects.equals(this.fromDate, other.fromDate);
    }

    public Long getSantri() {
        return santri;
    }

    public void setSantri(Long santri) {
        this.santri = santri;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    
}
