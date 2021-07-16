package nl.tsai.airosearch.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.tsai.airosearch.airport.Airport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country",
        indexes = {
                @Index(name = "IX_country_name", columnList = "name"),
        })
@EqualsAndHashCode(exclude = {"airports"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
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
    @Column(name = "keywords", columnDefinition = "text")
    private String keywords;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    Set<Airport> airports = new HashSet<>();

}
