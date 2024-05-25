package es.uca.dss.ParkControl.core.ParkingManagement;

import es.uca.dss.ParkControl.core.Record.Record;
import es.uca.dss.ParkControl.core.Record.RecordRepository;
import es.uca.dss.ParkControl.core.Record.RecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing parking statistics.
 */
@Service
public class ParkingStatisticsManagementService {
    private RecordService recordService;

    /**
     * Constructor for the ParkingStatisticsManagementService.
     *
     * @param recordRepository the record repository
     */
    public ParkingStatisticsManagementService(RecordRepository recordRepository) {
        this.recordService = new RecordService(recordRepository);
    }

    /**
     * Method to get the entries statistic by day.
     *
     * @param parkingId the id of the parking
     * @param dayDate the date of the day
     * @return the entries statistic for the given day
     */
    public Optional<List<Record>> getEntriesStatisticByDay(UUID parkingId, LocalDateTime dayDate) {
        return Optional.of(recordService.getEntriesByDay(parkingId, dayDate));
    }

    /**
     * Method to get the exits statistic by day.
     *
     * @param parkingId the id of the parking
     * @param dayDate the date of the day
     * @return the exits statistic for the given day
     */
    public Optional<List<Record>> getExitsStatisticByDay(UUID parkingId, LocalDateTime dayDate) {
        return Optional.of(recordService.getExitsByDay(parkingId, dayDate));
    }

    /**
     * Method to get the entries statistic by month.
     *
     * @param parkingId the id of the parking
     * @param monthDate the date of the month
     * @return the entries statistic for the given month
     */
    public Optional<List<Record>> getEntriesStatisticByMonth(UUID parkingId, LocalDateTime monthDate) {
        return Optional.of(recordService.getEntriesByMonth(parkingId, monthDate));
    }

    /**
     * Method to get the exits statistic by month.
     *
     * @param parkingId the id of the parking
     * @param monthDate the date of the month
     * @return the exits statistic for the given month
     */
    public Optional<List<Record>> getExitsStatisticByMonth(UUID parkingId, LocalDateTime monthDate) {
        return Optional.of(recordService.getExitsByMonth(parkingId, monthDate));
    }
}