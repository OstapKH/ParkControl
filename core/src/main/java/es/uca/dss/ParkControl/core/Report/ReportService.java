package es.uca.dss.ParkControl.core.Report;

import java.util.List;
import java.util.UUID;
public class ReportService {

    private  ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void createReport(Report report) {
        reportRepository.save(report);
    }

    public Report getReport(UUID id) {
        return reportRepository.findById(id);
    }

    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }

    public void deleteReport(UUID id){
        reportRepository.deleteById(id);
    }
}
