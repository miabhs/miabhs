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

    public AtributPembelajaranValue(String... values) {
        this.marker = values[0].trim();
        if(values.length > 1) this.deskripsi = values[1].trim();
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
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.marker);
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
        return Objects.equals(this.marker, other.marker);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(marker != null) sb.append(marker);
        if(deskripsi != null) sb.append(":").append(deskripsi);
        return sb.toString() ;
    }
    
}
