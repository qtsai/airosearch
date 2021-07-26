package nl.tsai.airosearch.country;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("integrationtest")
@SpringBootTest
public class CountrySearchRepositoryIntegrationTest {

    @Autowired
    private CountrySearchRepository countrySearchRepository;

    @Test
    void searchByTerm_whenExactCodeIsEntered_shouldBestMatchCountryCode() {
        List<Country> countries = countrySearchRepository.searchByTerm("US", 10, 0);

        assertThat(countries.isEmpty(), is(false));

        Country bestMatch = countries.get(0);
        assertThat(bestMatch.getName(), is("United States"));
    }

    @Test
    void searchByTerm_whenExactNameIsEntered_shouldBestMatchCountryName() {
        List<Country> countries = countrySearchRepository.searchByTerm("United States", 10, 0);

        assertThat(countries.isEmpty(), is(false));

        Country bestMatch = countries.get(0);
        assertThat(bestMatch.getName(), is("United States"));
    }

    @Test
    void searchByTerm_whenStartingPartIsEntered_shouldWildcardMatch() {
        List<Country> countries = countrySearchRepository.searchByTerm("zimb", 10, 0);

        assertThat(countries.isEmpty(), is(false));

        Country bestMatch = countries.get(0);
        assertThat(bestMatch.getName(), is("Zimbabwe"));
    }

    @Test
    void searchByTerm_whenTypoIsEntered_shouldFuzzyMatch() {
        List<Country> countries = countrySearchRepository.searchByTerm("zimbabde", 10, 0);

        assertThat(countries.isEmpty(), is(false));

        Country bestMatch = countries.get(0);
        assertThat(bestMatch.getName(), is("Zimbabwe"));
    }

    @Test
    void searchSingleByTerm_whenExactCode_shouldReturnExactCodeMatch() {
        List<Country> countries = countrySearchRepository.searchSingleByTerm("ZW");

        assertThat(countries.isEmpty(), is(false));

        Country bestMatch = countries.get(0);
        assertThat(bestMatch.getCode(), is("ZW"));
        assertThat(bestMatch.getName(), is("Zimbabwe"));
    }
}
