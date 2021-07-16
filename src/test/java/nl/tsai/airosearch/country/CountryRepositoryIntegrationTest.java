package nl.tsai.airosearch.country;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
class CountryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CountryRepository countryRepository;

//    @ParameterizedTest
//    @ValueSource(strings = {"Zimbabwe", "Zimba", "zimb", "babwe"})
//    void findByNameIgnoreCaseContaining_shouldReturnCountry(String countryName) {
//        Country yemen = new Country(302611, "ZW", "Zimbabwe", Continent.AF, "https://en.wikipedia.org/wiki/Zimbabwe", emptyList());
//        entityManager.persist(yemen);
//        entityManager.flush();
//
//        Country result = countryRepository.findByNameIgnoreCaseContaining(countryName);
//
//        assertThat(result.getName(), is("Zimbabwe"));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"YE", "ye"})
//    void findByCodeIgnoreCase_shouldReturnCountry(String countryCode) {
//        Country yemen = new Country(302671, "YE", "Yemen", Continent.AS, "https://en.wikipedia.org/wiki/Yemen", emptyList());
//        entityManager.persist(yemen);
//        entityManager.flush();
//
//        Country result = countryRepository.findByCodeIgnoreCase(countryCode);
//
//        assertThat(result.getName(), is("Yemen"));
//    }

}
