package org.ir.analytics.controller;

import org.apache.commons.io.FileUtils;
import org.ir.analytics.model.Static;
import org.ir.crawling.CrawlerController;

import java.nio.file.Path;

public class AnalyzeStatic implements Analysis {

    private final static Static aStatic = new Static();

    @Override
    public void analyze(Path filepath, Path outputPath) {
        aStatic.setName("Ruchit Bhardwaj");
        aStatic.setUscId("1111417799");
        aStatic.setNewsSite("latimes.com");
        aStatic.setNumberOfThreads(String.valueOf(CrawlerController.NUMBER_OF_CRAWLERS));
        outputAnalysisToFile(outputPath, aStatic.toString());
    }

}
