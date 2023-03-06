package org.ir.cli;

import com.beust.jcommander.Parameter;

import java.net.URL;
import java.nio.file.Path;

/**
 * This is a singleton class that holds the command line arguments.
 */
public class Props {

    private static Props props;

    // Private constructor to prevent instantiation from outside
    private Props() {}

    // Public static method to get the singleton instance
    public static Props getInstance() {
        if (props == null) {
            props = new Props();
        }
        return props;
    }

    @Parameter(names = {"--seed-url-https"}, description = "The seed url to start crawling from (https)\nDefault: https://www.latimes.com/", required = true)
    public String SEED_URL_HTTPS = "https://www.latimes.com/";

    public String SEED_URL_HTTP = getHttpSeedUrl();

    private String getHttpSeedUrl() {
        return SEED_URL_HTTPS.replace("https://", "http://");
    }

    @Parameter(names = {"--root"}, description = "This is the crawled storage folder. This is useful when you want to resume a crawl from a previous crawl.\nDefault: data/crawl/root")
    public String ROOT = "../data/crawl/root";

    @Parameter(names = {"--output-dir"}, description = "This is the output directory where the crawl outputs are stored.\nDefault: output")
    public String OUTPUT_DIR = "output";

    @Parameter(names = {"--number-of-crawlers"}, description = "The number of crawlers to be used. This controls the internal thread count\nDefault: 30")
    public int NUMBER_OF_CRAWLERS = 30;

    @Parameter(names = {"--max-depth"}, description = "The maximum depth of crawling. This is useful when you want to limit the crawl to a certain depth.\nDefault: 16")
    public int MAX_DEPTH = 16;

    @Parameter(names = {"--max-page-count"}, description = "The maximum number of pages to be crawled. This is useful when you want to limit the crawl to a certain number of pages.\nDefault: 20")
    public int MAX_PAGE_COUNT = 20;

    @Parameter(names = {"--politeness-delay-in-millis"}, description = "Controls the delay with which crawler4j crawls subsequent urls.\nDefault: 1")
    public int POLITENESS_DELAY_IN_MILLIS = 1;
    
    public Path ROOT_PATH = Path.of(ROOT);



}
