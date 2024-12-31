/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.view.admin.BentukAktifitasEditorPage;
import id.my.mdn.kupu.app.pengasuhan.view.widget.BentukAktifitasList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
@Named(value = "bentukAktifitasPage")
@ViewScoped
public class BentukAktifitasPage extends Page implements Serializable {
    
    @Inject
    @Bookmarked
    private BentukAktifitasList dataView;    
    
    @Override
    @PostConstruct
    public void init() {
        super.init();
    }
    
    @Creator(of = "dataView")
    public void openBentukAktifitasCreator() {
        gotoChild(BentukAktifitasEditorPage.class).open();
    } 
    
    @Editor(of = "dataView")
    public void openBentukAktifitasEditor() {
        gotoChild(BentukAktifitasEditorPage.class)
                .addParam("entity")
                .withValues(dataView.getSelections().get(0))
                .open();
    }  
    
    @Deleter(of = "dataView")
    public void openBentukAktifitasDeleter() {
        dataView.deleteSelections();
    } 

    public BentukAktifitasList getDataView() {
        return dataView;
    }
    
}
