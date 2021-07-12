package nl.tsai.airosearch.airport;

import nl.tsai.airosearch.airport.runway.Runway;
import nl.tsai.airosearch.country.Continent;
import nl.tsai.airosearch.country.Country;
import nl.tsai.airosearch.persistence.ListToStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "airport",
        indexes = {
                @Index(name = "IX_airport_identifier", columnList = "ident"),
                @Index(name = "IX_airport_name", columnList = "name"),
                @Index(name = "IX_airport_country", columnList = "iso_country")
        })
public class Airport {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "ident", unique = true)
    private String identifier;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AirportType type;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "latitude_deg")
    private Double latitude;
    @Column(name = "longitude_deg")
    private Double longitude;
    @Column(name = "elevation_ft")
    private Integer elevation;
    @Enumerated(EnumType.STRING)
    @Column(name = "continent")
    private Continent continent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "iso_country", nullable = false, referencedColumnName = "code")
    private Country country;

    @Column(name = "iso_region")
    private String region;
    @Column(name = "municipality")
    private String municipality;
    @Column(name = "scheduled_service")
    private Boolean scheduledService;
    @Column(name = "gps_code")
    private String gpsCode;
    @Column(name = "iata_code")
    private String iataCode;
    @Column(name = "local_code")
    private String localCode;
    @Column(name = "home_link")
    private String homeLink;
    @Column(name = "wikipedia_link")
    private String wikipediaLink;
    @Convert(converter = ListToStringConverter.class)
    @Column(name = "keywords")
    private List<String> keywords = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airport")
    private Set<Runway> runways = new HashSet<>();

    protected Airport() {
        // Required by JPA
    }

    public Integer getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public AirportType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getElevation() {
        return elevation;
    }

    public Continent getContinent() {
        return continent;
    }

    public Country getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getMunicipality() {
        return municipality;
    }

    public Boolean getScheduledService() {
        return scheduledService;
    }

    public String getGpsCode() {
        return gpsCode;
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getLocalCode() {
        return localCode;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public String getWikipediaLink() {
        return wikipediaLink;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public Set<Runway> getRunways() {
        return runways;
    }
}
