package nl.tsai.airosearch.country;

import java.util.List;

public interface CountryService {
    List<CountryDto> findTop10CountriesByAirportsCount();

    List<CountryDto> searchCountriesBy(String term, int size, int page);
}
