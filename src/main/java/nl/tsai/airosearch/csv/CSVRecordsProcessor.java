package nl.tsai.airosearch.csv;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

public abstract class CSVRecordsProcessor<T> implements CSVRecordProcessor {

    private static final int BATCH_SIZE = 1000;

    @Override
    public void processAll(final List<CSVRecord> records) {
        List<T> entities = new ArrayList<>();
        for (CSVRecord record : records) {
            entities.add(process(record));
            if (entities.size() >= BATCH_SIZE) {
                save(entities);
                entities = new ArrayList<>();
            }
        }
        save(entities);
    }

    protected abstract T process(final CSVRecord record);

    protected abstract void save(List<T> entries);

}
