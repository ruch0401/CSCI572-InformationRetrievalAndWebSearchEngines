package org.ir.analytics.controller;

import org.ir.analytics.model.Static;
import org.ir.cli.Props;

import java.net.URL;
import java.nio.file.Path;

public class AnalyzeStatic implements Analysis {
    private final static Static aStatic = new Static();
    static final Props props = Props.getInstance();


    @Override
    public String analyze(Path filepath, Path outputPath) {
        aStatic.setName(props.getName());
        aStatic.setUscId(props.getUscId());
        aStatic.setNewsSite(props.getSEED_URL_HTTPS());
        aStatic.setNumberOfThreads(String.valueOf(props.NUMBER_OF_CRAWLERS));
        return aStatic.toString();
    }

}
