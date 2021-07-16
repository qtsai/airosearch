package nl.tsai.airosearch.csv;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "csv_import_history")
@EqualsAndHashCode(exclude = {"id", "createDateTime"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CSVImport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private LocalDateTime createDateTime;
    @Column(name = "path")
    private String path;

    public CSVImport(String path) {
        this.path = path;
    }
}
