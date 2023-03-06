package org.ir.analytics.controller;

import org.ir.analytics.model.Static;
import org.ir.cli.Props;
import org.ir.crawling.controller.CrawlerController;

import java.nio.file.Path;

public class AnalyzeStatic implements Analysis {

    private final static Static aStatic = new Static();
    static final Props props = Props.getInstance();


    @Override
    public String analyze(Path filepath, Path outputPath) {
        aStatic.setName("Ruchit Bhardwaj");
        aStatic.setUscId("1111417799");
        aStatic.setNewsSite("latimes.com");
        aStatic.setNumberOfThreads(String.valueOf(props.NUMBER_OF_CRAWLERS));
        return aStatic.toString();
    }

}
