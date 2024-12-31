/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_AKTIFITASPENGASUHANLOG")
public class AktifitasPengasuhanLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private AktifitasPengasuhanLogId id;

    @MapsId("aktifitasPengasuhan")
    @ManyToOne
    private AktifitasPengasuhan aktifitasPengasuhan;

    @Lob
    private String logText;

    @Lob
    private byte[] logAudio;

    @Lob
    private byte[] logVideo;

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final AktifitasPengasuhanLog other = (AktifitasPengasuhanLog) obj;
        return Objects.equals(this.id, other.id);
    }

    public AktifitasPengasuhanLogId getId() {
        return id;
    }

    public void setId(AktifitasPengasuhanLogId id) {
        this.id = id;
    }

    public AktifitasPengasuhan getAktifitasPengasuhan() {
        return aktifitasPengasuhan;
    }

    public void setAktifitasPengasuhan(AktifitasPengasuhan aktifitasPengasuhan) {
        this.aktifitasPengasuhan = aktifitasPengasuhan;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public byte[] getLogAudio() {
        return logAudio;
    }

    public void setLogAudio(byte[] logAudio) {
        this.logAudio = logAudio;
    }

    public byte[] getLogVideo() {
        return logVideo;
    }

    public void setLogVideo(byte[] logVideo) {
        this.logVideo = logVideo;
    }

    public LocalDateTime getLogTime() {
        if (id == null) {
            id = new AktifitasPengasuhanLogId();
            id.setLogTime(LocalDateTime.now());
        }
        return id.getLogTime();
    }

    public void setLogTime(LocalDateTime logTime) {
        if (id == null) {
            id = new AktifitasPengasuhanLogId();
        }
        id.setLogTime(logTime);
    }

}
