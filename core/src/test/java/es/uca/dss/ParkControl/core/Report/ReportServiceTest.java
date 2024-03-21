package es.uca.dss.ParkControl.core.Report;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
public class ReportServiceTest {
    private ReportService reportService;
    private ReportRepository reportRepository;
    private Report report;

    @Before
    public void setUp(){

        reportRepository = new InMemoryReportRepository();
        reportService = new ReportService(reportRepository);

        report = new Report();
        report.setId(UUID.randomUUID());
    }

    @Test
    public void testCreateReport(){
        reportService.createReport(report);
        List<Report> reports = reportRepository.findAll();
        assertEquals(1,reports.size());

        Report retrievedReport = reportRepository.findById(report.getId());
        assertEquals(retrievedReport,report);
    }

    @Test
    public void testGetReport(){
        reportService.createReport(report);
        Report retrievedreport = reportService.getReport(report.getId());
        assertEquals(retrievedreport,report);
    }

    @Test
    public void testGetAllReports(){
        List<Report> reports = reportService.getAllReports();
        assertNotNull(reports); //Entendemos null como no crear la lista
        assertEquals(0,reports.size());
    }

    @Test
    public void testDeleteReport(){
        reportService.createReport(report);
        reportService.deleteReport(report.getId());
        List<Report> reports = reportService.getAllReports();
        assertEquals(0,reports.size());
    }

}
