package nl.tsai.airosearch.runway;

import nl.tsai.airosearch.airport.AirportRepository;
import nl.tsai.airosearch.csv.CSVRecordsProcessor;
import nl.tsai.airosearch.csv.NumberUtils;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RunwayCSVRecordsProcessor extends CSVRecordsProcessor<Runway> {

    private final RunwayRepository runwayRepository;
    private final AirportRepository airportRepository;

    public RunwayCSVRecordsProcessor(RunwayRepository runwayRepository,
                                     AirportRepository airportRepository) {
        this.runwayRepository = runwayRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    protected Runway process(CSVRecord record) {
        return Runway.builder()
                .id(NumberUtils.createInteger(record.get("id")))
                .airport(airportRepository.findOneById(NumberUtils.createInteger(record.get("airport_ref"))))
                .length(NumberUtils.createInteger(record.get("length_ft")))
                .width(NumberUtils.createInteger(record.get("width_ft")))
                .surface(record.get("surface"))
                .lighted(Boolean.parseBoolean(record.get("lighted")))
                .closed(Boolean.parseBoolean(record.get("closed")))
                .lengthIdentifier(record.get("le_ident"))
                .lengthLatitude(NumberUtils.createDouble(record.get("le_latitude_deg")))
                .lengthLongitude(NumberUtils.createDouble(record.get("le_longitude_deg")))
                .lengthElevation(NumberUtils.createInteger(record.get("le_elevation_ft")))
                .lengthHeading(NumberUtils.createFloat(record.get("le_heading_degT")))
                .lengthDisplacedThreshold(NumberUtils.createInteger(record.get("le_displaced_threshold_ft")))
                .heightIdentifier(record.get("he_ident"))
                .heightLatitude(NumberUtils.createDouble(record.get("he_latitude_deg")))
                .heightLongitude(NumberUtils.createDouble(record.get("he_longitude_deg")))
                .heightElevation(NumberUtils.createInteger(record.get("he_elevation_ft")))
                .heightHeading(NumberUtils.createFloat(record.get("he_heading_degT")))
                .heightDisplacedThreshold(NumberUtils.createInteger(record.get("he_displaced_threshold_ft")))
                .build();
    }

    @Override
    protected void save(List<Runway> runways) {
        runwayRepository.saveAll(runways);
    }

}
