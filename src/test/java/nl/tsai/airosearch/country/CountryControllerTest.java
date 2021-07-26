package nl.tsai.airosearch.country;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryController.class)
public class CountryControllerTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void countriesByCodeOrName_whenNoExistingNameOrCode_shouldReturnEmptyArrayResponse() throws Exception {
        String term = "NOPE";
        when(countryService.searchCountriesBy(term, 10, 0)).thenReturn(
                Collections.emptyList());

        mockMvc.perform(get("/countries/search?term=" + term))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
        ;
    }

    @Test
    void countriesByCodeOrName_whenValidSearchTerm_shouldReturnOkResponse() throws Exception {
        String term = "zimb";

        List<CountryDto> countries = countries(10);
        when(countryService.searchCountriesBy(term, 10, 0)).thenReturn(countries);

        mockMvc.perform(get("/countries/search?term=" + term))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
        ;
    }

    @Test
    void countriesByCodeOrName_whenInvalidTerm_shouldReturnBadRequest() throws Exception {
        String term = "";
        mockMvc.perform(get("/countries/search?term=" + term))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("countriesByCodeOrName.term: must not be blank")))
                .andExpect(jsonPath("$.status", is(400)))
        ;
    }

    @Test
    void countriesByCodeOrName_whenInvalidSize_shouldReturnBadRequest() throws Exception {
        int size = 0;
        mockMvc.perform(get("/countries/search?term=YE&size=" + size))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("countriesByCodeOrName.size: must be greater than or equal to 1")))
                .andExpect(jsonPath("$.status", is(400)))
        ;
    }

    @Test
    void countriesByCodeOrName_whenInvalidPageNumber_shouldReturnBadRequest() throws Exception {
        int page = -1;
        mockMvc.perform(get("/countries/search?term=YE&page=" + page))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("countriesByCodeOrName.page: must be greater than or equal to 0")))
                .andExpect(jsonPath("$.status", is(400)))
        ;
    }

    @Test
    void top10HighestAirportCount_shouldReturnStatusOk() throws Exception {
        List<CountryDto> countries = countries(10);
        when(countryService.findTop10CountriesByAirportsCount()).thenReturn(countries);

        mockMvc.perform(get("/countries/top-10-most-airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
        ;
    }

    private List<CountryDto> countries(int count) {
        return IntStream.range(0, count)
                .mapToObj(id ->
                        new Country(id, "code-" + id, "name-" + id, Continent.AS,
                                "wikilink", "keywords"))
                .map(CountryDto::new)
                .collect(Collectors.toList());
    }

}
