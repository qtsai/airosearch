package nl.tsai.airosearch.airport;

import nl.tsai.airosearch.country.Continent;
import nl.tsai.airosearch.country.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
public class AirportRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AirportRepository airportRepository;

    private Country country;

    @BeforeEach
    void init() {
        country = new Country(302611, "ZW", "Zimbabwe", Continent.AF, "https://en.wikipedia.org/wiki/Zimbabwe", "keyword");
        entityManager.persist(country);
    }

    @Test
    void findOneById_whenExists_shouldReturnAirport() {
        Integer id = new Random().nextInt();
        Airport airport = new Airport(id, "ident" + id, "type" + id, "name" + id, 0.0D, 0.0D, 0,
                Continent.AF, country, "region", "muni", true, "gps", "iata", "local",
                "homeLiink", "wiki", "words");
        entityManager.persist(airport);
        entityManager.flush();

        Airport result = airportRepository.findOneById(id);

        assertThat(result.getId(), is(id));
    }

    @Test
    void findOneById_whenNotExists_shouldReturnNull() {
        Airport result = airportRepository.findOneById(0);

        assertThat(result, is(nullValue()));
    }

    @Test
    void findAirportsByCountry_Code_whenExists_returnListOfAirports() {
        Integer id = new Random().nextInt();
        Airport airport = new Airport(id, "ident" + id, "type" + id, "name" + id, 0.0D, 0.0D, 0,
                Continent.AF, country, "region", "muni", true, "gps", "iata", "local",
                "homeLiink", "wiki", "words");
        entityManager.persist(airport);
        entityManager.flush();

        List<Airport> result = airportRepository.findAirportsByCountry_Code("ZW", PageRequest.of(0, 1));

        assertThat(result.size(), is(1));
        assertThat(result.get(0).getId(), is(id));

    }

    @Test
    void findAirportsByCountry_Code_whenNotExists_returnEmptyList() {
        List<Airport> result = airportRepository.findAirportsByCountry_Code("ZW", PageRequest.of(0, 1));

        assertThat(result.size(), is(0));
    }

}
