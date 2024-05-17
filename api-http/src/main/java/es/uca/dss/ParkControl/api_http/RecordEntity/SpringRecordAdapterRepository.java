package es.uca.dss.ParkControl.api_http.RecordEntity;


import es.uca.dss.ParkControl.core.Record.Record;
import es.uca.dss.ParkControl.core.Record.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class SpringRecordAdapterRepository implements RecordRepository {
    private final SpringRecordJpaRepository repository;
    @Autowired
    public SpringRecordAdapterRepository(SpringRecordJpaRepository repository) {
        this.repository = repository;
    }


    @Override
    public void save(Record record) {
        repository.save(record);
    }

    @Override
    public Record findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Record> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Record> findByVehicle(UUID vehicle) {
        List<Record> allRecords = repository.findAll();
        List<Record> records = new ArrayList<>();
        for (Record record : allRecords) {
            if (record.getTicket().getVehicle().getId().equals(vehicle)) {
                records.add(record);
            }
        }
        return records;
    }

    @Override
    public List<Record> findByParking(UUID parking) {
        return repository.findByParkingId(parking);
    }
}
