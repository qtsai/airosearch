package nl.tsai.airosearch.airport;

import nl.tsai.airosearch.country.Continent;
import nl.tsai.airosearch.country.Country;
import nl.tsai.airosearch.country.CountrySearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirportServiceImplTest {

    @Mock
    private CountrySearchRepository countrySearchRepository;
    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportServiceImpl airportService;

    @Test
    void searchAirportsBy_whenCountryNotFound_returnEmptyPage() {
        String term = "YE";

        when(countrySearchRepository.searchSingleByTerm(term)).thenReturn(Collections.emptyList());

        AirportsPageResponse result = airportService.searchAirportsBy(term, 10, 0);

        assertThat(result.getAirports().size(), is(0));
        assertThat(result.getCurrent(), is(0));
        assertThat(result.getSize(), is(0));
        assertThat(result.getNumberOfPages(), is(0));
    }

    @Test
    void searchAirportsBy_whenNumberOfAirportsFoundByCodeLessThanPageSize_returnSinglePage() {
        String code = "YE";
        int pageSize = 10;
        int pageNumber = 0;

        Country country = mock(Country.class, RETURNS_DEEP_STUBS);
        when(country.getCode()).thenReturn(code);
        when(country.getAirports().size()).thenReturn(8);

        when(countrySearchRepository.searchSingleByTerm(code)).thenReturn(Collections.singletonList(country));
        when(airportRepository.findAirportsByCountry_Code(eq("YE"), any(PageRequest.class)))
                .thenReturn(airports(8, country));

        AirportsPageResponse result = airportService.searchAirportsBy(code, pageSize, pageNumber);

        assertThat(result.getAirports().size(), is(8));
        assertThat(result.getCurrent(), is(0));
        assertThat(result.getSize(), is(10));
        assertThat(result.getNumberOfPages(), is(1));
    }

    @Test
    void searchAirportsBy_whenNumberOfAirportsFoundByCodeEqualsPageSize_returnSinglePage() {
        String code = "YE";
        int pageSize = 10;
        int pageNumber = 0;

        Country country = mock(Country.class, RETURNS_DEEP_STUBS);
        when(country.getCode()).thenReturn(code);
        when(country.getAirports().size()).thenReturn(10);

        when(countrySearchRepository.searchSingleByTerm(code)).thenReturn(Collections.singletonList(country));
        when(airportRepository.findAirportsByCountry_Code(eq("YE"), any(PageRequest.class)))
                .thenReturn(airports(10, country));

        AirportsPageResponse result = airportService.searchAirportsBy(code, pageSize, pageNumber);

        assertThat(result.getAirports().size(), is(10));
        assertThat(result.getCurrent(), is(0));
        assertThat(result.getSize(), is(10));
        assertThat(result.getNumberOfPages(), is(1));
    }

    @Test
    void searchAirportsBy_whenNumberOfAirportsFoundByCodeGreaterThanPageSizeAndPageOne_returnFirstPage() {
        String code = "YE";
        int pageSize = 10;
        int pageNumber = 0;

        Country country = mock(Country.class, RETURNS_DEEP_STUBS);
        when(country.getCode()).thenReturn(code);
        when(country.getAirports().size()).thenReturn(15);

        when(countrySearchRepository.searchSingleByTerm(code)).thenReturn(Collections.singletonList(country));
        when(airportRepository.findAirportsByCountry_Code(eq(code), any(PageRequest.class)))
                .thenReturn(airports(10, country));

        AirportsPageResponse result = airportService.searchAirportsBy(code, pageSize, pageNumber);

        assertThat(result.getAirports().size(), is(10));
        assertThat(result.getCurrent(), is(0));
        assertThat(result.getSize(), is(10));
        assertThat(result.getNumberOfPages(), is(2));
    }

    @Test
    void searchAirportsBy_whenNumberOfAirportsFoundByCodeGreaterThanPageSizeAndPageOne_returnSecondPage() {
        String code = "YE";
        int pageSize = 10;
        int pageNumber = 1;

        Country country = mock(Country.class, RETURNS_DEEP_STUBS);
        when(country.getCode()).thenReturn(code);
        when(country.getAirports().size()).thenReturn(15);

        when(countrySearchRepository.searchSingleByTerm(code)).thenReturn(Collections.singletonList(country));
        when(airportRepository.findAirportsByCountry_Code(eq(code), any(PageRequest.class)))
                .thenReturn(airports(5, country));

        AirportsPageResponse result = airportService.searchAirportsBy(code, pageSize, pageNumber);

        assertThat(result.getAirports().size(), is(5));
        assertThat(result.getCurrent(), is(1));
        assertThat(result.getSize(), is(10));
        assertThat(result.getNumberOfPages(), is(2));
    }

    private List<Airport> airports(int count, Country country) {
        return IntStream.range(0, count)
                .mapToObj(id -> new Airport(id, "ident" + id, "type" + id, "name" + id, 0.0D, 0.0D, 0,
                        Continent.AF, country, "region", "muni", true, "gps", "iata", "local",
                        "homeLiink", "wiki", "words"))
                .collect(Collectors.toList());
    }
}
