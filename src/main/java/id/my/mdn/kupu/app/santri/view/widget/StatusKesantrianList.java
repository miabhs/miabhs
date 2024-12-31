/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import java.util.Arrays;
import java.util.List;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "statusKesantrianList")
@Dependent
public class StatusKesantrianList implements IValueList<StatusKesantrian> {

    @Override
    public List<StatusKesantrian> getFetchedItems() {
        return Arrays.asList(StatusKesantrian.values());
    }

    
    
}
