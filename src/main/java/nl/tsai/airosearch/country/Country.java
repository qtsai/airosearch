package nl.tsai.airosearch.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.tsai.airosearch.airport.Airport;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

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
@Indexed
@AnalyzerDef(name = "customAnalyzer",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class)
        }
)
@EqualsAndHashCode(exclude = {"airports"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Country implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    @Field(analyzer = @Analyzer(definition = "customAnalyzer"))
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Field(termVector = TermVector.YES, analyzer = @Analyzer(definition = "customAnalyzer"))
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
    private final Set<Airport> airports = new HashSet<>();

}
