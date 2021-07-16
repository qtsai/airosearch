package nl.tsai.airosearch.csv;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

public interface CSVRecordProcessor {
    void processAll(final List<CSVRecord> records);
}
