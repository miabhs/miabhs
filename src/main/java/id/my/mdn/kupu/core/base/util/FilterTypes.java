/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public final class FilterTypes {

    @FunctionalInterface
    public static interface FilterListener {

        void onFilter(Object obj);
    }

    @FunctionalInterface
    public static interface StaticFilter {

        List<FilterData> get();

        default StaticFilter plus(final StaticFilter other) {
            if (other == null) {
                return this;
            }
            return () -> {
                List<FilterData> combinedFilter = new ArrayList<>();
                combinedFilter.addAll(this.get());
                List<FilterData> otherFilterData = other.get();
                if (otherFilterData != null) {
                    combinedFilter.addAll(otherFilterData);
                }
                return combinedFilter;
            };
        }
    }

    public static final class FilterData {

        public static FilterData by(String name, Object value) {
            return new FilterData(name, value);
        }

        public final String name;
        public final Object value;

        public FilterData(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
}
