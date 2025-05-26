/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.view;

import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.widget.Filter;
import id.my.mdn.kupu.core.reporting.model.ReportingJob;
import id.my.mdn.kupu.core.reporting.service.ReportingJobQueue;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public abstract class ReportingChildPage extends ChildPage {

    @Inject
    private ReportingJobQueue jobQueue;

    protected final Filter filter;

    @Override
    public Map<String, List<String>> getStates() {
        Map<String, List<String>> states = super.getStates();
        states.putAll(filter.getStates());
        return states;
    }

    public ReportingChildPage() {
        filter = new Filter(this::onFilter);
    }

    public void onFilter(Object obj) {
        prepareReport(null);
    }

    public Filter getFilter() {
        return filter;
    }

    protected boolean isReady() {
        return true;
    }

    protected abstract List<ReportingJob> prepareReportingJob();

    public void prepareReport(ActionEvent event) {

        jobQueue.setBusy(true);
        PrimeFaces.current().executeScript("PF('poll').start()");
        PrimeFaces.current().executeScript("PF('blocker').show()");

        if (!isReady()) {
            jobQueue.setBusy(false);
            return;
        }

        List<ReportingJob> preparedJobs = prepareReportingJob();

        for (ReportingJob preparedJob : preparedJobs) {
            jobQueue.put(preparedJob);
        }

    }

    public void pollForCompleteness() {
        if (!jobQueue.isBusy()) {
            PrimeFaces.current().executeScript("PF('poll').stop()");
            PrimeFaces.current().executeScript("PF('blocker').hide()");
        }
    }
}
