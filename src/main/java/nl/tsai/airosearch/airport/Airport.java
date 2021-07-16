package nl.tsai.airosearch.airport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.tsai.airosearch.country.Continent;
import nl.tsai.airosearch.country.Country;
import nl.tsai.airosearch.runway.Runway;

import javax.persistence.Column;
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
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "airport",
        indexes = {
                @Index(name = "IX_airport_identifier", columnList = "ident"),
                @Index(name = "IX_airport_name", columnList = "name"),
                @Index(name = "IX_airport_country", columnList = "iso_country")
        })
@EqualsAndHashCode(exclude = {"country", "runways"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Airport implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "ident", unique = true)
    private String identifier;
    @Column(name = "type", nullable = false)
    private String type;
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
    @JoinColumn(name = "iso_country", referencedColumnName = "code")
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
    @Column(name = "keywords", columnDefinition = "text")
    private String keywords;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airport")
    private final Set<Runway> runways = new HashSet<>();

}
