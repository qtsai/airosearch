package nl.tsai.airosearch.country;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/top-10-most-airports")
    public List<CountryDto> top10HighestAirportCount() {
        return countryService.findTop10CountriesByAirportsCount();
    }
}
