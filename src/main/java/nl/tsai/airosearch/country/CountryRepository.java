package nl.tsai.airosearch.country;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    Country findByCode(String code);

    @Query(
            value = "SELECT c.*, count(*) as count " +
                    "FROM country c " +
                    "INNER JOIN airport a ON a.iso_country = c.code " +
                    "GROUP BY a.iso_country " +
                    "ORDER BY count DESC " +
                    "LIMIT 10",
            nativeQuery = true
    )
    List<Country> findTop10CountriesByAirportsCount();
}