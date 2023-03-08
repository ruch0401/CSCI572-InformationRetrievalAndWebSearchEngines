package org.ir.cli;

import com.beust.jcommander.Parameter;
import lombok.Getter;

import java.nio.file.Path;


/**
 * This is a singleton class that holds the command line arguments.
 */
public class Props {

    private static Props props;

    // Private constructor to prevent instantiation from outside
    private Props() {
    }

    // Public static method to get the singleton instance
    public static Props getInstance() {
        if (props == null) {
            props = new Props();
        }
        return props;
    }

    @Parameter(names = {"--help", "-h"}, help = false, description = "Displays all configurable options available.")
    @Getter
    private boolean help = false;

    @Parameter(names = {"--name"}, description = "Name using which the report will be created")
    @Getter
    private String name = "Ruchit Bhardwaj";

    @Parameter(names = {"--usc-id"}, description = "USC ID of the name using which the report will be created")
    @Getter
    private String uscId = "1235678910";

    @Parameter(names = {"--seed-url-https"}, description = "The seed url to start crawling from (https)", required = true)
    @Getter
    private String SEED_URL_HTTPS = "https://www.latimes.com/";

    @Getter
    public String SEED_URL_HTTP = getHttpSeedUrl();

    private String getHttpSeedUrl() {
        return SEED_URL_HTTPS.replace("https://", "http://");
    }

    @Parameter(names = {"--root"}, description = "This is the crawled storage folder. This is useful when you want to resume a crawl from a previous crawl.")
    @Getter
    public String ROOT = "../data/crawl/root";

    @Parameter(names = {"--output-dir"}, description = "This is the output directory where the crawl outputs are stored.")
    @Getter
    private String OUTPUT_DIR = "output";

    @Parameter(names = {"--output-fetch"}, description = "This is the output filename where the crawl's fetch outputs are stored.", required = true)
    @Getter
    private String OUTPUT_FETCH = "fetch.csv";

    @Parameter(names = {"--output-visit"}, description = "This is the output filename where the crawl's visit outputs are stored.", required = true)
    @Getter
    private String OUTPUT_VISIT = "visit.csv";

    @Parameter(names = {"--output-url"}, description = "This is the output filename where the crawl's url outputs are stored.", required = true)
    @Getter
    private String OUTPUT_URL = "urls.csv";

    @Parameter(names = {"--output-report"}, description = "This is the output filename with which the analytics report will be generated.", required = true)
    @Getter
    private String OUTPUT_REPORT = "CrawlReport.txt";

    @Parameter(names = {"--number-of-crawlers"}, description = "The number of crawlers to be used. This controls the internal thread count")
    @Getter
    public int NUMBER_OF_CRAWLERS = 30;

    @Parameter(names = {"--max-depth"}, description = "The maximum depth of crawling. This is useful when you want to limit the crawl to a certain depth.")
    public int MAX_DEPTH = 16;

    @Parameter(names = {"--max-page-count"}, description = "The maximum number of pages to be crawled. This is useful when you want to limit the crawl to a certain number of pages.")
    @Getter
    public int MAX_PAGE_COUNT = 20;

    @Parameter(names = {"--politeness-delay-in-millis"}, description = "Controls the delay with which crawler4j crawls subsequent urls.")
    @Getter
    public int POLITENESS_DELAY_IN_MILLIS = 1;

    @Getter
    public Path ROOT_PATH = Path.of(ROOT);

    @Override
    public String toString() {
        return String.format("\nHELP: %s\nNAME: '%s'\nUSCID: '%s'\nSEED_URL_HTTPS: '%s'\nSEED_URL_HTTP: '%s'\nROOT: '%s'\nOUTPUT_DIR: '%s'\nOUTPUT_FETCH: '%s'\nOUTPUT_VISIT: '%s'\nOUTPUT_URL: '%s'\nOUTPUT_REPORT: '%s'\nNUMBER_OF_CRAWLERS: %d\nMAX_DEPTH: %d\nMAX_PAGE_COUNT: %d\nPOLITENESS_DELAY_IN_MILLIS: %d\nROOT_PATH: %s\n",
                help, name, uscId, SEED_URL_HTTPS, SEED_URL_HTTP, ROOT, OUTPUT_DIR, OUTPUT_FETCH, OUTPUT_VISIT, OUTPUT_URL, OUTPUT_REPORT, NUMBER_OF_CRAWLERS, MAX_DEPTH, MAX_PAGE_COUNT, POLITENESS_DELAY_IN_MILLIS, ROOT_PATH);
    }
}
