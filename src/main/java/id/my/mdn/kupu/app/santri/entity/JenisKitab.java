/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_JENISKITAB")
public class JenisKitab implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Miabh_JenisKitab", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Miabh_JenisKitab", strategy = GenerationType.TABLE)
    private Long id;
    
    private String judul;
    
    private String penulis;
    
    private int jumlahHalaman;
    
    private String description;
    
    @ManyToOne
    private JenisPengajaran jenisPengajaran;
    
    @ManyToOne
    private KategoriKitab kategoriKitab;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public int getJumlahHalaman() {
        return jumlahHalaman;
    }

    public void setJumlahHalaman(int jumlahHalaman) {
        this.jumlahHalaman = jumlahHalaman;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JenisPengajaran getJenisPengajaran() {
        return jenisPengajaran;
    }

    public void setJenisPengajaran(JenisPengajaran jenisPengajaran) {
        this.jenisPengajaran = jenisPengajaran;
    }

    public KategoriKitab getKategoriKitab() {
        return kategoriKitab;
    }

    public void setKategoriKitab(KategoriKitab kategoriKitab) {
        this.kategoriKitab = kategoriKitab;
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
        if (!(object instanceof JenisKitab)) {
            return false;
        }
        JenisKitab other = (JenisKitab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id != null ? String.valueOf(id) : null;
    }
    
}
