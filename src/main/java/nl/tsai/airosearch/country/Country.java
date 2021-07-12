package nl.tsai.airosearch.country;

import nl.tsai.airosearch.airport.Airport;
import nl.tsai.airosearch.persistence.ListToStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "country",
        indexes = {
                @Index(name = "IX_country_name", columnList = "name"),
        })
public class Country implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "continent", nullable = false)
    private Continent continent;
    @Column(name = "wikipedia_link")
    private String wikipediaLink;
    @Column(name = "keywords")
    @Convert(converter = ListToStringConverter.class)
    private List<String> keywords = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    Set<Airport> airports = new HashSet<>();

    protected Country() {
        // Required by JPA
    }

    public Country(Integer id, String code, String name, Continent continent, String wikipediaLink, List<String> keywords) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.wikipediaLink = wikipediaLink;
        this.keywords = keywords;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Continent getContinent() {
        return continent;
    }

    public String getWikipediaLink() {
        return wikipediaLink;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public Set<Airport> getAirports() {
        return airports;
    }
}
