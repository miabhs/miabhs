/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Embeddable
public class AtributPembelajaran implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private String allowableValues;
    
    @Column(name = "ATRIBUT_ORDER", nullable = false, insertable = false, updatable = false)
    private Integer atributOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllowableValues() {
        return allowableValues;
    }

    public void setAllowableValues(String allowableValues) {
        this.allowableValues = allowableValues;
    }

    public Integer getAtributOrder() {
        return atributOrder;
    }

    public void setAtributOrder(Integer atributOrder) {
        this.atributOrder = atributOrder;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.name);
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
        final AtributPembelajaran other = (AtributPembelajaran) obj;
        return Objects.equals(this.name, other.name);
    }
    
}
