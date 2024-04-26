package es.uca.dss.ParkControl.core.Report;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
public class ReportTest {
    private Report report;

    @Before
    public void SetUp(){
        report = new Report();
        report.setId(UUID.randomUUID());
        report.setReportType(ReportType.INCOME);
        report.setStartDate(LocalDate.of(2024,3,2));
        report.setEndDate(LocalDate.of(2024,3,4));
    }

    @Test
    public void testSetterAndGetters(){
        assertEquals(report.getId(),report.getId());
        assertEquals(ReportType.INCOME,report.getReportType());
        assertEquals(LocalDate.of(2024,3,2),report.getStartDate());
        assertEquals(LocalDate.of(2024,3,4),report.getEndDate());
    }
}
