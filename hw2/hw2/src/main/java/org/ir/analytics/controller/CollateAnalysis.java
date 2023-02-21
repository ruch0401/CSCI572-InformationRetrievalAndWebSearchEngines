package org.ir.analytics.controller;

import org.ir.crawling.CrawlerController;

import java.nio.file.Files;
import java.nio.file.Path;

public class CollateAnalysis {
    private static final Path REPORT = Path.of(CrawlerController.OUTPUT_DIR, "CrawlReport_latimes.txt");
    private static final Path FETCH_CSV = Path.of(CrawlerController.OUTPUT_DIR, "fetch_latimes.csv");
    public static void main(String[] args) {

        performInitialCleanUpAndCreateNewReportFile();

        AnalyzeStatic analyzeStatic = new AnalyzeStatic();
        AnalyzeFetch analyzeFetch = new AnalyzeFetch();

        analyzeStatic.analyze(FETCH_CSV, REPORT);
        analyzeFetch.analyze(FETCH_CSV, REPORT);


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
