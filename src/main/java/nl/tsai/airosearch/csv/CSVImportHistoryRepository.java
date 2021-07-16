package nl.tsai.airosearch.csv;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CSVImportHistoryRepository extends CrudRepository<CSVImport, Integer> {

    boolean existsByPath(String path);

}
