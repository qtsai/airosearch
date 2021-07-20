package nl.tsai.airosearch.airport;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends PagingAndSortingRepository<Airport, Integer> {
    Airport findOneById(Integer airportReference);

    List<Airport> findAirportsByCountry_Code(String code, Pageable pageable);
}
