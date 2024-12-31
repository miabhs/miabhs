/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_KELOMPOKPENGASUHAN")
public class KelompokPengasuhan extends PartyRole {
    
    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<KelompokPengasuhan> {

        public Builder() {
            super(new KelompokPengasuhan());
        }

        public Builder withOrganization(Organization organization) {
            if (organization.getRoles() == null) {
                organization.setRoles(new ArrayList<>());
            }
            entity.setParty(organization);
            organization.getRoles().add(entity);

            return this;
        }

    }
    
    @Transient
    private Pengasuhan pengasuhan;

    public Pengasuhan getPengasuhan() {
        return pengasuhan;
    }

    public KelompokPengasuhan() {
    }

    public KelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan, Pengasuhan pengasuhan) {
        this.setId(kelompokPengasuhan.getId());
        this.setParty(kelompokPengasuhan.getParty());
        this.pengasuhan = pengasuhan;
    }

    public KelompokPengasuhan(Long id, Organization organization) {
        setId(id);
        setOrganization(organization);
    }
    
    final public void setOrganization(Organization organization) {
        setParty(organization);
    }
    
    final public Organization getOrganization() {
        return (Organization) getParty();
    }
    
}
