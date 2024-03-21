package es.uca.dss.ParkControl.core.Record;

import java.util.List;
import java.util.UUID;

public interface RecordRepository {
    void save(Record record);

    Record findById(UUID id);

    List<Record> findAll();

    void deleteById(UUID id);

    List<Record> findByVehicle(UUID vehicle);

    List<Record> findByParking(UUID parking);

}
