/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_AKTIFITASPENGASUHAN")
public class AktifitasPengasuhan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @ManyToOne
    private Ustadz ustadz;
    
    @ManyToOne
    private Santri santri;
    
    private LocalDate assignmentDate;
    
    @Lob
    private String remarks;

    @OneToMany(mappedBy = "aktifitasPengasuhan")
    private List<AktifitasPengasuhanLog> logs;
    
    @PrePersist
    private void prePersist() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final AktifitasPengasuhan other = (AktifitasPengasuhan) obj;
        return Objects.equals(this.id, other.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ustadz getUstadz() {
        return ustadz;
    }

    public void setUstadz(Ustadz ustadz) {
        this.ustadz = ustadz;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public LocalDate getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDate assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public List<AktifitasPengasuhanLog> getLogs() {
        return logs;
    }

    public void setLogs(List<AktifitasPengasuhanLog> logs) {
        this.logs = logs;
    }
    
}
