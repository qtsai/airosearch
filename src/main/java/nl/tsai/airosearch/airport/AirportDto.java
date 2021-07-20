package nl.tsai.airosearch.airport;

import lombok.Getter;
import nl.tsai.airosearch.runway.RunwayDto;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AirportDto {

    private final Integer id;
    private final String identifier;
    private final String type;
    private final String name;
    private final String region;
    private final String municipality;
    private final Boolean scheduledService;
    private final String homeLink;
    private final String wikipediaLink;
    private final String keywords;
    private final Set<RunwayDto> runways;

    public AirportDto(Airport airport) {
        this.id = airport.getId();
        this.identifier = airport.getIdentifier();
        this.type = airport.getType();
        this.name = airport.getName();
        this.region = airport.getRegion();
        this.municipality = airport.getMunicipality();
        this.scheduledService = airport.getScheduledService();
        this.homeLink = airport.getHomeLink();
        this.wikipediaLink = airport.getWikipediaLink();
        this.keywords = airport.getKeywords();
        this.runways = airport.getRunways().stream().map(RunwayDto::new).collect(Collectors.toSet());
    }
}