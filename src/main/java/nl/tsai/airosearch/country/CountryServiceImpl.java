package nl.tsai.airosearch.country;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService{

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryDto> findTop10CountriesByAirportsCount() {
        List<Country> countries = countryRepository.findTop10CountriesByAirportsCount();
        return countries.stream().map(CountryDto::new).collect(Collectors.toList());
    }
}
