/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import jakarta.persistence.CascadeType;
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
@Table(name = "MIABH_KATEGORIKITAB")
public class KategoriKitab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name = "Miabh_KategoriKitab", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Miabh_KategoriKitab", strategy = GenerationType.TABLE)
    private Long id;
    
    private String name;

    @OneToMany(mappedBy = "kategoriKitab", cascade = CascadeType.ALL)
    private List<JenisKitab> listJenisKitab;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof KategoriKitab)) {
            return false;
        }
        KategoriKitab other = (KategoriKitab) object;
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
