package org.ir.analytics.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.ir.common.HttpCode;
import org.ir.crawling.model.enums.StatHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AnalyzeStatusCode implements Analysis {

    private static final Logger logger = LoggerFactory.getLogger(AnalyzeStatusCode.class);
    @Override
    public String analyze(Path filepath, Path outputPath) {
        System.out.println(String.format("Starting analyzing outgoing status code data", filepath.getFileName()));
        try (Reader reader = Files.newBufferedReader(filepath);) {
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(StatHeader.URL.value, StatHeader.STATUS_CODE.value)
                    .setSkipHeaderRecord(true)
                    .build();

            List<CSVRecord> records = csvFormat.parse(reader).getRecords();

            Map<Integer, Integer> statusCodeAnalysisMap = new TreeMap<>();
            for (final CSVRecord record : records) {
                String statusCode = record.get(StatHeader.STATUS_CODE.value);
                int statusCodeInt = Integer.parseInt(statusCode);
                statusCodeAnalysisMap.put(statusCodeInt, statusCodeAnalysisMap.getOrDefault(statusCodeInt, 0) + 1);
            }

            return constructDynamicOutputString(statusCodeAnalysisMap);
        } catch (Exception e) {
            logger.error("Error while analyzing the fetch file: " + e.getMessage());
        }
        return String.format("%s did not return any output string", this.getClass().getName());
    }

    private String constructDynamicOutputString(Map<Integer, Integer> statusCodeAnalysisMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("Status Codes:\n");
        sb.append("=============\n");
        statusCodeAnalysisMap.forEach((key, value) -> {
            sb.append(String.format("%s %s: %s\n", key, HttpCode.getDescriptionForCode(key), value));
        });
        sb.append("\n"); // this is added to make sure that proper spacing is maintained in the output file
        return sb.toString();
    }
}
