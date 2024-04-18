package es.uca.dss.ParkControl.core.Record;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<Record> getEntriesByDay(UUID parkingId, LocalDateTime dayDate){
        List<Record> recordList = new ArrayList<>();
        List<Record> recordsByParking = recordRepository.findByParking(parkingId);
        for (Record record : recordsByParking){
            if (record.getDateOfEntry().isAfter(dayDate) && record.getDateOfEntry().isBefore(dayDate.plusDays(1))){
                recordList.add(record);
            }
        }
        return recordList;
    }

    public List<Record> getExitsByDay(UUID parkingId, LocalDateTime dayDate){
        List<Record> recordList = new ArrayList<>();
        List<Record> recordsByParking = recordRepository.findByParking(parkingId);
        for (Record record : recordsByParking){
            if (record.getDateOfExit().isAfter(dayDate) && record.getDateOfExit().isBefore(dayDate.plusDays(1))){
                recordList.add(record);
            }
        }
        return recordList;
    }


    public List<Record> getEntriesByMonth(UUID parkingId, LocalDateTime monthDate){
        List<Record> recordList = new ArrayList<>();
        List<Record> recordsByParking = recordRepository.findByParking(parkingId);
        for (Record record : recordsByParking){
            if (record.getDateOfEntry().isAfter(monthDate) && record.getDateOfEntry().isBefore(monthDate.plusDays(1))){
                recordList.add(record);
            }
        }
        return recordList;
    }

    public List<Record> getExitsByMonth(UUID parkingId, LocalDateTime monthDate){
        List<Record> recordList = new ArrayList<>();
        List<Record> recordsByParking = recordRepository.findByParking(parkingId);
        for (Record record : recordsByParking){
            if (record.getDateOfExit().isAfter(monthDate) && record.getDateOfExit().isBefore(monthDate.plusMonths(1))){
                recordList.add(record);
            }
        }
        return recordList;
    }

}
