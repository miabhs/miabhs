/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.widget;

import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterListener;
import id.my.mdn.kupu.core.base.view.util.ConverterUtil;
import id.my.mdn.kupu.core.base.view.widget.IValueList.Parameters;
import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import id.my.mdn.kupu.core.base.view.widget.Selector.SelectionModeSelector;
import id.my.mdn.kupu.core.base.view.widget.Selector.SelectorListener;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.PhaseId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 * @param <E>
 */
public abstract class AbstractValueList<E>
        implements IValueList<E>, IBookmarkable, ISelectable<E>, IFilterable {

    @FunctionalInterface
    public static interface DefaultList<E> {
        List<E> get();
    }

    protected List<E> fetchedItems;

    protected boolean valid = false;

    protected boolean cached = false;

    protected Selector<E> selector;

    protected Parameters parameters;

    protected Parameters hiddenParameters;

    protected String name = "dataTbl";

    protected DefaultList<E> defaultList = () -> null;

    protected DefaultChecker defaultChecker = null;

    protected final Filter filter;
    
    private final Sorter<E> sorter;

    public AbstractValueList(Class<E> entityClass) {
        this.hiddenParameters = () -> new HashMap<String, Object>();
        this.parameters = () -> new HashMap<String, Object>();

        this.selector = new Selector<>();
        this.selector.setName(name);

        this.filter = new Filter(this::onFilter);
        
        this.sorter = new Sorter(entityClass, this::onSort);
    }

    protected abstract List<E> getFetchedItemsInternal(
            Map<String, Object> parameters,
            List<FilterData> filters,
            List<SorterData> sorters,
            DefaultList<E> defaultList,
            DefaultChecker defaultChecker
    );

    @Override
    public List<E> getFetchedItems() {
        if (!isCached()) {
            return getFetchedItemsInternal(
                    getParameters(),
                    filter.getValues(),
                    getSorters(),
                    defaultList,
                    defaultChecker);
        } else {
            if (!isValid()) {
                fetchedItems = getFetchedItemsInternal(
                        getParameters(),
                        filter.getValues(),
                        getSorters(),
                        defaultList,
                        defaultChecker);
                validate();
            }

            return fetchedItems;
        }
    }

    public Sorter getSorter() {
        return sorter;
    }

    public final List<SorterData> getSorters() {
        return sorter.getListSorterData();
    }

    public void validate() {
        valid = true;
    }

    public void invalidate() {
        valid = false;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    @Override
    public void setSelection(E selection) {
        PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
        if (phaseId.equals(PhaseId.APPLY_REQUEST_VALUES)) {
            selector.setSelection(selection);
        }
    }

    public void setSelectionInternal(E selection) {
        selector.setSelectionInternal(selection);
    }

    @Override
    public void setSelections(List<E> selections) {
        PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
        if (phaseId.equals(PhaseId.APPLY_REQUEST_VALUES)) {
            selector.setSelections(selections);
        }
    }

    public void setSelectionsInternal(List<E> selections) {
        selector.setSelectionsInternal(selections);
    }

    @Override
    public E getSelection() {
        return selector.getSelection();
    }

    @Override
    public List<E> getSelections() {
        return selector.getSelections();
    }

    public List<E> getSelectionsInternal() {
        return selector.getSelectionsInternal();
    }

    public E getSelectionInternal() {
        return selector.getSelectionInternal();
    }

    @Override
    public void addSelectListener(SelectorListener listener) {
        selector.addListener(listener);
    }

    public void setSelectionMode(SelectionModeSelector mode) {
        selector.setSelectionMode(mode);
    }

    public String getSelectionMode() {
        return selector.getSelectionMode();
    }

    public void reset(ActionEvent evt) {
        reset();
    }

    public void reset() {
        resetInternal();
        invalidate();
    }

    protected void resetInternal() {
        if (getSelectionMode().equals(SINGLE)) {
            selector.setSelectionInternal(null);
        } else {
            selector.setSelectionsInternal(null);
        }
    }

    @Override
    public void addFilterListener(FilterListener filterListener) {
        filter.addListener(filterListener);
    }

    public void onFilter(Object obj) {
        doFilter();
    }

    @Override
    public void doFilter() {
        reset();
    }

    @Override
    public void clearFilter() {
        filter.clear();
    }

    public void clearFilter(ActionEvent evt) {
        clearFilter();
    }

    public void onSort(Object obj) {
        doSort();
    }

    public void doSort() {
        invalidate();
    }

    public void setParameter(String name, Object value) {
        parameters.get().put(name, value);
    }

    public void setHiddenParameter(String name, Object value) {
        hiddenParameters.get().put(name, value);
    }

    @Override
    public Map<String, List<String>> getStates() {

        Map<String, List<String>> states = new HashMap<>();

        parameters.get().entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .forEach(entry -> {
                    Object value = entry.getValue();
                    Converter converter = ConverterUtil.findConverter(CDI.current(), value.getClass());
                    List<String> stateValue = null;
                    if (converter != null) {
                        stateValue = Arrays.asList(converter.getAsString(null, null, value));
                    } else {
                        stateValue = Arrays.asList(String.valueOf(value));
                    }
                    states.put(entry.getKey(), stateValue);

                });

        states.putAll(selector.getStates());

        states.putAll(filter.getStates());
        
        states.putAll(sorter.getStates());

        return states;
    }

    public void onRemoveSelection(UnselectEvent evt) {
        onRemoveSelection((E) evt.getObject());
    }

    protected void onRemoveSelection(E e) {
        selector.removeSelection(e);
    }

    public Filter getFilter() {
        return filter;
    }

    public Selector<E> getSelector() {
        return selector;
    }

    public void setSelector(Selector<E> selector) {
        this.selector = selector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.selector.setName(name);
    }

    public void setDefaultList(DefaultList<E> defaultList) {
        this.defaultList = defaultList;
    }

    public void setDefaultChecker(DefaultChecker defaultChecker) {
        this.defaultChecker = defaultChecker;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public void setHiddenParameters(Parameters hiddenParameters) {
        this.hiddenParameters = hiddenParameters;
    }

    public void setFetchedItems(List<E> fetchedItems) {
        this.fetchedItems = fetchedItems;
    }

    public Map<String, Object> getParameters() {
        Map<String, Object> allParameters = new HashMap<>();
        allParameters.putAll(parameters.get());
        allParameters.putAll(hiddenParameters.get());

        return allParameters;
    }

}
