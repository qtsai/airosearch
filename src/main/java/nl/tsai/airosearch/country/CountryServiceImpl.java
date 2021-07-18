package nl.tsai.airosearch.country;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountrySearchRepository countrySearchRepository;

    public CountryServiceImpl(CountryRepository countryRepository,
                              CountrySearchRepository countrySearchRepository) {
        this.countryRepository = countryRepository;
        this.countrySearchRepository = countrySearchRepository;
    }

    @Override
    public List<CountryDto> findTop10CountriesByAirportsCount() {
        List<Country> countries = countryRepository.findTop10CountriesByAirportsCount();
        return countries.stream().map(CountryDto::new).collect(Collectors.toList());
    }

    @Override
    public List<CountryDto> searchCountriesBy(String term, int size, int page) {
        List<Country> countries = countrySearchRepository.searchByTerm(term, size, page);
        return countries.stream().map(CountryDto::new).collect(Collectors.toList());
    }

}
