package org.ir.analytics.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.ir.analytics.model.FileSize;
import org.ir.analytics.model.OutgoingUrl;
import org.ir.crawling.model.StatHeader;
import org.ir.crawling.model.VisitCrawlStat;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyzeFileSize implements Analysis {
    public static final OutgoingUrl outgoingUrl = new OutgoingUrl();
    private final FileSize fileSize = new FileSize();

    @Override
    public String analyze(Path filepath, Path outputPath) {
        try (Reader reader = Files.newBufferedReader(filepath);) {
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(StatHeader.URL.value,
                            StatHeader.SIZE_OF_DOWNLOADED_PAGE.value,
                            StatHeader.NUMBER_OF_OUTLINKS.value,
                            StatHeader.CONTENT_TYPE.value)
                    .setSkipHeaderRecord(true)
                    .build();

            List<CSVRecord> records = csvFormat.parse(reader).getRecords();
            final var castedRecords = records
                    .stream()
                    .map(record -> {
                        String url = record.get(StatHeader.URL.value);
                        String downloadSize = record.get(StatHeader.SIZE_OF_DOWNLOADED_PAGE.value);
                        String outlinks = record.get(StatHeader.NUMBER_OF_OUTLINKS.value);
                        String contentType = record.get(StatHeader.CONTENT_TYPE.value);
                        return new VisitCrawlStat(url, Long.parseLong(downloadSize), Integer.parseInt(outlinks), contentType);
                    }).collect(Collectors.toList());

            // fetching total outgoing links here as this is where the corresponding visit.csv file is being read.
            // this is not a good design approach. defeats the purpose of separation of concerns.
            outgoingUrl.setTotalUrlsExtracted(getTotalOutgoingLinksCount(castedRecords));

            fileSize.setLessThanOneKB(getLessThanOneKBCount(castedRecords));
            fileSize.setBetweenOneAndTenKB(getBetweenOneAndTenKBCount(castedRecords));
            fileSize.setBetweenTenAndHundredKB(getBetweenTenAndHundredKBCount(castedRecords));
            fileSize.setBetweenHundredAndThousandKB(getBetweenHundredAndThousandKBCount(castedRecords));
            fileSize.setMoreThanEqualToThousandKB(getMoreThanEqualToThousandKBCount(castedRecords));

            // Combining two strings. Bad Idea.
            String contentTypeOutput = this.analyzeAndConstructDynamicOutputStringForContentTypeMetric(castedRecords);
            String fileSizeOutput = fileSize.toString();
            return fileSizeOutput + contentTypeOutput;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("%s did not return any output string", this.getClass().getName());
    }

    private long getTotalOutgoingLinksCount(List<VisitCrawlStat> records) {
        return records
                .stream()
                .map(VisitCrawlStat::getNumberOfOutlinks)
                .mapToInt(Integer::intValue).sum();
    }

    private int getLessThanOneKBCount(List<VisitCrawlStat> records) {
        return (int) records
                .stream()
                .filter(record -> record.getDownloadedSize() < 1024)
                .count();
    }

    private int getBetweenOneAndTenKBCount(List<VisitCrawlStat> records) {
        return (int) records
                .stream()
                .filter(record -> record.getDownloadedSize() >= 1024)
                .filter(record -> record.getDownloadedSize() < 10240)
                .count();
    }

    private int getBetweenTenAndHundredKBCount(List<VisitCrawlStat> records) {
        return (int) records
                .stream()
                .filter(record -> record.getDownloadedSize() >= 10240)
                .filter(record -> record.getDownloadedSize() < 102400)
                .count();
    }

    private int getBetweenHundredAndThousandKBCount(List<VisitCrawlStat> records) {
        return (int) records
                .stream()
                .filter(record -> record.getDownloadedSize() >= 102400)
                .filter(record -> record.getDownloadedSize() < 1024000)
                .count();
    }

    private int getMoreThanEqualToThousandKBCount(List<VisitCrawlStat> records) {
        return (int) records
                .stream()
                .filter(record -> record.getDownloadedSize() >= 1024000)
                .count();
    }

    private String analyzeAndConstructDynamicOutputStringForContentTypeMetric(List<VisitCrawlStat> castedRecords) {
        Map<String, Integer> stats = new HashMap<>();
        castedRecords.forEach(record -> {
            stats.put(record.getContentType(), stats.getOrDefault(record.getContentType(), 0) + 1);
        });

        StringBuilder sb = new StringBuilder();
        sb.append("Content Types: \n");
        sb.append("==============\n");
        stats.forEach((key, value) -> {
            sb.append(String.format("%s: %s\n", key, value));
        });
        sb.append("\n"); // this is added to make sure that proper spacing is maintained in the output file
        return sb.toString();
    }
}
