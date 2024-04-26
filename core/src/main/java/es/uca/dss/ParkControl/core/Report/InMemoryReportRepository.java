package es.uca.dss.ParkControl.core.Report;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryReportRepository implements ReportRepository{
    private List<Report> reports;

    public InMemoryReportRepository() {
        this.reports = new ArrayList<>();
    }

    @Override
    public void save(Report report) {
        UUID id = report.getId();
        for (int i = 0; i < reports.size(); i++) {
            if (reports.get(i).getId().equals(id)) {
                reports.set(i, report);
                return;
            }
        }
        reports.add(report);
    }
    @Override
    public Report findById(UUID id) {
        for (Report report : reports) {
            if (report.getId().equals(id)) {
                return report;
            }
        }
        return null;
    }

    @Override
    public List<Report> findAll() {
        return reports;
    }
    @Override
    public void deleteById(UUID id) {
        reports.removeIf(report -> report.getId().equals(id));
    }
}
