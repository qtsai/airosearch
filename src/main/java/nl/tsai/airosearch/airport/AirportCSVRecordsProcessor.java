package nl.tsai.airosearch.airport;

import nl.tsai.airosearch.country.Continent;
import nl.tsai.airosearch.country.CountryRepository;
import nl.tsai.airosearch.csv.CSVRecordsProcessor;
import nl.tsai.airosearch.csv.NumberUtils;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AirportCSVRecordsProcessor extends CSVRecordsProcessor<Airport> {

    private final AirportRepository airportRepository;
    private final CountryRepository countryRepository;

    public AirportCSVRecordsProcessor(AirportRepository airportRepository,
                                      CountryRepository countryRepository) {
        this.airportRepository = airportRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    protected Airport process(CSVRecord record) {
        return Airport.builder()
                .id(NumberUtils.createInteger(record.get("id")))
                .identifier(record.get("ident"))
                .type(record.get("type"))
                .name(record.get("name"))
                .latitude(NumberUtils.createDouble(record.get("latitude_deg")))
                .longitude(NumberUtils.createDouble(record.get("longitude_deg")))
                .elevation(NumberUtils.createInteger(record.get("elevation_ft")))
                .continent(Continent.valueOf(record.get("continent")))
                .country(countryRepository.findByCode(record.get("iso_country")))
                .region(record.get("iso_region"))
                .municipality(record.get("municipality"))
                .scheduledService(Boolean.parseBoolean(record.get("scheduled_service")))
                .gpsCode(record.get("gps_code"))
                .iataCode(record.get("iata_code"))
                .localCode(record.get("local_code"))
                .homeLink((record.get("home_link")))
                .wikipediaLink(record.get("wikipedia_link"))
                .keywords(record.get("keywords"))
                .build();
    }

    @Override
    protected void save(List<Airport> airports) {
        airportRepository.saveAll(airports);
    }
}
