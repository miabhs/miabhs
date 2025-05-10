/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.GenderType;
import id.my.mdn.kupu.core.party.entity.Organization;
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

    public KelompokPengasuhan() {
    }

    public KelompokPengasuhan(Long id, Organization organization) {
        setId(id);
        setOrganization(organization);
    }
    
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }
    
    final public void setOrganization(Organization organization) {
        setParty(organization);
    }
    
    final public Organization getOrganization() {
        return (Organization) getParty();
    }
    
}
