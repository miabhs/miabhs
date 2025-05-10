/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Embeddable
public class AtributPembelajaranValue implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String marker;
    
    private String deskripsi;
    
    private BigDecimal bobot;

    public AtributPembelajaranValue() {
    }

    public AtributPembelajaranValue(String marker, String deskripsi, BigDecimal bobot) {
        this.marker = marker;
        this.deskripsi = deskripsi;
        this.bobot = bobot;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public BigDecimal getBobot() {
        return bobot;
    }

    public void setBobot(BigDecimal bobot) {
        this.bobot = bobot;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.marker);
        hash = 17 * hash + Objects.hashCode(this.deskripsi);
        hash = 17 * hash + Objects.hashCode(this.bobot);
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
        final AtributPembelajaranValue other = (AtributPembelajaranValue) obj;
        if (!Objects.equals(this.marker, other.marker)) {
            return false;
        }
        if (!Objects.equals(this.deskripsi, other.deskripsi)) {
            return false;
        }
        return Objects.equals(this.bobot, other.bobot);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(marker != null) sb.append(marker);
        return sb.toString() ;
    }
    
}
