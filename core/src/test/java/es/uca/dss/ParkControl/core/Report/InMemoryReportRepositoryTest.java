package es.uca.dss.ParkControl.core.Report;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class InMemoryReportRepositoryTest {
    private InMemoryReportRepository inMemoryReportRepository;
    private Report report;
    @Before
    public void SetUp(){
        inMemoryReportRepository = new InMemoryReportRepository();
        report = new Report();
        report.setId(UUID.randomUUID());
    }

    @Test
    public void testSave(){
        inMemoryReportRepository.save(report);
        List<Report> reports = inMemoryReportRepository.findAll();
        assertEquals(1,reports.size());
        Report retrievedReport = inMemoryReportRepository.findById(report.getId());
        assertEquals(retrievedReport,report);
    }

    @Test
    public void testFindById(){
        inMemoryReportRepository.save(report);
        Report retrievedReport = inMemoryReportRepository.findById(report.getId());
        assertEquals(retrievedReport,report);
    }

    @Test
    public void testFindAll(){
        List<Report> reports = inMemoryReportRepository.findAll();
        assertNotNull(reports);
        assertEquals(0,reports.size());
    }

    @Test
    public void testDeleteById(){
        inMemoryReportRepository.save(report);
        inMemoryReportRepository.deleteById(report.getId());
        List<Report> reports = inMemoryReportRepository.findAll();
        assertEquals(0,reports.size());
    }
}
