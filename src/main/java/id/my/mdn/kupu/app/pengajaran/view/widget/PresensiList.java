/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.widget;

import id.my.mdn.kupu.app.pengajaran.entity.Presensi;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "presensiList")
@Dependent
public class PresensiList implements IValueList<Presensi>{

    @Override
    public List<Presensi> getFetchedItems() {
        return Arrays.asList(Presensi.values());
    }
    
}
