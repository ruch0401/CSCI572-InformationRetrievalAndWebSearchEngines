package org.ir.analytics.controller;

import org.ir.crawling.CrawlerController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CollateAnalysis {
    private static final Path REPORT = Path.of(CrawlerController.OUTPUT_DIR, "CrawlReport_latimes.txt");
    private static final Path FETCH_CSV = Path.of(CrawlerController.OUTPUT_DIR, "fetch_latimes.csv");
    private static final Path URLS_CSV = Path.of(CrawlerController.OUTPUT_DIR, "urls_latimes.csv");
    private static final Path VISIT_CSV = Path.of(CrawlerController.OUTPUT_DIR, "visit_latimes.csv");
    public static void main(String[] args) {

        performInitialCleanUpAndCreateNewReportFile();

        AnalyzeStatic analyzeStatic = new AnalyzeStatic();
        AnalyzeFetch analyzeFetch = new AnalyzeFetch();
        AnalyzeOutgoingUrls analyzeOutgoingUrls = new AnalyzeOutgoingUrls();
        AnalyzeStatusCode analyzeStatusCode = new AnalyzeStatusCode();
        AnalyzeFileSize analyzeFileSize = new AnalyzeFileSize();

        final String staticData = analyzeStatic.analyze(null, REPORT);
        final String fetchData = analyzeFetch.analyze(FETCH_CSV, REPORT);
        final String fileSizeData = analyzeFileSize.analyze(VISIT_CSV, REPORT);
        final String outgoingUrlData = analyzeOutgoingUrls.analyze(URLS_CSV, REPORT); // TODO: confirm that #total urls extracted is the sum of #outgoing links; #total urls extracted is the sum of all values in column 3 of visit.csv
        final String statusCodeData = analyzeStatusCode.analyze(FETCH_CSV, REPORT);

        StringBuilder sb = new StringBuilder();
        sb.append(staticData);
        sb.append(fetchData);
        sb.append(outgoingUrlData);
        sb.append(statusCodeData);
        sb.append(fileSizeData);
        CollateAnalysis.outputAnalysisToFile(REPORT, sb.toString());
    }

    private static void performInitialCleanUpAndCreateNewReportFile() {
        try {
            Files.deleteIfExists(REPORT);
            Files.createFile(REPORT);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    private static void outputAnalysisToFile(Path filepath, String data) {
        try {
            Files.writeString(filepath, data, StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
}
