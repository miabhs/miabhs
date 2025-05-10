/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "MIABH_PELAKSANAANHALAQOH")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "PelaksanaanHalaqoh",
            classes = {
                @ConstructorResult(
                        targetClass = PelaksanaanHalaqoh.class,
                        columns = {
                            @ColumnResult(name = "PENGAJARANDATE", type = LocalDate.class),
                            @ColumnResult(name = "CUSTOMTIME", type = Boolean.class),
                            @ColumnResult(name = "BEGINTIME", type = LocalDateTime.class),
                            @ColumnResult(name = "ENDTIME", type = LocalDateTime.class)
                        }
                )
            }
    )
})
public class PelaksanaanHalaqoh implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PelaksanaanHalaqohId id;

    @MapsId("halaqohPengajaran")
    @ManyToOne
    private HalaqohPengajaran halaqohPengajaran;

    private boolean customTime;

    private LocalDateTime startTime;

    private LocalDateTime finishTime;

    public PelaksanaanHalaqoh() {
    }

    public PelaksanaanHalaqoh(LocalDate pengajaranDate, boolean customTime, LocalDateTime begin, LocalDateTime end) {
        this.id = new PelaksanaanHalaqohId();
        id.setPengajaranDate(pengajaranDate);
        this.customTime = customTime;
        this.startTime = begin;
        this.finishTime = end;
    }

    public PelaksanaanHalaqohId getId() {
        return id;
    }

    public void setId(PelaksanaanHalaqohId id) {
        this.id = id;
    }

    public HalaqohPengajaran getHalaqohPengajaran() {
        return halaqohPengajaran;
    }

    public void setHalaqohPengajaran(HalaqohPengajaran halaqohPengajaran) {
        this.halaqohPengajaran = halaqohPengajaran;
    }

    public LocalDate getPengajaranDate() {
        return id.getPengajaranDate();
    }

    public boolean isCustomTime() {
        return customTime;
    }

    public void setCustomTime(boolean customTime) {
        this.customTime = customTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public LocalTime getBeginTime() {
        return startTime != null ? startTime.toLocalTime() : null;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.startTime = beginTime != null ? LocalDateTime.of(id.getPengajaranDate(), beginTime) : null;
    }

    public LocalTime getEndTime() {
        return finishTime != null ? finishTime.toLocalTime() : null;
    }

    public void setEndTime(LocalTime endTime) {
        this.finishTime = endTime != null ? LocalDateTime.of(id.getPengajaranDate(), endTime) : null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final PelaksanaanHalaqoh other = (PelaksanaanHalaqoh) obj;
        return Objects.equals(this.id, other.id);
    }

}
