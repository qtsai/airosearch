package nl.tsai.airosearch.airport.runway;

import nl.tsai.airosearch.airport.Airport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "runway")
public class Runway {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Airport.class)
    @JoinColumn(name = "airport_ref", referencedColumnName = "id")
    private Airport airportReference;
    @Column(name = "airport_ident")
    private String airportIdentifier;
    @Column(name = "length_ft")
    private Integer length;
    @Column(name = "width_ft")
    private String width;
    @Column(name = "surface")
    private String surface;
    @Column(name = "lighted", nullable = false)
    private boolean lighted;
    @Column(name = "closed", nullable = false)
    private boolean closed;

    @Column(name = "le_ident")
    private String lengthIdentifier;
    @Column(name = "le_latitude_deg")
    private Double lengthLatitude;
    @Column(name = "le_longitude_deg")
    private Double lengthLongitude;
    @Column(name = "le_elevation_ft")
    private Integer lengthElevation;
    @Column(name = "le_heading_degT")
    private Float lengthHeading;
    @Column(name = "le_displaced_threshold_ft")
    private Integer lengthDisplacedThreshold;

    @Column(name = "he_ident")
    private String heightIdentifier;
    @Column(name = "he_latitude_deg")
    private Double heightLatitude;
    @Column(name = "he_longitude_deg")
    private Double heightLongitude;
    @Column(name = "he_elevation_ft")
    private Integer heightElevation;
    @Column(name = "he_heading_degT")
    private Float heightHeading;
    @Column(name = "he_displaced_threshold_ft")
    private Integer heightDisplacedThreshold;

    protected Runway() {
        // Required by JPA
    }

    public Integer getId() {
        return id;
    }

    public Airport getAirportReference() {
        return airportReference;
    }

    public String getAirportIdentifier() {
        return airportIdentifier;
    }

    public Integer getLength() {
        return length;
    }

    public String getWidth() {
        return width;
    }

    public String getSurface() {
        return surface;
    }

    public boolean isLighted() {
        return lighted;
    }

    public boolean isClosed() {
        return closed;
    }

    public String getLengthIdentifier() {
        return lengthIdentifier;
    }

    public Double getLengthLatitude() {
        return lengthLatitude;
    }

    public Double getLengthLongitude() {
        return lengthLongitude;
    }

    public Integer getLengthElevation() {
        return lengthElevation;
    }

    public Float getLengthHeading() {
        return lengthHeading;
    }

    public Integer getLengthDisplacedThreshold() {
        return lengthDisplacedThreshold;
    }

    public String getHeightIdentifier() {
        return heightIdentifier;
    }

    public Double getHeightLatitude() {
        return heightLatitude;
    }

    public Double getHeightLongitude() {
        return heightLongitude;
    }

    public Integer getHeightElevation() {
        return heightElevation;
    }

    public Float getHeightHeading() {
        return heightHeading;
    }

    public Integer getHeightDisplacedThreshold() {
        return heightDisplacedThreshold;
    }
}
