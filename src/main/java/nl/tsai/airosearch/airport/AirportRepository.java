package nl.tsai.airosearch.airport;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends CrudRepository<Airport, Integer> {
    Airport findOneById(Integer airportReference);
}
