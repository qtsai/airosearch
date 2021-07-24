package nl.tsai.airosearch.csv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class CSVImportHistoryRepositoryTest {

    @Autowired
    private CSVImportHistoryRepository csvImportHistoryRepository;

    @Test
    void existsByPath_whenExists_shouldReturnTrue() {
        csvImportHistoryRepository.save(new CSVImport("test/path"));

        Boolean result = csvImportHistoryRepository.existsByPath("test/path");

        assertThat(result, is(true));
    }

    @Test
    void existsByPath_whenNotExists_shouldReturnFalse() {
        Boolean result = csvImportHistoryRepository.existsByPath("test/path");

        assertThat(result, is(false));
    }

}