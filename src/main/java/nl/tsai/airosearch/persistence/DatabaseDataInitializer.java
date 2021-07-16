package nl.tsai.airosearch.persistence;

import lombok.extern.slf4j.Slf4j;
import nl.tsai.airosearch.airport.AirportCSVRecordsProcessor;
import nl.tsai.airosearch.airport.AirportRepository;
import nl.tsai.airosearch.country.CountryCSVRecordsProcessor;
import nl.tsai.airosearch.country.CountryRepository;
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

    public DatabaseDataInitializer(CountryRepository countryRepository, AirportRepository airportRepository, RunwayRepository runwayRepository) {
        this.countryRepository = countryRepository;
        this.airportRepository = airportRepository;
        this.runwayRepository = runwayRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        StopWatch sw = new StopWatch("DB init timer");
        sw.start("Initializing Database");
        log.info("Initializing database data");
        log.info("Initializing country data");
        initializeCountryData();
        log.info("Initializing airport data");
        initializeAirportData();
        log.info("Initializing runway data");
        initializeRunwayData();
        sw.stop();
        log.info("Finished with initializing database data in {} seconds", sw.getTotalTimeSeconds());
    }

    private void initializeCountryData() {
        ClassPathResource countryCSV = new ClassPathResource("db/countries.csv");
        readCSVFile(countryCSV, new CountryCSVRecordsProcessor(countryRepository));
    }

    private void initializeAirportData() {
        ClassPathResource airportCSV = new ClassPathResource("db/airports.csv");
        readCSVFile(airportCSV, new AirportCSVRecordsProcessor(airportRepository, countryRepository));
    }

    private void initializeRunwayData() {
        ClassPathResource runwayCSV = new ClassPathResource("db/runways.csv");
        readCSVFile(runwayCSV, new RunwayCSVRecordsProcessor(runwayRepository, airportRepository));
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
