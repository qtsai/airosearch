package nl.tsai.airosearch.country;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountrySearchRepository countrySearchRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    @Test
    void findTop10CountriesByAirportsCount() {
        List<Country> mockedCountries = countries(10);
        when(countryRepository.findTop10CountriesByAirportsCount())
                .thenReturn(mockedCountries);

        List<CountryDto> dtos = countryService.findTop10CountriesByAirportsCount();

        assertThat(dtos.size(), is(10));

    }

    @Test
    void searchCountriesBy_whenSearchIsEmpty_shouldReturnEmptyList() {
        String term = "YE";
        int pageSize = 10;
        int pageNumber = 0;

        List<Country> mockedCountries = countries(0);
        when(countrySearchRepository.searchByTerm(term, pageSize, pageNumber))
                .thenReturn(mockedCountries);

        List<CountryDto> dtos = countryService.searchCountriesBy(term, pageSize, pageNumber);

        assertThat(dtos.size(), is(0));
    }

    @Test
    void searchCountriesBy_whenSearchHasResults_shouldReturnSameSizeResult() {
        String term = "YE";
        int pageSize = 10;
        int pageNumber = 0;

        List<Country> mockedCountries = countries(10);
        when(countrySearchRepository.searchByTerm(term, pageSize, pageNumber))
                .thenReturn(mockedCountries);

        List<CountryDto> dtos = countryService.searchCountriesBy(term, pageSize, pageNumber);

        assertThat(dtos.size(), is(10));
    }

    private List<Country> countries(int count) {
        return IntStream.range(0, count)
                .mapToObj(id -> new Country())
                .collect(Collectors.toList());
    }
}