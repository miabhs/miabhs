/*
 * Copyright 2014 Medinacom <hq.medinacom at gmail.com>.
 * All rights reserved

 * A lot of time, effort and money is spent designing and implementing the software.
 * All system design, text, graphics, the selection and arrangement thereof, and
 * all software compilations, underlying source code, software and all other material
 * on this software are copyright Medinacom <hq.medinacom at gmail.com> and any affiliates.
 * 
 * In simple terms, every element of this software is protected by copyright.
 * Unless you have our express written permission, you are not allowed
 * to copy partially and or completely, modify partially and or completely,
 * use partially and or completely and or reproduce any part of this  software
 * in any way, shape and or form.
 * 
 * Taking material from other source code and or document Medinacom <hq.medinacom at gmail.com> and affiliates has designed is
 * also prohibited. You can be prosecuted by the licensee as well as by us as licensor.
 * 
 * Any other use of materials of this software, including reproduction for purposes other
 * than that noted in the business agreement, modification, distribution, or republication,
 * without the prior written permission of Medinacom <hq.medinacom at gmail.com> is strictly prohibited.
 * 
 * The source code, partially and or completely, shall not be presented and or shown
 * and or performed to public and or other parties without the prior written permission
 * of Medinacom <hq.medinacom at gmail.com>

 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.HierarchicalEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Medinacom <hq.medinacom at gmail.com>
 */
@Entity
@Table(name = "MIABH_PERIODEPEMBELAJARAN")
@NamedQueries ({
    @NamedQuery(name="PeriodePembelajaran.getPeriod", 
            query="SELECT ap "
                    + "FROM PeriodePembelajaran ap "
                    + "WHERE ap.fromDate <= :fromDate AND ap.thruDate >= :thruDate"),
    @NamedQuery(name="PeriodePembelajaran.getPrevPeriod", 
            query="SELECT ap "
                    + "FROM PeriodePembelajaran ap "
                    + "WHERE ap.fromDate = ("
                    + "SELECT MAX(ap2.fromDate) "
                    + "FROM PeriodePembelajaran ap2 "
                    + "WHERE ap2.fromDate < :fromDate"
                    + ")")
})
public class PeriodePembelajaran implements Serializable, Comparable<PeriodePembelajaran> , HierarchicalEntity<PeriodePembelajaran> {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name = "Miabh_PeriodePembelajaran", table = "KEYGEN", allocationSize = 1)
    @GeneratedValue(generator = "Miabh_PeriodePembelajaran", strategy = GenerationType.TABLE)
    private Long id;
    
    @ManyToOne
    private TahunPembelajaran tahunPembelajaran;
    
    private String name;

    private LocalDate fromDate;

    private LocalDate thruDate;

    @ManyToOne
    private PeriodePembelajaran parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PeriodePembelajaran> children;

    @Override
    public String toString() {
        return id != null ? String.valueOf(id) : null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final PeriodePembelajaran other = (PeriodePembelajaran) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int compareTo(PeriodePembelajaran t) {
        return this.getId().compareTo(t.getId());
    }

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">

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

    public TahunPembelajaran getTahunPembelajaran() {
        return tahunPembelajaran;
    }

    public void setTahunPembelajaran(TahunPembelajaran tahunPembelajaran) {
        this.tahunPembelajaran = tahunPembelajaran;
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
    
    @Override
    public List<PeriodePembelajaran> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<PeriodePembelajaran> children) {
        this.children = children;
    }

    @Override
    public PeriodePembelajaran getParent() {
        return parent;
    }

    @Override
    public void setParent(PeriodePembelajaran parent) {
        this.parent = parent;
    }
    
//</editor-fold>

}
