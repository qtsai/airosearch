package nl.tsai.airosearch.airport;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class AirportsPageResponse {

    private final List<AirportDto> airports;
    private final int current;
    private final int size;
    private final int numberOfPages;

    public AirportsPageResponse(List<AirportDto> airports, int current, int size, int numberOfPages) {
        this.airports = airports;
        this.current = current;
        this.size = size;
        this.numberOfPages = numberOfPages;
    }

    public static AirportsPageResponse empty() {
        return new AirportsPageResponse(Collections.emptyList(), 0, 0, 0);
    }
}
