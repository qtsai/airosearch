package nl.tsai.airosearch.airport;

import nl.tsai.airosearch.country.Country;
import nl.tsai.airosearch.country.CountrySearchRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {

    private final CountrySearchRepository countrySearchRepository;
    private final AirportRepository airportRepository;

    public AirportServiceImpl(CountrySearchRepository countrySearchRepository,
                              AirportRepository airportRepository) {
        this.countrySearchRepository = countrySearchRepository;
        this.airportRepository = airportRepository;
    }

    public AirportsPageResponse searchAirportsBy(String term, int size, int page) {
        List<Country> countries = countrySearchRepository.searchSingleByTerm(term);
        if (countries.isEmpty()) return AirportsPageResponse.empty();
        Country country = countries.get(0);
        PageRequest pageRequest = PageRequest.of(page, size);
        String code = country.getCode();
        List<AirportDto> airports = airportRepository.findAirportsByCountry_Code(code, pageRequest).stream()
                .map(AirportDto::new)
                .collect(Collectors.toList());
        return new AirportsPageResponse(airports, page, size, (int) Math.ceil((double) country.getAirports().size() / size));
    }

}
