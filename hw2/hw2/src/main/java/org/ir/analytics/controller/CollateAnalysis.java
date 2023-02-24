package org.ir.analytics.controller;

import org.ir.crawling.CrawlerController;

import java.nio.file.Files;
import java.nio.file.Path;

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

        analyzeStatic.analyze(null, REPORT);
        analyzeFetch.analyze(FETCH_CSV, REPORT);
        analyzeOutgoingUrls.analyze(URLS_CSV, REPORT);
        analyzeStatusCode.analyze(FETCH_CSV, REPORT);
        analyzeFileSize.analyze(VISIT_CSV, REPORT);
    }

    private static void performInitialCleanUpAndCreateNewReportFile() {
        try {
            Files.deleteIfExists(REPORT);
            Files.createFile(REPORT);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
}
