package es.uca.dss.ParkControl.api_http.RecordEntity;

import es.uca.dss.ParkControl.core.Record.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface SpringRecordJpaRepository extends JpaRepository<Record, UUID> {
    List<Record> findByParkingId(UUID parkingId);
}
