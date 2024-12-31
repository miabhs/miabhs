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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_TAHUNPEMBELAJARAN")
public class TahunPembelajaran implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Miabh_TahunPembelajaran", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Miabh_TahunPembelajaran", strategy = GenerationType.TABLE)
    private Long id;
    
    private String name;
    
    private LocalDate fromDate;
    
    private LocalDate thruDate;

    @OneToMany(mappedBy = "tahunPembelajaran")
    private List<PeriodePembelajaran> listPeriodePembelajaran;

    public TahunPembelajaran() {
    }

    public TahunPembelajaran(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

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

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public List<PeriodePembelajaran> getListPeriodePembelajaran() {
        return listPeriodePembelajaran;
    }

    public void setListPeriodePembelajaran(List<PeriodePembelajaran> listPeriodePembelajaran) {
        this.listPeriodePembelajaran = listPeriodePembelajaran;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final TahunPembelajaran other = (TahunPembelajaran) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
