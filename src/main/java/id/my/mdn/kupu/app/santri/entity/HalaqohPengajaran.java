/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_HALAQOHPENGAJARAN")
public class HalaqohPengajaran implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Miabh_HalaqohPengajaran", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Miabh_HalaqohPengajaran", strategy = GenerationType.TABLE)
    private Long id;
    
    private String nama;
    
    @ManyToOne
    private Ustadz pengampu;
    
    @ManyToOne
    private JenisKitab jenisKitab;
    
    private LocalTime beginAt;
    
    private LocalTime endedAt;
    
    @ManyToMany
    @JoinTable(name = "MIABH_HALAQOHSANTRI",
            joinColumns = @JoinColumn(name = "HALAQOHPENGAJARAN_ID"),
            inverseJoinColumns = @JoinColumn(name = "MIABH_ID")
    )
    private List<Santri> listSantri;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Ustadz getPengampu() {
        return pengampu;
    }

    public void setPengampu(Ustadz pengampu) {
        this.pengampu = pengampu;
    }

    public JenisKitab getJenisKitab() {
        return jenisKitab;
    }

    public void setJenisKitab(JenisKitab jenisKitab) {
        this.jenisKitab = jenisKitab;
    }

    public List<Santri> getListSantri() {
        return listSantri;
    }

    public void setListSantri(List<Santri> listSantri) {
        this.listSantri = listSantri;
    }

    public LocalTime getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(LocalTime beginAt) {
        this.beginAt = beginAt;
    }

    public LocalTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalTime endedAt) {
        this.endedAt = endedAt;
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
        if (!(object instanceof HalaqohPengajaran)) {
            return false;
        }
        HalaqohPengajaran other = (HalaqohPengajaran) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}
