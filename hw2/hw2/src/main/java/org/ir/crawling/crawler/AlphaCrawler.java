package org.ir.crawling.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.ir.cli.Props;
import org.ir.crawling.model.*;
import org.ir.crawling.model.enums.Indicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Pattern;

public class AlphaCrawler extends WebCrawler {
    static final Props props = Props.getInstance();
    private static List<FetchCrawlStat> fetchCrawlStats;
    private static List<VisitCrawlStat> visitCrawlStats;
    private static List<UrlCrawlStat> urlCrawlStats;
    private static final Pattern ACCEPTED_DOC_TYPES = Pattern.compile(".*(\\.(html?|php|pdf|docx?))$");
    private static final Pattern ACCEPTED_IMAGE_TYPES = Pattern.compile(".*(\\.(jpe?g|ico|png|bmp|svg|gif|webp|tiff))$");
    private static final Logger logger = LoggerFactory.getLogger(AlphaCrawler.class);

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
        String path = url.getPath().toLowerCase();
        return href.startsWith(props.SEED_URL_HTTPS) || href.startsWith(props.SEED_URL_HTTP);
    }

    /**
     * @param webUrl            WebUrl containing the statusCode
     * @param statusCode        Html Status Code number
     * @param statusDescription Html Status Code description
     * We also need to log urls even if the crawler gets a status code other than the 2XX family.
     */
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
            logFetchData(page);
            logVisitData(page);
            logOutgoingUrlData(page);
            System.out.println(String.format("Parsed URL... %d", page.getStatusCode()));
        } catch (Exception e) {
            logger.error(MessageFormat.format("{0}\nCaused by: {1}", e.getMessage(), e.getCause()));
        }
    }

    private boolean isValidContentType(Page page) {
        String contentType = page.getContentType().split(";")[0].toLowerCase();
        return contentType.contains("image")
                || contentType.contains("html")
                || contentType.contains("pdf")
                || contentType.contains("doc")
                || contentType.contains("msword")
                || contentType.contains("document");
    }

    private void logFetchData(Page page) {
        String url = page.getWebURL().getURL();
        int statusCode = page.getStatusCode();
        fetchCrawlStat = new FetchCrawlStat(url, statusCode);
        fetchCrawlStats.add(fetchCrawlStat);
    }

    private void logVisitData(Page page) {
        if (isValidContentType(page)) {
            String url = page.getWebURL().getURL();
            int statusCode = page.getStatusCode();
            ParseData parseData = page.getParseData();
            long downloadSize = page.getContentData().length;
            String contentType = page.getContentType().split(";")[0];
            int numberOfOutlinks = parseData.getOutgoingUrls().size();
            if (statusCode >= 200 && statusCode <= 299) {
                VisitCrawlStat visitCrawlStat = new VisitCrawlStat(url, downloadSize, numberOfOutlinks, contentType);
                visitCrawlStats.add(visitCrawlStat);
            }
        }
    }

    private void logOutgoingUrlData(Page page) throws MalformedURLException {
        String url = page.getWebURL().getURL();
        ParseData parseData = page.getParseData();
        URL originUrl = new URL(url);
        for (WebURL webURL : parseData.getOutgoingUrls()) {
            URL outlinkUrl = new URL(webURL.getURL());
            String indicator = originUrl.getHost().equals(outlinkUrl.getHost()) ? Indicator.OK.value : Indicator.N_OK.value;
            UrlCrawlStat urlCrawlStat = new UrlCrawlStat(webURL.getURL(), indicator);
            urlCrawlStats.add(urlCrawlStat);
        }
    }
}