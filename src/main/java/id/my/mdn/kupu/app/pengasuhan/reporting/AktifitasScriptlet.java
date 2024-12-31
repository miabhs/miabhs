/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.core.base.util.Constants;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class AktifitasScriptlet extends JRDefaultScriptlet {
    
    private DateTimeFormatter dateFormatter;

    @Override
    public void beforeReportInit() throws JRScriptletException {
        dateFormatter = DateTimeFormatter.ofPattern(Constants.FORMAT_LOCALDATE);
    }

    @Override
    public void beforeDetailEval() throws JRScriptletException {
        LocalDate activityDate = (LocalDate) getFieldValue("activityDate");
        String formattedActivityDate = dateFormatter.format(activityDate);
        setVariableValue("activityDate", formattedActivityDate);
        
    }
    
}
