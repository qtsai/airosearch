package nl.tsai.airosearch.runway;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunwayRepository extends CrudRepository<Runway, Integer> {
}
