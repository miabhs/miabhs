/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

/**
 *
 * @author aphasan
 */
public class KppHalaqoh {
    
    private final Integer kppNum;
    
    private final String name;

    public KppHalaqoh(Integer kppNum, String firstName, String lastName) {
        this.kppNum = kppNum;

        this.name = ((firstName != null && !firstName.isBlank()) ? " " + firstName : "")
                + ((lastName != null && !lastName.isBlank()) ? " " + lastName : "");
    }

    public Integer getKppNum() {
        return kppNum;
    }

    public String getName() {
        return "Ust. " + name;
    }
    
}
