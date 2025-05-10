/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.ArrayList;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_PELAKSANAKEPENGASUHAN")
public class PelaksanaKepengasuhan extends PartyRelationship {

    private static final long serialVersionUID = 1L;

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<PelaksanaKepengasuhan> {

        public Builder() {
            super(new PelaksanaKepengasuhan());
        }

        public Builder from(PartyRole source) {
            if (source != null) {
                entity.setFromRole(source);
                if (source.getSourceRelationships() == null) {
                    source.setSourceRelationships(new ArrayList<>());
                }
                source.getSourceRelationships().add(entity);
            }

            return this;
        }

        public Builder to(KelompokPengasuhan target) {
            if (target != null) {
                entity.setToRole(target);
                if (target.getTargetRelationships() == null) {
                    target.setTargetRelationships(new ArrayList<>());
                }
                target.getTargetRelationships().add(entity);
            }

            return this;
        }

    }
    
    @Enumerated(EnumType.STRING)
    private FungsionalKepengasuhan fungsional;
    
    @Enumerated(EnumType.STRING)
    private PersonalSalutation salute;

    public FungsionalKepengasuhan getFungsional() {
        return fungsional;
    }

    public void setFungsional(FungsionalKepengasuhan fungsional) {
        this.fungsional = fungsional;
    }

    public PersonalSalutation getSalute() {
        return salute;
    }

    public void setSalute(PersonalSalutation salute) {
        this.salute = salute;
    }

    public Ustadz getUstadz() {
        return (Ustadz) getFromRole();
    }

    public void setUstadz(Ustadz ustadz) {
        setFromRole(ustadz);
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        return (KelompokPengasuhan) getToRole();
    }

    public void setKelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        setToRole(kelompokPengasuhan);
    }

}
