package org.ir;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Controller {
    private static final String ROOT = "../data/crawl/root";
    private static final int NUMBER_OF_CRAWLERS = 7;
    public static final String SEED_URL = "https://www.latimes.com/";
    public static final int MAX_DEPTH = 16;
    public static final int MAX_PAGE_COUNT = 20000;
    public static final int POLITENESS_DELAY_IN_MILLIS = 1000;

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public static void main(String[] args) throws Exception {

        Path path = Path.of(ROOT);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(ROOT);
        config.setMaxDepthOfCrawling(MAX_DEPTH);
        config.setMaxPagesToFetch(MAX_PAGE_COUNT);
        config.setPolitenessDelay(POLITENESS_DELAY_IN_MILLIS);

        // Instantiate the controller for this crawl.
        final var pageFetcher = new PageFetcher(config);
        final var robotstxtConfig = new RobotstxtConfig();
        final var robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        final var controller = new CrawlController(config, pageFetcher, robotstxtServer);

        // For each crawl, you need to add some seed urls. These are the first
        // URLs that are fetched and then the crawler starts following links
        // which are found in these pages
        controller.addSeed(SEED_URL);

        // The factory which creates instances of crawlers.
        CrawlController.WebCrawlerFactory<AlphaCrawler> factory = AlphaCrawler::new;

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        controller.startNonBlocking(factory, NUMBER_OF_CRAWLERS);

        List<Object> crawlersLocalData = controller.getCrawlersLocalData();
        long totalLinks = 0;
        long totalTextSize = 0;
        int totalProcessedPages = 0;

        logger.info("Aggregated Statistics:");
        logger.info("\tProcessed Pages: {}", totalProcessedPages);
        logger.info("\tTotal Links found: {}", totalLinks);
        logger.info("\tTotal Text Size: {}", totalTextSize);

        // Wait for 10 seconds
        Thread.sleep(5 * 1000);

        // Send the shutdown request and then wait for finishing
        controller.shutdown();
        controller.waitUntilFinish();
    }
}