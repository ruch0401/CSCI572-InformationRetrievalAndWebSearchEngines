package org.ir.crawling.controller;

import com.beust.jcommander.JCommander;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.ir.analytics.controller.CollateAnalysis;
import org.ir.cli.Props;
import org.ir.crawling.crawler.AlphaCrawler;
import org.ir.crawling.model.FetchCrawlStat;
import org.ir.crawling.model.UrlCrawlStat;
import org.ir.crawling.model.VisitCrawlStat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrawlerController {
    static final Props props = Props.getInstance();
    static final Path FETCH_LATIMES_CSV_OP = Path.of(props.getOUTPUT_DIR(), "fetch_latimes.csv");
    static final Path VISIT_LATIMES_CSV_OP = Path.of(props.getOUTPUT_DIR(), "visit_latimes.csv");
    static final Path URLS_LATIMES_CSV_OP = Path.of(props.getOUTPUT_DIR(), "urls_latimes.csv");

    static {
        try {
            initBookKeepAndOutputFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initBookKeepAndOutputFiles() throws IOException {

        System.out.println("Initializing output files and directories");

        if (Files.notExists(props.ROOT_PATH)) {
            Files.createDirectories(props.ROOT_PATH);
        }

        // ensuring that the output files do not exist previously. if they do, deleting and creating a new ones.
        Files.deleteIfExists(FETCH_LATIMES_CSV_OP);
        Files.deleteIfExists(VISIT_LATIMES_CSV_OP);
        Files.deleteIfExists(URLS_LATIMES_CSV_OP);

        Files.createDirectories(FETCH_LATIMES_CSV_OP.getParent());
        Files.createDirectories(VISIT_LATIMES_CSV_OP.getParent());
        Files.createDirectories(URLS_LATIMES_CSV_OP.getParent());

        Files.createFile(FETCH_LATIMES_CSV_OP);
        Files.createFile(VISIT_LATIMES_CSV_OP);
        Files.createFile(URLS_LATIMES_CSV_OP);
    }

    public static void main(String[] args) throws Exception {

        if (parseCommandOrExit(args)) return;
        CrawlConfig config = new CrawlConfig();
        configureCrawler(config);
        final var controller = instantiateCrawlerForThisCrawl(config);

        System.out.println(MessageFormat.format("Setting seed as {0}", props.getSEED_URL_HTTPS()));
        controller.addSeed(props.getSEED_URL_HTTPS());
        instantiateAndStartCrawling(controller);
        writeCrawlOutputsToFile();
        deleteCrawlStorageFolder();
    }

    private static void deleteCrawlStorageFolder() {
        try {
            Files.walkFileTree(props.ROOT_PATH, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean parseCommandOrExit(String[] args) {
        System.out.println("Attempting to parse command line arguments");
        if (props.isHelp() || Arrays.asList(args).contains("--help")) {
            JCommander.newBuilder()
                    .addObject(props)
                    .build()
                    .usage();
            return true;
        }

        JCommander.newBuilder()
                .addObject(props)
                .build().
                parse(args);

        System.out.println("Successfully parsed command line arguments as follows:");
        System.out.println(props.toString());
        return false;

    }

    private static void configureCrawler(CrawlConfig config) {
        System.out.println(String.format("Configuring crawler to parse %s", props.getSEED_URL_HTTPS()));
        config.setCrawlStorageFolder(props.ROOT_PATH.toString());
        config.setMaxDepthOfCrawling(props.MAX_DEPTH);
        config.setMaxPagesToFetch(props.MAX_PAGE_COUNT);
        config.setPolitenessDelay(props.POLITENESS_DELAY_IN_MILLIS);
        config.setIncludeBinaryContentInCrawling(true);
    }

    private static CrawlController instantiateCrawlerForThisCrawl(CrawlConfig config) throws Exception {
        System.out.println("Instantiating crawler for this crawl");
        // Instantiate the controller for this crawl.
        final var pageFetcher = new PageFetcher(config);
        final var robotstxtConfig = new RobotstxtConfig();
        final var robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        return new CrawlController(config, pageFetcher, robotstxtServer);
    }

    private static void instantiateAndStartCrawling(CrawlController controller) {
        System.out.println("Beginning the crawl. This is a blocking operation");
        // instantiate and start crawling
        List<FetchCrawlStat> fetchCrawlStats = new ArrayList<>();
        List<VisitCrawlStat> visitCrawlStats = new ArrayList<>();
        List<UrlCrawlStat> urlCrawlStats = new ArrayList<>();
        CrawlController.WebCrawlerFactory<AlphaCrawler> factory = () -> new AlphaCrawler(fetchCrawlStats, visitCrawlStats, urlCrawlStats);

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        controller.start(factory, props.NUMBER_OF_CRAWLERS);
        System.out.println(String.format("\nCompleted crawling %d pages", AlphaCrawler.getFetchedUrlCount()));
    }

    private static void writeCrawlOutputsToFile() throws IOException {
        FetchWrite fetchWrite = new FetchWrite();
        fetchWrite.write(AlphaCrawler.getFetchStats());

        VisitWrite visitWrite = new VisitWrite();
        visitWrite.write(AlphaCrawler.getVisitStats());

        UrlWrite urlWrite = new UrlWrite();
        urlWrite.write(AlphaCrawler.getUrlStats());

        System.out.println("\nCompleted writing crawl outputs to files. Now generating analytics");
        CollateAnalysis.main(new String[]{});
    }

}