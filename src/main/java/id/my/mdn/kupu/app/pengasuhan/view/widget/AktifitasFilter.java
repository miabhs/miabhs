/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */

import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import java.io.Serializable;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

@Named(value = "aktifitasFilter")
@Dependent
public class AktifitasFilter extends FilterContent implements Serializable {    
    
    @Bookmark
    private Santri santri;

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }
}
