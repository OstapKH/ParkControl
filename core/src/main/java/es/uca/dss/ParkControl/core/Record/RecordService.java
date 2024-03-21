package es.uca.dss.ParkControl.core.Record;

import java.util.List;
import java.util.UUID;

public class RecordService {
    private RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public void createRecord(Record record) {
        recordRepository.save(record);
    }

    public Record getRecord(UUID id) {
        return recordRepository.findById(id);
    }

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public void deleteRecord(UUID id) {
        recordRepository.deleteById(id);
    }

    public List<Record> findByVehicle(UUID vehicle) {
        return recordRepository.findByVehicle(vehicle);
    }

    public List<Record> findByParking(UUID parking) {
        return recordRepository.findByParking(parking);
    }
}
