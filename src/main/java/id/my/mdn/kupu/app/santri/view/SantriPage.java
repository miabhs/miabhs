/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.view.admin.SantriDetailPage;
import id.my.mdn.kupu.app.santri.view.admin.SantriEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.SantriFilter;
import id.my.mdn.kupu.app.santri.view.widget.SantriList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.security.service.SecurityService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "santriPage")
@ViewScoped
public class SantriPage extends Page implements Serializable {

    @Inject
    @Bookmarked
    private SantriList dataView;
    
    @Inject
    private SecurityService securityService;
    
    @Override
    @PostConstruct
    public void init() {
        super.init();
        dataView.getFilter()
                .<SantriFilter>getContent()
                .setStatusKesantrian(StatusKesantrian.ACTIVE);
        dataView.getFilter().setFiltering(true);
    }
    
    public void createLogin(ActionEvent evt) {
        Santri santri = dataView.getSelection();
        String username = santri.getNis();
        securityService.createLogin(
                santri.getParty(), 
                username, 
                username, 
                "santri.Santri"
        );
    }
    
    public void removeLogin(ActionEvent evt) {
        Santri santri = dataView.getSelection();
        securityService.removeLogin(santri.getNis().toLowerCase());
    }

    @Creator(of = "dataView")
    public void openDataCreator() {
        gotoChild(SantriEditorPage.class)
                .open();
    }

    @Editor(of = "dataView")
    public void openDataEditor() {
        gotoChild(SantriDetailPage.class)
                .addParam("santri")
                .withValues(dataView.getSelections().get(0))
                .open();
    }

    @Deleter(of = "dataView")
    public void openDataDeleter() {
        dataView.deleteSelections();
    }    
    
    public void gotoPengasuhan(ActionEvent evt) {
        
//        Map<String, Object> attrs = evt.getComponent().getAttributes();
//        Pengasuhan pengasuhan = (Pengasuhan) attrs.get("pengasuhan");
//        
//        LocalDate now = LocalDate.now().minusDays(25);
//        LocalDate fromDate;
//        LocalDate thruDate;
//        int friday = 4;
//        int today = now.getDayOfWeek().ordinal();
//        if (today < friday) {
//            fromDate = now.minusDays(today + 3);
//            thruDate = now.plusDays(friday - today - 1);
//        } else if (today > friday) {
//            fromDate = now.minusDays(today - friday);
//            thruDate = now.plusDays(6 - today + 4);
//        } else {
//            fromDate = now;
//            thruDate = now.plusDays(6);
//        }
//        
//        gotoChild(PengasuhanPage.class)
//                .addParam("ss")
//                .withValues(pengasuhan)
//                .addParam("kelompokPengasuhan")
//                .withValues(pengasuhan.getKelompokPengasuhan())
//                .addParam("fromDate")
//                .withValues(EntityUtil.stringKeyFromLocalDate(fromDate, Constants.KEYFORMAT_LOCALDATE))
//                .addParam("thruDate")
//                .withValues(EntityUtil.stringKeyFromLocalDate(thruDate, Constants.KEYFORMAT_LOCALDATE))
//                .addParam("j")
//                .withValues("pokok")
//                .open();
    }

    public SantriList getDataView() {
        return dataView;
    }
    
}
