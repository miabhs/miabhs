/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_JENISPENGAJARAN")
public class JenisPengajaran implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Miabh_JenisPengajaran", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Miabh_JenisPengajaran", strategy = GenerationType.TABLE)
    private Long id;
    
    private String kode;
    
    private String nama;
    
    private String description;

    @OneToMany(mappedBy = "jenisPengajaran")
    private List<JenisKitab> listJenisKitab;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<JenisKitab> getListJenisKitab() {
        return listJenisKitab;
    }

    public void setListJenisKitab(List<JenisKitab> listJenisKitab) {
        this.listJenisKitab = listJenisKitab;
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
        if (!(object instanceof JenisPengajaran)) {
            return false;
        }
        JenisPengajaran other = (JenisPengajaran) object;
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
