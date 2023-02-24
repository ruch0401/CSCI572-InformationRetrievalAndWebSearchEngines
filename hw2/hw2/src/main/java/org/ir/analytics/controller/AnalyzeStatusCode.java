package org.ir.analytics.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.ir.crawling.model.StatHeader;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzeStatusCode implements Analysis {
    @Override
    public void analyze(Path filepath, Path outputPath) {
        try (Reader reader = Files.newBufferedReader(filepath);) {
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(StatHeader.URL.value, StatHeader.STATUS_CODE.value)
                    .setSkipHeaderRecord(true)
                    .build();

            List<CSVRecord> records = csvFormat.parse(reader).getRecords();

            Map<Integer, Integer> statusCodeAnalysisMap = new HashMap<>();
            for (final CSVRecord record: records) {
                String statusCode = record.get(StatHeader.STATUS_CODE.value);
                int statusCodeInt = Integer.parseInt(statusCode);
                statusCodeAnalysisMap.put(statusCodeInt, statusCodeAnalysisMap.getOrDefault(statusCodeInt, 0) + 1);
            }

            String outputString = constructDynamicOutputString(statusCodeAnalysisMap);
            outputAnalysisToFile(outputPath, outputString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String constructDynamicOutputString(Map<Integer, Integer> statusCodeAnalysisMap) {
        // TODO: Add 200 OK, 401 Unauthorized, messages like these
        StringBuilder sb = new StringBuilder();
        sb.append("Status Codes:\n");
        sb.append("=============\n");
        statusCodeAnalysisMap.forEach((key, value) -> {
            sb.append(String.format("%s: %s\n", key, value));
        });
        sb.append("\n"); // this is added to make sure that proper spacing is maintained in the output file
        return sb.toString();
    }
}
