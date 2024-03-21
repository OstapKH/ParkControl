package es.uca.dss.ParkControl.core.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryRecordRepository implements RecordRepository{
    private List<Record> records;

    public InMemoryRecordRepository() {
        this.records = new ArrayList<>();
    }

    @Override
    public void save(Record record) {
        UUID id = record.getId();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId().equals(id)) {
                records.set(i, record);
                return;
            }
        }
        records.add(record);
    }

    @Override
    public Record findById(UUID id) {
        for (Record record : records) {
            if (record.getId().equals(id)) {
                return record;
            }
        }
        return null;
    }

    @Override
    public List<Record> findAll() {
        return records;
    }

    @Override
    public void deleteById(UUID id) {
        records.removeIf(record -> record.getId().equals(id));
    }

    @Override
    public List<Record> findByVehicle(UUID vehicle) {
        List<Record> vehicleRecords = new ArrayList<>();
        for (Record record : records) {
            if (record.getTicket().getVehicle().getId() == vehicle) {
                vehicleRecords.add(record);
            }
        }
        return vehicleRecords;
    }

    @Override
    public List<Record> findByParking(UUID parking) {
        List<Record> parkingRecords = new ArrayList<>();
        for (Record record : records) {
            if (record.getParking().getId() == parking) {
                parkingRecords.add(record);
            }
        }
        return parkingRecords;
    }
}
