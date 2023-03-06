package org.ir.analytics.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.ir.analytics.model.Fetch;
import org.ir.crawling.model.enums.StatHeader;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AnalyzeFetch implements Analysis {
    private static final Fetch fetch = new Fetch();

    @Override
    public String analyze(Path inputPath, Path outputPath) {
        try (Reader reader = Files.newBufferedReader(inputPath);
        ) {
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(StatHeader.URL.value, StatHeader.STATUS_CODE.value)
                    .setSkipHeaderRecord(true)
                    .build();

            List<CSVRecord> records = csvFormat.parse(reader).getRecords();
            fetch.setFetchCountAttempted(records.size());

            for (final CSVRecord csvRecord: records) {
                int statusCode = Integer.parseInt(csvRecord.get(StatHeader.STATUS_CODE.value));
                if (statusCode >= 200 && statusCode <= 299) {
                    fetch.setFetchCountSucceeded(fetch.getFetchCountSucceeded() + 1);
                } else {
                    fetch.setFetchCountFailedOrAborted(fetch.getFetchCountFailedOrAborted() + 1);
                }
            }
            return fetch.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("%s did not return any output string", this.getClass().getName());
    }
}
