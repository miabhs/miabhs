/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Embeddable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Embeddable
public class AktifitasPengasuhanLogId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String aktifitasPengasuhan;
    
    private LocalDateTime logTime;

    public AktifitasPengasuhanLogId() {}

    public AktifitasPengasuhanLogId(String aktifitasPengasuhan, LocalDateTime logTime) {
        this.aktifitasPengasuhan = aktifitasPengasuhan;
        this.logTime = logTime;
    }
    
    public AktifitasPengasuhanLogId(String[] params) {
        this(params[0], EntityUtil.stringKeyToLocalDateTime(params[1], Constants.KEYFORMAT_LOCALDATETIME));
    }

    @Override
    public String toString() {
        return EntityUtil.createStringId(aktifitasPengasuhan, EntityUtil.stringKeyFromLocalDateTime(logTime, Constants.KEYFORMAT_LOCALDATETIME));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.aktifitasPengasuhan);
        hash = 59 * hash + Objects.hashCode(this.logTime);
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
        final AktifitasPengasuhanLogId other = (AktifitasPengasuhanLogId) obj;
        if (!Objects.equals(this.aktifitasPengasuhan, other.aktifitasPengasuhan)) {
            return false;
        }
        return Objects.equals(this.logTime, other.logTime);
    }

    public String getAktifitasPengasuhan() {
        return aktifitasPengasuhan;
    }

    public void setAktifitasPengasuhan(String aktifitasPengasuhan) {
        this.aktifitasPengasuhan = aktifitasPengasuhan;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }
    
}
