/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.entity.JenisSantri;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "jenisSantriList")
@Dependent
public class JenisSantriList implements IValueList<JenisSantri> {

    @Override
    public List<JenisSantri> getFetchedItems() {
        return Arrays.asList(JenisSantri.values());
    }
    
    
}
