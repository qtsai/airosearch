package nl.tsai.airosearch.runway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.tsai.airosearch.airport.Airport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "runway")
@EqualsAndHashCode(exclude = {"airport"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Runway implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airport_id")
    private Airport airport;

    @Column(name = "length_ft")
    private Integer length;
    @Column(name = "width_ft")
    private Integer width;
    @Column(name = "surface")
    private String surface;
    @Column(name = "lighted")
    private Boolean lighted;
    @Column(name = "closed")
    private Boolean closed;

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

}
