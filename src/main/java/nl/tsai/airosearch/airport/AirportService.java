package nl.tsai.airosearch.airport;

public interface AirportService {
    AirportsPageResponse searchAirportsBy(String term, int size, int page);
}
