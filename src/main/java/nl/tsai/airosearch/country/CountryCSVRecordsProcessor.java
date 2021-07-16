package nl.tsai.airosearch.country;

import nl.tsai.airosearch.csv.CSVRecordsProcessor;
import nl.tsai.airosearch.csv.NumberUtils;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountryCSVRecordsProcessor extends CSVRecordsProcessor<Country> {

    private final CountryRepository countryRepository;

    public CountryCSVRecordsProcessor(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    protected Country process(final CSVRecord record) {
        return Country.builder()
                .id(NumberUtils.createInteger(record.get("id")))
                .code(record.get("code"))
                .name(record.get("name"))
                .continent(Continent.valueOf(record.get("continent")))
                .wikipediaLink(record.get("wikipedia_link"))
                .keywords(record.get("keywords"))
                .build();
    }

    @Override
    protected void save(List<Country> countries) {
        countryRepository.saveAll(countries);
    }

}
