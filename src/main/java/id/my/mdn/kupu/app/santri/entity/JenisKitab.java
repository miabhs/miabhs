/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.app.pengajaran.entity.AtributPembelajaran;
import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_JENISKITAB")
public class JenisKitab implements Serializable, HierarchicalEntity<JenisKitab> {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "Miabh_JenisKitab", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Miabh_JenisKitab", strategy = GenerationType.TABLE)
    private Long id;

    @Column(unique = true)
    private String kode;

    private String judul;

    private String subJudul;

    @ElementCollection
    @CollectionTable(name = "MIABH_ATRIBUTKITAB")
    @OrderColumn(name = "ATRIBUT_ORDER")
    private List<AtributPembelajaran> listAtribut;

    private String penulis;

    private int jumlahHalaman;

    private String description;

    @ManyToOne
    private JenisPengajaran jenisPengajaran;

    @ManyToOne
    private KategoriKitab kategoriKitab;

    private boolean berjilid;
    
    private boolean dirincikan;

    @ManyToOne
    private JenisKitab parent;

    @OneToMany(mappedBy = "parent")
    private List<JenisKitab> children;

    public JenisKitab() {
    }

    public JenisKitab(Long id) {
        this.id = id;
    }

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

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSubJudul() {
        return subJudul;
    }

    public void setSubJudul(String subJudul) {
        this.subJudul = subJudul;
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

    public List<AtributPembelajaran> getListAtribut() {
        return listAtribut;
    }

    public void setListAtribut(List<AtributPembelajaran> listAtribut) {
        this.listAtribut = listAtribut;
    }

    public boolean isBerjilid() {
        return berjilid;
    }

    public void setBerjilid(boolean berjilid) {
        this.berjilid = berjilid;
    }

    @Override
    public JenisKitab getParent() {
        return parent;
    }

    @Override
    public void setParent(JenisKitab parent) {
        this.parent = parent;
    }

    @Override
    public List<JenisKitab> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<JenisKitab> children) {
        this.children = children;
    }

    public boolean isDirincikan() {
        return dirincikan;
    }

    public void setDirincikan(boolean dirincikan) {
        this.dirincikan = dirincikan;
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

    public String getLabel() {
        StringBuilder sb = new StringBuilder(judul);
        if (subJudul != null && !subJudul.isEmpty()) {
            sb.append(" - ").append(subJudul);
        }
        return sb.toString();
    }

}
