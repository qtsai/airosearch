package nl.tsai.airosearch.country;

import lombok.Getter;

@Getter
public class CountryDto {

    private final Integer id;
    private final String code;
    private final String name;
    private final Continent continent;
    private final String wikipediaLink;
    private final String keywords;
    private final Integer numberOfAirports;

    public CountryDto(Country country) {
        this.id = country.getId();
        this.code = country.getCode();
        this.name = country.getName();
        this.continent = country.getContinent();
        this.wikipediaLink = country.getWikipediaLink();
        this.keywords = country.getKeywords();
        this.numberOfAirports = country.getAirports().size();
    }

}
