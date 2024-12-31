/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public enum Status {
    
    HIJAU,
    KUNING,
    MERAH;
    
    public String getName() {
        return StringUtils.capitalize(name().toLowerCase());
    }

    @Override
    public String toString() {
        return name();
    }
}
