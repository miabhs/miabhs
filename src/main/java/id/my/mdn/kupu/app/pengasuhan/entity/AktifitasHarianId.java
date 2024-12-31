/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

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
public class AktifitasHarianId implements Serializable {

    private static final long serialVersionUID = 1L;

    private long santri;

    private LocalDate date;

    public AktifitasHarianId() {
    }

    public AktifitasHarianId(long santri, LocalDate date) {
        this.santri = santri;
        this.date = date;
    }

    public AktifitasHarianId(String... params) {
        this(
                Long.parseLong(params[0]),
                EntityUtil.stringKeyToLocalDate(params[1], "ddMMyyyy")
        );
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(santri, EntityUtil.stringKeyFromLocalDate(date, "ddMMyyyy"));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.santri ^ (this.santri >>> 32));
        hash = 37 * hash + Objects.hashCode(this.date);
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
        final AktifitasHarianId other = (AktifitasHarianId) obj;
        if (this.santri != other.santri) {
            return false;
        }
        return Objects.equals(this.date, other.date);
    }

    public long getSantri() {
        return santri;
    }

    public void setSantri(long santri) {
        this.santri = santri;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
}
