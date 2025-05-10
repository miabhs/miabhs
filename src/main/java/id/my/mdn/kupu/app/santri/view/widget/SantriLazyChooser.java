/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.LazyChooser.LazyChooserListener;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.primefaces.PrimeFaces;

/**
 *
 * @author aphasan
 */
@Named(value = "santriLazyChooser")
@Dependent
public class SantriLazyChooser implements Serializable {

    @Inject
    private SantriLazyList list;

    private String searchTerm;

    private Santri choosenEntity;

    private List<String> updates;

    private LazyChooserListener<Santri> listener;

    @PostConstruct
    public void init() {
        list.getFilter().setStaticFilter(() -> {
            return List.of(FilterData.by("name", searchTerm)
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

    public void setListener(LazyChooserListener<Santri> listener) {
        this.listener = listener;
    }

    public SantriLazyList getList() {
        return list;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Santri getChoosenEntity() {
        return choosenEntity;
    }

    public void setChoosenEntity(Santri choosenEntity) {
        this.choosenEntity = choosenEntity;
    }
}
