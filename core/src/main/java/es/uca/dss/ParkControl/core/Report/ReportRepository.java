package es.uca.dss.ParkControl.core.Report;

import java.util.List;
import java.util.UUID;

public interface ReportRepository {
    void save(Report report);

    Report findById(UUID id);

    List<Report> findAll();

    void deleteById(UUID id);
}
