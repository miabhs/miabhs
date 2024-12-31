/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.ArrayList;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_PENGASUHAN")
@NamedQueries({
    @NamedQuery(name = "Pengasuhan.getUstadz", 
            query = "SELECT p.fromRole "
                    + "FROM Pengasuhan p "
                    + "WHERE p.toRole = :kelompokPengasuhan "
                    + "AND TYPE(p.fromRole) = Ustadz"),
    @NamedQuery(name = "Pengasuhan.getMusyrif", 
            query = "SELECT p.fromRole "
                    + "FROM Pengasuhan p "
                    + "WHERE p.toRole = :kelompokPengasuhan "
                    + "AND TYPE(p.fromRole) = Musyrif")
})
public class Pengasuhan extends PartyRelationship {

    private static final long serialVersionUID = 1L;
    
    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Pengasuhan> {

        public Builder() {
            super(new Pengasuhan());
        }

        public Builder from(KelompokPengasuhan kelompokPengasuhan) {
            entity.setFromRole(kelompokPengasuhan);
            if (kelompokPengasuhan.getSourceRelationships() == null) {
                kelompokPengasuhan.setSourceRelationships(new ArrayList<>());
            }
            kelompokPengasuhan.getSourceRelationships().add(entity);

            return this;
        }

        public Builder to(Santri santri) {
            entity.setToRole(santri);
            if (santri.getTargetRelationships() == null) {
                santri.setTargetRelationships(new ArrayList<>());
            }
            santri.getTargetRelationships().add(entity);

            return this;
        }

    }
    
    private boolean koordinator;
    
    public KelompokPengasuhan getKelompokPengasuhan() {
        return (KelompokPengasuhan) getFromRole();
    }
    
    public void setKelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        setFromRole(kelompokPengasuhan);
    }

    public Santri getSantri() {
        return (Santri) getToRole();
    }

    public void setSantri(Santri santri) {
        setToRole(santri);
    }

    public boolean isKoordinator() {
        return koordinator;
    }

    public void setKoordinator(boolean koordinator) {
        this.koordinator = koordinator;
    }

}
