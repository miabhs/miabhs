/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author aphasan
 */
@Named(value = "kelompokPengasuhanFilter")
@Dependent
public class KelompokPengasuhanFilter extends FilterContent implements Serializable {
    
    @Bookmark(name = "nm")
    private String name; 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
