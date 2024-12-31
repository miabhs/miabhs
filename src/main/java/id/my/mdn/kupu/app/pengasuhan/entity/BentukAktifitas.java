/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_BENTUKAKTIFITAS")
public class BentukAktifitas implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    private String kode;
    
    @Lob @Basic(fetch = FetchType.EAGER)
    private String bentuk;
    
    @Enumerated(EnumType.STRING)
    private JenisAktifitas jenis; 
    
    @Enumerated(EnumType.STRING)
    private NilaiAktifitas nilai;
    
    private LocalDateTime created;

    public BentukAktifitas() {
    }

    public BentukAktifitas(String id, String kode, String bentuk, JenisAktifitas jenis, NilaiAktifitas nilai) {
        this.id = id;
        this.kode = kode;
        this.bentuk = bentuk;
        this.jenis = jenis;
        this.nilai = nilai;
    }
    
    @PrePersist
    private void prePersist() {
        id = UUID.randomUUID().toString();
        created = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public JenisAktifitas getJenis() {
        return jenis;
    }

    public void setJenis(JenisAktifitas jenis) {
        this.jenis = jenis;
    }

    public NilaiAktifitas getNilai() {
        return nilai;
    }

    public void setNilai(NilaiAktifitas nilai) {
        this.nilai = nilai;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
    
    public String getLabel() {
        return (kode != null && !kode.isEmpty()) ? kode + " -- " + bentuk : bentuk;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final BentukAktifitas other = (BentukAktifitas) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id;
    }
    
}
