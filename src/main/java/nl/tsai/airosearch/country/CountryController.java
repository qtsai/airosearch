package nl.tsai.airosearch.country;

import nl.tsai.airosearch.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/countries")
@Validated
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }


    @GetMapping("/search")
    public List<CountryDto> countriesByCodeOrName(@RequestParam @NotBlank String term,
                                                  @RequestParam(defaultValue = "10") @Min(1) int size,
                                                  @RequestParam(defaultValue = "1") @Min(1) int page) {
        return countryService.searchCountriesBy(term, size, page);
    }

    @GetMapping("/top-10-most-airports")
    public List<CountryDto> top10HighestAirportCount() {
        return countryService.findTop10CountriesByAirportsCount();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(ConstraintViolationException e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}
