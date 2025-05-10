/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.app.santri.entity.JenisPengajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.view.annotation.SorterField;
import id.my.mdn.kupu.core.base.view.annotation.SorterField.Order;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_PENCAPAIANBELAJAR")
public class PencapaianBelajar implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @ManyToOne
    private Santri santri;
    
    @ManyToOne
    private JenisKitab jenisKitab; 
    
    @SorterField(label = "Tanggal Pembelajaran", order = Order.DESC)
    private LocalDate pengajaranDate;
    
    @ElementCollection
    @CollectionTable(name = "MIABH_ATRIBUTSETORANPEMBELAJARAN")
    @OrderColumn(name = "ATRIBUT_ORDER")
    private List<AtributHasilPembelajaran> setoranPembelajaran;
    
    @Enumerated(EnumType.STRING)
    private NilaiSetoran setoran; 

    @SorterField(label = "Tanggal Input", order = Order.DESC)
    private LocalDateTime created;

    public PencapaianBelajar() {
    }
    
    @Transient
    private JenisPengajaran jenisPengajaran;
    
    @Transient
    private String capaian;
    
    public PencapaianBelajar(Long jenisPengajaranId, String jenisPengajaranKode, String jenisPengajaranNama,
            Long jenisKitabId, String jenisKitabJudul, String capaian) {
        this.jenisPengajaran = new JenisPengajaran();
        this.jenisPengajaran.setId(jenisPengajaranId);
        this.jenisPengajaran.setKode(jenisPengajaranKode);
        this.jenisPengajaran.setNama(jenisPengajaranNama);
        
        this.jenisKitab = new JenisKitab();
        this.jenisKitab.setId(jenisKitabId);
        this.jenisKitab.setJudul(jenisKitabJudul);
        
        this.capaian = capaian;
    }

    public JenisPengajaran getJenisPengajaran() {
        return jenisPengajaran;
    }

    public String getCapaian() {
        return capaian;
    }

    @PrePersist
    private void prePersist() {
        id = UUID.randomUUID().toString();
        created = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final PencapaianBelajar other = (PencapaianBelajar) obj;
        return Objects.equals(this.id, other.id);
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public LocalDate getPengajaranDate() {
        return pengajaranDate;
    }

    public void setPengajaranDate(LocalDate pengajaranDate) {
        this.pengajaranDate = pengajaranDate;
    }

    public JenisKitab getJenisKitab() {
        return jenisKitab;
    }

    public void setJenisKitab(JenisKitab jenisKitab) {
        this.jenisKitab = jenisKitab;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NilaiSetoran getSetoran() {
        return setoran;
    }

    public void setSetoran(NilaiSetoran setoran) {
        this.setoran = setoran;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<AtributHasilPembelajaran> getSetoranPembelajaran() {
        return setoranPembelajaran;
    }

    public void setSetoranPembelajaran(List<AtributHasilPembelajaran> setoranPembelajaran) {
        this.setoranPembelajaran = setoranPembelajaran;
    }
    
}
