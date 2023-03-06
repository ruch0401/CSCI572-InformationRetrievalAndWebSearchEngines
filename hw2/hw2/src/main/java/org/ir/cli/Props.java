package org.ir.cli;

import org.ir.crawling.controller.CrawlerController;

import java.nio.file.Path;

public class Props {
    public static final String SEED_URL_HTTPS = "https://www.latimes.com/";
    public static final String SEED_URL_HTTP = "http://www.latimes.com/";

    public static final String ROOT = "../data/crawl/root";
    public static final String OUTPUT_DIR = "output";
    public static final int NUMBER_OF_CRAWLERS = 30;
    public static final String SEED_URL = "https://www.latimes.com/";
    public static final int MAX_DEPTH = 16;
    public static final int MAX_PAGE_COUNT = 20;
    public static final int POLITENESS_DELAY_IN_MILLIS = 1;
    public static final Path ROOT_PATH = Path.of(ROOT);


}
