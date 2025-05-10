/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.entity.FungsionalKepengasuhan;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "fungsionalKepengasuhanList")
@Dependent
public class FungsionalKepengasuhanList implements IValueList<FungsionalKepengasuhan> {

    @Override
    public List<FungsionalKepengasuhan> getFetchedItems() {
        return Arrays.asList(FungsionalKepengasuhan.values());
    }
    
}
