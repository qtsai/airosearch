package nl.tsai.airosearch.persistence;

import lombok.extern.slf4j.Slf4j;
import nl.tsai.airosearch.airport.AirportCSVRecordsProcessor;
import nl.tsai.airosearch.airport.AirportRepository;
import nl.tsai.airosearch.country.CountryCSVRecordsProcessor;
import nl.tsai.airosearch.country.CountryRepository;
import nl.tsai.airosearch.csv.CSVImport;
import nl.tsai.airosearch.csv.CSVImportHistoryRepository;
import nl.tsai.airosearch.csv.CSVRecordProcessor;
import nl.tsai.airosearch.runway.RunwayCSVRecordsProcessor;
import nl.tsai.airosearch.runway.RunwayRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class DatabaseDataInitializer {

    private final CountryRepository countryRepository;
    private final AirportRepository airportRepository;
    private final RunwayRepository runwayRepository;
    private final CSVImportHistoryRepository csvImportHistoryRepository;

    public DatabaseDataInitializer(CountryRepository countryRepository, AirportRepository airportRepository, RunwayRepository runwayRepository, CSVImportHistoryRepository csvImportHistoryRepository) {
        this.countryRepository = countryRepository;
        this.airportRepository = airportRepository;
        this.runwayRepository = runwayRepository;
        this.csvImportHistoryRepository = csvImportHistoryRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        StopWatch sw = new StopWatch("Database Initialization");
        log.info("Initializing Database data...");
        log.info("Initializing 'country' data...");
        sw.start("country");
        initializeCountryData();
        sw.stop();
        log.info("Completed initialization of 'country' data in {} ms", sw.getLastTaskTimeMillis());
        log.info("Initializing 'airport' data...");
        sw.start("airport");
        initializeAirportData();
        sw.stop();
        log.info("Completed initialization of 'airport' data in {} ms", sw.getLastTaskTimeMillis());
        log.info("Initializing 'runway' data...");
        sw.start("runway");
        initializeRunwayData();
        sw.stop();
        log.info("Completed initialization of 'runway' data in {} ms", sw.getLastTaskTimeMillis());
        log.info("Finished with initializing database data in {} ms", sw.getTotalTimeMillis());
    }

    private void initializeCountryData() {
        String path = "db/countries.csv";
        if (csvImportHistoryRepository.existsByPath(path)) {
            log.info("Skipping 'country' data, already initialized");
            return;
        }
        ClassPathResource countryCSV = new ClassPathResource(path);
        readCSVFile(countryCSV, new CountryCSVRecordsProcessor(countryRepository));
        csvImportHistoryRepository.save(new CSVImport(path));
    }

    private void initializeAirportData() {
        String path = "db/airports.csv";
        if (csvImportHistoryRepository.existsByPath(path)) {
            log.info("Skipping 'airport' data, already initialized");
            return;
        }
        ClassPathResource airportCSV = new ClassPathResource(path);
        readCSVFile(airportCSV, new AirportCSVRecordsProcessor(airportRepository, countryRepository));
        csvImportHistoryRepository.save(new CSVImport(path));
    }

    private void initializeRunwayData() {
        String path = "db/runways.csv";
        if (csvImportHistoryRepository.existsByPath(path)) {
            log.info("Skipping 'runway' data, already initialized");
            return;
        }
        ClassPathResource runwayCSV = new ClassPathResource(path);
        readCSVFile(runwayCSV, new RunwayCSVRecordsProcessor(runwayRepository, airportRepository));
        csvImportHistoryRepository.save(new CSVImport(path));
    }

    private void readCSVFile(Resource resource, CSVRecordProcessor processor) {
        try (
                final BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                final CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())
        ) {

            processor.processAll(csvParser.getRecords());

        } catch (IOException e) {
            log.warn("Failed to read records");
        }
    }
}
