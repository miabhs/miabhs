/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.ArrayList;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "MIABH_PENGAMPUHALAQOH")
public class PengampuHalaqoh extends PartyRelationship {

    private static final long serialVersionUID = 1L;

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<PengampuHalaqoh> {

        public Builder() {
            super(new PengampuHalaqoh());
        }

        public Builder from(Ustadz fromRole) {
            entity.setFromRole(fromRole);
            if (fromRole != null) {
                if (fromRole.getSourceRelationships() == null) {
                    fromRole.setSourceRelationships(new ArrayList<>());
                }
                fromRole.getSourceRelationships().add(entity);
            }

            return this;
        }

        public Builder to(HalaqohPengajaran toRole) {
            entity.setToRole(toRole);
            if (toRole != null) {
                if (toRole.getTargetRelationships() == null) {
                    toRole.setTargetRelationships(new ArrayList<>());
                }
                toRole.getTargetRelationships().add(entity);
            }

            return this;
        }

    }

    public Ustadz getUstadz() {
        return (Ustadz) getFromRole();
    }

    public void setUstadz(Ustadz ustadz) {
        setFromRole(ustadz);
    }

    public Ustadz getHalaqoh() {
        return (Ustadz) getToRole();
    }

    public void setHalaqoh(Ustadz halaqoh) {
        setToRole(halaqoh);
    }

}
