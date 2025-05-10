/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.entity.JenisPeriodePembelajaran;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named("jenisPeriodePembelajaranList")
@Dependent
public class JenisPeriodePembelajaranList implements IValueList<JenisPeriodePembelajaran> {

    @Override
    public List<JenisPeriodePembelajaran> getFetchedItems() {
        return Arrays.asList(JenisPeriodePembelajaran.values());
    }
    
}
