package nl.tsai.airosearch.country;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

    Country findByNameIgnoreCaseContaining(String name);

    Country findByCodeIgnoreCase(String code);

}
