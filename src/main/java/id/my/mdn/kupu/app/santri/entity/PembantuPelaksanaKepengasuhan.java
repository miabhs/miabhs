/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_PEMBANTUPELAKSANAKEPENGASUHAN")
public class PembantuPelaksanaKepengasuhan extends PartyRelationship {

    private static final long serialVersionUID = 1L;
    
    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<PembantuPelaksanaKepengasuhan> {

        public Builder() {
            super(new PembantuPelaksanaKepengasuhan());
        }

        public Builder from(Ustadz ustadz) {
            entity.setFromRole(ustadz);
            if (ustadz.getSourceRelationships() == null) {
                ustadz.setSourceRelationships(new ArrayList<>());
            }
            ustadz.getSourceRelationships().add(entity);

            return this;
        }

        public Builder to(KelompokPengasuhan kelompokPengasuhan) {
            entity.setToRole(kelompokPengasuhan);
            if (kelompokPengasuhan.getTargetRelationships() == null) {
                kelompokPengasuhan.setTargetRelationships(new ArrayList<>());
            }
            kelompokPengasuhan.getTargetRelationships().add(entity);

            return this;
        }

    }
    
    @ManyToOne
    private FungsiPelaksanaKepengasuhan fungsiPelaksanaKepengasuhan;

    public FungsiPelaksanaKepengasuhan getFungsiPelaksanaKepengasuhan() {
        return fungsiPelaksanaKepengasuhan;
    }
    
    @ManyToOne
    private PersonalAttribute personalAttribute;

    public void setFungsiPelaksanaKepengasuhan(FungsiPelaksanaKepengasuhan fungsiPelaksanaKepengasuhan) {
        this.fungsiPelaksanaKepengasuhan = fungsiPelaksanaKepengasuhan;
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

    public PersonalAttribute getPersonalAttribute() {
        return personalAttribute;
    }

    public void setPersonalAttribute(PersonalAttribute personalAttribute) {
        this.personalAttribute = personalAttribute;
    }
    
}
