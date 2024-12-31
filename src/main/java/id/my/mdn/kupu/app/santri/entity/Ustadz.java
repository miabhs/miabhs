/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.entity.PersonRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.ArrayList;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_USTADZ")
public class Ustadz extends PersonRole {
    
    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Ustadz> {

        public Builder() {
            super(new Ustadz());
        }

        public Builder withPerson(Person person) {
            if (person.getRoles() == null) {
                person.setRoles(new ArrayList<>());
            }
            entity.setParty(person);
            person.getRoles().add(entity);

            return this;
        }

    }
    
}
