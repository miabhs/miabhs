/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.LazyChooser.LazyChooserListener;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import org.primefaces.PrimeFaces;

/**
 *
 * @author aphasan
 */
@Named(value = "bentukAktifitasLazyChooser")
@Dependent
public class BentukAktifitasLazyChooser {

    @Inject
    private BentukAktifitasLazyList list;
    
    private String searchTerm;

    private BentukAktifitas choosenEntity;

    private List<String> updates;

    private LazyChooserListener<BentukAktifitas> listener;

    @PostConstruct
    public void init() {
        list.getFilter().setStaticFilter(() -> {
            return List.of(
                    FilterData.by("bentuk", searchTerm)
            );
        });
    }

    public void initContext(ActionEvent evt) {
        String upd = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("updates");
        this.updates = List.of(upd.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
    }

    public String getUpdates() {
        return updates.stream().collect(Collectors.joining(" "));
    }

    public void onSelect(AjaxBehaviorEvent evt) {
        if (listener != null) {
            listener.onSelect(choosenEntity);
            PrimeFaces.current().ajax().update(updates);
        }
    }

    public void setListener(LazyChooserListener<BentukAktifitas> listener) {
        this.listener = listener;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public BentukAktifitas getChoosenEntity() {
        return choosenEntity;
    }

    public void setChoosenEntity(BentukAktifitas choosenEntity) {
        this.choosenEntity = choosenEntity;
    }

    public BentukAktifitasLazyList getList() {
        return list;
    }
}
