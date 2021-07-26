package nl.tsai.airosearch.airport;

import nl.tsai.airosearch.country.Continent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AirportController.class)
public class AirportControllerTest {

    @MockBean
    private AirportService airportService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void countriesByCodeOrName_whenNoValidTerm_shouldReturnEmptyPageResponse() throws Exception {
        String term = "NOPE";
        when(airportService.searchAirportsBy(term, 10, 0)).thenReturn(
                AirportsPageResponse.empty());

        mockMvc.perform(get("/airports/search?term=" + term))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airports.length()", is(0)))
                .andExpect(jsonPath("$.current", is(0)))
                .andExpect(jsonPath("$.size", is(0)))
                .andExpect(jsonPath("$.numberOfPages", is(0)))
        ;
    }

    @Test
    void countriesByCodeOrName_whenValidSearchTerm_shouldReturnOkResponse() throws Exception {
        String term = "YE";

        AirportsPageResponse response = new AirportsPageResponse(airports(10), 0, 10, 1);
        when(airportService.searchAirportsBy(term, 10, 0)).thenReturn(response);

        mockMvc.perform(get("/airports/search?term=" + term))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airports.length()", is(10)))
                .andExpect(jsonPath("$.current", is(0)))
                .andExpect(jsonPath("$.size", is(10)))
                .andExpect(jsonPath("$.numberOfPages", is(1)))
        ;
    }

    @Test
    void countriesByCodeOrName_whenInvalidTerm_shouldReturnBadRequest() throws Exception {
        String term = "";
        mockMvc.perform(get("/airports/search?term=" + term))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("countriesByCodeOrName.term: must not be blank")))
                .andExpect(jsonPath("$.status", is(400)))
        ;
    }

    @Test
    void countriesByCodeOrName_whenInvalidSize_shouldReturnBadRequest() throws Exception {
        int size = 0;
        mockMvc.perform(get("/airports/search?term=YE&size=" + size))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("countriesByCodeOrName.size: must be greater than or equal to 1")))
                .andExpect(jsonPath("$.status", is(400)))
        ;
    }

    @Test
    void countriesByCodeOrName_whenInvalidPageNumber_shouldReturnBadRequest() throws Exception {
        int page = -1;
        mockMvc.perform(get("/airports/search?term=YE&page=" + page))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("countriesByCodeOrName.page: must be greater than or equal to 0")))
                .andExpect(jsonPath("$.status", is(400)))
        ;
    }

    private List<AirportDto> airports(int count) {
        return IntStream.range(0, count)
                .mapToObj(id -> new Airport(id, "ident" + id, "type" + id, "name" + id, 0.0D, 0.0D, 0,
                        Continent.AF, null, "region", "muni", true, "gps", "iata", "local",
                        "homeLiink", "wiki", "words"))
                .map(AirportDto::new)
                .collect(Collectors.toList());
    }
}
