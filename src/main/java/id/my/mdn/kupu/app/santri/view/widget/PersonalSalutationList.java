/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.entity.PersonalSalutation;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "personalSalutationList")
@Dependent
public class PersonalSalutationList implements IValueList<PersonalSalutation> {

    @Override
    public List<PersonalSalutation> getFetchedItems() {
        return Arrays.asList(PersonalSalutation.values());
    }
    
}
