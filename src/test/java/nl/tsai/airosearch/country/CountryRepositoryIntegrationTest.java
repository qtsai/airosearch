package nl.tsai.airosearch.country;

import nl.tsai.airosearch.airport.Airport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

@DataJpaTest
class CountryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CountryRepository countryRepository;


    @Test
    void findByCode_whenRowExists_shouldReturnCountry() {
        Country yemen = new Country(302611, "ZW", "Zimbabwe", Continent.AF, "https://en.wikipedia.org/wiki/Zimbabwe", "keyword");
        entityManager.persist(yemen);
        entityManager.flush();

        Country result = countryRepository.findByCode("ZW");

        assertThat(result.getName(), is("Zimbabwe"));
    }

    @Test
    void findByCode_whenNotExist_shouldReturnNull() {
        Country result = countryRepository.findByCode("ZW");

        assertThat(result, is(nullValue()));
    }

    @Test
    void findTop10CountriesByAirportsCount_shouldReturnOrderedByNumberOfAirports() {
        Country yemen = new Country(302611, "ZW", "Zimbabwe", Continent.AF, "https://en.wikipedia.org/wiki/Zimbabwe", "keyword");
        entityManager.persist(yemen);
        persistAirports(5, yemen);
        Country iran = new Country(302612, "IR", "Iran", Continent.AS, "tt", "keyword");
        entityManager.persist(iran);
        persistAirports(3, iran);
        Country iraq = new Country(302613, "RQ", "Iraq", Continent.AS, "tt", "keyword");
        entityManager.persist(iraq);
        persistAirports(6, iraq);

        entityManager.flush();

        List<Country> result = countryRepository.findTop10CountriesByAirportsCount();

        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("Iraq"));
        assertThat(result.get(1).getName(), is("Zimbabwe"));
        assertThat(result.get(2).getName(), is("Iran"));
    }

    private void persistAirports(int count, Country country) {
        for (int i = 0; i < count; i++) {
            Integer id = new Random().nextInt();
            Airport airport = new Airport(id, "ident" + id, "type" + id, "name" + id, 0.0D, 0.0D, 0,
                    Continent.AF, country, "region", "muni", true, "gps", "iata", "local",
                    "homeLiink", "wiki", "words");
            entityManager.persist(airport);
        }
    }

}
