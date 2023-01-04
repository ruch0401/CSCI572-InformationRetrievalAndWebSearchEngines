package org.ir.crawling;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.HttpStatus;
import org.ir.crawling.model.*;

import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Pattern;

public class AlphaCrawler extends WebCrawler {
    final AlphaCrawlerPojo alphaCrawlerPojo = new AlphaCrawlerPojo(CrawlerController.SEED_URL);
    private static List<FetchCrawlStat> fetchCrawlStats;
    private static List<VisitCrawlStat> visitCrawlStats;
    private static List<UrlCrawlStat> urlCrawlStats;
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|mp3|mp4|zip|gz|json))$");

    private static FetchCrawlStat fetchCrawlStat = null;

    public AlphaCrawler(List<FetchCrawlStat> fetchCrawlStats, List<VisitCrawlStat> visitCrawlStats, List<UrlCrawlStat> urlCrawlStats) {
        AlphaCrawler.fetchCrawlStats = fetchCrawlStats;
        AlphaCrawler.visitCrawlStats = visitCrawlStats;
        AlphaCrawler.urlCrawlStats = urlCrawlStats;
    }

    public static List<FetchCrawlStat> getFetchStats() {
        return fetchCrawlStats;
    }

    public static List<VisitCrawlStat> getVisitStats() {
        return visitCrawlStats;
    }

    public static List<UrlCrawlStat> getUrlStats() {
        return urlCrawlStats;
    }

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();

        // this was needed because for some reason, the crawler was trying to crawl the css files despite the filter
        if (url.getURL().contains(".css")) {
            return false;
        }

        return !FILTERS.matcher(href).matches()
                && href.startsWith(alphaCrawlerPojo.getSeedUrl());
    }

    @Override
    public void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        if (statusCode != 200) {
            fetchCrawlStat = new FetchCrawlStat(webUrl.getURL(), statusCode);
            fetchCrawlStats.add(fetchCrawlStat);
            System.out.println(MessageFormat.format("\nURL: {0} {1}\n", webUrl.getURL(), statusCode));
        }
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        try {
            String url = page.getWebURL().getURL();
            int statusCode = page.getStatusCode();
            long downloadSize = page.getContentData().length;
            String contentType = page.getContentType().split(";")[0];

            ParseData parseData = page.getParseData();
            int numberOfOutlinks = parseData.getOutgoingUrls().size();

            fetchCrawlStat = new FetchCrawlStat(url, statusCode);
            fetchCrawlStats.add(fetchCrawlStat);

            if (statusCode >= 200 && statusCode <= 299) {
                VisitCrawlStat visitCrawlStat = new VisitCrawlStat(url, downloadSize, numberOfOutlinks, contentType);
                visitCrawlStats.add(visitCrawlStat);
            }

            URL originUrl = new URL(url);
            for (WebURL webURL : parseData.getOutgoingUrls()) {
                URL outlinkUrl = new URL(webURL.getURL());
                String indicator = originUrl.getHost().equals(outlinkUrl.getHost()) ? Indicator.OK.value : Indicator.N_OK.value;
                UrlCrawlStat urlCrawlStat = new UrlCrawlStat(webURL.getURL(), indicator);
                urlCrawlStats.add(urlCrawlStat);
            }
            System.out.println(".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}